package com.arthur.gazizov.archiver.core;

import com.arthur.gazizov.archiver.core.wrap.FileCompressor;
import com.arthur.gazizov.archiver.core.wrap.FileDecompressor;
import com.arthur.gazizov.archiver.core.wrap.FileWorker;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 10.10.17.
 */
public class FileWorkerIT {
  private FileWorker worker;

  @Before
  public void init(){
    worker = new FileWorker();
  }

  @Test
  public void test(){
    String filePath = "/Users/arthurgazizov/Desktop/big.txt";
    final FileCompressor lz77Compressor = worker.lz77Compressor();
    lz77Compressor.compress(filePath);
    final FileCompressor lz78Compressor = worker.lz78Compressor();
    lz78Compressor.compress(filePath);
    final FileCompressor lzwCompressor = worker.lzwCompressor();
    lzwCompressor.compress(filePath);
    final FileDecompressor decompressor = worker.decompressor();
    decompressor.decompress(filePath + ".lz77");
    decompressor.decompress(filePath + ".lz78");
    decompressor.decompress(filePath + ".lzw");
  }
}
