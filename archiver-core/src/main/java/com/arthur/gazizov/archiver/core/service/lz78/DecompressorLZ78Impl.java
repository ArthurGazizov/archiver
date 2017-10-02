package com.arthur.gazizov.archiver.core.service.lz78;

import com.arthur.gazizov.archiver.core.service.AbstractDecompressor;
import com.arthur.gazizov.archiver.core.service.common.Dictionary;
import com.arthur.gazizov.archiver.core.service.common.ReversedDictionary;
import com.arthur.gazizov.archiver.core.service.sequence.Sequence;
import com.arthur.gazizov.archiver.core.service.sequence.SimpleSequence;
import com.arthur.gazizov.archiver.core.utils.ByteUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class DecompressorLZ78Impl extends AbstractDecompressor {
  private final PackerLZ78Config config;
  private Dictionary dictionary;
  private ReversedDictionary reversedDictionary;
  private Sequence sequence;

  public DecompressorLZ78Impl() {
    this.config = new PackerLZ78Config();
  }

  @Override
  protected void init() {
    dictionary = new Dictionary();
    reversedDictionary = new ReversedDictionary();
    sequence = new SimpleSequence(1);
  }

  @Override
  protected void dispose() {
    dictionary.clear();
    reversedDictionary.clear();
  }

  @Override
  protected byte[] process(byte[] bytes) {
    List<Byte> result = new ArrayList<>();
    int position = 0;
    while (position < bytes.length) {
      int index;
      if (sequence.current() > ByteUtils.THREE_BYTE_RANGE) {
        index = ByteUtils.toInt4(bytes, position);
        position += 4;
      } else if (sequence.current() > ByteUtils.TWO_BYTE_RANGE) {
        index = ByteUtils.toInt3(bytes, position);
        position += 3;
      } else if (sequence.current() > ByteUtils.ONE_BYTE_RANGE) {
        index = ByteUtils.toInt2(bytes, position);
        position += 2;
      } else {
        index = bytes[position];
        position++;
      }
      byte lastByte = bytes[position];
      position++;
      ByteBuffer newKey;
      if (index != 0) {
        ByteBuffer byteBuffer = reversedDictionary.get(index);
        ByteUtils.addToTail(result, byteBuffer.array());
        ByteUtils.addToTail(result, lastByte);
        newKey = ByteUtils.addToTail(byteBuffer, lastByte);
      } else {
        ByteUtils.addToTail(result, lastByte);
        newKey = ByteBuffer.wrap(new byte[]{lastByte});
      }
      putIfAbsent(newKey);
    }
    return ByteUtils.unboxing(result);
  }

  private void putIfAbsent(ByteBuffer buffer) {
    if (dictionary.size() < config.getDictionaryMaxSize()) {
      if (!dictionary.containsKey(buffer)) {
        dictionary.put(buffer, sequence.current());
        reversedDictionary.put(sequence.current(), buffer);
        sequence.increase();
      }
    }
  }
}
