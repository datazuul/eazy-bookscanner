package com.datazuul.eazy.bookscanner.devices;

import java.util.List;

public class FocusModeChange {

  private int currentMode;
  private List<String> execute;

  public int getCurrentMode() {
    return currentMode;
  }

  public List<String> getExecute() {
    return execute;
  }

  public void setCurrentMode(int currentMode) {
    this.currentMode = currentMode;
  }

  public void setExecute(List<String> execute) {
    this.execute = execute;
  }

  @Override
  public String toString() {
    return "FocusModeChange{" + "currentMode=" + currentMode + ", execute=" + execute + '}';
  }
}
