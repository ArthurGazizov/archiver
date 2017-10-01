package com.arthur.gazizov.archiver.core.service.lzw;

import com.arthur.gazizov.archiver.core.service.Repacker;
import com.arthur.gazizov.archiver.core.utils.ByteUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 30.09.17.
 */
public class RepackerLZWImpl implements Repacker {
  private final LZWConfig config;
  private final Map<ByteBuffer, Integer> map;
  private final Map<Integer, ByteBuffer> reversed;

  public RepackerLZWImpl() {
    this.config = new LZWConfig();
    this.map = LZWUtils.defaultMap();
    this.reversed = new HashMap<>();
    this.map.forEach((key, value) -> reversed.put(value, key));
  }

  @Override
  public byte[] repack(byte[] bytes) {
    List<Byte> builder = new ArrayList<>();
    int position = 0;
    Integer prevValueInit = null;
    while (position < bytes.length) {
      int parsed;
      if (map.size() > ((1 << 23) - 1)) {
        parsed = ByteUtils.toInt4(bytes, position);
        position += 4;
      } else if (map.size() > ((1 << 15) - 1)) {
        parsed = ByteUtils.toInt3(bytes, position);
        position += 3;
      } else if (map.size() > ((1 << 7) - 1)) {
        parsed = ByteUtils.toInt2(bytes, position);
        position += 2;
      } else {
        parsed = bytes[position];
        position++;
      }
      final int value = parsed;


      final Integer prevValue = prevValueInit;
      Optional<ByteBuffer> keyOptional = Optional.ofNullable(reversed.get(value));
      Optional<ByteBuffer> prevKeyOptional = Optional.ofNullable(reversed.get(prevValue));
      ByteBuffer prevKey = prevKeyOptional.orElse(ByteBuffer.wrap(new byte[0]));
      if (!keyOptional.isPresent()) {
        keyOptional = Optional.of(prevKey);
        for (byte b : prevKey.array()) {
          builder.add(b);
        }
        builder.add(keyOptional.get().array()[0]);
      } else {
        for (byte b : keyOptional.get().array()) {
          builder.add(b);
        }
      }
      if (map.size() < config.getMaxMapSize()) {
        ByteBuffer finalKey = ByteUtils.add(prevKey, keyOptional.get().array()[0]);
        map.putIfAbsent(finalKey, map.size());
        reversed.putIfAbsent(map.get(finalKey), finalKey);
      }
      prevValueInit = value;
    }
    byte[] ret = new byte[builder.size()];
    for (int i = 0; i < ret.length; i++) {
      ret[i] = builder.get(i);
    }
    return ret;
  }
}
