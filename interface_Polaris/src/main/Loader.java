package main;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import static java.lang.Math.PI;
import javax.imageio.ImageIO;

import javax.swing.*;
import static javax.swing.GroupLayout.Alignment.CENTER;

/**
 * Classe representant une fenêtre permettant de charger un fichier d'image.
 * Cette classe etend {@link JFrame} et permet à l'utilisateur de choisir un fichier d'image,
 * puis enregistre le chemin de ce fichier dans un fichier texte.
 * @author RAVEN, Chadi A.
 */
public class Loader extends javax.swing.JFrame{
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
            FileWriter writer = new FileWriter("../cartography/image_aTraiter/output.txt");
            writer.write(path);
            writer.close();
            File file = new File("../cartography/image_aTraiter/output.txt");
            System.out.println("2. Chemin absolu de output.txt : " + file.getAbsolutePath());
            System.out.println("3. Output.txt a été correctement modifié");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        constellationshow1 = new javax.swing.JButton();
        background1 = new interfacegraphique.Background();
        bouton = new main.Button();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        constellationshow = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        constellationshow2 = new javax.swing.JButton();

        constellationshow1.setBackground(new java.awt.Color(0, 0, 0));
        constellationshow1.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        constellationshow1.setForeground(new java.awt.Color(255, 255, 255));
        constellationshow1.setText("Constellation");
        constellationshow1.setBorder(null);
        constellationshow1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                constellationshow1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bouton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        bouton.setForeground(new java.awt.Color(255, 255, 255));
        bouton.setText("Polaris");
        bouton.setToolTipText("");
        bouton.setBorderPainted(true);
        bouton.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        bouton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bouton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonActionPerformed(evt);
            }
        });

        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane1.setRequestFocusEnabled(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), java.awt.Color.gray, java.awt.Color.gray));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(560, 550));
        jPanel1.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jPanel1ComponentAdded(evt);
            }
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                jPanel1ComponentRemoved(evt);
            }
        });

        constellationshow.setBackground(new java.awt.Color(0, 0, 0));
        constellationshow.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        constellationshow.setForeground(new java.awt.Color(255, 255, 255));
        constellationshow.setText("Constellation");
        constellationshow.setBorder(null);
        constellationshow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                constellationshowActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(constellationshow, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(constellationshow, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jTabbedPane1.addTab("", jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.gray));
        jPanel2.setOpaque(false);
        jPanel2.setRequestFocusEnabled(false);

        constellationshow2.setBackground(new java.awt.Color(0, 0, 0));
        constellationshow2.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        constellationshow2.setForeground(new java.awt.Color(255, 255, 255));
        constellationshow2.setText("Histoire");
        constellationshow2.setBorder(null);
        constellationshow2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                constellationshow2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(constellationshow2, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(constellationshow2, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("", jPanel2);

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jTabbedPane1)
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(background1Layout.createSequentialGroup()
                .addGap(183, 183, 183)
                .addComponent(bouton, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(bouton, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                .addGap(24, 24, 24))
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
                System.out.println("1. Bouton bien actionné : Path de l'image écrit dans output.txt: " + path);
                transition(path);
            }
        }    
    }//GEN-LAST:event_boutonActionPerformed

    public void jPanel1ComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jPanel1ComponentAdded

    }//GEN-LAST:event_jPanel1ComponentAdded
    private int largeur = 651; // Largeur fixe
    private int hauteur = 330; // Hauteur fixe 
    private void constellationshowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_constellationshowActionPerformed
                                             
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("/Users/chadiaitekioui/Coding/Polaris/polaris/interface_Polaris/src/interfacegraphique/orsaminor.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            return; // Stop si l'image n'est pas trouvée
        }

        if (img == null) {
            System.err.println("Image non chargée !");
            return;
        }

        // Vérifier si l'image est verticale, si oui, la tourner
        if (img.getHeight() > img.getWidth()) {
            AffineTransform tx = AffineTransform.getRotateInstance(Math.PI / 2, img.getWidth() / 2, img.getHeight() / 2);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            img = op.filter(img, null);
        }

        // Redimensionner l'image pour qu'elle tienne dans le cadre (sans déformation)
        Image scaledImage = img.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);

        // Supprimer les anciens composants pour éviter que ça s'empile
        jPanel1.removeAll();

        // Créer le JLabel avec l'image
        JLabel imageLabel = new JLabel(icon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        // Utiliser BorderLayout pour centrer l'image dans le cadre
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(imageLabel, BorderLayout.CENTER);

        // Mettre à jour l'affichage
        jPanel1.revalidate();
        jPanel1.repaint();

        System.out.println("L'image a bien été affichée !");
    }//GEN-LAST:event_constellationshowActionPerformed

    private void jPanel1ComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jPanel1ComponentRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1ComponentRemoved

    private void constellationshow1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_constellationshow1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_constellationshow1ActionPerformed

    private void constellationshow2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_constellationshow2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_constellationshow2ActionPerformed
    public Button getMonBouton() {
        return bouton;  // Remplace "monBouton" par le vrai nom de ton bouton
    }
    /**
     * Point d'entree principal de l'application.
     * Cree et affiche la fenêtre de l'application.
     * 
     * @param args Les arguments de la ligne de commande.
     * @throws IOException 
     * @throws InterruptedException 
     */             
    public static void main(String args[]) throws IOException, InterruptedException {
        // Crée et affiche l'interface
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Loader loader = new Loader();  // Création de la fenêtre
                loader.setVisible(true);
 
                // 🔹 Attendre que l'interface soit bien affichée avant d'appliquer le flou
                /*SwingUtilities.invokeLater(() -> {
                    Button myButton = loader.getMonBouton();  // Récupérer le bouton
                    myButton.setBlur(loader.getContentPane());  // Appliquer le flou sur l'arrière-plan du bouton
                });*/
            }
        });
        String pathString;
        pathString = "../cartography/image_aTraiter";
        // Chemin vers le dossier contenant output.txt
        Path dir = Paths.get(pathString);
        // Création du WatchService
        WatchService watchService = FileSystems.getDefault().newWatchService();
        // Enregistrement du dossier pour surveiller les modifications de fichiers
        dir.register(watchService, ENTRY_MODIFY);

        System.out.println("0. Surveillance de modifications de output.txt pour relancer le python");

        long lastModifiedTime = 0;
        while (true) {
            // Récupérer les événements du watch service
            WatchKey key = watchService.take();

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                // Récupère le nom du fichier modifié
                Path fileName = (Path) event.context();

                // Si c'est output.txt qui a été modifié
                if (kind == ENTRY_MODIFY && fileName.toString().equals("output.txt")) {
                    long currentModifiedTime = Files.getLastModifiedTime(dir.resolve(fileName)).toMillis();
                    if (currentModifiedTime - lastModifiedTime > 1000) { // 1 seconde de délai
                        lastModifiedTime = currentModifiedTime;
                        System.out.println("4. Dans la boucle infinie de l'interface. Lancement du script Python imminent");

                        // Lancement du script Python
                        try {
                            Thread.sleep(500);
                            // Récupère le chemin absolu du projet courant
                            String projectPath = new File("").getAbsolutePath();

                            // Chemin relatif vers le script Python
                            String scriptRelativePath = "../cartography/unix/dist/ThresholdDetectMethod";

                            // Construit le chemin complet vers le script
                            String scriptFullPath = projectPath + File.separator + scriptRelativePath;

                            // Définir la commande pour lancer le script Python
                            List<String> commands = new ArrayList<>();
                            // "python3" pour macOS/Linux, "python" pour Windows.
                            //commands.add("python3");
                            commands.add(scriptFullPath);

                            // Vérifie la commande construite
                            System.out.println("5. Commande exécutée : " + commands);

                            // Crée le ProcessBuilder avec le dossier du projet comme répertoire de travail
                            ProcessBuilder pb = new ProcessBuilder(commands);
                            pb.directory(new File(projectPath));
                            pb.redirectErrorStream(true); // Fusionne la sortie d'erreur avec la sortie standard

                            // Lance le processus
                            Process process = pb.start();

                            // Capturer la sortie du processus
                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                            }

                            // Vérifie si l'exécution est réussie
                            if (process.waitFor() == 0) {
                                System.out.println("10. Script python exécuté avec succès !");
                            } else {
                                System.out.println("6. Une erreur est survenue lors de l'exécution du script.");
                            }
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private interfacegraphique.Background background1;
    private main.Button bouton;
    private javax.swing.JButton constellationshow;
    private javax.swing.JButton constellationshow1;
    private javax.swing.JButton constellationshow2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
