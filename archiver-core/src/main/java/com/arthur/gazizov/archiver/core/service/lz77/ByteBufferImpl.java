package com.arthur.gazizov.archiver.core.service.lz77;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class ByteBufferImpl implements ByteBuffer {
  private List<Byte> data;

  public ByteBufferImpl() {
    this.data = new ArrayList<>();
  }

  @Override
  public Byte get(int index) {
    return data.get(index);
  }

  @Override
  public void add(Byte value) {
    data.add(value);
  }

  @Override
  public int size() {
    return data.size();
  }
}
