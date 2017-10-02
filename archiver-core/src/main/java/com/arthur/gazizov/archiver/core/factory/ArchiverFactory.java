package com.arthur.gazizov.archiver.core.factory;

import com.arthur.gazizov.archiver.core.service.Compressor;
import com.arthur.gazizov.archiver.core.service.Decompressor;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 30.09.17.
 */
public interface ArchiverFactory {
  Compressor loadCompressor();
  Decompressor loadDecompressor();
}
