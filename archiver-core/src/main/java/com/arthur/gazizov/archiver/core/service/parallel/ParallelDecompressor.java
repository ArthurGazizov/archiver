package com.arthur.gazizov.archiver.core.service.parallel;

import com.arthur.gazizov.archiver.core.exception.AlgorithmException;
import com.arthur.gazizov.archiver.core.factory.ArchiverFactory;
import com.arthur.gazizov.archiver.core.service.AbstractDecompressor;
import com.arthur.gazizov.archiver.core.service.Decompressor;
import com.arthur.gazizov.archiver.core.utils.ByteUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class ParallelDecompressor extends AbstractDecompressor {
  private ArchiverFactory factory;

  public ParallelDecompressor(ArchiverFactory factory) {
    this.factory = factory;
  }

  @Override
  protected byte[] process(byte[] bytes) {
    int offset = 0;
    int parts = ByteUtils.toInt4(bytes, offset);
    offset += 4;
    int[] startPositions = new int[parts];
    for (int i = 0; i < parts; i++) {
      startPositions[i] = ByteUtils.toInt4(bytes, offset);
      offset += 4;
    }
    ExecutorService executorService = Executors.newCachedThreadPool();
    byte[][] results = new byte[parts][];
    for (int threadId = 0; threadId < parts; threadId++) {
      final int partId = threadId;
      final Decompressor decompressor = factory.loadDecompressor();
      executorService.execute(() -> {
        int len;
        if (partId != parts - 1) {
          len = startPositions[partId + 1] - startPositions[partId];
        } else {
          len = bytes.length - startPositions[partId];
        }
        byte[] workArea = new byte[len];
        System.arraycopy(bytes, startPositions[partId], workArea, 0, len);
        byte[] localResult = decompressor.decompress(workArea);
        results[partId] = localResult;
      });
    }
    executorService.shutdown();
    boolean finished = false;
    try {
      finished = executorService.awaitTermination(10, TimeUnit.MINUTES);
    } catch (InterruptedException e) {
      throw new AlgorithmException();
    }
    if (!finished) {
      throw new AlgorithmException();
    }
    return aggregate(results);
  }

  private byte[] aggregate(byte[][] results) {
    int size = 0;
    for (int i = 0; i < results.length; i++) {
      size += results[i].length;
    }
    final byte[] ret = new byte[size];
    int position = 0;
    for (int i = 0; i < results.length; i++) {
      System.arraycopy(results[i], 0, ret, position, results[i].length);
      position += results[i].length;
    }
    return ret;
  }
}
