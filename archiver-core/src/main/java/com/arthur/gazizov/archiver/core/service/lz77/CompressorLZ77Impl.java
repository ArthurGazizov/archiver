package com.arthur.gazizov.archiver.core.service.lz77;

import com.arthur.gazizov.archiver.core.service.AbstractCompressor;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 01.10.17.
 */
public class CompressorLZ77Impl extends AbstractCompressor {
  private final LZ77Config config;

  public CompressorLZ77Impl() {
    this.config = new LZ77Config();
  }

  @Override
  public byte[] process(byte[] bytes) {
    CodeSequence codeSequence = new CodeSequence();
    ByteBuffer slidingWindow = new ByteBufferImpl();
    int position = 0;
    while (position < bytes.length) {
      ByteBuffer buffer = new ByteBufferImpl();
      buffer.add(bytes[position]);
      int lastSuccessIndex = -1;
      int index;
      while (((index = LZ77Utils.indexOfSubArray(slidingWindow, buffer)) != -1)
              && buffer.size() < config.getMaxBufferSize()
              && position + 1 < bytes.length) {
        lastSuccessIndex = index;
        slidingWindow.add(buffer.get(buffer.size() - 1));
        position++;
        buffer.add(bytes[position]);
      }
      slidingWindow.add(buffer.get(buffer.size() - 1));
      codeSequence.add(Code.Builder.aCode()
              .offset(lastSuccessIndex)
              .length((buffer.size() - 1))
              .lastValue(buffer.get(buffer.size() - 1))
              .build());
      position++;
    }
    return codeSequence.fetchBytes();
  }
}
