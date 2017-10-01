package com.arthur.gazizov.archiver.core.service.lzw;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 01.10.17.
 */
public class LZWUtils {
  public static Map<ByteBuffer, Integer> defaultMap() {
    Map<ByteBuffer, Integer> map = new HashMap<>();
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
      map.put(ByteBuffer.wrap(new byte[]{(byte) i}), i);
    }
    return map;
  }
}
