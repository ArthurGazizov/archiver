package com.arthur.gazizov.archiver.core.factory.parallel;

import com.arthur.gazizov.archiver.core.factory.ArchiverFactory;
import com.arthur.gazizov.archiver.core.factory.impl.LZWFactory;
import com.arthur.gazizov.archiver.core.service.parallel.ParallelConfig;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 10.10.17.
 */
public class LZWParallelFactory extends AbstractParallelFactory {
  @Override
  protected ArchiverFactory LoadFactory() {
    return new LZWFactory();
  }

  @Override
  protected void Configure(ParallelConfig config) {
    config.setBatchSize(1024 * 1024);
  }
}
