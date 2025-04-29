package com.example.interfacegraphique;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import tsp.polaris.auxiliaries.Functions;
import tsp.polaris.recognition.Recognition;
import tsp.polaris.recognition.starSet.TriangleMatchingException;

public class HelloController {

@FXML private StackPane root;
@FXML private MediaView backgroundMediaView;
@FXML private TextArea consoleOutput;
@FXML private ImageView imageView;
@FXML private Pane orbContainer;
@FXML private ImageView bottomRightImage;
@FXML private Button RECOGNITION;
@FXML private Button CONSTELLATION;

private MediaPlayer mediaPlayer;
private MediaPlayer clickSoundPlayer;
private MediaPlayer musicPlayer;
private boolean isConstellationVisible = false; // État initial : non visible

private String outputpath="cartography/image_aTraiter/output.txt";
private String listeetoilepath="FXInterface/src/main/resources/transmission/liste_etoiles.csv";

@FXML
public void initialize() {
    // Au début, l'image et le texte sont invisibles
    CONSTELLATION.setVisible(false);
    animateButton(RECOGNITION);
    
    animateButton(CONSTELLATION);

    
    setupBackgroundVideo();
    setupLoaderandPolaris();
}

private void animateButton(Button button) {
    Timeline timeline = new Timeline(
        new KeyFrame(Duration.seconds(0), event -> button.getStyleClass().add("button-container")),
        new KeyFrame(Duration.seconds(1), event -> button.getStyleClass().remove("button-container")),
        new KeyFrame(Duration.seconds(1), event -> button.getStyleClass().add("button-effect"))
    );
    timeline.setCycleCount(Timeline.INDEFINITE); // Répète l'animation indéfiniment
    timeline.play();
}

private void setupLoaderandPolaris() {
    bottomRightImage.setImage(new Image(getClass().getResource("/images/polaris 2.png").toExternalForm()));
    playBackgroundMusic();
    
    LuminousOrb orb = new LuminousOrb();
    orbContainer.getChildren().add(orb);
    System.out.println("Loader activé au démarrage.");
}

@FXML
public void handleRecognition(ActionEvent event) throws NumberFormatException, TriangleMatchingException, IOException {
    eraser(outputpath);
    eraser(listeetoilepath);
    Object source = event.getSource();
    Stage primaryStage = (Stage) ((Node) source).getScene().getWindow();
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File("."));
    File selectedFile = fileChooser.showOpenDialog(primaryStage);
    if (selectedFile != null) {
        String path = selectedFile.getAbsolutePath();
        System.out.println("[Java] 1. Bouton bien actionné : Path de l'image dans output.txt: " + path);
        write_in_output(path);
        Task<Void> recognitionTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String projectPath = new File("").getAbsolutePath();
                String scriptPath = projectPath + File.separator + "cartography" + File.separator + "ThresholdDetectMethod.py";
                System.out.println("[Java] 4. Lancement du script Python...");
                ProcessBuilder pb = new ProcessBuilder("python3", scriptPath, path);
                pb.directory(new File(projectPath));
                pb.redirectErrorStream(true);
                Process process = pb.start();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("[Python] " + line);
                    }
                }
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    System.out.println("[Java] Script Python terminé. Lancement de la reconnaissance...");
                    Recognition.run();
                } else {
                    throw new RuntimeException("Script Python échoué avec code : " + exitCode);
                }
                return null;
            }
        };
        new Thread(recognitionTask).start();
    } else {
        System.out.println("Aucun fichier sélectionné.");
    }
}

@FXML
public void handleConstellation(ActionEvent event) throws IOException, IllegalArgumentException {
    if (isConstellationVisible) {
        System.out.println("Constellation déjà visible.");
        imageView.setVisible(false);
        orbContainer.setVisible(true);
        isConstellationVisible = false;
        consoleOutput.setOpacity(0.0);

    }else{
        String name = Functions.lireLigneUnique("FXInterface/src/main/resources/transmission/name.txt");
        System.out.println("[Java] 11. Nom de la constellation : " + name);
        try {
            InputStream imageStream = getClass().getResourceAsStream("/images/output.png");
            if (imageStream == null) {
                System.err.println("Image non trouvée dans les ressources");
                return;
            }
            Image image = new Image(imageStream);
            imageView.setImage(image);
            imageView.setVisible(true);
            consoleOutput.setOpacity(1.0);
            chargerTexte("/baseDDonnees_txt/" + name + ".txt");
            System.out.println("Affichage réussi !");
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
    //on inverse de l'état
    isConstellationVisible = !isConstellationVisible;
}

@FXML
public void handleClose(ActionEvent event) {
    eraser(outputpath);
    eraser(listeetoilepath);
    System.out.println("Fermeture de l'application...");
    Stage stage = (Stage) root.getScene().getWindow();
    stage.close();
}

@FXML
public void handleMaximiser(ActionEvent event) {
    Stage stage = (Stage) root.getScene().getWindow();
    stage.setMaximized(true);
    System.out.println("Fenêtre maximisée");
}

// Méthode pour minimiser la fenêtre
public void handleMinimize(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setIconified(true); // Met la fenêtre en petit écran
}

@FXML
public void handleRestore(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setMaximized(false); // Remet la fenêtre en petite taille
    System.out.println("Fenêtre restaurée à sa taille normale.");
}

@FXML
public void eraser(String path) {
    try {
        FileWriter writer = new FileWriter(path);
        writer.write("");
        writer.close();
        System.out.println("File content erased: " + path);
    } catch (IOException e) {
        System.err.println("An error occurred while erasing the file: " + e.getMessage());
        e.printStackTrace();
    }
}

private void setupBackgroundVideo() {
    try {
        URL videoUrl = getClass().getResource("/images/cassio.mp4");
        if (videoUrl == null) {
            System.err.println("ERREUR: Fichier vidéo introuvable dans /images/propEmma.mp4");
            return;
        }
        Media media = new Media(videoUrl.toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        backgroundMediaView.setMediaPlayer(mediaPlayer);
        root.getChildren().remove(backgroundMediaView);
        root.getChildren().add(0, backgroundMediaView);
        backgroundMediaView.fitWidthProperty().bind(root.widthProperty());
        backgroundMediaView.fitHeightProperty().bind(root.heightProperty());
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.1);
        mediaPlayer.setOnReady(() -> {
            System.out.println("Vidéo chargée - Résolution: " + media.getWidth() + "x" + media.getHeight());
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
private void playBackgroundMusic() {
    try {
        URL musicUrl = getClass().getResource("/audio/Spore - Galaxy Ambience.mp3");
        if (musicUrl == null) {
            System.err.println("ERREUR: FXInterface/src/main/resources/audio/Spore - Galaxy Ambience.mp3");
            return;
        }
        Media media = new Media(musicUrl.toExternalForm());
        musicPlayer = new MediaPlayer(media);
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        musicPlayer.setVolume(0.5);
        musicPlayer.play();
        System.out.println("Musique de fond démarrée.");
    } catch (Exception e) {
        System.err.println("Erreur lors de la lecture de la musique : " + e.getMessage());
        e.printStackTrace();
    }
}

@FXML
public void playMusicOnButtonClick(ActionEvent event) {
    try {
        if (clickSoundPlayer == null) {
            URL musicUrl = getClass().getResource("/audio/clickSound.mp3");
            if (musicUrl == null) {
                System.err.println("ERREUR: Fichier audio introuvable dans /audio/clickSound.mp3");
                return;
            }
            Media media = new Media(musicUrl.toExternalForm());
            clickSoundPlayer = new MediaPlayer(media);
            clickSoundPlayer.setOnEndOfMedia(() -> {
                clickSoundPlayer.stop();
            });
        }
        clickSoundPlayer.stop();
        clickSoundPlayer.play();
        System.out.println("Musique déclenchée par le bouton.");
    } catch (Exception e) {
        System.err.println("Erreur lors de la lecture de la musique : " + e.getMessage());
        e.printStackTrace();
    }
}

@FXML
public void playMusicOnButtonHover(ActionEvent event) {
    try {
        if (clickSoundPlayer == null) {
            URL musicUrl = getClass().getResource("/audio/hover3.mp3");
            if (musicUrl == null) {
                System.err.println("ERREUR: Fichier audio introuvable dans /audio/hover3.mp3");
                return;
            }
            Media media = new Media(musicUrl.toExternalForm());
            clickSoundPlayer = new MediaPlayer(media);
            clickSoundPlayer.setOnEndOfMedia(() -> {
                clickSoundPlayer.stop();
            });
        }
        clickSoundPlayer.stop();
        clickSoundPlayer.play();
        System.out.println("Musique déclenchée par le bouton.");
    } catch (Exception e) {
        System.err.println("Erreur lors de la lecture de la musique : " + e.getMessage());
        e.printStackTrace();
    }
}

@FXML
public void handleRecognitionAndPlayMusic(ActionEvent event) throws NumberFormatException, TriangleMatchingException, IOException {
    playMusicOnButtonClick(event);
    playMusicOnButtonHover(event);
    try {
        Thread.sleep(500);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    handleRecognition(event);

    // Ajout important après reconnaissance
    RECOGNITION.setVisible(false);
    orbContainer.setVisible(false);
    CONSTELLATION.setVisible(true);
   
}

  
@FXML
public void handleMaximiserAndPlayMusic(ActionEvent event) throws NumberFormatException, TriangleMatchingException, IOException {
    playMusicOnButtonClick(event);
    playMusicOnButtonHover(event);
    try {
        Thread.sleep(500);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    handleMaximiser(event);
}

@FXML
public void handleConstellationAndPlayMusic(ActionEvent event) throws NumberFormatException, TriangleMatchingException, IOException {
    playMusicOnButtonClick(event);
    playMusicOnButtonHover(event);
    try {
        Thread.sleep(500);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    handleConstellation(event);
}

@FXML
public void handleMaximiseAndPlayMusic(ActionEvent event) throws NumberFormatException, TriangleMatchingException, IOException {
    playMusicOnButtonClick(event);
    playMusicOnButtonHover(event);
    try {
        Thread.sleep(500);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    handleMaximiser(event);
}



private void chargerTexte(String cheminTexte) {
    try {
        String textFullPath = new File("").getAbsolutePath() + File.separator + cheminTexte;
        String contenu = Files.readString(Path.of(textFullPath));
        consoleOutput.setText(contenu);
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
        System.out.println("[Java] 2. Chemin absolu de output.txt : " + file.getAbsolutePath());
        System.out.println("[Java] 3. Output.txt a été correctement modifié");
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
            String scriptPath = projectPath + File.separator + "cartography" + File.separator + "ThresholdDetectMethod.py";
            System.out.println("[Java] 4. Lancement du script Python...");
            ProcessBuilder pb = new ProcessBuilder("python3", scriptPath, filePath);
            pb.directory(new File(projectPath));
            pb.redirectErrorStream(true);
            try {
                Process process = pb.start();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("[Python] " + line);
                    }
                }
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    System.out.println("[Java] 5. Script exécuté avec succès !");
                    try {
                        runJavaScript();
                    } catch (NumberFormatException | TriangleMatchingException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("[Java] 5. Erreur (code " + exitCode + ")");
                }
            } catch (IOException | InterruptedException e) {
                System.err.println("[Java] ERREUR: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
    };
    new Thread(pythonTask).start();
}

private void runJavaScript() throws TriangleMatchingException, NumberFormatException, IOException {
    Recognition.run();
}
}
