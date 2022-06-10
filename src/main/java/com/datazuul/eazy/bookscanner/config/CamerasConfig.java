package com.datazuul.eazy.bookscanner.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cameras")
public class CamerasConfig {

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
