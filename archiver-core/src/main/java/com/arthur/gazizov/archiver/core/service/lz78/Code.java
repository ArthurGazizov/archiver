package com.arthur.gazizov.archiver.core.service.lz78;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class Code {
  private int index;
  private byte lastByte;

  public int getIndex() {
    return index;
  }

  public byte getLastByte() {
    return lastByte;
  }

  public static final class Builder {
    private int index;
    private byte lastValue;

    private Builder() {
    }

    public static Builder aCode() {
      return new Builder();
    }

    public Builder index(int index) {
      this.index = index;
      return this;
    }

    public Builder lastValue(byte lastValue) {
      this.lastValue = lastValue;
      return this;
    }

    public Code build() {
      Code code = new Code();
      code.lastByte = this.lastValue;
      code.index = this.index;
      return code;
    }
  }
}
