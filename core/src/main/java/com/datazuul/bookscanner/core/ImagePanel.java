package com.datazuul.bookscanner.core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.imgscalr.Scalr;

public class ImagePanel extends javax.swing.JPanel {

  private BufferedImage image;

  /**
   * Creates new form ImagePanel
   */
  public ImagePanel() {
    initComponents();
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 400, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 300, Short.MAX_VALUE)
    );
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  // End of variables declaration//GEN-END:variables
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (this.image != null) {
      Graphics2D g2 = (Graphics2D) g;
      g2.drawImage(this.image, null, 0, (getHeight() - this.image.getHeight()) / 2);
    }
  }

  public void setImage(BufferedImage inputImage) {
    BufferedImage img = inputImage;
    final int targetWidth = this.getWidth();
    final int targetHeight = this.getHeight();
    boolean needsScaling = img.getWidth() != targetWidth || img.getHeight() != targetHeight;
    if (needsScaling && targetWidth > 0 && targetHeight > 0) {
      img = Scalr.resize(img, Scalr.Method.BALANCED, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight, new java.awt.image.BufferedImageOp[0]);
    }
    this.image = img;
  }
}
