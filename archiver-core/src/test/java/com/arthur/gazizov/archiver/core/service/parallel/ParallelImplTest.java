package com.arthur.gazizov.archiver.core.service.parallel;

import com.arthur.gazizov.archiver.core.factory.ArchiverFactory;
import com.arthur.gazizov.archiver.core.factory.impl.LZ78Factory;
import com.arthur.gazizov.archiver.core.factory.impl.ParallelFactory;
import com.arthur.gazizov.archiver.core.service.AbstractArchiverTest;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class ParallelImplTest extends AbstractArchiverTest {

  @Override
  protected String getExtension() {
    return ".plz78";
  }

  @Override
  protected ArchiverFactory getArchiverFactory() {
    return new ParallelFactory(new LZ78Factory());
  }
}
