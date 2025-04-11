package com.example.interfacegraphique;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

public class HelloController {

    // Éléments FXML
    @FXML private AnchorPane rootPane;
    @FXML private Label titleLabel;
    @FXML private Button recognitionButton;
    @FXML private ProgressBar progressBar;
    @FXML private Pane contentPane;
    @FXML private MediaView backgroundMediaView;

    // Variables
    private double points = 0;
    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {
        setupBackgroundVideo();
        setupUI();
    }

    private void setupBackgroundVideo() {
        try {
            // Chargement de la vidéo
            URL videoUrl = getClass().getResource("/media/background.mp4");
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

    private void setupUI() {
        // Style des éléments
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        // Gestion de la taille des boutons
        recognitionButton.setPrefSize(200, 60);
        
        // Positionnement initial
        updateUIPosition();
        
        // Écouteurs de redimensionnement
        rootPane.widthProperty().addListener((obs, old, newVal) -> updateUIPosition());
        rootPane.heightProperty().addListener((obs, old, newVal) -> updateUIPosition());
    }

    private void updateUIPosition() {
        double centerX = rootPane.getWidth() / 2;
        double centerY = rootPane.getHeight() / 2;
        
        titleLabel.setLayoutX(centerX - titleLabel.getWidth()/2);
        titleLabel.setLayoutY(rootPane.getHeight() * 0.1);
        
        recognitionButton.setLayoutX(centerX - 220);
        recognitionButton.setLayoutY(centerY - 30);
        
        progressBar.setLayoutX(centerX - progressBar.getWidth()/2);
        progressBar.setLayoutY(rootPane.getHeight() * 0.8);
    }

    @FXML
    private void handleRecognition() {
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

    @FXML
    private void increaseProgress() {
        points = Math.min(1.0, points + 0.1);
        progressBar.setProgress(points);
    }

    @FXML
    private void decreaseProgress() {
        points = Math.max(0.0, points - 0.1);
        progressBar.setProgress(points);
    }

    public void cleanup() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
    }
}