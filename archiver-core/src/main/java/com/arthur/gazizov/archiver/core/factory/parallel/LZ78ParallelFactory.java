package com.arthur.gazizov.archiver.core.factory.parallel;

import com.arthur.gazizov.archiver.core.factory.ArchiverFactory;
import com.arthur.gazizov.archiver.core.factory.impl.LZ78Factory;
import com.arthur.gazizov.archiver.core.service.parallel.ParallelConfig;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 10.10.17.
 */
public class LZ78ParallelFactory extends AbstractParallelFactory {
  @Override
  protected ArchiverFactory LoadFactory() {
    return new LZ78Factory();
  }

  @Override
  protected void Configure(ParallelConfig config) {
    config.setBatchSize(1024 * 1024);
  }
}
