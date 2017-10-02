package com.arthur.gazizov.archiver.core.service.lzw;

import com.arthur.gazizov.archiver.core.service.common.Dictionary;

import java.nio.ByteBuffer;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 01.10.17.
 */
public class LZWUtils {
  public static Dictionary defaultDictionary() {
    Dictionary map = new Dictionary();
    int code = 0;
    for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++, code++) {
      map.put(ByteBuffer.wrap(new byte[]{(byte) i}), code);
    }
    return map;
  }
}
