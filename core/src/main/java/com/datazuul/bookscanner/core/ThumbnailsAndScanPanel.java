package com.datazuul.bookscanner.core;

import chdk.ptp.java.ICamera;
import chdk.ptp.java.connection.CameraUsbDevice;
import chdk.ptp.java.connection.UsbUtils;
import chdk.ptp.java.exception.CameraConnectionException;
import chdk.ptp.java.exception.GenericCameraException;
import chdk.ptp.java.exception.PTPTimeoutException;
import chdk.ptp.java.model.CameraMode;
import com.datazuul.bookscanner.core.devices.CameraFactory;
import com.datazuul.bookscanner.core.services.CaptureAndSaveWorker;
import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.usb.UsbDisconnectedException;
import javax.usb.UsbException;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;

public class ThumbnailsAndScanPanel extends javax.swing.JPanel {

  private ICamera cam1;
  private ICamera cam2;

  /**
   * map containing all global actions
   */
  private HashMap<KeyStroke, Action> actionMap = new HashMap<KeyStroke, Action>();

  /**
   * Creates new form ThumbnailsAndScanPanel
   */
  public ThumbnailsAndScanPanel() {
    initCameras();
    initComponents();
//    thumbnailsContainerPanel.add(Box.createRigidArea(new Dimension(150,10)));

    if (cam1 != null && cam2 != null) {
      try {
        leftScanPanel.setCamera(cam1);
        leftScanPanel.startLiveView();

        rightScanPanel.setCamera(cam2);
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
        DialogDisplayer.getDefault().notify(d);
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

    thumbnailsScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    thumbnailsScrollPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    thumbnailsScrollPane.setHorizontalScrollBar(null);
    thumbnailsScrollPane.setMinimumSize(new java.awt.Dimension(200, 200));

    thumbnailsContainerPanel.setMaximumSize(new java.awt.Dimension(300, 100000));
    thumbnailsContainerPanel.setMinimumSize(new java.awt.Dimension(300, 0));
    thumbnailsContainerPanel.setLayout(new javax.swing.BoxLayout(thumbnailsContainerPanel, javax.swing.BoxLayout.Y_AXIS));
    thumbnailsScrollPane.setViewportView(thumbnailsContainerPanel);

    add(thumbnailsScrollPane, java.awt.BorderLayout.WEST);

    scanPanels.setLayout(new javax.swing.BoxLayout(scanPanels, javax.swing.BoxLayout.X_AXIS));
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
    ICamera leftCamera = leftScanPanel.getCamera();
    BufferedImage leftImage = leftScanPanel.imagePanel.getImage();
    ICamera rightCamera = rightScanPanel.getCamera();
    BufferedImage rightImage = rightScanPanel.imagePanel.getImage();

    leftScanPanel.setCamera(rightCamera);
    leftScanPanel.setImage(rightImage);

    rightScanPanel.setCamera(leftCamera);
    rightScanPanel.setImage(leftImage);
  }//GEN-LAST:event_exchangeScanPanelsBtnActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel bottomPanel;
  private javax.swing.JButton exchangeScanPanelsBtn;
  private com.datazuul.bookscanner.core.ScanPanel leftScanPanel;
  private javax.swing.JPanel middlePanel;
  private com.datazuul.bookscanner.core.ScanPanel rightScanPanel;
  private javax.swing.JPanel scanPanels;
  private javax.swing.JButton shootButton;
  private javax.swing.JPanel thumbnailsContainerPanel;
  private javax.swing.JScrollPane thumbnailsScrollPane;
  // End of variables declaration//GEN-END:variables

  private void shoot() {
    try {
      if (cam1 != null && cam2 != null) {
        leftScanPanel.stopLiveView();
        rightScanPanel.stopLiveView();

        int leftScanRotationDegrees = leftScanPanel.cameraPanel.getRotationDegrees();
        int rightScanRotationDegrees = rightScanPanel.cameraPanel.getRotationDegrees();
        // FIXME: workaround to make sure live view is not blocking cams/connection
//        if (cam1.isConnected()) {
//          cam1.disconnect();
//        }
//        if (cam2.isConnected()) {
//          cam2.disconnect();
//        }
        // create new thumbnailPanel to be filled
        ThumbnailsPanel thumbnailsPanel = new ThumbnailsPanel();
        CaptureAndSaveWorker captureAndSaveService1 = new CaptureAndSaveWorker(cam1, "png", "image-00001.png", leftScanRotationDegrees, leftScanPanel.imagePanel, thumbnailsPanel.getLeftImagePanel());
        CaptureAndSaveWorker captureAndSaveService2 = new CaptureAndSaveWorker(cam2, "png", "image-00002.png", rightScanRotationDegrees, rightScanPanel.imagePanel, thumbnailsPanel.getRightImagePanel());

        captureAndSaveService1.execute();
        captureAndSaveService2.execute();

        thumbnailsContainerPanel.add(thumbnailsPanel);

        thumbnailsContainerPanel.revalidate();
        thumbnailsContainerPanel.repaint();
        thumbnailsScrollPane.revalidate();
        thumbnailsScrollPane.repaint();
        scrollToBottom(thumbnailsScrollPane);
        return;
      }
//    } catch (CameraNotFoundException ex) {
//      Logger.getLogger(ThumbnailsAndScanPanel.class.getName()).log(Level.WARNING, "no camera detected");
    } catch (SecurityException ex) {
      Logger.getLogger(ThumbnailsAndScanPanel.class
              .getName()).log(Level.SEVERE, (String) null, ex);
    }
    return;
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
