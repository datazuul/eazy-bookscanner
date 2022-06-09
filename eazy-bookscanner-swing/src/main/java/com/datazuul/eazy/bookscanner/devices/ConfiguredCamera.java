package com.datazuul.eazy.bookscanner.devices;

import chdk.ptp.java.camera.FailSafeCamera;
import chdk.ptp.java.exception.GenericCameraException;
import chdk.ptp.java.exception.PTPTimeoutException;
import chdk.ptp.java.model.FocusMode;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.usb.UsbDevice;

public class ConfiguredCamera extends FailSafeCamera {

  private Logger log = Logger.getLogger(ConfiguredCamera.class.getName());

  private final Product product;

  public ConfiguredCamera(UsbDevice device, Product product) {
    super(device);
    this.product = product;
  }

  @Override
  public void setFocusMode(FocusMode mode) throws GenericCameraException, PTPTimeoutException {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      log.log(Level.SEVERE, e.getLocalizedMessage(), e);
      throw new GenericCameraException(e.getLocalizedMessage());
    }

    // currently only supporting autofocus mode
    if (FocusMode.AUTO.equals(mode)) {
      List<FocusModeChange> autofocus = product.getAutofocus();
      int currentFocusMode = getFocusMode().getValue();
      for (FocusModeChange focusModeChange : autofocus) {
        if (currentFocusMode == focusModeChange.getCurrentMode()) {
          List<String> commands = focusModeChange.getExecute();
          if (commands != null) {
            for (String command : commands) {
              switch (command) {
                case "LEFT":
                  this.executeLuaCommand("click('left');");
                  break;
                case "RIGHT":
                  this.executeLuaCommand("click('right');");
                  break;
                case "SET":
                  this.executeLuaCommand("click('set');");
                  break;
                default:
                  break;
              }
              if (command.startsWith("sleep")) {
                long milliseconds = Long.valueOf(command.substring(5));
                try {
                  Thread.sleep(milliseconds);
                } catch (InterruptedException ex) {
                  log.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
                  throw new GenericCameraException(ex.getLocalizedMessage());
                }
              }
            }
          }
          // activate auto focus and focus
          try {
            this.executeLuaCommand("set_aflock(0);");
            this.executeLuaCommand("press('shoot_half');");
            Thread.sleep(800);
            this.executeLuaCommand("release('shoot_half');");
            this.executeLuaCommand("set_aflock(1);");
            Thread.sleep(1000);
          } catch (InterruptedException ex) {
            log.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
            throw new GenericCameraException(ex.getLocalizedMessage());
          }
        }
      }
    }
  }

  @Override
  public void setZoom(int zoomPosition) throws PTPTimeoutException, GenericCameraException {
    Zoom zoom = product.getZoom();
    if (zoom != null) {
      List<String> preExecute = zoom.getPreExecute();
      for (String command : preExecute) {
        switch (command) {
          case "SET_AUTOFOCUS":
            setFocusMode(FocusMode.AUTO);
            break;
          default:
            break;
        }
      }
    }
    super.setZoom(zoomPosition);
  }
}
