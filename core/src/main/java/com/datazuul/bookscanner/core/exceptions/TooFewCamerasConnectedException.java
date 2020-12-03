package com.datazuul.bookscanner.core.exceptions;

public class TooFewCamerasConnectedException extends RuntimeException {
  private int camerasCount;

  public TooFewCamerasConnectedException(int amountOfConnectedCameras) {
    this.camerasCount = amountOfConnectedCameras;
  }

  public int getCamerasCount() {
    return camerasCount;
  }
}
