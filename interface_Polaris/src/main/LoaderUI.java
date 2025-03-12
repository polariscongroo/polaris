package main;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import javax.imageio.ImageIO;

import javax.swing.*;

/**
 * Classe representant une fenêtre permettant de charger un fichier d'image.
 * Cette classe etend {@link JFrame} et permet à l'utilisateur de choisir un fichier d'image,
 * puis enregistre le chemin de ce fichier dans un fichier texte.
 * @author RAVEN, Chadi A.
 */
public class LoaderUI extends javax.swing.JFrame{

    private interfacegraphique.Background background1;
    private main.Button bouton;
    private javax.swing.JButton constellationshow;
    private javax.swing.JButton constellationshow1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea textarea;
    public String path; // Variable to store the selected image path
    private final int largeur = 651; // Largeur fixe
    private final int hauteur = 330; // Hauteur fixe 

    /**
     * Constructeur de la classe {@code Loader}.
     * Initialise les composants graphiques de l'interface.
     */
    // Creer un panel pour load une image
    public LoaderUI() {
        initComponents();
    }

    /**
     * Effectue une transition en enregistrant le chemin du fichier d'image dans un fichier texte.
     * @param path Le chemin du fichier d'image selectionne.
     */
    public static void write_in_output(String path) {
        try {
            FileWriter writer = new FileWriter("cartography/image_aTraiter/output.txt");
            writer.write(path);
            writer.close();
            File file = new File("cartography/image_aTraiter/output.txt");
            System.out.println("2. Chemin absolu de output.txt : " + file.getAbsolutePath());
            System.out.println("3. Output.txt a été correctement modifié");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
        
    private void initComponents() {

        constellationshow1 = new javax.swing.JButton();
        background1 = new interfacegraphique.Background();
        bouton = new main.Button();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        constellationshow = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textarea = new javax.swing.JTextArea();

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
                boutonPolaris(evt);
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

        textarea.setBackground(new java.awt.Color(0, 0, 0));
        textarea.setColumns(20);
        textarea.setFont(new java.awt.Font("Serif", 2, 24)); // NOI18N
        textarea.setLineWrap(true);
        textarea.setRows(5);
        textarea.setOpaque(false);
        jScrollPane1.setViewportView(textarea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
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

    /**
     * Action declenchee lorsqu'un utilisateur clique sur le bouton de chargement.
     * Permet de selectionner un fichier image à partir d'une boîte de dialogue.
     * Enregistre ensuite le chemin du fichier dans un fichier texte.
     * 
     * @param evt L'evenement lie au clic sur le bouton.
     */
    private void boutonPolaris(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == bouton) {
            JFileChooser fileChooser = new JFileChooser();
              
            fileChooser.setCurrentDirectory(new File(".")); // Sets current directory

            int response = fileChooser.showOpenDialog(null); // Select file to open

            if (response == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                path = file.getAbsolutePath(); // Store the file path in the variable
                System.out.println("1. Bouton bien actionné : Path de l'image écrit dans output.txt: " + path);
                write_in_output(path);
            }
        }    
    }

    public void jPanel1ComponentAdded(java.awt.event.ContainerEvent evt) {

    }
    private void constellationshowActionPerformed(java.awt.event.ActionEvent evt) {
                                             
        BufferedImage img = null;
        try {
            // Récupère le chemin absolu du projet courant
            String imgPath = new File("").getAbsolutePath();

            // Chemin relatif vers le script Python
            String imgRelativePath = "interface_Polaris/src/interfacegraphique/orsaminor.jpg";

            // Construit le chemin complet vers le script
            String imgFullPath0 = imgPath + File.separator + imgRelativePath;
            img = ImageIO.read(new File(imgFullPath0));
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
        try {
        // Récupère le chemin absolu du projet courant
        String textPath = new File("").getAbsolutePath();

        // Chemin relatif vers le script Python
        String textRelativePath = "baseDDonnees_txt/apus.txt";

        // Construit le chemin complet vers le script
        String textFullPath0 = textPath + File.separator + textRelativePath;
        // Spécifier le chemin du fichier
        Path textFullPath1 = Path.of(textFullPath0);

            // Lire le contenu du fichier dans une variable
            String contenu = Files.readString(textFullPath1);
            textarea.setText(contenu);

            // Afficher le contenu
            System.out.println(contenu);
            System.out.println("10. Le texte est écrit correctement dans le panel");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Mettre à jour l'affichage
        jPanel1.revalidate();
        jPanel1.repaint();

        System.out.println("L'image a bien été affichée !");
    }

    private void jPanel1ComponentRemoved(java.awt.event.ContainerEvent evt) {}

    private void constellationshow1ActionPerformed(java.awt.event.ActionEvent evt) {}

    public Button getMonBouton() {
        return bouton;  // Remplace "monBouton" par le vrai nom de ton bouton
    }

}