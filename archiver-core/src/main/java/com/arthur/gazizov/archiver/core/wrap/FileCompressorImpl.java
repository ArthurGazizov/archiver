package com.arthur.gazizov.archiver.core.wrap;

import com.arthur.gazizov.archiver.core.factory.ArchiverFactory;
import com.arthur.gazizov.archiver.core.service.Compressor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 10.10.17.
 */
public class FileCompressorImpl implements FileCompressor {
  private final ArchiverFactory archiverFactory;
  private final FileWorkerConfig config;

  public FileCompressorImpl(ArchiverFactory archiverFactory, FileWorkerConfig config) {
    this.archiverFactory = archiverFactory;
    this.config = config;
  }

  @Override
  public void compress(String path) {
    final Compressor compressor = archiverFactory.loadCompressor();
    try {
      final byte[] bytes = Files.readAllBytes(new File(path).toPath());
      final byte[] compressed = compressor.compress(bytes);
      FileOutputStream fos = new FileOutputStream(path + config.getTypeMapping().get(archiverFactory.getClass()));
      fos.write(compressed);
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
