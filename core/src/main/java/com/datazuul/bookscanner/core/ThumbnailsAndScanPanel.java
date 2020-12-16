package com.datazuul.bookscanner.core;

import chdk.ptp.java.ICamera;
import chdk.ptp.java.connection.CameraUsbDevice;
import chdk.ptp.java.connection.UsbUtils;
import chdk.ptp.java.exception.CameraConnectionException;
import com.datazuul.bookscanner.core.devices.CameraFactory;
import com.datazuul.bookscanner.core.services.CaptureAndSaveService;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.usb.UsbDisconnectedException;
import javax.usb.UsbException;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;

public class ThumbnailsAndScanPanel extends javax.swing.JPanel {

  private ICamera cam1;
  private ICamera cam2;
  
  /**
   * Creates new form ThumbnailsAndScanPanel
   */
  public ThumbnailsAndScanPanel() {
    initCameras();
    initComponents();
    if (cam1 != null && cam2 != null) {
      try {
        this.leftScanPanel.setCamera(cam1);
        this.rightScanPanel.setCamera(cam2);
      } catch (UsbDisconnectedException ex) {
        Exceptions.printStackTrace(ex);
      }
    }
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
      cam2 = CameraFactory.getCamera((CameraUsbDevice) cameras.toArray()[1]);
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
    }
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jScrollPane1 = new javax.swing.JScrollPane();
    thumbnailsList = new javax.swing.JList<>();
    scanPanels = new javax.swing.JPanel();
    leftScanPanel = new com.datazuul.bookscanner.core.ScanPanel();
    rightScanPanel = new com.datazuul.bookscanner.core.ScanPanel();
    bottomPanel = new javax.swing.JPanel();
    shootButton = new javax.swing.JButton();

    setLayout(new java.awt.BorderLayout());

    thumbnailsList.setModel(new javax.swing.AbstractListModel<String>() {
      String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
      public int getSize() { return strings.length; }
      public String getElementAt(int i) { return strings[i]; }
    });
    jScrollPane1.setViewportView(thumbnailsList);

    add(jScrollPane1, java.awt.BorderLayout.LINE_START);

    scanPanels.setLayout(new javax.swing.BoxLayout(scanPanels, javax.swing.BoxLayout.X_AXIS));
    scanPanels.add(leftScanPanel);
    scanPanels.add(rightScanPanel);

    add(scanPanels, java.awt.BorderLayout.CENTER);

    bottomPanel.setLayout(new java.awt.BorderLayout());

    shootButton.setText("shoot!");
    shootButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        shootButtonActionPerformed(evt);
      }
    });
    bottomPanel.add(shootButton, java.awt.BorderLayout.CENTER);

    add(bottomPanel, java.awt.BorderLayout.PAGE_END);
  }// </editor-fold>//GEN-END:initComponents

  private void shootButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shootButtonActionPerformed
    shoot();
  }//GEN-LAST:event_shootButtonActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JPanel bottomPanel;
  private javax.swing.JScrollPane jScrollPane1;
  private com.datazuul.bookscanner.core.ScanPanel leftScanPanel;
  private com.datazuul.bookscanner.core.ScanPanel rightScanPanel;
  private javax.swing.JPanel scanPanels;
  private javax.swing.JButton shootButton;
  private javax.swing.JList<String> thumbnailsList;
  // End of variables declaration//GEN-END:variables

  private void shoot() {
    try {
      if (cam1 != null && cam2 != null) {
        CaptureAndSaveService captureAndSaveService1 = new CaptureAndSaveService(cam1, "png", "image-00001.png");
        CaptureAndSaveService captureAndSaveService2 = new CaptureAndSaveService(cam2, "png", "image-00002.png");

        // do parallel two camera shots using multithreading
        Thread thread1 = new Thread(captureAndSaveService1);
        Thread thread2 = new Thread(captureAndSaveService2);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        BufferedImage cam1Image = captureAndSaveService1.getBufferedImage();
        if (cam1Image != null) {
          this.leftScanPanel.imagePanel.setImage(cam1Image);
          this.leftScanPanel.imagePanel.repaint();
        }
        BufferedImage cam2Image = captureAndSaveService2.getBufferedImage();
        if (cam2Image != null) {
          this.rightScanPanel.imagePanel.setImage(cam2Image);
          this.rightScanPanel.imagePanel.repaint();
        }
        return;
      }
//    } catch (CameraNotFoundException ex) {
//      Logger.getLogger(ThumbnailsAndScanPanel.class.getName()).log(Level.WARNING, "no camera detected");
    } catch (SecurityException | InterruptedException ex) {
      Logger.getLogger(ThumbnailsAndScanPanel.class.getName()).log(Level.SEVERE, (String) null, ex);
    }
    return;
  }
}
