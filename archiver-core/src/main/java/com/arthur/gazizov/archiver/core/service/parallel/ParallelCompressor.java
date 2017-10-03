package com.arthur.gazizov.archiver.core.service.parallel;

import com.arthur.gazizov.archiver.core.exception.AlgorithmException;
import com.arthur.gazizov.archiver.core.factory.ArchiverFactory;
import com.arthur.gazizov.archiver.core.service.AbstractCompressor;
import com.arthur.gazizov.archiver.core.service.Compressor;
import com.arthur.gazizov.archiver.core.utils.ByteUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class ParallelCompressor extends AbstractCompressor {
  private final ArchiverFactory factory;
  private final ParallelConfig config;

  public ParallelCompressor(ArchiverFactory factory) {
    this.factory = factory;
    this.config = new ParallelConfig();
  }

  @Override
  protected void init() {
    super.init();
  }

  @Override
  protected byte[] process(byte[] bytes) {
    int blockLength = config.getBatchSize();
    int blocksCount = bytes.length / blockLength + (bytes.length % blockLength == 0 ? 0 : 1);
    ExecutorService executorService = Executors.newCachedThreadPool();
    byte[][] results = new byte[blocksCount][];
    for (int i = 0; i < blocksCount; i++) {
      final int blockId = i;
      final Compressor compressor = factory.loadCompressor();
      executorService.execute(() -> {
        byte[] workArea;
        if (blockId != blocksCount - 1) {
          workArea = new byte[blockLength];
          System.arraycopy(bytes, blockId * blockLength, workArea, 0, blockLength);
        } else {
          int lastBlockLength = bytes.length - (blocksCount - 1) * blockLength;
          workArea = new byte[lastBlockLength];
          System.arraycopy(bytes, blockId * blockLength, workArea, 0, lastBlockLength);
        }
        byte[] localResult = compressor.compress(workArea);
        results[blockId] = localResult;
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
    int parts = results.length;
    int[] partsIndexStart = new int[parts];
    int metaInfoSize = (1 + parts) * 4; //bytes
    int size = metaInfoSize;
    for (int i = 0; i < parts; i++) {
      partsIndexStart[i] = size;
      size += results[i].length;
    }
    final byte[] ret = new byte[size];
    final byte[] partsInfo = ByteUtils.intTo4Bytes(parts);
    System.arraycopy(partsInfo, 0, ret, 0, 4);
    int metaStartPosition = 4;
    for (int i = 0; i < parts; i++) {
      byte[] partInfo = ByteUtils.intTo4Bytes(partsIndexStart[i]);
      System.arraycopy(partInfo, 0, ret, metaStartPosition, 4);
      metaStartPosition += 4;
    }
    int position = metaInfoSize;
    for (int i = 0; i < parts; i++) {
      System.arraycopy(results[i], 0, ret, position, results[i].length);
      position += results[i].length;
    }
    return ret;
  }
}
