package com.arthur.gazizov.archiver.core.factory.impl;

import com.arthur.gazizov.archiver.core.factory.ArchiverFactory;
import com.arthur.gazizov.archiver.core.service.Compressor;
import com.arthur.gazizov.archiver.core.service.Decompressor;
import com.arthur.gazizov.archiver.core.service.lzw.CompressorLZWImpl;
import com.arthur.gazizov.archiver.core.service.lzw.DecompressorLZWImpl;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 01.10.17.
 */
public class LZWFactory implements ArchiverFactory {
  @Override
  public Compressor loadCompressor() {
    return new CompressorLZWImpl();
  }

  @Override
  public Decompressor loadDecompressor() {
    return new DecompressorLZWImpl();
  }
}
