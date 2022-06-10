package com.datazuul.eazy.bookscanner.workers;

import chdk.ptp.java.ICamera;
import chdk.ptp.java.exception.GenericCameraException;
import chdk.ptp.java.exception.PTPTimeoutException;
import chdk.ptp.java.model.CameraMode;
import chdk.ptp.java.model.FocusMode;
import com.datazuul.eazy.bookscanner.gui.ImagePanel;
import com.datazuul.eazy.bookscanner.gui.ThumbnailPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import javax.imageio.ImageIO;
import javax.swing.SwingWorker;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Rotation;

public class CaptureAndSaveWorker extends SwingWorker<CaptureAndSaveWorker.ImageAndThumbnail, Void> {

  private final ICamera camera;
  private final String filename;
  private final FocusMode focusMode;
  private final String format;
  private final ImagePanel imagePanelFull;
  private final int manualFocusValue;
  private final int number;
  private final int rotationDegrees;
  private final boolean setupMode;
  private final String targetDirectory;
  private final ThumbnailPanel thumbnailPanel;

  public CaptureAndSaveWorker(ICamera camera, FocusMode focusMode, int manualFocusValue, boolean setupMode, String targetDirectory, String format, int number, String filename, int rotationDegrees, ImagePanel imagePanelFull, ThumbnailPanel thumbnailPanel) {
    this.camera = camera;
    this.filename = filename;
    this.focusMode = focusMode;
    this.format = format;
    this.imagePanelFull = imagePanelFull;
    this.manualFocusValue = manualFocusValue;
    this.number = number;
    this.rotationDegrees = rotationDegrees;
    this.setupMode = setupMode;
    this.targetDirectory = targetDirectory;
    this.thumbnailPanel = thumbnailPanel;
  }

  @Override
  protected ImageAndThumbnail doInBackground() throws Exception {
    BufferedImage capturedImage;
    BufferedImage rotatedImage;
    BufferedImage thumbnailImage;

    try {
      if (!camera.isConnected()) {
        camera.connect();
      }
      camera.setOperationMode(CameraMode.RECORD);

      if (focusMode == FocusMode.AUTO) {
        camera.setFocusMode(FocusMode.AUTO);
      }
      if (focusMode == FocusMode.MF) {
        camera.setFocusMode(FocusMode.MF);
        camera.setFocus(manualFocusValue);
      }
      capturedImage = camera.getPicture();
      System.out.println("before rotate: " + capturedImage.getWidth() + " x " + capturedImage.getHeight() + " pixels");
      if (rotationDegrees != 0) {
        Rotation rotation = null;
        switch (rotationDegrees) {
          case -270:
            rotation = Rotation.CW_90;
            break;
          case -90:
            rotation = Rotation.CW_270;
            break;
          case 90:
            rotation = Rotation.CW_90;
            break;
          case 180:
            rotation = Rotation.CW_180;
            break;
          case 270:
            rotation = Rotation.CW_270;
            break;
          default:
            rotation = null;
        }
        if (rotation != null) {
          rotatedImage = Scalr.rotate(capturedImage, rotation);
          capturedImage.flush(); // see javadoc of rotate method...
        } else {
          rotatedImage = capturedImage;
        }
      } else {
        rotatedImage = capturedImage;
      }
      System.out.println("after rotate: " + rotatedImage.getWidth() + " x " + rotatedImage.getHeight() + " pixels");

      if (!setupMode) {
        // save full size scan
        File outputfile = new File(targetDirectory + File.separator + filename);
        ImageIO.write(rotatedImage, format, outputfile);
        System.out.println("saved to " + outputfile.getAbsolutePath());

        // save thumbnail
        String targetDirectoryThumbnails = targetDirectory + File.separator + "thumbnails";
        File directory = new File(targetDirectoryThumbnails);
        if (!directory.exists()) {
          directory.mkdir();
        }
        File thumbnailOutputfile = new File(targetDirectoryThumbnails + File.separator + filename);
        thumbnailImage = Scalr.resize(rotatedImage, Scalr.Method.BALANCED, Scalr.Mode.FIT_TO_WIDTH, 100, 150, new java.awt.image.BufferedImageOp[0]);
        ImageIO.write(thumbnailImage, format, thumbnailOutputfile);
        System.out.println("thumbnail saved to " + thumbnailOutputfile.getAbsolutePath());
        return new ImageAndThumbnail(rotatedImage, thumbnailImage);
      } else {
        return new ImageAndThumbnail(rotatedImage, null);
      }
//      camera.disconnect();
    } catch (GenericCameraException | PTPTimeoutException | IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  protected void done() {
    try {
      ImageAndThumbnail imageAndThumbnail = get();
      imagePanelFull.setImage(imageAndThumbnail.getFullsizeImage());
      imagePanelFull.repaint();

      thumbnailPanel.getLabel().setText(String.valueOf(number));
      thumbnailPanel.setNumber(number);
      thumbnailPanel.getImagePanel().setImage(imageAndThumbnail.getThumbnailImage());
      thumbnailPanel.repaint();
    } catch (InterruptedException | ExecutionException ex) {
      // if interrupted do nothing
    }
  }

  protected class ImageAndThumbnail {

    private final BufferedImage fullsizeImage;
    private final BufferedImage thumbnailImage;

    protected ImageAndThumbnail(BufferedImage fullsizeImage, BufferedImage thumbnailImage) {
      this.fullsizeImage = fullsizeImage;
      this.thumbnailImage = thumbnailImage;
    }

    public BufferedImage getFullsizeImage() {
      return fullsizeImage;
    }

    public BufferedImage getThumbnailImage() {
      return thumbnailImage;
    }

  }
}
