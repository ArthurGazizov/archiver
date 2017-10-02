package com.arthur.gazizov.archiver.core.factory.impl;

import com.arthur.gazizov.archiver.core.factory.ArchiverFactory;
import com.arthur.gazizov.archiver.core.service.Compressor;
import com.arthur.gazizov.archiver.core.service.Decompressor;
import com.arthur.gazizov.archiver.core.service.lz78.CompressorLZ78Impl;
import com.arthur.gazizov.archiver.core.service.lz78.DecompressorLZ78Impl;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class LZ78Factory implements ArchiverFactory {
  @Override
  public Compressor loadCompressor() {
    return new CompressorLZ78Impl();
  }

  @Override
  public Decompressor loadDecompressor() {
    return new DecompressorLZ78Impl();
  }
}
