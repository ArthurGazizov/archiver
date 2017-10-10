package com.arthur.gazizov.archiver.core.factory.parallel;

import com.arthur.gazizov.archiver.core.factory.ArchiverFactory;
import com.arthur.gazizov.archiver.core.service.Compressor;
import com.arthur.gazizov.archiver.core.service.Decompressor;
import com.arthur.gazizov.archiver.core.service.parallel.ParallelCompressor;
import com.arthur.gazizov.archiver.core.service.parallel.ParallelConfig;
import com.arthur.gazizov.archiver.core.service.parallel.ParallelDecompressor;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 10.10.17.
 */
public abstract class AbstractParallelFactory implements ArchiverFactory {
  @Override
  public Compressor loadCompressor() {
    ParallelConfig config = new ParallelConfig();
    Configure(config);
    return new ParallelCompressor(LoadFactory(), config);
  }

  @Override
  public Decompressor loadDecompressor() {
    ParallelConfig config = new ParallelConfig();
    Configure(config);
    return new ParallelDecompressor(LoadFactory(), config);
  }

  protected abstract ArchiverFactory LoadFactory();

  protected abstract void Configure(ParallelConfig config);
}
