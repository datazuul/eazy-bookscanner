package com.datazuul.bookscanner.core.services;

import chdk.ptp.java.ICamera;
import chdk.ptp.java.exception.GenericCameraException;
import chdk.ptp.java.exception.PTPTimeoutException;
import chdk.ptp.java.model.CameraMode;
import chdk.ptp.java.model.FocusMode;
import com.datazuul.bookscanner.core.ImagePanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javax.imageio.ImageIO;
import javax.swing.SwingWorker;
import org.openide.util.Exceptions;

public class CaptureAndSaveWorker extends SwingWorker<BufferedImage, Void> {

  private final ICamera camera;
  private final String filename;
  private final String format;
  private ImagePanel imagePanel;

  public CaptureAndSaveWorker(ICamera camera, String format, String filename, ImagePanel imagePanel) {
    this.camera = camera;
    this.filename = filename;
    this.format = format;
    this.imagePanel = imagePanel;
  }

  @Override
  protected BufferedImage doInBackground() throws Exception {
    try {
      if (!camera.isConnected()) {
        camera.connect();
      }
      camera.setOperationMode(CameraMode.RECORD);
      camera.setFocusMode(FocusMode.AUTO);
      BufferedImage image = camera.getPicture();
      System.out.println("" + image.getWidth() + " x " + image.getWidth() + " pixels");

      // save full size scan
      File outputfile = new File(System.getProperty("user.home") + File.separator + filename);
      ImageIO.write(image, format, outputfile);
      System.out.println("saved to " + outputfile.getAbsolutePath());

      // save thumbnail
//      camera.disconnect();
      return image;
    } catch (GenericCameraException | PTPTimeoutException | IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  protected void done() {
    try {
      BufferedImage image = get();
      imagePanel.setImage(image);
      imagePanel.repaint();
    } catch (InterruptedException | ExecutionException ex) {
      // if interrupted do nothing
    }
  }

}
