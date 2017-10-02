package com.arthur.gazizov.archiver.core.service.lz77;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public interface ByteBuffer {
  Byte get(int index);

  void add(Byte value);

  int size();
}
