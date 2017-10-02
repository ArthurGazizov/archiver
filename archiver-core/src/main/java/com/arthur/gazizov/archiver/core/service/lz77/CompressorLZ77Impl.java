package com.arthur.gazizov.archiver.core.service.lz77;

import com.arthur.gazizov.archiver.core.service.Compressor;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 01.10.17.
 */
public class CompressorLZ77Impl implements Compressor {
  private final LZ77Config config;
  private final StringBuffer buffer;

  public CompressorLZ77Impl() {
    this.config = new LZ77Config();
    this.buffer = new StringBuffer(config.getBufferSize());
  }

  @Override
  public byte[] compress(byte[] bytes) {
    return new byte[0];
  }

  private void trimSearchBuffer() {
    if (buffer.length() > config.getBufferSize()) {
      buffer.delete(0, buffer.length() - config.getBufferSize());
    }
  }
}
