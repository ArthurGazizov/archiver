package com.arthur.gazizov.archiver.ui;

import com.arthur.gazizov.archiver.ui.views.MainView;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 10.10.17.
 */
public class Runner implements Runnable {
  @Override
  public void run() {
    new MainView();
  }
}