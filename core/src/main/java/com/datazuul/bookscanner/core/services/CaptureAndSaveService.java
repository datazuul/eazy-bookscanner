package com.datazuul.bookscanner.core.services;

import chdk.ptp.java.ICamera;
import chdk.ptp.java.exception.GenericCameraException;
import chdk.ptp.java.exception.PTPTimeoutException;
import chdk.ptp.java.model.CameraMode;
import chdk.ptp.java.model.FocusMode;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CaptureAndSaveService implements Runnable {

  private BufferedImage bufferedImage;
  private final ICamera camera;
  private final String filename;
  private final String format;

  public CaptureAndSaveService(ICamera camera, String format, String filename) {
    this.camera = camera;
    this.filename = filename;
    this.format = format;
  }

  public BufferedImage getBufferedImage() {
    return bufferedImage;
  }

  @Override
  public void run() {
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
      this.bufferedImage = image;
    } catch (GenericCameraException | PTPTimeoutException | IOException ex) {
      throw new RuntimeException(ex);
    }
  }
}
