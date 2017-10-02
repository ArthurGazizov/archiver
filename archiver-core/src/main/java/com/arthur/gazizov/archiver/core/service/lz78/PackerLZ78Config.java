package com.arthur.gazizov.archiver.core.service.lz78;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class PackerLZ78Config {
  private final int dictionaryMaxSize = 100_000;

  public int getDictionaryMaxSize() {
    return dictionaryMaxSize;
  }
}
