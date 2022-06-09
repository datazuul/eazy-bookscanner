package com.datazuul.eazy.bookscanner.devices;

import chdk.ptp.java.ICamera;
import chdk.ptp.java.connection.CameraUsbDevice;
import com.datazuul.eazy.bookscanner.App;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class CameraFactory {

  public static ICamera getCamera(CameraUsbDevice cameraUsbDevice) {
    CamerasProperties props = App.CONTEXT.getBean(CamerasProperties.class);

    Short idVendor = cameraUsbDevice.getIdVendor();
    Short idProduct = cameraUsbDevice.getIdProduct();

    ConfiguredCamera camera = getCamera(props, idVendor, idProduct, cameraUsbDevice);
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

  private static ConfiguredCamera getCamera(CamerasProperties props, Short idVendor, Short idProduct, CameraUsbDevice cameraUsbDevice) {
    List<Vendor> vendors = props.getVendors();
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
