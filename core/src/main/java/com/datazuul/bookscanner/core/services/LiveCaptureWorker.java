package com.datazuul.bookscanner.core.services;

import chdk.ptp.java.ICamera;
import chdk.ptp.java.exception.CameraConnectionException;
import com.datazuul.bookscanner.core.ImagePanel;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.SwingWorker;
import org.openide.util.Exceptions;

public class LiveCaptureWorker extends SwingWorker<Void, BufferedImage> {

  private ICamera camera;
  private ImagePanel imagePanel;

  public LiveCaptureWorker(ICamera camera, ImagePanel imagePanel) {
    this.camera = camera;
    this.imagePanel = imagePanel;
  }

  @Override
  protected Void doInBackground() throws Exception {
    while (camera.isConnected() && !isCancelled()) {
      try {
        BufferedImage visorImage = camera.getView();
        if (visorImage != null && visorImage.getWidth() > 0 && visorImage.getHeight() > 0) {
          publish(visorImage);
        }
      } catch (CameraConnectionException ex) {
        Exceptions.printStackTrace(ex);
      }
    }
    return null;
  }

  @Override
  protected void process(List<BufferedImage> chunks) {
    BufferedImage mostRecentImage = chunks.get(chunks.size() - 1);
    imagePanel.setImage(mostRecentImage);
    imagePanel.repaint();
  }
}
