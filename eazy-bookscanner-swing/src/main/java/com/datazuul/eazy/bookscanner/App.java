package com.datazuul.eazy.bookscanner;

import com.datazuul.eazy.bookscanner.gui.ThumbnailsAndScanPanel;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import org.kordamp.ikonli.material2.Material2AL;
import org.kordamp.ikonli.swing.FontIcon;

public class App extends JFrame {

  public static final int ICON_SIZE = 20;

  public App() {
    initUI();
  }

  private void createMenuBar() {

    JMenuBar menuBar = new JMenuBar();

    // FILE
    JMenu fileMenu = new JMenu("File");
    fileMenu.setMnemonic(KeyEvent.VK_F); //  the menu can be opened with the Alt+F shortcut

    FontIcon exitIcon = createFontIcon(Material2AL.EXIT_TO_APP);
    exitIcon.setIconColor(Color.RED);
    JMenuItem exitMenuItem = new JMenuItem("Exit", exitIcon);
    exitMenuItem.setMnemonic(KeyEvent.VK_E); // it can be activated with the Alt+F+E key combination
    exitMenuItem.setToolTipText("Exit application");
    exitMenuItem.addActionListener((event) -> System.exit(0));
    fileMenu.add(exitMenuItem);

    menuBar.add(fileMenu);

    // HELP
    JMenu helpMenu = new JMenu("Help");
    helpMenu.setMnemonic(KeyEvent.VK_H);

    JMenuItem aboutMemuItem = new JMenuItem("About");
    aboutMemuItem.setMnemonic(KeyEvent.VK_A);
    helpMenu.add(aboutMemuItem);

    menuBar.add(helpMenu);
    
    setJMenuBar(menuBar);
  }

  private FontIcon createFontIcon(Material2AL iconCode) {
    FontIcon exitIcon = new FontIcon();
    exitIcon.setIkon(iconCode);
    exitIcon.setIconSize(ICON_SIZE);
    return exitIcon;
  }

  private void initUI() {
    createMenuBar();

    setTitle("datazuul's Eazy BookScanner");
    setSize(1024, 800);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    ThumbnailsAndScanPanel thumbnailsAndScanPanel = new ThumbnailsAndScanPanel();
    getContentPane().add(thumbnailsAndScanPanel);
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      App ex = new App();
      ex.setVisible(true);
    });
  }
}
