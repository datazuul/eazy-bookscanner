package com.datazuul.bookscanner.core.devices;

import chdk.ptp.java.ICamera;
import chdk.ptp.java.SupportedCamera;
import chdk.ptp.java.connection.CameraUsbDevice;
import javax.usb.UsbDevice;

public class CameraFactory {

  public static ICamera getCamera(CameraUsbDevice cameraUsbDevice) {
    SupportedCamera sc = SupportedCamera.getCamera(cameraUsbDevice.getIdVendor(), cameraUsbDevice.getIdProduct());
    if (sc != null) {
      // known camera - create specified class
      try {
        UsbDevice usbDevice = cameraUsbDevice.getDevice();
        return sc.getClazz().getConstructor(UsbDevice.class).newInstance(usbDevice);
      } catch (Exception ex) {
        return null;
      }
    }
    return null;
  }
}
