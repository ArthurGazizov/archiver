package com.arthur.gazizov.archiver.core.service.lzw;

import com.arthur.gazizov.archiver.core.service.AbstractCompressor;
import com.arthur.gazizov.archiver.core.service.common.Dictionary;
import com.arthur.gazizov.archiver.core.service.sequence.Sequence;
import com.arthur.gazizov.archiver.core.service.sequence.SimpleSequence;
import com.arthur.gazizov.archiver.core.utils.ByteUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 30.09.17.
 */
public class CompressorLZWImpl extends AbstractCompressor {
  private final LZWConfig config;
  private Dictionary dictionary;
  private Sequence sequence;

  public CompressorLZWImpl() {
    this.config = new LZWConfig();
  }

  private byte[] toBytes(int code) {
    if (sequence.current() > ByteUtils.THREE_BYTE_RANGE) {
      return ByteUtils.intTo4Bytes(code);
    } else if (sequence.current() > ByteUtils.TWO_BYTE_RANGE) {
      return ByteUtils.intTo3Bytes(code);
    } else if (sequence.current() > ByteUtils.ONE_BYTE_RANGE) {
      return ByteUtils.intTo2Bytes(code);
    } else {
      return ByteUtils.intTo1Bytes(code);
    }
  }

  @Override
  protected void dispose() {
    dictionary.clear();
  }

  @Override
  protected void init() {
    dictionary = LZWUtils.defaultDictionary();
    sequence = new SimpleSequence(dictionary.size());
  }

  @Override
  protected byte[] process(byte[] bytes) {
    List<Byte> bytesList = new ArrayList<>();
    int position = 0;
    while (position < bytes.length) {
      ByteBuffer current = ByteBuffer.wrap(new byte[]{bytes[position]});
      position++;
      while (dictionary.containsKey(current) && position < bytes.length) {
        current = ByteUtils.addToTail(current, bytes[position]);
        position++;
      }
      if (!dictionary.containsKey(current)) {
        ByteBuffer lastSuccess = ByteBuffer.wrap(current.array(), 0, current.array().length - 1);
        ByteUtils.addToTail(bytesList, toBytes(dictionary.get(lastSuccess)));
        putIfAbsent(current);
        position--;
      } else {
        ByteUtils.addToTail(bytesList, toBytes(dictionary.get(current)));
      }
    }
    return ByteUtils.unboxing(bytesList);
  }

  private void putIfAbsent(ByteBuffer buffer) {
    if (dictionary.size() < config.getDictionaryMaxSize()) {
      if (!dictionary.containsKey(buffer)) {
        dictionary.put(buffer, sequence.current());
        sequence.increase();
      }
    }
  }
}
