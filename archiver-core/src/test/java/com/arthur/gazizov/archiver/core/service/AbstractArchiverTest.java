package com.arthur.gazizov.archiver.core.service;

import com.arthur.gazizov.archiver.core.factory.ArchiverFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public abstract class AbstractArchiverTest {
  protected ArchiverFactory archiverFactory;

  @Before
  public void init() {
    archiverFactory = getArchiverFactory();
  }

  @Test
  public void test() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < 100_000; i++) {
      builder.append(ThreadLocalRandom.current().nextInt());
    }
    String originalMessage = "abrakadabra" + builder.toString();
    byte[] originalMessageBytes = originalMessage.getBytes();

    byte[] compressedBytes = archiverFactory
            .loadCompressor()
            .compress(originalMessageBytes);
    Assert.assertTrue(compressedBytes.length <= originalMessageBytes.length);

    byte[] decompressedBytes = archiverFactory
            .loadDecompressor()
            .decompress(compressedBytes);
    String repackedMessage = new String(decompressedBytes);
    Assert.assertEquals(originalMessage, repackedMessage);
  }

  @Test
  public void testFile() throws IOException {
    String originalFilePath = originalFilePath();
    Path path = Paths.get(originalFilePath);
    byte[] bytes = Files.readAllBytes(path);
    byte[] compressedBytes = archiverFactory
            .loadCompressor()
            .compress(bytes);
    String supplyedFilePath = originalFilePath.substring(0, originalFilePath.lastIndexOf('.'))
            + System.currentTimeMillis()
            + getExtension();
    OutputStream outputStream = new FileOutputStream(supplyedFilePath);
    outputStream.write(compressedBytes);
    outputStream.close();


    Path compressedFilePath = Paths.get(supplyedFilePath);
    byte[] foundCompressedBytes = Files.readAllBytes(compressedFilePath);
    byte[] decompressedBytes = archiverFactory
            .loadDecompressor()
            .decompress(foundCompressedBytes);
    String supplyedDecompressedFilePath = supplyedFilePath.substring(0, supplyedFilePath.lastIndexOf('.')) + ".txt";

    outputStream = new FileOutputStream(supplyedDecompressedFilePath);
    outputStream.write(decompressedBytes);
    outputStream.close();

    Path decompressedFilePath = Paths.get(supplyedDecompressedFilePath);
    byte[] foundDecompressedBytes = Files.readAllBytes(decompressedFilePath);

    Assert.assertEquals(bytes.length, foundDecompressedBytes.length);
    Assert.assertEquals(ByteBuffer.wrap(bytes), ByteBuffer.wrap(foundDecompressedBytes));
  }

  protected abstract String getExtension();

  protected abstract ArchiverFactory getArchiverFactory();

  private String originalFilePath() {
    return "/Users/arthurgazizov/Desktop/big.txt";
  }
}
