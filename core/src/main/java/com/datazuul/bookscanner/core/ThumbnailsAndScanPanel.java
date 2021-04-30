package com.datazuul.bookscanner.core;

import chdk.ptp.java.ICamera;
import chdk.ptp.java.connection.CameraUsbDevice;
import chdk.ptp.java.connection.UsbUtils;
import chdk.ptp.java.exception.CameraConnectionException;
import chdk.ptp.java.exception.GenericCameraException;
import chdk.ptp.java.exception.PTPTimeoutException;
import chdk.ptp.java.model.CameraMode;
import chdk.ptp.java.model.FocusMode;
import com.datazuul.bookscanner.core.devices.CameraFactory;
import com.datazuul.bookscanner.core.workers.CaptureAndSaveWorker;
import java.awt.Adjustable;
import java.awt.Component;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.usb.UsbDisconnectedException;
import javax.usb.UsbException;
import org.apache.commons.lang3.StringUtils;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.Exceptions;

public class ThumbnailsAndScanPanel extends javax.swing.JPanel {

  private ICamera cam1;
  private ICamera cam2;
  private ICamera leftCamera;
  private ICamera rightCamera;

  private int leftNumber = 1;
  private int rightNumber = 2;
  private int nextLeftNumber = 3;
  private int nextRightNumber = 4;

  private String targetDirectory;
  private String lastLeftFilename;
  private String lastRightFilename;

  private boolean setupMode = true;

  public ThumbnailsAndScanPanel() {
    initComponents();
    initCameras();
    targetDirectory = System.getProperty("user.home"); // default
    projectDirectoryPathLabel.setText(targetDirectory);

    String[] formatNames = new String[]{"jpg", "tif", "png"};
    List<String> supportedFormatNames = new ArrayList<>();
    for (String formatName : formatNames) {
      Iterator<ImageWriter> imageWritersByFormatName = ImageIO.getImageWritersByFormatName(formatName);
      if (imageWritersByFormatName.hasNext()) {
        supportedFormatNames.add(formatName);
      }
    }
    formatNamesDropdown.setModel(new DefaultComboBoxModel(supportedFormatNames.toArray()));

    if (cam1 != null && cam2 != null) {
      leftCamera = cam1;
      rightCamera = cam2;
      try {
        leftScanPanel.setCamera(leftCamera);
        leftScanPanel.startLiveView();

        rightScanPanel.setCamera(rightCamera);
        rightScanPanel.startLiveView();
      } catch (UsbDisconnectedException ex) {
        Exceptions.printStackTrace(ex);
      }
    }
    initGlobalKeyshortcuts();

  }

  void destroy() throws CameraConnectionException {
    if (cam1 != null && cam1.isConnected()) {
      cam1.disconnect();
    }
    if (cam2 != null && cam2.isConnected()) {
      cam2.disconnect();
    }
  }

  private void exchangeFiles(String file1Path, String file2Path) {
    File file1 = new File(file1Path);
    File file1Tmp = new File(file1Path + "-tmp");
    file1.renameTo(file1Tmp);

    File file2 = new File(file2Path);
    File file2Tmp = new File(file2Path + "-tmp");
    file2.renameTo(file2Tmp);

    file1Tmp.renameTo(file2);
    file2Tmp.renameTo(file1);
  }

  private void initCameras() {
    try {
      Collection<CameraUsbDevice> cameras = UsbUtils.listAttachedCameras();
      for (CameraUsbDevice cameraUsbDevice : cameras) {
        System.out.println(cameraUsbDevice);
      }
      if (cameras.isEmpty() || cameras.size() == 1) {
        int camerasCount = cameras.size();
        String message;
        if (camerasCount == 0) {
          message = "No cameras ";
        } else {
          message = "Only one camera ";
        }
        NotifyDescriptor d = new NotifyDescriptor(
                message + "found. Please attach two cameras before retrying again.", // Dialog message
                "Warning", // Dialog title
                NotifyDescriptor.DEFAULT_OPTION, // Buttons
                NotifyDescriptor.WARNING_MESSAGE, // Symbol
                null, // Own buttons as Object[]
                null); // Additional buttons as Object[]
        if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.OK_OPTION) {
          initCameras();
        }
        return;
      }

      // now we have two cameras (or more) connected
      cam1 = CameraFactory.getCamera((CameraUsbDevice) cameras.toArray()[0]);
      cam1.connect();
      cam1.setOperationMode(CameraMode.RECORD);
      cam2 = CameraFactory.getCamera((CameraUsbDevice) cameras.toArray()[1]);
      cam2.connect();
      cam2.setOperationMode(CameraMode.RECORD);
    } catch (SecurityException | UsbException ex) {
      Exceptions.printStackTrace(ex);
      NotifyDescriptor d = new NotifyDescriptor(
              "Error getting USB cameras: " + ex.getMessage(), // Dialog message
              "Error", // Dialog title
              NotifyDescriptor.DEFAULT_OPTION, // Buttons
              NotifyDescriptor.ERROR_MESSAGE, // Symbol
              null, // Own buttons as Object[]
              null); // Additional buttons as Object[]
      DialogDisplayer.getDefault().notify(d);
    } catch (CameraConnectionException ex) {
      Exceptions.printStackTrace(ex);
    } catch (PTPTimeoutException | GenericCameraException ex) {
      Exceptions.printStackTrace(ex);
    }
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    directoryPanel = new javax.swing.JPanel();
    projectDirectoryLabel = new javax.swing.JLabel();
    chooseDirectory = new javax.swing.JButton();
    projectDirectoryPathLabel = new javax.swing.JLabel();
    formatNamesDropdown = new javax.swing.JComboBox<>();
    setupModeCheckbox = new javax.swing.JCheckBox();
    thumbnailsScrollPane = new javax.swing.JScrollPane();
    thumbnailsContainerPanel = new javax.swing.JPanel();
    scanPanels = new javax.swing.JPanel();
    leftScanPanel = new com.datazuul.bookscanner.core.ScanPanel();
    middlePanel = new javax.swing.JPanel();
    exchangeScanPanelsBtn = new javax.swing.JButton();
    rightScanPanel = new com.datazuul.bookscanner.core.ScanPanel();
    bottomPanel = new javax.swing.JPanel();
    shootButton = new javax.swing.JButton();

    setLayout(new java.awt.BorderLayout());

    directoryPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    projectDirectoryLabel.setText("Project directory:");
    directoryPanel.add(projectDirectoryLabel);

    chooseDirectory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/folder.gif"))); // NOI18N
    chooseDirectory.setToolTipText("Choose directory for captured images for this project");
    chooseDirectory.setIconTextGap(0);
    chooseDirectory.setMaximumSize(new java.awt.Dimension(35, 29));
    chooseDirectory.setMinimumSize(new java.awt.Dimension(25, 25));
    chooseDirectory.setPreferredSize(new java.awt.Dimension(29, 29));
    chooseDirectory.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        chooseDirectoryActionPerformed(evt);
      }
    });
    directoryPanel.add(chooseDirectory);

    projectDirectoryPathLabel.setMaximumSize(new java.awt.Dimension(32000, 35));
    projectDirectoryPathLabel.setMinimumSize(new java.awt.Dimension(200, 20));
    projectDirectoryPathLabel.setPreferredSize(new java.awt.Dimension(500, 20));
    directoryPanel.add(projectDirectoryPathLabel);

    directoryPanel.add(formatNamesDropdown);

    setupModeCheckbox.setBackground(new java.awt.Color(255, 255, 0));
    setupModeCheckbox.setSelected(true);
    setupModeCheckbox.setText("Setup Mode");
    setupModeCheckbox.setToolTipText("uncheck to start saving images into project folder");
    setupModeCheckbox.setAlignmentX(1.0F);
    setupModeCheckbox.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        setupModeCheckboxStateChanged(evt);
      }
    });
    directoryPanel.add(setupModeCheckbox);

    add(directoryPanel, java.awt.BorderLayout.NORTH);

    thumbnailsScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    thumbnailsScrollPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    thumbnailsScrollPane.setHorizontalScrollBar(null);
    thumbnailsScrollPane.setMinimumSize(new java.awt.Dimension(200, 200));
    thumbnailsScrollPane.setPreferredSize(new java.awt.Dimension(236, 300));

    thumbnailsContainerPanel.setMaximumSize(new java.awt.Dimension(300, 100000));
    thumbnailsContainerPanel.setMinimumSize(new java.awt.Dimension(300, 0));
    thumbnailsContainerPanel.setLayout(new javax.swing.BoxLayout(thumbnailsContainerPanel, javax.swing.BoxLayout.Y_AXIS));
    thumbnailsScrollPane.setViewportView(thumbnailsContainerPanel);

    add(thumbnailsScrollPane, java.awt.BorderLayout.WEST);

    scanPanels.setLayout(new javax.swing.BoxLayout(scanPanels, javax.swing.BoxLayout.X_AXIS));

    leftScanPanel.setAlignmentX(0.5F);
    leftScanPanel.setPreferredSize(new java.awt.Dimension(511, 800));
    scanPanels.add(leftScanPanel);

    exchangeScanPanelsBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exchange-alt.gif"))); // NOI18N
    exchangeScanPanelsBtn.setToolTipText("exchange left and right");
    exchangeScanPanelsBtn.setAlignmentY(1.0F);
    exchangeScanPanelsBtn.setMaximumSize(new java.awt.Dimension(20, 20));
    exchangeScanPanelsBtn.setMinimumSize(new java.awt.Dimension(20, 20));
    exchangeScanPanelsBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        exchangeScanPanelsBtnActionPerformed(evt);
      }
    });
    middlePanel.add(exchangeScanPanelsBtn);

    scanPanels.add(middlePanel);
    scanPanels.add(rightScanPanel);

    add(scanPanels, java.awt.BorderLayout.CENTER);

    bottomPanel.setLayout(new java.awt.BorderLayout());

    shootButton.setBackground(new java.awt.Color(204, 255, 204));
    shootButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/camera.gif"))); // NOI18N
    shootButton.setToolTipText("shoot pictures with both cameras");
    shootButton.setMaximumSize(new java.awt.Dimension(59, 50));
    shootButton.setMinimumSize(new java.awt.Dimension(59, 50));
    shootButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        shootButtonActionPerformed(evt);
      }
    });
    bottomPanel.add(shootButton, java.awt.BorderLayout.CENTER);

    add(bottomPanel, java.awt.BorderLayout.PAGE_END);
  }// </editor-fold>//GEN-END:initComponents

  private void initGlobalKeyshortcuts() {
    // FIXME found no way to attach SPACE-keystroke to shoot button
    // would also have disadvantages: when focus on other buttons you can not use space to click? etc....

//    shootButton.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "Click Me Button");
//    shootButton.getActionMap().put("Click Me Button", shootButton.getAction());
//    Keymap keymap = textField.getKeymap();
//    KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0,
//        false);
//    keymap.removeKeyStrokeBinding(keystroke);
  }

  private void shootButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shootButtonActionPerformed
    shoot();
  }//GEN-LAST:event_shootButtonActionPerformed

  private void exchangeScanPanelsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exchangeScanPanelsBtnActionPerformed
    if (leftCamera == cam1) {
      leftCamera = cam2;
      rightCamera = cam1;
    } else {
      leftCamera = cam1;
      rightCamera = cam2;
    }

    // update scan panels
    leftScanPanel.setCamera(leftCamera);
    rightScanPanel.setCamera(rightCamera);
    BufferedImage rightImage = rightScanPanel.imagePanel.getImage();
    BufferedImage leftImage = leftScanPanel.imagePanel.getImage();
    leftScanPanel.setImage(rightImage);
    rightScanPanel.setImage(leftImage);
    leftScanPanel.repaint();
    rightScanPanel.repaint();

    // update images filenames
    if (lastLeftFilename != null && lastRightFilename != null) {
      String pathLeftFile = targetDirectory + File.separator + lastLeftFilename;
      String pathRightFile = targetDirectory + File.separator + lastRightFilename;
      exchangeFiles(pathLeftFile, pathRightFile);
    }

    // update latest thumbnails panel (if already one exists)
    Component[] thumbnailsPanels = thumbnailsContainerPanel.getComponents();
    if (thumbnailsPanels != null && thumbnailsPanels.length > 0) {
      ThumbnailsPanel lastThumbnailsPanel = (ThumbnailsPanel) thumbnailsPanels[thumbnailsPanels.length - 1];
      ThumbnailPanel leftThumbnailPanel = lastThumbnailsPanel.getLeftThumbnailPanel();
      ThumbnailPanel rightThumbnailPanel = lastThumbnailsPanel.getRightThumbnailPanel();
      BufferedImage leftThumbnailImage = leftThumbnailPanel.getImagePanel().getImage();
      BufferedImage rightThumbnailImage = rightThumbnailPanel.getImagePanel().getImage();
      leftThumbnailPanel.getImagePanel().setImage(rightThumbnailImage);
      rightThumbnailPanel.getImagePanel().setImage(leftThumbnailImage);
      leftThumbnailPanel.repaint();
      rightThumbnailPanel.repaint();
      // update thumbnails filenames
      if (lastLeftFilename != null && lastRightFilename != null) {
        String pathLeftFile = targetDirectory + File.separator + "thumbnails" + File.separator + lastLeftFilename;
        String pathRightFile = targetDirectory + File.separator + "thumbnails" + File.separator + lastRightFilename;
        exchangeFiles(pathLeftFile, pathRightFile);
      }
    }
  }//GEN-LAST:event_exchangeScanPanelsBtnActionPerformed

  private void chooseDirectoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseDirectoryActionPerformed
    File projectDir = new FileChooserBuilder("project-dir").setTitle("Open File").
            setDefaultWorkingDirectory(new File(targetDirectory)).setApproveText("Open").showOpenDialog();
    if (projectDir != null && projectDir.isDirectory() && projectDir.canWrite()) {
      targetDirectory = projectDir.getAbsolutePath();
      projectDirectoryPathLabel.setText(targetDirectory);
      setImageNumbers();
    }
  }//GEN-LAST:event_chooseDirectoryActionPerformed

  private void setImageNumbers() throws NumberFormatException {
    // if directory contains images (resumption of previous scan session) get maximum number
    // and initialize left/right number and next left/right number
    Path targetDirectoryPath = Path.of(targetDirectory);
    final File targetDir = targetDirectoryPath.toFile();
    File[] imageFiles = targetDir.listFiles(new FilenameFilter() {
      @Override
      public boolean accept(File targetDir, String name) {
        return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".tif");
      }
    });
    if (imageFiles != null && imageFiles.length > 0) {
      Arrays.sort(imageFiles);
      File imageFileMaxNumber = imageFiles[(imageFiles.length - 1)];
      String filenameMaxNumber = imageFileMaxNumber.getName();
      Pattern numberPattern = Pattern.compile("image-[0]*(\\d*)\\..*");
      Matcher m = numberPattern.matcher(filenameMaxNumber);
      if (m.find()) {
        int maxNumber = Integer.valueOf(m.group(1));
        
        leftNumber = maxNumber + 1;
        rightNumber = maxNumber + 2;
        nextLeftNumber = maxNumber + 3;
        nextRightNumber = maxNumber + 4;
      }
    }
  }

  private void setupModeCheckboxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_setupModeCheckboxStateChanged
    setupMode = setupModeCheckbox.isSelected();
    if (setupMode) {
      setupModeCheckbox.setOpaque(true);
    } else {
      setupModeCheckbox.setOpaque(false);
      setupModeCheckbox.setEnabled(false);
    }
    
    // reset in either changed state
    setImageNumbers();
  }//GEN-LAST:event_setupModeCheckboxStateChanged

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel bottomPanel;
  private javax.swing.JButton chooseDirectory;
  private javax.swing.JPanel directoryPanel;
  private javax.swing.JButton exchangeScanPanelsBtn;
  private javax.swing.JComboBox<String> formatNamesDropdown;
  private com.datazuul.bookscanner.core.ScanPanel leftScanPanel;
  private javax.swing.JPanel middlePanel;
  private javax.swing.JLabel projectDirectoryLabel;
  private javax.swing.JLabel projectDirectoryPathLabel;
  private com.datazuul.bookscanner.core.ScanPanel rightScanPanel;
  private javax.swing.JPanel scanPanels;
  private javax.swing.JCheckBox setupModeCheckbox;
  private javax.swing.JButton shootButton;
  private javax.swing.JPanel thumbnailsContainerPanel;
  private javax.swing.JScrollPane thumbnailsScrollPane;
  // End of variables declaration//GEN-END:variables

  private void shoot() {
    try {
      if (leftCamera != null && rightCamera != null) {
// FIXME: workaround to make sure live view is not blocking cams/connection
//        if (cam1.isConnected()) {
//          cam1.disconnect();
//        }
//        if (cam2.isConnected()) {
//          cam2.disconnect();
//        }

        leftScanPanel.stopLiveView();
        rightScanPanel.stopLiveView();

        int leftManualFocusValue = -1;
        FocusMode leftFocusMode = leftScanPanel.cameraPanel.getFocusMode();
        if (leftFocusMode == FocusMode.MF) {
          leftManualFocusValue = leftScanPanel.cameraPanel.getManualFocusValue();
        }
        int leftScanRotationDegrees = leftScanPanel.cameraPanel.getRotationDegrees();

        int rightManualFocusValue = -1;
        FocusMode rightFocusMode = rightScanPanel.cameraPanel.getFocusMode();
        if (rightFocusMode == FocusMode.MF) {
          rightManualFocusValue = rightScanPanel.cameraPanel.getManualFocusValue();
        }
        int rightScanRotationDegrees = rightScanPanel.cameraPanel.getRotationDegrees();

        // create new thumbnailPanel to be filled
        ThumbnailsPanel thumbnailsPanel = new ThumbnailsPanel();

        String selectedFormat = (String) formatNamesDropdown.getSelectedItem();
        String imageFormat = selectedFormat;
        String filenameExtension = "." + selectedFormat;
        String leftFilename = "image-" + StringUtils.leftPad(String.valueOf(leftNumber), 5, '0') + filenameExtension;
        String rightFilename = "image-" + StringUtils.leftPad(String.valueOf(rightNumber), 5, '0') + filenameExtension;

        CaptureAndSaveWorker captureAndSaveService1 = new CaptureAndSaveWorker(leftCamera, leftFocusMode, leftManualFocusValue, setupMode, targetDirectory, imageFormat, leftNumber, leftFilename, leftScanRotationDegrees, leftScanPanel.imagePanel, thumbnailsPanel.getLeftThumbnailPanel());
        CaptureAndSaveWorker captureAndSaveService2 = new CaptureAndSaveWorker(rightCamera, rightFocusMode, rightManualFocusValue, setupMode, targetDirectory, imageFormat, rightNumber, rightFilename, rightScanRotationDegrees, rightScanPanel.imagePanel, thumbnailsPanel.getRightThumbnailPanel());
        captureAndSaveService1.execute();
        captureAndSaveService2.execute();

        // capturing and saving has been done, prepare for next shot
        lastLeftFilename = leftFilename;
        lastRightFilename = rightFilename;
        leftNumber += 2;
        rightNumber += 2;

        // handle usecase where a previous shot has been selected to be reshot
        // and therefore these previous (lower) numbers were used for images
        if (leftNumber < nextLeftNumber) {
          leftNumber = nextLeftNumber;
        }
        if (rightNumber < nextRightNumber) {
          rightNumber = nextRightNumber;
        }
        nextLeftNumber = leftNumber;
        nextRightNumber = rightNumber;

        if (!setupMode) {
          thumbnailsContainerPanel.add(thumbnailsPanel);
          thumbnailsContainerPanel.revalidate();
          thumbnailsContainerPanel.repaint();
          thumbnailsScrollPane.revalidate();
          thumbnailsScrollPane.repaint();
          scrollToBottom(thumbnailsScrollPane);
        }
      }
    } catch (SecurityException ex) {
      Logger.getLogger(ThumbnailsAndScanPanel.class
              .getName()).log(Level.SEVERE, (String) null, ex);
    }
  }

  private void scrollToBottom(JScrollPane scrollPane) {
    JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
    AdjustmentListener downScroller = new AdjustmentListener() {
      @Override
      public void adjustmentValueChanged(AdjustmentEvent e) {
        Adjustable adjustable = e.getAdjustable();
        adjustable.setValue(adjustable.getMaximum());
        verticalBar.removeAdjustmentListener(this);
      }
    };
    verticalBar.addAdjustmentListener(downScroller);
  }
}
