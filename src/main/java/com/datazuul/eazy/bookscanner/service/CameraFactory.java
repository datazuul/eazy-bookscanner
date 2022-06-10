package com.datazuul.eazy.bookscanner.service;

import chdk.ptp.java.ICamera;
import chdk.ptp.java.connection.CameraUsbDevice;
import com.datazuul.eazy.bookscanner.config.CamerasConfig;
import com.datazuul.eazy.bookscanner.config.Product;
import com.datazuul.eazy.bookscanner.config.Vendor;
import com.datazuul.eazy.bookscanner.devices.ConfiguredCamera;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class CameraFactory {

  private final CamerasConfig camerasConfig;

  public CameraFactory(CamerasConfig camerasConfig) {
    this.camerasConfig = camerasConfig;
  }

  public ICamera getCamera(CameraUsbDevice cameraUsbDevice) {
    Short idVendor = cameraUsbDevice.getIdVendor();
    Short idProduct = cameraUsbDevice.getIdProduct();

    ConfiguredCamera camera = getCamera(camerasConfig, idVendor, idProduct, cameraUsbDevice);
    return camera;

//    SupportedCamera sc = SupportedCamera.getCamera(idVendor, idProduct);
//    if (sc != null) {
//      // known camera - create specified class
//      try {
//        UsbDevice usbDevice = cameraUsbDevice.getDevice();
//        return sc.getClazz().getConstructor(UsbDevice.class).newInstance(usbDevice);
//      } catch (Exception ex) {
//        return null;
//      }
//    }
//    return null;
  }

  private static ConfiguredCamera getCamera(CamerasConfig config, Short idVendor, Short idProduct, CameraUsbDevice cameraUsbDevice) {
    List<Vendor> vendors = config.getVendors();
    if (vendors != null) {
      Vendor vendor = vendors.stream().filter(v -> Objects.equals(idVendor, v.getId())).findAny().orElse(null);
      if (vendor != null) {
        System.out.println("found vendor: " + vendor);
        List<Product> products = vendor.getProducts();
        if (products != null) {
          Product product = products.stream().filter(p -> Objects.equals(idProduct, p.getId())).findAny().orElse(null);
          if (product != null) {
            System.out.println("found product: " + product);
            return new ConfiguredCamera(cameraUsbDevice.getDevice(), product);
          }
        }
      }
    }
    return null;
  }
}
