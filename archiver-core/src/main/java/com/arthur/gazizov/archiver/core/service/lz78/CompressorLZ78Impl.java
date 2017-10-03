package com.arthur.gazizov.archiver.core.service.lz78;

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
 * Created on 02.10.17.
 */
public class CompressorLZ78Impl extends AbstractCompressor {
  private final LZ78Config config;
  private Dictionary dictionary;
  private Sequence sequence;

  public CompressorLZ78Impl() {
    this.config = new LZ78Config();
  }

  @Override
  protected void init() {
    dictionary = new Dictionary();
    sequence = new SimpleSequence(1);
  }

  @Override
  protected void dispose() {
    dictionary.clear();
  }

  @Override
  protected byte[] process(byte[] bytes) {
    List<Byte> byteList = new ArrayList<>();
    int position = 0;
    while (position < bytes.length) {
      ByteBuffer current = ByteBuffer.wrap(new byte[]{bytes[position]});
      position++;
      while (dictionary.containsKey(current) && position < bytes.length) {
        current = ByteUtils.addToTail(current, bytes[position]);
        position++;
      }
      Code.Builder codeBuilder = Code.Builder.aCode();
      if (current.array().length > 1) {
        ByteBuffer lastSuccess = ByteBuffer.wrap(current.array(), 0, current.array().length - 1);
        codeBuilder
                .index(dictionary.get(lastSuccess))
                .lastValue(current.array()[current.array().length - 1]);
      } else {
        codeBuilder
                .index(0)
                .lastValue(current.array()[0]);
      }
      Code code = codeBuilder.build();
      ByteUtils.addToTail(byteList, toBytes(code));
      putIfAbsent(current);
    }
    return ByteUtils.unboxing(byteList);
  }

  private byte[] toBytes(Code code) {
    byte[] index;
    if (sequence.current() > ByteUtils.THREE_BYTE_RANGE) {
      index = ByteUtils.intTo4Bytes(code.getIndex());
    } else if (sequence.current() > ByteUtils.TWO_BYTE_RANGE) {
      index = ByteUtils.intTo3Bytes(code.getIndex());
    } else if (sequence.current() > ByteUtils.ONE_BYTE_RANGE) {
      index = ByteUtils.intTo2Bytes(code.getIndex());
    } else {
      index = ByteUtils.intTo1Bytes(code.getIndex());
    }
    byte[] ret = new byte[index.length + 1];
    System.arraycopy(index, 0, ret, 0, index.length);
    ret[ret.length - 1] = code.getLastByte();
    return ret;
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
