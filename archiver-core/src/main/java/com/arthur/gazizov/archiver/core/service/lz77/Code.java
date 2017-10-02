package com.arthur.gazizov.archiver.core.service.lz77;

import com.arthur.gazizov.archiver.core.utils.ByteUtils;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class Code {
  private short length;
  private int offset;
  private byte lastValue;

  public static Code fromBytes(byte[] bytes, int position) {
    short length = (short) ByteUtils.toInt2(bytes, position);
    int offset = ByteUtils.toInt4(bytes, position + 2);
    byte lastValue = bytes[position + 2 + 4];
    return Code.Builder.aCode()
            .length(length)
            .offset(offset)
            .lastValue(lastValue)
            .build();
  }

  public short getLength() {
    return length;
  }

  public int getOffset() {
    return offset;
  }

  public byte getLastValue() {
    return lastValue;
  }

  public byte[] getBytes() {
    byte[] lengthAsBytes = ByteUtils.intTo2Bytes(length);
    byte[] offsetAsBytes = ByteUtils.intTo4Bytes(offset);
    byte[] ret = new byte[2 + 4 + 1];
    System.arraycopy(lengthAsBytes, 0, ret, 0, 2);
    System.arraycopy(offsetAsBytes, 0, ret, 2, 4);
    ret[6] = lastValue;
    return ret;
  }

  public static final class Builder {
    private short length;
    private int offset;
    private byte lastValue;

    private Builder() {
    }

    public static Builder aCode() {
      return new Builder();
    }

    public Builder length(int length) {
      this.length = (short) length;
      return this;
    }

    public Builder offset(int offset) {
      this.offset = offset;
      return this;
    }

    public Builder lastValue(byte lastValue) {
      this.lastValue = lastValue;
      return this;
    }

    public Code build() {
      Code code = new Code();
      code.lastValue = this.lastValue;
      code.length = this.length;
      code.offset = this.offset;
      return code;
    }
  }
}
