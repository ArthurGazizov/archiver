package com.arthur.gazizov.archiver.core.service;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 30.09.17.
 */
public interface Compressor {
  byte[] compress(byte[] bytes);
}
