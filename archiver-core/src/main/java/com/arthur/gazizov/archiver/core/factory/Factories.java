package com.arthur.gazizov.archiver.core.factory;

import com.arthur.gazizov.archiver.core.factory.parallel.LZ77ParallelFactory;
import com.arthur.gazizov.archiver.core.factory.parallel.LZ78ParallelFactory;
import com.arthur.gazizov.archiver.core.factory.parallel.LZWParallelFactory;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 10.10.17.
 */
public class Factories {
  public static ArchiverFactory LZWFactory()
  {
    return new LZWParallelFactory();
  }
  public static ArchiverFactory LZ77Factory()
  {
    return new LZ77ParallelFactory();
  }
  public static ArchiverFactory LZ78Factory()
  {
    return new LZ78ParallelFactory();
  }
}
