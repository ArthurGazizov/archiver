package com.arthur.gazizov.archiver.core.service.lz77;

import java.util.ArrayList;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class CodeSequence extends ArrayList<Code> {
  public static CodeSequence loadCodesFromBytes(byte[] bytes) {
    CodeSequence codes = new CodeSequence();
    int position = 0;
    for (; position < bytes.length; position += 5) {
      codes.add(Code.fromBytes(bytes, position));
    }
    return codes;
  }

  public byte[] fetchBytes() {
    byte[] ret = new byte[size() * 5];
    int index = 0;
    for (Code code : this) {
      byte[] bytes = code.getBytes();
      System.arraycopy(bytes, 0, ret, index * 5, 5);
      index++;
    }
    return ret;
  }
}
