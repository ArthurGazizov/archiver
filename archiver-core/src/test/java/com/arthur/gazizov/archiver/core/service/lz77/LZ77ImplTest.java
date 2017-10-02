package com.arthur.gazizov.archiver.core.service.lz77;

import com.arthur.gazizov.archiver.core.factory.ArchiverFactory;
import com.arthur.gazizov.archiver.core.factory.impl.LZ77Factory;
import com.arthur.gazizov.archiver.core.service.AbstractArchiverTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class LZ77ImplTest extends AbstractArchiverTest {
  @Override
  @Ignore
  @Test
  public void test() {
    super.test();
  }

  @Override
  @Test
  @Ignore
  public void testFile() throws IOException {
    super.testFile();
  }

  @Override
  protected String getExtension() {
    return ".lz77";
  }

  @Override
  protected ArchiverFactory getArchiverFactory() {
    return new LZ77Factory();
  }

  @Test
  public void simpleTest() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < 10_000; i++) {
      builder.append(ThreadLocalRandom.current().nextInt());
    }
    String originalMessage = "kabababababz" + builder.toString();
    byte[] originalMessageBytes = originalMessage.getBytes();

    byte[] compressedBytes = archiverFactory
            .loadCompressor()
            .compress(originalMessageBytes);
    byte[] decompressedBytes = archiverFactory
            .loadDecompressor()
            .decompress(compressedBytes);
    String repackedMessage = new String(decompressedBytes);
    Assert.assertEquals(originalMessage, repackedMessage);
  }
}
