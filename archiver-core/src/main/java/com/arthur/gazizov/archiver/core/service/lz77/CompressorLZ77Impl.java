package com.arthur.gazizov.archiver.core.service.lz77;

import com.arthur.gazizov.archiver.core.service.AbstractCompressor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    List<Byte> slidingWindow = new ArrayList<>();
    int position = 0;
    while (position < bytes.length) {
      List<Byte> buffer = new ArrayList<>();
      buffer.add(bytes[position]);
      int lastSuccessIndex = -1;
      int index;
      while (((index = Collections.indexOfSubList(slidingWindow, buffer)) != -1)
              && buffer.size() < config.getMaxBufferLength()
              && position + 1 < bytes.length) {
        lastSuccessIndex = index;
        position++;
        buffer.add(bytes[position]);
      }
      codeSequence.add(Code.Builder.aCode()
              .offset(slidingWindow.size() - lastSuccessIndex)
              .length((buffer.size() - 1))
              .lastValue(buffer.get(buffer.size() - 1))
              .build());
      slidingWindow.addAll(buffer);
      slidingWindow = moveSlidingWindow(slidingWindow);
      position++;
    }
    return codeSequence.fetchBytes();
  }

  private List<Byte> moveSlidingWindow(List<Byte> slidingWindow) {
    if (slidingWindow.size() > config.getMaxDictionaryLength()) {
      List<Byte> ret = new ArrayList<>(config.getMaxDictionaryLength());
      for (int i = 0; i < config.getMaxDictionaryLength(); i++) {
        ret.add(slidingWindow.get(slidingWindow.size() - config.getMaxDictionaryLength() + i));
      }
      return ret;
    }
    return slidingWindow;
  }
}
