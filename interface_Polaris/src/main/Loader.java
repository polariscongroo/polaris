package main;
import java.io.File;
import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * Classe representant une fenêtre permettant de charger un fichier d'image.
 * Cette classe etend {@link JFrame} et permet à l'utilisateur de choisir un fichier d'image,
 * puis enregistre le chemin de ce fichier dans un fichier texte.
 * @author RAVEN, Chadi A.
 */
public class Loader extends javax.swing.JFrame {
    /**
     * Constructeur de la classe {@code Loader}.
     * Initialise les composants graphiques de l'interface.
     */
    // Creer un panel pour load une image
    public Loader() {
        initComponents();
    }
    /**
     * Effectue une transition en enregistrant le chemin du fichier d'image dans un fichier texte.
     * @param path Le chemin du fichier d'image selectionne.
     */
    public static void transition(String path) {
        try {
            FileWriter writer = new FileWriter("cartography/image_aTraiter/output.txt");
            writer.write(path);
            writer.close();
            File file = new File("cartography/image_aTraiter/output.txt");
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
     * Action declenchee lorsqu'un utilisateur clique sur le bouton de chargement.
     * Permet de selectionner un fichier image à partir d'une boîte de dialogue.
     * Enregistre ensuite le chemin du fichier dans un fichier texte.
     * 
     * @param evt L'evenement lie au clic sur le bouton.
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
     * Point d'entree principal de l'application.
     * Cree et affiche la fenêtre de l'application.
     * 
     * @param args Les arguments de la ligne de commande.
     * @throws IOException 
     * @throws InterruptedException 
     */             
    public static void main(String args[]) throws IOException, InterruptedException {
        // Cree et affiche l'interface
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Loader().setVisible(true);
            }
        });
        String pathString;
        pathString = "cartography/image_aTraiter";
        // Chemin vers le dossier contenant output.txt
        Path dir = Paths.get(pathString);
        // Création du WatchService
        WatchService watchService = FileSystems.getDefault().newWatchService();
        // Enregistrement du dossier pour surveiller les modifications de fichiers
        dir.register(watchService, ENTRY_MODIFY);
    
        System.out.println("Surveillance de output.txt...");
    
        while (true) {
            // Récupérer les événements du watch service
            WatchKey key = watchService.take();
    
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
    
                // Récupère le nom du fichier modifié
                Path fileName = (Path) event.context();
    
                // Si c'est output.txt qui a été modifié
                if (kind == ENTRY_MODIFY && fileName.toString().equals("output.txt")) {
                    System.out.println("output.txt a été modifié. Lancement du script Python...");
    
                    // Lancement du script Python
                    try {
                        Thread.sleep(500);  
                        //Build command 
                        //Récupère le chemin absolu du projet courant
                        String projectPath = new File("").getAbsolutePath();
    
                        // Chemin relatif vers le script Python
                        String scriptRelativePath = "cartography/ThresholdDetectMethod.py";
    
                        // Construit le chemin complet vers le script
                        String scriptFullPath = projectPath + File.separator + scriptRelativePath;
    
                        // Définir la commande pour lancer le script Python
                        List<String> commands = new ArrayList<>();
                        // "python3" pour macOS/Linux, "python" pour Windows.
                        commands.add("python3"); 
                        commands.add(scriptFullPath);
    
                        // Vérifie la commande construite
                        System.out.println("Commande exécutée : " + commands);
    
                        // Crée le ProcessBuilder avec le dossier du projet comme répertoire de travail
                        ProcessBuilder pb = new ProcessBuilder(commands);
                        pb.directory(new File(projectPath));
                        pb.redirectErrorStream(true); // Fusionne la sortie d'erreur avec la sortie standard
    
                        // Lance le processus
                        Process process = pb.start();
    
                        // Vérifie si l'exécution est réussie
                        if (process.waitFor() == 0) {
                            System.out.println("Script exécuté avec succès !");
                        } else {
                            System.out.println("Une erreur est survenue lors de l'exécution du script.");
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            // Réinitialise la clé pour continuer à écouter
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }
    //private final JPanel pan = new JPanel();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private interfacegraphique.Background background1;
    private main.Button bouton;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
