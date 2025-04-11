package com.example.interfacegraphique;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

public class HelloController {

    // Éléments FXML
    @FXML private AnchorPane rootPane;
    @FXML private MediaView backgroundMediaView;

    // Variables
    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {
        setupBackgroundVideo();
    }

    private void setupBackgroundVideo() {
        try {
            // Chargement de la vidéo
            URL videoUrl = getClass().getResource("/images/video_fond.mp4");
            if (videoUrl == null) {
                System.err.println("ERREUR: Ressource vidéo non trouvée");
                return;
            }
    
            Media media = new Media(videoUrl.toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            backgroundMediaView.setMediaPlayer(mediaPlayer);
    
            // Configuration pour être en arrière-plan
            backgroundMediaView.toBack(); // Force le plan le plus bas
    
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.1);
            
            mediaPlayer.setOnReady(() -> {
                System.out.println("Vidéo prête - dimensions: " 
                    + media.getWidth() + "x" + media.getHeight());
                mediaPlayer.play();
            });
    
            // Liaison des dimensions
            backgroundMediaView.fitWidthProperty().bind(rootPane.widthProperty());
            backgroundMediaView.fitHeightProperty().bind(rootPane.heightProperty());
    
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