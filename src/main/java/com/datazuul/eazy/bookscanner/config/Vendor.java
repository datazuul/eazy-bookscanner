package com.datazuul.eazy.bookscanner.config;

import java.util.List;

public class Vendor {

  private Short id;
  private String name;
  private List<Product> products;

  public Short getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setId(Short id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  @Override
  public String toString() {
    return "Vendor{" + "id=" + id + ", name=" + name + ", products=" + products + '}';
  }
}
