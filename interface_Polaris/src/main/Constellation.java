package main;

import javax.swing.JPanel;

/**
 * Classe représentant une zone graphique pour afficher une constellation dans une interface graphique.
 * Elle étend {@link JPanel} et configure la disposition de l'élément graphique.
 * @author RAVEN, Chadi A.
 */
public class Constellation extends javax.swing.JPanel {
    /**
     * Constructeur de la classe {@code Constellation}.
     * Initialise les composants graphiques du panneau, définissant la mise en page.
     */
    public Constellation() {
        initComponents();
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 580, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 280, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
