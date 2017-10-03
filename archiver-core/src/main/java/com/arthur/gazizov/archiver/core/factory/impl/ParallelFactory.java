package com.arthur.gazizov.archiver.core.factory.impl;

import com.arthur.gazizov.archiver.core.factory.ArchiverFactory;
import com.arthur.gazizov.archiver.core.service.Compressor;
import com.arthur.gazizov.archiver.core.service.Decompressor;
import com.arthur.gazizov.archiver.core.service.parallel.ParallelCompressor;
import com.arthur.gazizov.archiver.core.service.parallel.ParallelDecompressor;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class ParallelFactory implements ArchiverFactory {
  private ArchiverFactory archiverFactory;

  public ParallelFactory(ArchiverFactory archiverFactory) {
    this.archiverFactory = archiverFactory;
  }

  @Override
  public Compressor loadCompressor() {
    return new ParallelCompressor(archiverFactory);
  }

  @Override
  public Decompressor loadDecompressor() {
    return new ParallelDecompressor(archiverFactory);
  }
}
