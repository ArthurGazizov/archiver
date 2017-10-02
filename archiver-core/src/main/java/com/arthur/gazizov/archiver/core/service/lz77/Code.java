package com.arthur.gazizov.archiver.core.service.lz77;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class Code {
  private int length;
  private int offset;
  private byte lastValue;

  public int getLength() {
    return length;
  }

  public int getOffset() {
    return offset;
  }

  public byte getLastValue() {
    return lastValue;
  }

  public static final class Builder {
    private int length;
    private int offset;
    private byte lastValue;

    private Builder() {
    }

    public static Builder aCode() {
      return new Builder();
    }

    public Builder withLength(int length) {
      this.length = length;
      return this;
    }

    public Builder withOffset(int offset) {
      this.offset = offset;
      return this;
    }

    public Builder withLastValue(byte lastValue) {
      this.lastValue = lastValue;
      return this;
    }

    public Code build() {
      Code code = new Code();
      code.length = this.length;
      code.offset = this.offset;
      code.lastValue = this.lastValue;
      return code;
    }
  }
}
