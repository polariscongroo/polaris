package main;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Classe representant une fenêtre permettant de charger un fichier d'image.
 * Cette classe etend {@link JFrame} et permet à l'utilisateur de choisir un fichier d'image,
 * puis enregistre le chemin de ce fichier dans un fichier texte.
 * @author RAVEN, Chadi A.
 */
public class UI extends javax.swing.JFrame{

    private interfacegraphique.Background background;
    private main.Button Polaris_bouton;
    private javax.swing.JButton constellation_bouton_1;
    private javax.swing.JButton constellation_bouton_2;
    private javax.swing.JPanel Panneau_principal;
    private javax.swing.JPanel Panneau_secondaire;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTextArea textarea;
    public String path; // Variable to store the selected image path
    private final int largeur = 651; // Largeur fixe
    private final int hauteur = 330; // Hauteur fixe 

    /**
     * Constructeur de la classe {@code Loader}.
     * Initialise les composants graphiques de l'interface.
     */
    // Creer un panel pour load une image
    public UI() {initComponents();}

    /**
     * Initialise les composants graphiques de l'application.
     * Cette méthode est responsable de la création et de la configuration de tous les composants
     * visuels (boutons, panneaux, champs de texte, etc.) utilisés dans l'interface graphique.
     * Elle configure les propriétés des composants, telles que la couleur, la taille, les actions des boutons,
     * et l'ajout de ces composants à la fenêtre principale.
     */
    private void initComponents() {

        constellation_bouton_2 = new javax.swing.JButton();
        background = new interfacegraphique.Background();
        Polaris_bouton = new main.Button();
        jTabbedPane = new javax.swing.JTabbedPane();
        Panneau_principal = new javax.swing.JPanel();
        constellation_bouton_1 = new javax.swing.JButton();
        Panneau_secondaire = new javax.swing.JPanel();
        jScrollPane = new javax.swing.JScrollPane();
        textarea = new javax.swing.JTextArea();

        constellation_bouton_2.setBackground(new java.awt.Color(0, 0, 0));
        constellation_bouton_2.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        constellation_bouton_2.setForeground(new java.awt.Color(255, 255, 255));
        constellation_bouton_2.setText("Constellation");
        constellation_bouton_2.setBorder(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Polaris_bouton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Polaris_bouton.setForeground(new java.awt.Color(255, 255, 255));
        Polaris_bouton.setText("Polaris");
        Polaris_bouton.setToolTipText("");
        Polaris_bouton.setBorderPainted(true);
        Polaris_bouton.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        Polaris_bouton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Polaris_bouton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonPolaris(evt);
            }
        });

        jTabbedPane.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane.setRequestFocusEnabled(false);

        Panneau_principal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), java.awt.Color.gray, java.awt.Color.gray));
        Panneau_principal.setOpaque(false);
        Panneau_principal.setPreferredSize(new java.awt.Dimension(560, 550));

        constellation_bouton_1.setBackground(new java.awt.Color(0, 0, 0));
        constellation_bouton_1.setFont(new java.awt.Font("Serif", 1, 36)); // NOI18N
        constellation_bouton_1.setForeground(new java.awt.Color(255, 255, 255));
        constellation_bouton_1.setText("Constellation");
        constellation_bouton_1.setBorder(null);
        constellation_bouton_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonconstellation(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(Panneau_principal);
        Panneau_principal.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(constellation_bouton_1, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(constellation_bouton_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jTabbedPane.addTab("", Panneau_principal);

        Panneau_secondaire.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.white, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.gray));
        Panneau_secondaire.setOpaque(false);
        Panneau_secondaire.setRequestFocusEnabled(false);

        textarea.setBackground(new java.awt.Color(0, 0, 0));
        textarea.setColumns(20);
        textarea.setFont(new java.awt.Font("Serif", 2, 24)); // NOI18N
        textarea.setLineWrap(true);
        textarea.setRows(5);
        textarea.setOpaque(false);
        jScrollPane.setViewportView(textarea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(Panneau_secondaire);
        Panneau_secondaire.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("", Panneau_secondaire);

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background);
        background.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jTabbedPane)
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(background1Layout.createSequentialGroup()
                .addGap(183, 183, 183)
                .addComponent(Polaris_bouton, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(Polaris_bouton, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Action declenchee lorsqu'un utilisateur clique sur le bouton de chargement.
     * Permet de selectionner un fichier image à partir d'une boîte de dialogue.
     * Enregistre ensuite le chemin du fichier dans un fichier texte.
     * 
     * @param evt L'evenement lie au clic sur le bouton.
     */
    private void boutonPolaris(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == Polaris_bouton) {
            JFileChooser fileChooser = new JFileChooser();
              
            fileChooser.setCurrentDirectory(new File(".")); // Sets current directory

            int response = fileChooser.showOpenDialog(null); // Select file to open

            if (response == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                path = file.getAbsolutePath(); // Store the file path in the variable
                System.out.println("1. Bouton bien actionné : Path de l'image écrit dans output.txt: " + path);
                Loader.write_in_output(path);
            }
        }    
    }
    
    /**
     * Action déclenchée lorsqu'un utilisateur clique sur le bouton de la constellation.
     * Cette méthode permet de charger une image à partir d'un fichier et de l'afficher dans un panel.
     * Elle met également à jour le contenu d'une zone de texte avec un message descriptif.
     * 
     * @param evt L'événement lié au clic sur le bouton.
     */
    private void boutonconstellation(java.awt.event.ActionEvent evt) {
        // Charger l'image
        BufferedImage img = chargerImage("interface_Polaris/src/interfacegraphique/orsaminor.jpg");
        if (img == null) return;
    
        // Appliquer rotation si nécessaire
        img = ajusterRotationImage(img);
    
        // Redimensionner l'image
        Image scaledImage = redimensionnerImage(img, largeur, hauteur);
    
        // Afficher l'image dans le panel
        afficherImage(scaledImage);
    
        // Charger et afficher le texte
        chargerTexte("baseDDonnees_txt/apus.txt");
    
        System.out.println("L'image et le texte ont été affichés !");
    }
    
    /**
     * Charge une image à partir du chemin donné.
     * @param cheminImage Chemin relatif de l'image
     * @return L'image chargée ou null si échec
     */
    private BufferedImage chargerImage(String cheminImage) {
        try {
            String imgFullPath = new File("").getAbsolutePath() + File.separator + cheminImage;
            return ImageIO.read(new File(imgFullPath));
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Vérifie si l'image est verticale et la fait pivoter si nécessaire.
     * @param img Image d'origine
     * @return Image ajustée
     */
    private BufferedImage ajusterRotationImage(BufferedImage img) {
        if (img.getHeight() > img.getWidth()) {
            AffineTransform tx = AffineTransform.getRotateInstance(Math.PI / 2, img.getWidth() / 2, img.getHeight() / 2);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            return op.filter(img, null);
        }
        return img;
    }
    
    /**
     * Redimensionne une image aux dimensions spécifiées sans la déformer.
     * @param img Image d'origine
     * @param largeur Largeur cible
     * @param hauteur Hauteur cible
     * @return Image redimensionnée
     */
    private Image redimensionnerImage(BufferedImage img, int largeur, int hauteur) {
        return img.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
    }
    
    /**
     * Affiche une image dans le panneau principal.
     * @param image Image à afficher
     */
    private void afficherImage(Image image) {
        Panneau_principal.removeAll();
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        Panneau_principal.setLayout(new BorderLayout());
        Panneau_principal.add(imageLabel, BorderLayout.CENTER);
        Panneau_principal.revalidate();
        Panneau_principal.repaint();
    }
    
    /**
     * Charge et affiche le contenu d'un fichier texte dans la zone de texte.
     * @param cheminTexte Chemin relatif du fichier texte
     */
    private void chargerTexte(String cheminTexte) {
        try {
            String textFullPath = new File("").getAbsolutePath() + File.separator + cheminTexte;
            String contenu = Files.readString(Path.of(textFullPath));
            textarea.setText(contenu);
            System.out.println("Texte affiché : \n" + contenu);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du texte : " + e.getMessage());
        }
    }
    

}