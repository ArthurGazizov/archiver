package com.arthur.gazizov.archiver.ui.views;

import javax.swing.*;
import java.io.File;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 03.10.17.
 */
public class FileOpener {
  private boolean visible;
  private JFrame frame;
  private JFileChooser fileChooser;

  public FileOpener() {
    this.frame = new JFrame();
    this.fileChooser = new JFileChooser();
    this.fileChooser.setCurrentDirectory(new File("/Users/arthurgazizov/Desktop"));
    this.frame.add(fileChooser);
    this.frame.setSize(300, 250);
    this.frame.setVisible(true);
  }

  public void setVisible(boolean value){
    this.visible = value;
  }

  //public boolean getVisible()


}
