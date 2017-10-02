package com.arthur.gazizov.archiver.core.service.sequence;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class SimpleSequence implements Sequence {
  private int value;

  public SimpleSequence(int seed) {
    this.value = seed;
  }

  @Override
  public int current() {
    return this.value;
  }

  @Override
  public void increase() {
    this.value++;
  }
}
