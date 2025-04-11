package com.example.interfacegraphique;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

public class HelloController {

    // Éléments FXML
    @FXML private BorderPane rootPane;
    @FXML private MediaView backgroundMediaView;

    // Variables
    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {
        setupBackgroundVideo();
    }

    private void setupBackgroundVideo() {
        try {
            // 1. Chargement de la vidéo
            URL videoUrl = getClass().getResource("/images/video_fond.mp4");
            if (videoUrl == null) {
                System.err.println("ERREUR: Fichier vidéo introuvable dans /images/video_fond.mp4");
                return;
            }

            // 2. Création du média
            Media media = new Media(videoUrl.toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            backgroundMediaView.setMediaPlayer(mediaPlayer);

            // 3. Configuration spécifique à BorderPane
            BorderPane rootBorderPane = (BorderPane) backgroundMediaView.getParent(); // Important
            rootBorderPane.getChildren().remove(backgroundMediaView);
            rootBorderPane.getChildren().add(0, backgroundMediaView); // Mise au fond

            // 4. Paramétrage du MediaPlayer
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.1);
            
            // 5. Gestion du redimensionnement
            backgroundMediaView.fitWidthProperty().bind(rootBorderPane.widthProperty());
            backgroundMediaView.fitHeightProperty().bind(rootBorderPane.heightProperty());

            // 6. Lancement quand prêt
            mediaPlayer.setOnReady(() -> {
                System.out.println("Vidéo chargée - Résolution: " 
                    + media.getWidth() + "x" + media.getHeight());
                mediaPlayer.play();
            });

            // 7. Gestion des erreurs
            mediaPlayer.setOnError(() -> {
                System.err.println("Erreur média: " + mediaPlayer.getError().getMessage());
            });

        } catch (Exception e) {
            System.err.println("Échec de l'initialisation vidéo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRecognition() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg"));
        
        File file = fileChooser.showOpenDialog(rootPane.getScene().getWindow());
        if (file != null) {
            try {
                Files.write(Paths.get("output.txt"), 
                          file.getAbsolutePath().getBytes(), 
                          StandardOpenOption.CREATE);
                System.out.println("Fichier enregistré: " + file.getAbsolutePath());
            } catch (Exception e) {
                System.err.println("Erreur d'écriture: " + e.getMessage());
            }
        }
    }
}