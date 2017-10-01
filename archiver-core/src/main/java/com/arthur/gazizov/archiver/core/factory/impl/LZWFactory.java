package com.arthur.gazizov.archiver.core.factory.impl;

import com.arthur.gazizov.archiver.core.factory.ArchiverFactory;
import com.arthur.gazizov.archiver.core.service.Packer;
import com.arthur.gazizov.archiver.core.service.Repacker;
import com.arthur.gazizov.archiver.core.service.lzw.PackerLZWImpl;
import com.arthur.gazizov.archiver.core.service.lzw.RepackerLZWImpl;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 01.10.17.
 */
public class LZWFactory implements ArchiverFactory {
  @Override
  public Packer loadPacker() {
    return new PackerLZWImpl();
  }

  @Override
  public Repacker loadRepacker() {
    return new RepackerLZWImpl();
  }
}
