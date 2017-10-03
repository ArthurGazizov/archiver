package com.arthur.gazizov.archiver.core.service.lzw;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 30.09.17.
 */
public class LZWConfig {
  private final int dictionaryMaxSize = 8_000_000;

  public int getDictionaryMaxSize() {
    return dictionaryMaxSize;
  }
}
