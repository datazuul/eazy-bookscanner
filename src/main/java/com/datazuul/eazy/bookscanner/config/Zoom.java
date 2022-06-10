package com.datazuul.eazy.bookscanner.config;

import java.util.List;

public class Zoom {

  private List<String> preExecute;

  public List<String> getPreExecute() {
    return preExecute;
  }

  public void setPreExecute(List<String> preExecute) {
    this.preExecute = preExecute;
  }

  @Override
  public String toString() {
    return "Zoom{" + "preExecute=" + preExecute + '}';
  }
}
