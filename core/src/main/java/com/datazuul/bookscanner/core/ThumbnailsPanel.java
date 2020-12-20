package com.datazuul.bookscanner.core;

public class ThumbnailsPanel extends javax.swing.JPanel {

  public ThumbnailsPanel() {
    initComponents();
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    leftThumbnailPanel = new com.datazuul.bookscanner.core.ThumbnailPanel();
    rightThumbnailPanel = new com.datazuul.bookscanner.core.ThumbnailPanel();

    setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
    setMaximumSize(new java.awt.Dimension(400, 150));
    setPreferredSize(new java.awt.Dimension(200, 150));
    setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));

    leftThumbnailPanel.setPreferredSize(new java.awt.Dimension(100, 150));
    add(leftThumbnailPanel);

    rightThumbnailPanel.setPreferredSize(new java.awt.Dimension(100, 150));
    add(rightThumbnailPanel);
  }// </editor-fold>//GEN-END:initComponents


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private com.datazuul.bookscanner.core.ThumbnailPanel leftThumbnailPanel;
  private com.datazuul.bookscanner.core.ThumbnailPanel rightThumbnailPanel;
  // End of variables declaration//GEN-END:variables

  public ThumbnailPanel getLeftThumbnailPanel() {
    return leftThumbnailPanel;
  }

  public ThumbnailPanel getRightThumbnailPanel() {
    return rightThumbnailPanel;
  }
}
