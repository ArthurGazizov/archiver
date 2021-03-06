package com.arthur.gazizov.archiver.core.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 30.09.17.
 */
public class ByteUtilsTest {
  @Test
  public void test() {
    for (int i = 0; i < 1000; i++) {
      int value = Math.abs(ThreadLocalRandom.current().nextInt()) % ((1 << 7) - 1);
      byte[] bytes = ByteUtils.intTo1Bytes(value);
      int actual = ByteUtils.toInt1(bytes, 0);
      Assert.assertEquals(value, actual);

      value = Math.abs(ThreadLocalRandom.current().nextInt()) % ((1 << 15 - 1));
      bytes = ByteUtils.intTo2Bytes(value);
      actual = ByteUtils.toInt2(bytes, 0);
      Assert.assertEquals(value, actual);

      value = Math.abs(ThreadLocalRandom.current().nextInt()) % ((1 << 23 - 1));
      bytes = ByteUtils.intTo3Bytes(value);
      actual = ByteUtils.toInt3(bytes, 0);
      Assert.assertEquals(value, actual);

      value = Math.abs(ThreadLocalRandom.current().nextInt());
      bytes = ByteUtils.intTo4Bytes(value);
      actual = ByteUtils.toInt4(bytes, 0);
      Assert.assertEquals(value, actual);
    }
  }
}