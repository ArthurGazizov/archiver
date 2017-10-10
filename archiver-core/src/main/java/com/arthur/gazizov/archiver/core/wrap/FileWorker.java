package com.arthur.gazizov.archiver.core.wrap;

import com.arthur.gazizov.archiver.core.factory.Factories;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 10.10.17.
 */
public class FileWorker {
  public FileCompressor lzwCompressor() {
    return new FileCompressorImpl(Factories.LZWFactory(), FileWorkerConfig.getDefaultConfig());
  }

  public FileCompressor lz77Compressor() {
    return new FileCompressorImpl(Factories.LZ77Factory(), FileWorkerConfig.getDefaultConfig());
  }

  public FileCompressor lz78Compressor() {
    return new FileCompressorImpl(Factories.LZ78Factory(), FileWorkerConfig.getDefaultConfig());
  }

  public FileDecompressor decompressor() {
    return new FileDecompressorImpl(FileWorkerConfig.getDefaultConfig());
  }
}
