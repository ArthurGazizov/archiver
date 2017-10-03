package com.arthur.gazizov.archiver.ui.views;//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..

import javax.swing.*;
import java.awt.*;

public class MainView {
  public MainView() {
    JFrame guiFrame = new JFrame();
    JFileChooser fileChooser = new JFileChooser();
    JButton button = new JButton();
    //make sure the program exits when the frame closes
    guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    guiFrame.setTitle("Example GUI");
    guiFrame.setSize(300, 250);

    //This will center the JFrame in the middle of the screen
    guiFrame.setLocationRelativeTo(null);

    //Options for the JComboBox
    String[] fruitOptions = {"Apple", "Apricot", "Banana"
            , "Cherry", "Date", "Kiwi", "Orange", "Pear", "Strawberry"};

    //Options for the JList
    String[] vegOptions = {"Asparagus", "Beans", "Broccoli", "Cabbage"
            , "Carrot", "Celery", "Cucumber", "Leek", "Mushroom"
            , "Pepper", "Radish", "Shallot", "Spinach", "Swede"
            , "Turnip"};

    //The first JPanel contains a JLabel and JCombobox
    final JPanel comboPanel = new JPanel();
    JLabel comboLbl = new JLabel("Fruits:");
    JComboBox fruits = new JComboBox(fruitOptions);

    comboPanel.add(comboLbl);
    comboPanel.add(fruits);

    //Create the second JPanel. Add a JLabel and JList and
    //make use the JPanel is not visible.
    final JPanel listPanel = new JPanel();
    listPanel.setVisible(false);
    JLabel listLbl = new JLabel("Vegetables:");
    JList vegs = new JList(vegOptions);
    vegs.setLayoutOrientation(JList.HORIZONTAL_WRAP);

    listPanel.add(listLbl);
    listPanel.add(vegs);

    JButton vegFruitBut = new JButton("Fruit or Veg");

    //FileOpener fileOpener = new FileOpener();
    vegFruitBut.addActionListener(event -> {
      listPanel.setVisible(!listPanel.isVisible());
      comboPanel.setVisible(!comboPanel.isVisible());
      //fileOpener.setVisible(true);
    });

    JFileChooser jFileChooser = new JFileChooser();

    //The JFrame uses the BorderLayout layout manager.
    //Put the two JPanels and JButton in different areas.
    guiFrame.add(comboPanel, BorderLayout.NORTH);
    guiFrame.add(listPanel, BorderLayout.CENTER);
    guiFrame.add(vegFruitBut, BorderLayout.SOUTH);

    guiFrame.add(jFileChooser);

    //make sure the JFrame is visible
    guiFrame.setVisible(true);
  }

}