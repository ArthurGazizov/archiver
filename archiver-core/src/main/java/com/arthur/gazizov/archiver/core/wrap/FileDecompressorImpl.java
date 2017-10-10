package com.arthur.gazizov.archiver.core.wrap;

import com.arthur.gazizov.archiver.core.factory.ArchiverFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 10.10.17.
 */
public class FileDecompressorImpl implements FileDecompressor {
  private final FileWorkerConfig config;

  public FileDecompressorImpl(FileWorkerConfig config) {
    this.config = config;
  }

  @Override
  public void decompress(String path) {
    try {
      final byte[] bytes = Files.readAllBytes(new File(path).toPath());
      final String fileExtention = path.substring(path.lastIndexOf('.'));
      final ArchiverFactory archiverFactory = config.getTypeReMapping().get(fileExtention);
      final byte[] decompressed = archiverFactory
              .loadDecompressor()
              .decompress(bytes);
      String originalNameWithExtention = path.substring(0, path.lastIndexOf('.'));
      String originalName = originalNameWithExtention.substring(0, originalNameWithExtention.lastIndexOf('.'));
      String originalExtention = originalNameWithExtention.substring(originalNameWithExtention.lastIndexOf('.'));
      String outputName = originalName + originalExtention;
      int tryed = 1;
      while (Files.exists(new File(outputName).toPath())) {
        outputName = originalName + "_" + tryed + originalExtention;
        tryed++;
      }
      FileOutputStream fos = new FileOutputStream(outputName);
      fos.write(decompressed);
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
