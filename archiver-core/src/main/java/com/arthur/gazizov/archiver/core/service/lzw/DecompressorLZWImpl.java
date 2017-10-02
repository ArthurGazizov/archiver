package com.arthur.gazizov.archiver.core.service.lzw;

import com.arthur.gazizov.archiver.core.service.AbstractDecompressor;
import com.arthur.gazizov.archiver.core.service.common.Dictionary;
import com.arthur.gazizov.archiver.core.service.common.ReversedDictionary;
import com.arthur.gazizov.archiver.core.service.sequence.Sequence;
import com.arthur.gazizov.archiver.core.service.sequence.SimpleSequence;
import com.arthur.gazizov.archiver.core.utils.ByteUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 30.09.17.
 */
public class DecompressorLZWImpl extends AbstractDecompressor {
  private final LZWConfig config;
  private Dictionary dictionary;
  private ReversedDictionary reversedDictionary;
  private Sequence sequence;

  public DecompressorLZWImpl() {
    this.config = new LZWConfig();
  }

  @Override
  protected void init() {
    dictionary = LZWUtils.defaultDictionary();
    reversedDictionary = new ReversedDictionary();
    dictionary.forEach((key, value) -> reversedDictionary.put(value, key));
    sequence = new SimpleSequence(dictionary.size());
  }

  @Override
  protected void dispose() {
    dictionary.clear();
    reversedDictionary.clear();
  }

  @Override
  public byte[] process(byte[] bytes) {
    List<Byte> result = new ArrayList<>();
    int position = 0;
    int prevValueInit = -1;
    while (position < bytes.length) {
      int parsed;
      if (sequence.current() >= ByteUtils.THREE_BYTE_RANGE) {
        parsed = ByteUtils.toInt4(bytes, position);
        position += 4;
      } else if (sequence.current() >= ByteUtils.TWO_BYTE_RANGE) {
        parsed = ByteUtils.toInt3(bytes, position);
        position += 3;
      } else if (sequence.current() >= ByteUtils.ONE_BYTE_RANGE) {
        parsed = ByteUtils.toInt2(bytes, position);
        position += 2;
      } else {
        parsed = bytes[position];
        position++;
      }
      final int value = parsed;
      final Integer prevValue = prevValueInit;
      Optional<ByteBuffer> keyOptional = Optional.ofNullable(reversedDictionary.get(value));
      Optional<ByteBuffer> prevKeyOptional = Optional.ofNullable(reversedDictionary.get(prevValue));
      ByteBuffer prevKey = prevKeyOptional.orElse(ByteBuffer.wrap(new byte[0]));
      if (!keyOptional.isPresent()) {
        keyOptional = Optional.of(ByteBuffer.wrap(prevKey.array()));
        for (byte b : prevKey.array()) {
          result.add(b);
        }
        result.add(keyOptional.get().array()[0]);
      } else {
        for (byte b : keyOptional.get().array()) {
          result.add(b);
        }
      }
      ByteBuffer finalKey = ByteUtils.addToTail(prevKey, keyOptional.get().array()[0]);
      putIfAbsent(finalKey);
      prevValueInit = value;
    }
    return ByteUtils.unboxing(result);
  }

  private void putIfAbsent(ByteBuffer buffer) {
    if (dictionary.size() < config.getDictionaryMaxSize()) {
      if (!dictionary.containsKey(buffer)) {
        dictionary.putIfAbsent(buffer, sequence.current());
        reversedDictionary.putIfAbsent(sequence.current(), buffer);
        sequence.increase();
      }
    }
  }
}
