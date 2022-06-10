package com.datazuul.eazy.bookscanner.config;

import java.util.List;

public class Product {

  private List<FocusModeChange> autofocus;
  private Short id;
  private String name;
  private Zoom zoom;

  public List<FocusModeChange> getAutofocus() {
    return autofocus;
  }

  public Short getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Zoom getZoom() {
    return zoom;
  }

  public void setAutofocus(List<FocusModeChange> autofocus) {
    this.autofocus = autofocus;
  }

  public void setId(Short id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setZoom(Zoom zoom) {
    this.zoom = zoom;
  }

  @Override
  public String toString() {
    return "Product{" + "autofocus=" + autofocus + ", id=" + id + ", name=" + name + ", zoom=" + zoom + '}';
  }
}
