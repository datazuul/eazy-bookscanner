package com.datazuul.eazy.bookscanner.devices;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "cameras")
@ConfigurationPropertiesScan
public class CamerasProperties {

  private List<Vendor> vendors;

  public List<Vendor> getVendors() {
    return vendors;
  }

  public void setVendors(List<Vendor> vendors) {
    this.vendors = vendors;
  }

  @Override
  public String toString() {
    return "CamerasProperties{" + "vendors=" + vendors + '}';
  }
}
