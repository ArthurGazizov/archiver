package com.arthur.gazizov.archiver.core.service.lzw;

import com.arthur.gazizov.archiver.core.service.Packer;
import com.arthur.gazizov.archiver.core.utils.ByteUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 30.09.17.
 */
public class PackerLZWImpl implements Packer {
  private final Map<ByteBuffer, Integer> map;
  private final LZWConfig config;

  public PackerLZWImpl() {
    config = new LZWConfig();
    this.map = LZWUtils.defaultMap();
  }

  @Override
  public byte[] pack(byte[] input) {
    List<Byte> bytesList = new ArrayList<>();
    int position = 0;
    while (position < input.length) {
      ByteBuffer current = ByteBuffer.wrap(new byte[]{input[position]});
      position++;
      while (map.containsKey(current) && position < input.length) {
        current = ByteUtils.add(current, input[position]);
        position++;
      }
      if (!map.containsKey(current)) {
        ByteBuffer lastSuccess = ByteBuffer.wrap(current.array(), 0, current.array().length - 1);
        byte[] bytes = toBytes(this.map.get(lastSuccess));
        for (int i = 0; i < bytes.length; i++) {
          bytesList.add(bytes[i]);
        }
        if (map.size() < config.getMaxMapSize()) {
          this.map.put(current, this.map.size());
        }
        position--;
      } else {
        byte[] bytes = toBytes(map.get(current));
        for (int i = 0; i < bytes.length; i++) {
          bytesList.add(bytes[i]);
        }
      }
    }
    byte[] ret = new byte[bytesList.size()];
    for (int i = 0; i < ret.length; i++) {
      ret[i] = bytesList.get(i);
    }
    return ret;
  }

  private byte[] toBytes(int code) {
    if (map.size() >= ((1 << 23) - 1)) {
      return ByteUtils.intTo4Bytes(code);
    } else if (map.size() >= ((1 << 15) - 1)) {
      return ByteUtils.intTo3Bytes(code);
    } else if (map.size() >= ((1 << 7) - 1)) {
      return ByteUtils.intTo2Bytes(code);
    } else {
      return ByteUtils.intTo1Bytes(code);
    }
  }
}
