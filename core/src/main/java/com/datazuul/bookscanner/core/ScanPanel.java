package com.datazuul.bookscanner.core;

import chdk.ptp.java.ICamera;

public class ScanPanel extends javax.swing.JPanel {

  /**
   * Creates new form ScanPanel
   */
  public ScanPanel() {
    initComponents();
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    cameraPanel = new com.datazuul.bookscanner.core.CameraPanel();
    imagePanel = new com.datazuul.bookscanner.core.ImagePanel();

    setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
    add(cameraPanel);

    javax.swing.GroupLayout imagePanelLayout = new javax.swing.GroupLayout(imagePanel);
    imagePanel.setLayout(imagePanelLayout);
    imagePanelLayout.setHorizontalGroup(
      imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 400, Short.MAX_VALUE)
    );
    imagePanelLayout.setVerticalGroup(
      imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 285, Short.MAX_VALUE)
    );

    add(imagePanel);
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  protected com.datazuul.bookscanner.core.CameraPanel cameraPanel;
  protected com.datazuul.bookscanner.core.ImagePanel imagePanel;
  // End of variables declaration//GEN-END:variables

  private ICamera camera;

  public ICamera getCamera() {
    return camera;
  }

  public void setCamera(ICamera camera) {
    this.camera = camera;
    cameraPanel.setCamera(camera);
  }
}
