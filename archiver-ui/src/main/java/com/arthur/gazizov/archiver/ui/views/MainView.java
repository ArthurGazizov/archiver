package com.arthur.gazizov.archiver.ui.views;

import com.arthur.gazizov.archiver.core.wrap.FileCompressor;
import com.arthur.gazizov.archiver.core.wrap.FileDecompressor;
import com.arthur.gazizov.archiver.core.wrap.FileWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 10.10.17.
 */
public class MainView extends JFrame {
  private JButton compressButton;
  private JRadioButton lzwRadioButton;
  private JRadioButton lz77RadioButton;
  private JRadioButton lz78RadioButton;
  private JButton decompressButton;
  private JPanel panel1;
  private FileWorker fileWorker;
  private JFileChooser fileChooser;


  public MainView() {
    setContentPane(panel1);
    setResizable(false);
    setSize(400, 120);
    setTitle("Arciver");
    setLocationRelativeTo(null);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//    add(encryptButton);
    setVisible(true);
    fileWorker = new FileWorker();

    lz77RadioButton.addItemListener(e -> {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        lz78RadioButton.setSelected(false);
        lzwRadioButton.setSelected(false);
      }
    });
    lz78RadioButton.addItemListener(e -> {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        lz77RadioButton.setSelected(false);
        lzwRadioButton.setSelected(false);
      }
    });
    lzwRadioButton.addItemListener(e -> {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        lz78RadioButton.setSelected(false);
        lz77RadioButton.setSelected(false);
      }
    });
    fileChooser = new JFileChooser();
    compressButton.addActionListener(e -> {
      final int result = fileChooser.showOpenDialog(new Panel());
      if (result == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        fetchCompressor().compress(file.getPath());
      }
    });
    decompressButton.addActionListener(e -> {
      final int result = fileChooser.showOpenDialog(new Panel());
      if (result == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getSelectedFile();
        fetchDecompressor().decompress(file.getPath());
      }
    });
  }

  private FileCompressor fetchCompressor() {
    if (lzwRadioButton.isSelected()) {
      return fileWorker.lzwCompressor();
    } else if (lz77RadioButton.isSelected()) {
      return fileWorker.lz77Compressor();
    } else {
      return fileWorker.lz78Compressor();
    }
  }

  private FileDecompressor fetchDecompressor() {
    return fileWorker.decompressor();
  }
}
