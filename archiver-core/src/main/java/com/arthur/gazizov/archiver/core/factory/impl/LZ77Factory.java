package com.arthur.gazizov.archiver.core.factory.impl;

import com.arthur.gazizov.archiver.core.factory.ArchiverFactory;
import com.arthur.gazizov.archiver.core.service.Compressor;
import com.arthur.gazizov.archiver.core.service.Decompressor;
import com.arthur.gazizov.archiver.core.service.lz77.CompressorLZ77Impl;
import com.arthur.gazizov.archiver.core.service.lz77.DecompressorLZ77Impl;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class LZ77Factory implements ArchiverFactory {
  @Override
  public Compressor loadCompressor() {
    return new CompressorLZ77Impl();
  }

  @Override
  public Decompressor loadDecompressor() {
    return new DecompressorLZ77Impl();
  }
}
