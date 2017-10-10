package com.arthur.gazizov.archiver.core.wrap;

import com.arthur.gazizov.archiver.core.factory.ArchiverFactory;
import com.arthur.gazizov.archiver.core.factory.Factories;
import com.arthur.gazizov.archiver.core.factory.parallel.LZ77ParallelFactory;
import com.arthur.gazizov.archiver.core.factory.parallel.LZ78ParallelFactory;
import com.arthur.gazizov.archiver.core.factory.parallel.LZWParallelFactory;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 10.10.17.
 */
public class FileWorkerConfig {
  private static FileWorkerConfig DEFAULT_CONFIG = new FileWorkerConfig();
  private final Map<Type, String> typeMapping;
  private final Map<String, ArchiverFactory> typeReMapping;

  public FileWorkerConfig() {
    this.typeMapping = new HashMap<>();
    this.typeReMapping = new HashMap<>();
    this.typeMapping.put(LZWParallelFactory.class, ".lzw");
    this.typeMapping.put(LZ77ParallelFactory.class, ".lz77");
    this.typeMapping.put(LZ78ParallelFactory.class, ".lz78");
    this.typeReMapping.put(".lzw", Factories.LZWFactory());
    this.typeReMapping.put(".lz77", Factories.LZ77Factory());
    this.typeReMapping.put(".lz78", Factories.LZ78Factory());
  }

  public static FileWorkerConfig getDefaultConfig() {
    return DEFAULT_CONFIG;
  }

  public Map<Type, String> getTypeMapping() {
    return typeMapping;
  }

  public Map<String, ArchiverFactory> getTypeReMapping() {
    return typeReMapping;
  }
}
