package com.arthur.gazizov.archiver.core.service.sequence;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public interface Sequence {
  int current();

  void increase();
}
