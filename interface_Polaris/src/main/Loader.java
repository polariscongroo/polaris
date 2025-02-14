package main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * Classe représentant une fenêtre permettant de charger un fichier d'image.
 * Cette classe étend {@link JFrame} et permet à l'utilisateur de choisir un fichier d'image,
 * puis enregistre le chemin de ce fichier dans un fichier texte.
 * @author RAVEN, Chadi A.
 */
public class Loader extends javax.swing.JFrame {
    /**
     * Constructeur de la classe {@code Loader}.
     * Initialise les composants graphiques de l'interface.
     */
    // Créer un panel pour load une image
    public Loader() {
        initComponents();
    }
    /**
     * Effectue une transition en enregistrant le chemin du fichier d'image dans un fichier texte.
     * @param path Le chemin du fichier d'image sélectionné.
     */
    public static void transition(String path) {
        try {
            FileWriter writer = new FileWriter("output.txt");
            writer.write(path);
            writer.close();
            File file = new File("output.txt");
            System.out.println("Chemin absolu du fichier : " + file.getAbsolutePath());
            System.out.println("Successfully wrote text to file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new interfacegraphique.Background();
        panel = new javax.swing.JPanel();
        bouton = new main.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        background1.setBlur(panel);

        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.setForeground(new java.awt.Color(255, 255, 255));
        panel.setOpaque(false);

        bouton.setBorder(null);
        bouton.setForeground(new java.awt.Color(255, 255, 255));
        bouton.setText("Load ...");
        bouton.setToolTipText("");
        bouton.setFont(new java.awt.Font("Serif", 0, 24)); // NOI18N
        bouton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bouton, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bouton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup()
                .addContainerGap(240, Short.MAX_VALUE)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(240, Short.MAX_VALUE))
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    public String path; // Variable to store the selected image path
    /**
     * Action déclenchée lorsqu'un utilisateur clique sur le bouton de chargement.
     * Permet de sélectionner un fichier image à partir d'une boîte de dialogue.
     * Enregistre ensuite le chemin du fichier dans un fichier texte.
     * 
     * @param evt L'événement lié au clic sur le bouton.
     */
    private void boutonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonActionPerformed
        if (evt.getSource() == bouton) {
            JFileChooser fileChooser = new JFileChooser();
              
            fileChooser.setCurrentDirectory(new File(".")); // Sets current directory

            int response = fileChooser.showOpenDialog(null); // Select file to open

            if (response == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                path = file.getAbsolutePath(); // Store the file path in the variable
                System.out.println("Selected image path: " + path);
                transition(path);
            }
        }    
    }//GEN-LAST:event_boutonActionPerformed
    /**
     * Point d'entrée principal de l'application.
     * Crée et affiche la fenêtre de l'application.
     * 
     * @param args Les arguments de la ligne de commande.
     * @throws IOException 
     * @throws InterruptedException 
     */             
public static void main(String args[]) throws IOException, InterruptedException {
    // Crée et affiche l'interface
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new Loader().setVisible(true);
        }
    });
    //Build command 
    List<String> commands = new ArrayList<String>();
    commands.add("python");
    //Add arguments
    commands.add("/Users/chadiaitekioui/Coding/Polaris/polaris/cartography/ThresholdDetectMethod.py");
    System.out.println(commands);
    //Run macro on target
    ProcessBuilder pb = new ProcessBuilder(commands);
    pb.directory(new File("/Users/chadiaitekioui/Coding/Polaris/polaris"));
    pb.redirectErrorStream(true);
    Process process = pb.start();

    if (process.waitFor() == 0) {
        System.out.println("Success!!!!!!!");
    }
    else {
        System.out.println("error!!!!!!!");
    }
}
    //private final JPanel pan = new JPanel();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private interfacegraphique.Background background1;
    private main.Button bouton;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
