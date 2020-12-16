package com.datazuul.bookscanner.core.services;

import chdk.ptp.java.ICamera;
import chdk.ptp.java.exception.CameraConnectionException;
import com.datazuul.bookscanner.core.ImagePanel;
import java.awt.image.BufferedImage;
import org.openide.util.Exceptions;

public class LiveCaptureService implements Runnable {

  private ICamera camera;
  private ImagePanel imagePanel;

  public LiveCaptureService(ICamera camera, ImagePanel imagePanel) {
    this.camera = camera;
    this.imagePanel = imagePanel;
  }

  @Override
  public void run() {
    while (camera.isConnected()) {
      try {
        BufferedImage visorImage = camera.getView();
        imagePanel.setImage(visorImage);
        imagePanel.repaint();
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      } catch (CameraConnectionException ex) {
        Exceptions.printStackTrace(ex);
      }
    }
  }
}
