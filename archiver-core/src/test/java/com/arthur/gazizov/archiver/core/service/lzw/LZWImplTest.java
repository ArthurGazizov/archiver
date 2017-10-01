package com.arthur.gazizov.archiver.core.service.lzw;

import com.arthur.gazizov.archiver.core.factory.ArchiverFactory;
import com.arthur.gazizov.archiver.core.factory.impl.LZWFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 30.09.17.
 */
public class LZWImplTest {
  private ArchiverFactory archiverFactory;

  @Before
  public void init() {
    archiverFactory = new LZWFactory();
  }

  @Test
  public void test() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < 10000; i++) {
      builder.append(ThreadLocalRandom.current().nextInt());
    }
    String originalMessage = "abrakadabra" + builder.toString();
    byte[] originalMessageBytes = originalMessage.getBytes();

    byte[] packedBytes = archiverFactory
            .loadPacker()
            .pack(originalMessageBytes);
    Assert.assertTrue(packedBytes.length <= originalMessageBytes.length);

    byte[] repackedBytes = archiverFactory
            .loadRepacker()
            .repack(packedBytes);
    String repackedMessage = new String(repackedBytes);
    Assert.assertEquals(originalMessage, repackedMessage);
  }

  @Test
  @Ignore
  public void testFile() throws IOException {
    Path path = Paths.get("/Users/arthurgazizov/Desktop/book1.txt");
    byte[] bytes = Files.readAllBytes(path);
    byte[] pack = archiverFactory
            .loadPacker()
            .pack(bytes);
    OutputStream outputStream = new FileOutputStream("/Users/arthurgazizov/Desktop/book1.lzw");
    outputStream.write(pack);
    outputStream.close();
  }
}