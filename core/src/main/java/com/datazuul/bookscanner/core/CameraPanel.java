package com.datazuul.bookscanner.core;

import chdk.ptp.java.ICamera;
import chdk.ptp.java.exception.CameraConnectionException;
import chdk.ptp.java.exception.GenericCameraException;
import chdk.ptp.java.exception.PTPTimeoutException;
import chdk.ptp.java.model.FocusMode;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import javax.swing.SpinnerListModel;
import javax.usb.UsbDisconnectedException;
import javax.usb.UsbException;
import org.openide.util.Exceptions;

public class CameraPanel extends javax.swing.JPanel {

  private boolean zoomSliderInitialized;
  private final SpinnerListModel rotationModel = new SpinnerListModel(Arrays.asList(new Integer[]{-270, -180, -90, 0, 90, 180, 270}));

  /**
   * Creates new form CameraPanel
   */
  public CameraPanel() {
    initComponents();
  }

  public void setCameraName(String cameraName) {
    this.cameraName.setText(cameraName);
  }

  protected int getRotationDegrees() {
    return (int) rotationModel.getValue();
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    focusButtonGroup = new javax.swing.ButtonGroup();
    cameraName = new javax.swing.JLabel();
    zoomSlider = new javax.swing.JSlider();
    zoomLabel = new javax.swing.JLabel();
    rotationSpinner = new javax.swing.JSpinner();
    rotationLabel = new javax.swing.JLabel();
    focusGroupLabel = new javax.swing.JLabel();
    autoFocusRadioButton = new javax.swing.JRadioButton();
    manualFocusRadioButton = new javax.swing.JRadioButton();
    manualFocusSpinner = new javax.swing.JSpinner();

    setMaximumSize(new java.awt.Dimension(32767, 103));

    cameraName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    cameraName.setText("- no camera selected -");

    zoomSlider.setMajorTickSpacing(1);
    zoomSlider.setMaximum(10);
    zoomSlider.setMinorTickSpacing(1);
    zoomSlider.setPaintLabels(true);
    zoomSlider.setPaintTicks(true);
    zoomSlider.setSnapToTicks(true);
    zoomSlider.setValue(0);
    zoomSlider.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        zoomSliderStateChanged(evt);
      }
    });

    zoomLabel.setText("Zoom:");

    rotationSpinner.setModel(rotationModel);
    rotationSpinner.setValue(0);
    rotationSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        rotationSpinnerStateChanged(evt);
      }
    });

    rotationLabel.setLabelFor(rotationSpinner);
    rotationLabel.setText("Rotation:");
    rotationLabel.setToolTipText("degrees each taken image should be rotated");

    focusGroupLabel.setText("Focus:");

    focusButtonGroup.add(autoFocusRadioButton);
    autoFocusRadioButton.setSelected(true);
    autoFocusRadioButton.setText("auto");
    autoFocusRadioButton.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        focusItemChanged(evt);
      }
    });

    focusButtonGroup.add(manualFocusRadioButton);
    manualFocusRadioButton.setText("manual");
    manualFocusRadioButton.setToolTipText("manual setting of focus not implemented, yet");
    manualFocusRadioButton.setEnabled(false);
    manualFocusRadioButton.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        focusItemChanged(evt);
      }
    });

    manualFocusSpinner.setEnabled(false);
    manualFocusSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        manualFocusSpinnerStateChanged(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addGroup(layout.createSequentialGroup()
            .addComponent(zoomLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(zoomSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addComponent(cameraName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addGroup(layout.createSequentialGroup()
            .addComponent(focusGroupLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(autoFocusRadioButton)
              .addGroup(layout.createSequentialGroup()
                .addComponent(manualFocusRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(manualFocusSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))))
          .addGroup(layout.createSequentialGroup()
            .addComponent(rotationLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(rotationSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGap(13, 13, 13)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(cameraName)
              .addComponent(rotationSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(rotationLabel))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(zoomLabel)
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(focusGroupLabel)
                .addComponent(autoFocusRadioButton)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(manualFocusSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(manualFocusRadioButton)))
          .addGroup(layout.createSequentialGroup()
            .addGap(45, 45, 45)
            .addComponent(zoomSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap(12, Short.MAX_VALUE))
    );
  }// </editor-fold>//GEN-END:initComponents

  private void zoomSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_zoomSliderStateChanged
    if (camera != null) {
      int zoom = zoomSlider.getValue();
      try {
        if (zoomSliderInitialized) {
          if (zoom != camera.getZoom()) {
            ScanPanel scanPanel = (ScanPanel) getParent();
            scanPanel.stopLiveView();
            camera.setZoom(zoom);
            scanPanel.startLiveView();
          }
        }
      } catch (PTPTimeoutException | GenericCameraException ex) {
        Exceptions.printStackTrace(ex);
      }
      updateCameraStatus();
    }
  }//GEN-LAST:event_zoomSliderStateChanged

  private void rotationSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rotationSpinnerStateChanged
    Integer rotationDegrees = (Integer) rotationModel.getValue();
    if (rotationModel.getPreviousValue() != rotationDegrees) {
      System.out.println(rotationDegrees);
    }
  }//GEN-LAST:event_rotationSpinnerStateChanged

  private void focusItemChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_focusItemChanged
    try {
      if (autoFocusRadioButton.isSelected()) {
        manualFocusSpinner.setEnabled(false);
        // do nothing on change, read settings during shoot()...
//        if (camera != null) {
//          this.camera.setFocusMode(FocusMode.AUTO);
//        }
      } else {
        manualFocusSpinner.setEnabled(true);
        if (camera != null) {
          // do nothing on change, read settings during shoot()...
//          this.camera.setFocusMode(FocusMode.MF);
          manualFocusSpinner.setValue(this.camera.getFocus());
        }
      }
    } catch (PTPTimeoutException | GenericCameraException ex) {
      Exceptions.printStackTrace(ex);
    }
  }//GEN-LAST:event_focusItemChanged

  private void manualFocusSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_manualFocusSpinnerStateChanged
    // do nothing on change, read settings during shoot()...
//    if (camera != null) {
//      if (manualFocusSpinner.isEnabled()) {
//        int focusValue = (int) manualFocusSpinner.getValue();
//        try {
//          this.camera.setFocus(focusValue);
//        } catch (PTPTimeoutException | GenericCameraException ex) {
//          Exceptions.printStackTrace(ex);
//        }
//      }
//    }
  }//GEN-LAST:event_manualFocusSpinnerStateChanged


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JRadioButton autoFocusRadioButton;
  private javax.swing.JLabel cameraName;
  private javax.swing.ButtonGroup focusButtonGroup;
  private javax.swing.JLabel focusGroupLabel;
  private javax.swing.JRadioButton manualFocusRadioButton;
  private javax.swing.JSpinner manualFocusSpinner;
  private javax.swing.JLabel rotationLabel;
  private javax.swing.JSpinner rotationSpinner;
  private javax.swing.JLabel zoomLabel;
  private javax.swing.JSlider zoomSlider;
  // End of variables declaration//GEN-END:variables

  private ICamera camera;

  public ICamera getCamera() {
    return camera;
  }

  public void setCamera(ICamera camera) {
    if (camera != null) {
      this.camera = camera;

      // connect camera
      if (!this.camera.isConnected()) {
        try {
          this.camera.connect();
        } catch (CameraConnectionException ex) {
          Exceptions.printStackTrace(ex);
        }
      }

      // set camera name
      try {
        String cameraDescription = getCameraDescription(this.camera);
        setCameraName(cameraDescription);
      } catch (UnsupportedEncodingException | UsbException | UsbDisconnectedException ex) {
        Exceptions.printStackTrace(ex);
      }

      updateCameraStatus();
    }
  }

  private void updateCameraStatus() {
    if (camera != null) {
      // set zoom slider
      try {
        zoomSliderInitialized = false;
        zoomSlider.setMinimum(0);
        zoomSlider.setMinorTickSpacing(1);
        zoomSlider.setMaximum(this.camera.getZoomSteps());
        zoomSlider.setPaintLabels(true);
        zoomSlider.setValue(this.camera.getZoom());
        zoomSliderInitialized = true;
      } catch (PTPTimeoutException | GenericCameraException ex) {
        Exceptions.printStackTrace(ex);
      }

      // set focus group
      try {
        FocusMode focusMode = this.camera.getFocusMode();
        if (focusMode.equals(FocusMode.AUTO)) {
          autoFocusRadioButton.setSelected(true);
        } else {
          this.camera.setFocusMode(FocusMode.MF);
          manualFocusRadioButton.setSelected(true);
          manualFocusSpinner.setValue(this.camera.getFocus());
        }
      } catch (PTPTimeoutException | GenericCameraException ex) {
        Exceptions.printStackTrace(ex);
      }
      revalidate();
      repaint();
    }
  }

  private String getCameraDescription(ICamera camera) throws UnsupportedEncodingException, UsbException, UsbDisconnectedException {
    byte portNumber = camera.getUsbDevice().getParentUsbPort().getPortNumber();
    StringBuilder sbCameraName = new StringBuilder();
    sbCameraName.append(camera.getUsbDevice().getProductString());
    sbCameraName.append(" ").append(camera.getCameraInfo().name());
//    sbCameraName.append(" - Serial-Nr.: ").append(camera.getUsbDevice().getSerialNumberString()); // too long, breaks UI
    sbCameraName.append(" - Port: ").append(portNumber);
    return sbCameraName.toString();
  }
  
  public FocusMode getFocusMode() {
    if (autoFocusRadioButton.isSelected()) {
      return FocusMode.AUTO;
    } else {
      return FocusMode.MF;
    }
  }
  
  public int getManualFocusValue() {
    return (int) manualFocusSpinner.getValue();
  }
}
