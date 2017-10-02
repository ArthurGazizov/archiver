package com.arthur.gazizov.archiver.core.service;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public abstract class AbstractCompressor implements Compressor {
  @Override
  public byte[] compress(byte[] bytes) {
    init();
    byte[] result = process(bytes);
    dispose();
    return result;
  }

  protected abstract void init();

  protected abstract void dispose();

  protected abstract byte[] process(byte[] bytes);
}
