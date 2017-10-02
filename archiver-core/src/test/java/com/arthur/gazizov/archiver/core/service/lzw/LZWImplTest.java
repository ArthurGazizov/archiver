package com.arthur.gazizov.archiver.core.service.lzw;

import com.arthur.gazizov.archiver.core.factory.impl.LZWFactory;
import com.arthur.gazizov.archiver.core.service.AbstractArchiverTest;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 30.09.17.
 */
public class LZWImplTest extends AbstractArchiverTest {
  @Override
  public void init() {
    archiverFactory = new LZWFactory();
  }

  @Override
  protected String getExtension() {
    return ".lzw";
  }
}