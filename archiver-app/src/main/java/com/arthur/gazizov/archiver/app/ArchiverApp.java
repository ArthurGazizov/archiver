package com.arthur.gazizov.archiver.app;

import com.arthur.gazizov.archiver.ui.Runner;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 03.10.17.
 */
public class ArchiverApp {
  public static void main(String[] args) {
    Runnable runnable = new Runner();
    runnable.run();
  }
}
