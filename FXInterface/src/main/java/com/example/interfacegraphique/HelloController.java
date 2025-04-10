package com.example.interfacegraphique;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HelloController {

    @FXML private Label POLARIS;
    @FXML private ProgressBar progressBar;
    @FXML private AnchorPane PrincipalPane;
    @FXML private Button loadingButton;
    @FXML private Button switchButton;
    @FXML private VBox VB;
    @FXML private Pane panneauNoir;
    @FXML private String path;

    private double points = 0;
    private boolean enModeHistoire = false;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;

    @FXML
    public void initialize() {
        try {
            // Initialiser les composants dans le bon ordre
            setupUIComponents();
            setupVideoPlayer();
            
            // Configurer le redimensionnement
            setupResizeListeners();
            
        } catch (Exception e) {
            System.err.println("Erreur d'initialisation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupResizeListeners() {
        // Écouter les changements de taille
        PrincipalPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0) {
                Platform.runLater(this::updateElementsPosition);
            }
        });
        
        PrincipalPane.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0) {
                Platform.runLater(this::updateElementsPosition);
            }
        });
    }

    private void updateElementsPosition() {
        double paneWidth = PrincipalPane.getWidth();
        double paneHeight = PrincipalPane.getHeight();
        
        // Position centrale avec espacement
        double centerX = paneWidth / 2;
        double centerY = paneHeight / 2;
        
        // Positionner loadingButton à gauche du centre
        loadingButton.setLayoutX(centerX - loadingButton.getWidth() - 20);
        loadingButton.setLayoutY(centerY - loadingButton.getHeight()/2);
        
        // Positionner switchButton à droite du centre
        switchButton.setLayoutX(centerX + 20);
        switchButton.setLayoutY(centerY - switchButton.getHeight()/2);
        
        // Positionner la barre de progression
        progressBar.setLayoutX(centerX - progressBar.getWidth()/2);
        progressBar.setLayoutY(paneHeight * 0.8);
        
        // Positionner le titre
        POLARIS.setLayoutX(centerX - POLARIS.getWidth()/2);
        POLARIS.setLayoutY(paneHeight * 0.1);
    }


    private void setupVideoPlayer() {
        try {
            URL videoUrl = getClass().getResource("/images/video_fond.mp4");
            if (videoUrl != null) {
                Media media = new Media(videoUrl.toExternalForm());
                mediaPlayer = new MediaPlayer(media);
                mediaView = new MediaView(mediaPlayer);

                mediaView.setFitWidth(PrincipalPane.getWidth());
                mediaView.setFitHeight(PrincipalPane.getHeight());
                mediaPlayer.setVolume(0.1);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.setAutoPlay(true);

                PrincipalPane.getChildren().add(0, mediaView);

                PrincipalPane.widthProperty().addListener((obs, oldVal, newVal) -> {
                    mediaView.setFitWidth(newVal.doubleValue());
                });

                PrincipalPane.heightProperty().addListener((obs, oldVal, newVal) -> {
                    mediaView.setFitHeight(newVal.doubleValue());
                });
            }
        } catch (Exception e) {
            System.err.println("Media initialization failed: " + e.getMessage());
        }
    }

    private void setupUIComponents() {
        POLARIS.setFont(Font.font("Arial", 36));
        POLARIS.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

        bindButtonSize();
        PrincipalPane.widthProperty().addListener((obs, oldVal, newVal) -> updateButtonPosition());
        PrincipalPane.heightProperty().addListener((obs, oldVal, newVal) -> updateButtonPosition());
        Platform.runLater(this::updateButtonPosition);
    }

    @FXML
    void addPoints(MouseEvent event) {
        points = points + 0.1;
        progressBar.setProgress(points);
    }

    @FXML
    void subtractPoints(MouseEvent event) {
        points = points - 0.1;
        progressBar.setProgress(points);
    }

    @FXML
    /**
     * Action declenchee lorsqu'un utilisateur clique sur le bouton de chargement.
     * Permet de selectionner un fichier image à partir d'une boîte de dialogue.
     * Enregistre ensuite le chemin du fichier dans un fichier texte.
     * 
     * @param evt L'evenement lie au clic sur le bouton.
     */
    private void onClickButton(ActionEvent event) {
        if (event.getSource() == loadingButton) {
            FileChooser fileChooser = new FileChooser();
            
            // Définir le répertoire initial
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            
            // Ajouter des filtres d'extension si nécessaire
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Fichiers images", "*.jpg", "*.jpeg", "*.png", "*.gif");
            fileChooser.getExtensionFilters().add(extFilter);
            
            // Afficher la boîte de dialogue
            Stage stage = (Stage) loadingButton.getScene().getWindow();
            File file = fileChooser.showOpenDialog(stage);
            
            if (file != null) {
                path = file.getAbsolutePath();
                System.out.println("1. Bouton bien actionné : Path de l'image écrit dans output.txt: " + path);
                
                // Écrire dans le fichier de sortie (version JavaFX)
                try {
                    Files.write(Paths.get("output.txt"), path.getBytes(), StandardOpenOption.CREATE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void bindButtonSize() {
        loadingButton.prefWidthProperty().bind(PrincipalPane.widthProperty().multiply(0.2));
        loadingButton.prefHeightProperty().bind(PrincipalPane.heightProperty().multiply(0.1));
        switchButton.prefWidthProperty().bind(PrincipalPane.widthProperty().multiply(0.15));
        switchButton.prefHeightProperty().bind(PrincipalPane.heightProperty().multiply(0.075));
        
        // Rafraîchir le positionnement quand la taille change
        loadingButton.widthProperty().addListener((obs, oldVal, newVal) -> updateButtonPosition());
        loadingButton.heightProperty().addListener((obs, oldVal, newVal) -> updateButtonPosition());
        switchButton.widthProperty().addListener((obs, oldVal, newVal) -> updateButtonPosition());
        switchButton.heightProperty().addListener((obs, oldVal, newVal) -> updateButtonPosition());
    }

    private void updateButtonPosition() {
        // Attendre que la scène soit disponible
        Platform.runLater(() -> {
            double paneWidth = PrincipalPane.getWidth();
            double paneHeight = PrincipalPane.getHeight();
            
            if (paneWidth > 0 && paneHeight > 0) {
                // Positionnement relatif au centre
                loadingButton.setLayoutX((paneWidth - loadingButton.getWidth()) / 2 - loadingButton.getWidth()/2);
                loadingButton.setLayoutY((paneHeight - loadingButton.getHeight()) / 2);
                
                switchButton.setLayoutX((paneWidth - switchButton.getWidth()) / 2 + loadingButton.getWidth()/2);
                switchButton.setLayoutY((paneHeight - switchButton.getHeight()) / 2);
            }
        });
    }

    @FXML
    private void switchMode() {
        if (enModeHistoire) {
            POLARIS.setText("CONSTELLATION");
            switchButton.setText("HISTOIRE");
            setupConstellationBackground();
        } else {
            POLARIS.setText("HISTOIRE DES CONSTELLATIONS");
            switchButton.setText("CONSTELLATION");
            panneauNoir.setBackground(null);
            panneauNoir.setStyle("-fx-background-color: darkblue;");
        }
        enModeHistoire = !enModeHistoire;
    }

    private void setupConstellationBackground() {
        URL imageUrl = getClass().getResource("/images/constellation.jpg");
        if (imageUrl != null) {
            try {
                Image image = new Image(imageUrl.toExternalForm());
                BackgroundImage bgImage = new BackgroundImage(
                        image,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
                );
                panneauNoir.setBackground(new Background(bgImage));
                panneauNoir.setStyle("-fx-background-color: transparent;");
            } catch (Exception e) {
                System.out.println("Error loading constellation image: " + e.getMessage());
            }
        }
    }

    public void cleanup() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
    }
}