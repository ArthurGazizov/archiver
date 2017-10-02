package com.arthur.gazizov.archiver.core.service.lz78;

import com.arthur.gazizov.archiver.core.factory.impl.LZ78Factory;
import com.arthur.gazizov.archiver.core.service.AbstractArchiverTest;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class LZ78ImplTest extends AbstractArchiverTest {
  @Override
  public void init() {
    archiverFactory = new LZ78Factory();
  }

  @Override
  protected String getExtension() {
    return ".lz78";
  }
}
