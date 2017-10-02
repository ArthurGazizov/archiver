package com.arthur.gazizov.archiver.core.service.lz77;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 01.10.17.
 */
public class LZ77Config {
  private final int maxBufferLength = 100;
  private final int maxDictionaryLength = 32000;

  public int getMaxBufferLength() {
    return maxBufferLength;
  }

  public int getMaxDictionaryLength() {
    return maxDictionaryLength;
  }
}
