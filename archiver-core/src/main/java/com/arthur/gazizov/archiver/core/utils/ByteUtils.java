package com.arthur.gazizov.archiver.core.utils;

import java.nio.ByteBuffer;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 30.09.17.
 */
public class ByteUtils {
  public static byte[] intTo4Bytes(int x) {
    return intToBytesType(x, 4);
  }

  public static byte[] intTo3Bytes(int x) {
    return intToBytesType(x, 3);
  }

  public static byte[] intTo2Bytes(int x) {
    return intToBytesType(x, 2);
  }

  public static byte[] intTo1Bytes(int x) {
    return intToBytesType(x, 1);
  }

  public static int toInt4(byte[] bytes, int offset) {
    return toIntByBytes(bytes, offset, 4);
  }

  public static int toInt3(byte[] bytes, int offset) {
    return toIntByBytes(bytes, offset, 3);
  }

  public static int toInt2(byte[] bytes, int offset) {
    return toIntByBytes(bytes, offset, 2);
  }

  public static int toInt1(byte[] bytes, int offset) {
    return toIntByBytes(bytes, offset, 1);
  }


  private static byte[] intToBytesType(int x, int bytesCount) {
//    ByteBuffer byteBuffer = ByteBuffer.allocate(bytesCount);
//    byteBuffer.putInt(x);
//    return byteBuffer.array();
    byte[] bytes = new byte[bytesCount];
    for (int i = bytesCount - 1, iter = 0; iter < bytesCount; i--, x >>>= 8, iter++) {
      bytes[i] = (byte) (x & 0xFF);
    }
    return bytes;
  }

  private static int toIntByBytes(byte[] bytes, int offset, int bytesCount) {
//    ByteBuffer byteBuffer = ByteBuffer.wrap(bytes, offset, bytesCount);
//    return byteBuffer.getInt();
    int ret = 0;
    for (int i = 0; i < bytesCount && i + offset < bytes.length; i++) {
      ret <<= 8;
      ret |= (int) bytes[i + offset] & 0xFF;
    }
    return ret;
  }

  public static ByteBuffer add(ByteBuffer buffer, byte value) {
    byte[] origin = buffer.array();
    byte[] ret = new byte[origin.length + 1];
    System.arraycopy(origin, 0, ret, 0, origin.length);
    ret[ret.length - 1] = value;
    return ByteBuffer.wrap(ret);
  }
  public static ByteBuffer addAll(ByteBuffer buffer, byte[] bytes) {
    byte[] origin = buffer.array();
    byte[] ret = new byte[origin.length + bytes.length];
    System.arraycopy(origin, 0, ret, 0, origin.length);
    System.arraycopy(bytes, 0, ret, origin.length, bytes.length);
    return ByteBuffer.wrap(ret);
  }
}
