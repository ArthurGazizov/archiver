package com.arthur.gazizov.archiver.core.service;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 30.09.17.
 */
public interface Decompressor {
  byte[] decompress(byte[] bytes);
}
