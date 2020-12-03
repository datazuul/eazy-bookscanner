package com.datazuul.bookscanner.core;

import chdk.ptp.java.ICamera;
import chdk.ptp.java.connection.CameraUsbDevice;
import chdk.ptp.java.connection.UsbUtils;
import chdk.ptp.java.exception.CameraConnectionException;
import chdk.ptp.java.exception.CameraShootException;
import chdk.ptp.java.exception.GenericCameraException;
import chdk.ptp.java.exception.PTPTimeoutException;
import chdk.ptp.java.model.CameraMode;
import chdk.ptp.java.model.FocusMode;
import com.datazuul.bookscanner.core.devices.CameraFactory;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.usb.UsbDisconnectedException;
import javax.usb.UsbException;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

public class ThumbnailsAndScanPanel extends javax.swing.JPanel {

  /**
   * Creates new form ThumbnailsAndScanPanel
   */
  public ThumbnailsAndScanPanel() {
    initComponents();
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
      ICamera cam1 = CameraFactory.getCamera((CameraUsbDevice) cameras.toArray()[0]);
      ICamera cam2 = CameraFactory.getCamera((CameraUsbDevice) cameras.toArray()[1]);

      if (cam1 != null && cam2 != null) {
        String cam1Description = getCameraDescription(cam1);
        this.leftScanPanel.cameraPanel.setCameraName(cam1Description.toString());

        String cam2Description = getCameraDescription(cam2);
        this.rightScanPanel.cameraPanel.setCameraName(cam2Description.toString());

        BufferedImage cam1Image = captureAndSaveImage(cam1, "png", "image-00001.png");
        if (cam1Image != null) {
          this.leftScanPanel.imagePanel.setImage(cam1Image);
          this.leftScanPanel.imagePanel.repaint();
        }
        BufferedImage cam2Image = captureAndSaveImage(cam2, "png", "image-00002.png");
        if (cam2Image != null) {
          this.rightScanPanel.imagePanel.setImage(cam2Image);
          this.rightScanPanel.imagePanel.repaint();
        }
        return;
      }
//    } catch (CameraNotFoundException ex) {
//      Logger.getLogger(ThumbnailsAndScanPanel.class.getName()).log(Level.WARNING, "no camera detected");
    } catch (SecurityException ex) {
      Logger.getLogger(ThumbnailsAndScanPanel.class.getName()).log(Level.SEVERE, (String) null, ex);
    } catch (UsbException ex) {
      Logger.getLogger(ThumbnailsAndScanPanel.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
    } catch (Exception ex) {
      Logger.getLogger(ThumbnailsAndScanPanel.class.getName()).log(Level.SEVERE, (String) null, ex);
    }
    return;
  }

  private BufferedImage captureAndSaveImage(ICamera camera, String format, String filename) throws IOException, CameraConnectionException, CameraShootException, PTPTimeoutException, GenericCameraException {
    camera.connect();
    boolean isConnected = true;
    camera.setOperationMode(CameraMode.RECORD);
    camera.setFocusMode(FocusMode.AUTO);
    BufferedImage image = camera.getPicture();
    System.out.println("" + image.getWidth() + " x " + image.getWidth() + " pixels");
    File outputfile = new File(System.getProperty("user.home") + File.separator + filename);
    ImageIO.write(image, format, outputfile);
    System.out.println("saved to " + outputfile.getAbsolutePath());
    camera.disconnect();
    return image;
  }

  private String getCameraDescription(ICamera cam1) throws UnsupportedEncodingException, UsbException, UsbDisconnectedException {
    byte portNumber = cam1.getUsbDevice().getParentUsbPort().getPortNumber();
    StringBuilder sbCameraName = new StringBuilder();
    sbCameraName.append(cam1.getUsbDevice().getProductString());
    sbCameraName.append(" ").append(cam1.getCameraInfo().name());
    sbCameraName.append(" - Port: ").append(portNumber);
    return sbCameraName.toString();
  }
}
