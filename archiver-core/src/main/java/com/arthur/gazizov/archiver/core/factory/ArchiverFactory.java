package com.arthur.gazizov.archiver.core.factory;

import com.arthur.gazizov.archiver.core.service.Packer;
import com.arthur.gazizov.archiver.core.service.Repacker;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 30.09.17.
 */
public interface ArchiverFactory {
  Packer loadPacker();
  Repacker loadRepacker();
}
