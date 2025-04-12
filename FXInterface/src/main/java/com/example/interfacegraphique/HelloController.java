package com.example.interfacegraphique;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HelloController {

    // Éléments FXML
    @FXML private StackPane root;
    @FXML private MediaView backgroundMediaView;
    @FXML private final int largeur = 600; // Largeur fixe
    @FXML private final int hauteur = 400; // Hauteur fixe 
    @FXML private TextArea consoleOutput; // Pour afficher les logs
    // Variables
    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() { setupBackgroundVideo();}

    private void setupBackgroundVideo() {
        try {
            URL videoUrl = getClass().getResource("/images/video_fond.mp4");
            if (videoUrl == null) {
                System.err.println("ERREUR: Fichier vidéo introuvable dans /images/video_fond.mp4");
                return;
            }
    
            Media media = new Media(videoUrl.toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            backgroundMediaView.setMediaPlayer(mediaPlayer);
    
            // S'assure que le MediaView est bien en arrière-plan
            root.getChildren().remove(backgroundMediaView);
            root.getChildren().add(0, backgroundMediaView); // Index 0 = tout au fond
    
            // Resize auto
            backgroundMediaView.fitWidthProperty().bind(root.widthProperty());
            backgroundMediaView.fitHeightProperty().bind(root.heightProperty());
    
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.1);
    
            mediaPlayer.setOnReady(() -> {
                System.out.println("Vidéo chargée - Résolution: " 
                    + media.getWidth() + "x" + media.getHeight());
                mediaPlayer.play();
            });
    
            mediaPlayer.setOnError(() -> {
                System.err.println("Erreur média: " + mediaPlayer.getError().getMessage());
            });
    
        } catch (Exception e) {
            System.err.println("Échec de l'initialisation vidéo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRecognition(ActionEvent event) {
        // Vérifie que l'action vient bien du bon bouton si nécessaire (optionnel ici)
        Object source = event.getSource();
    
        // Récupère la fenêtre principale (équivalent à null dans JFileChooser)
        Stage primaryStage = (Stage) ((Node) source).getScene().getWindow();
    
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(".")); // Dossier par défaut
    
        // Ouvre la boîte de dialogue
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
    
        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath(); // Chemin du fichier
            System.out.println("1. Bouton bien actionné : Path de l'image écrit dans output.txt: " + path);
            write_in_output(path); // Appel à ta méthode personnalisée
                // Traitement du fichier sélectionné
            runPythonScript(path);
        }
    }
    
    @FXML
    public void handleConstellation(ActionEvent event) {
        // Charger l'image
        Image image = chargerImage("FXInterface/src/main/resources/images/orsaminor.jpg");
        if (image == null) return;
    
        // Appliquer rotation si nécessaire
        image = ajusterRotationImage(image);
    
        // Redimensionner l'image
        Image fxImage = redimensionnerImage(image, largeur, hauteur); // JavaFX Image attendue
    
        // Afficher l'image
        afficherImage(fxImage);
    
        // Charger et afficher le texte associé
        chargerTexte("baseDDonnees_txt/apus.txt");
    
        System.out.println("L'image et le texte ont été affichés !");
    }
    
    /**
     * Charge une image à partir du chemin donné.
     * @param cheminImage Chemin relatif de l'image
     * @return L'image chargée ou null si échec
     */
    private Image chargerImage(String cheminImage) {
        try {
            String imgFullPath = new File("").getAbsolutePath() + File.separator + cheminImage;
            return new Image("file:" + imgFullPath);  // Charger une image JavaFX directement
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'image : " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Vérifie si l'image est verticale et la fait pivoter si nécessaire.
     * @param img Image d'origine
     * @return Image ajustée
     */
    private Image ajusterRotationImage(Image img) {
        // Si l'image est en mode portrait (hauteur > largeur), on la fait pivoter
        if (img.getHeight() > img.getWidth()) {
            // Créer un ImageView pour l'image
            ImageView imageView = new ImageView(img);
            
            // Appliquer une rotation de 90 degrés autour du centre de l'image
            Rotate rotation = new Rotate(90, img.getWidth() / 2, img.getHeight() / 2);
            imageView.getTransforms().add(rotation);

            // Retourner l'image transformée (appliquée à l'ImageView)
            // L'ImageView est maintenant la représentation de l'image avec rotation appliquée
            return imageView.getImage();
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
    private Image redimensionnerImage(Image img, int largeur, int hauteur) {
        // Redimensionner l'image JavaFX avec un ratio d'aspect respecté
        return new Image(img.getUrl(), largeur, hauteur, true, true);
    }
    
    /**
     * Affiche une image dans une ImageView JavaFX.
     * @param image Image à afficher
     */
    private void afficherImage(Image image) {
        // Utilisation d'un ImageView pour afficher l'image
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(largeur);  // Largeur désirée
        imageView.setFitHeight(hauteur); // Hauteur désirée
        imageView.setPreserveRatio(true); // Conserver le ratio d'aspect
    
        // Ajouter l'ImageView à un StackPane ou autre container
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(imageView);
        // Ici, vous pouvez utiliser une méthode pour ajouter cela au layout JavaFX existant, par exemple :
        // mainLayout.getChildren().add(stackPane); ou l'afficher dans un autre conteneur comme un VBox.
    }
    
    /**
     * Charge et affiche le contenu d'un fichier texte dans la zone de texte.
     * @param cheminTexte Chemin relatif du fichier texte
     */
    private void chargerTexte(String cheminTexte) {
        try {
            String textFullPath = new File("").getAbsolutePath() + File.separator + cheminTexte;
            String contenu = Files.readString(Path.of(textFullPath));
            // Afficher le contenu du texte dans un TextArea (si présent)
            TextArea textarea = new TextArea(contenu);
            textarea.setEditable(false);  // Le texte est en lecture seule
            System.out.println("Texte affiché : \n" + contenu);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement du texte : " + e.getMessage());
        }
    }

	private static void write_in_output(String path) {
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
    
    private void runPythonScript(String filePath) {
        Task<Void> pythonTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String projectPath = new File("").getAbsolutePath();
                String scriptPath = projectPath + File.separator + "cartography/ThresholdDetectMethod.py";

                updateMessage("Lancement du script Python...");

                ProcessBuilder pb = new ProcessBuilder("python3", scriptPath, filePath); // On passe le chemin du fichier
                pb.directory(new File(projectPath));
                pb.redirectErrorStream(true);

                try {
                    Process process = pb.start();

                    // Lecture des sorties
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            updateMessage(line); // Envoie à l'interface
                        }
                    }

                    int exitCode = process.waitFor();
                    if (exitCode == 0) {
                        updateMessage("Script exécuté avec succès !");
                    } else {
                        updateMessage("Erreur (code " + exitCode + ")");
                    }
                } catch (IOException | InterruptedException e) {
                    updateMessage("ERREUR: " + e.getMessage());
                }
                return null;
            }
        };

        pythonTask.messageProperty().addListener((obs, oldVal, newVal) -> {
            Platform.runLater(() -> {
                consoleOutput.appendText(newVal + "\n");
            });
        });

        new Thread(pythonTask).start();
    }

}