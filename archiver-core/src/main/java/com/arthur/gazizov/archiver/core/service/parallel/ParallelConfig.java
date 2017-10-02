package com.arthur.gazizov.archiver.core.service.parallel;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class ParallelConfig {
  private int batchSize = 100_000;

  public int getBatchSize() {
    return batchSize;
  }
}
