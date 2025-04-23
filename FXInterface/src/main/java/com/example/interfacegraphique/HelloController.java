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

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import tsp.polaris.recognition.Recognition;
import tsp.polaris.recognition.starSet.TriangleMatchingException;

public class HelloController {

    // Éléments FXML
    @FXML private StackPane root;
    @FXML private MediaView backgroundMediaView;
    @FXML private TextArea consoleOutput; // Pour afficher les logs
    @FXML private ImageView imageView; // Pour afficher l'image
    @FXML private Pane orbContainer;

    // Variables
    private MediaPlayer mediaPlayer;

    private MediaPlayer clickSoundPlayer;
    
    private MediaPlayer musicPlayer;

    // Nettoyage des fichiers de sortie
    private String outputpath="cartography/image_aTraiter/output.txt";
    private String listeetoilepath="recognition/coorPoints/liste_etoiles.csv";

    @FXML
    public void initialize() {  setupBackgroundVideo(); // Configure la vidéo de fond
       
        playBackgroundMusic();
        // Crée une orbite lumineuse
        LuminousOrb orb = new LuminousOrb();
        orbContainer.getChildren().add(orb);
        System.out.println("Loader activé au démarrage.");}
         
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
    public void handleConstellation(ActionEvent event) {
        try {
            // Charger l'image
            InputStream imageStream = getClass().getResourceAsStream("/images/orsaminor.jpg");
            if (imageStream == null) {
                System.err.println("Image non trouvée dans les ressources");
                return;
            }
            Image image = new Image(imageStream);
            imageView.setImage(image);
            imageView.setVisible(true);
            consoleOutput.setOpacity(1.0);
    
            // Charger le texte - Utilisation du chemin relatif correct
            chargerTexte("/baseDDonnees_txt/apus.txt"); // Notez le / au début
    
            System.out.println("Affichage réussi !");
        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
            e.printStackTrace();
        }
    }
 
    @FXML
    public void handleClose(ActionEvent event) {
        eraser(outputpath);
        eraser(listeetoilepath);
        // Ferme l'application
        System.out.println("Fermeture de l'application...");
        Stage stage = (Stage) root.getScene().getWindow();
       
        stage.close();
    }

    @FXML
    public void handleMaximiser(ActionEvent event) {
        // Maximiser la fenêtre
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setMaximized(true);
        System.out.println("Fenêtre maximisée");
    }

    @FXML
    public void eraser(String path) {
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(""); // Empty the file
            writer.close();
            System.out.println("File content erased: " + path);
        } catch (IOException e) {
            System.err.println("An error occurred while erasing the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupBackgroundVideo() {
        try {
            URL videoUrl = getClass().getResource("/images/polaris.mp4");
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

    private void playBackgroundMusic() {
        try {
            // Charger le fichier audio
            URL musicUrl = getClass().getResource("/audio/Spore - Galaxy Ambience.mp3");
            if (musicUrl == null) {
                System.err.println("ERREUR: FXInterface/src/main/resources/audio/Spore - Galaxy Ambience.mp3");
                return;
            }

            Media media = new Media(musicUrl.toExternalForm());
            musicPlayer = new MediaPlayer(media);

            // Configurer la musique
            musicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Répéter en boucle
            musicPlayer.setVolume(0.5); // Volume (0.0 à 1.0)

            // Démarrer la musique
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
        // Initialiser le MediaPlayer une seule fois
        if (clickSoundPlayer == null) {
            URL musicUrl = getClass().getResource("/audio/clickSound.mp3");
            if (musicUrl == null) {
                System.err.println("ERREUR: Fichier audio introuvable dans /audio/clickSound.mp3");
                return;
            }

            Media media = new Media(musicUrl.toExternalForm());
            clickSoundPlayer = new MediaPlayer(media);

            // Réinitialiser le MediaPlayer après la fin de la lecture
            clickSoundPlayer.setOnEndOfMedia(() -> {
                clickSoundPlayer.stop(); // Arrêter le MediaPlayer
            });
        }

        // Redémarrer le son à chaque clic
        clickSoundPlayer.stop(); // Arrêter si le son est déjà en cours
        clickSoundPlayer.play(); // Jouer le son
        System.out.println("Musique déclenchée par le bouton.");
    } catch (Exception e) {
        System.err.println("Erreur lors de la lecture de la musique : " + e.getMessage());
        e.printStackTrace();
    }
}

    @FXML
    public void handleRecognitionAndPlayMusic(ActionEvent event) throws NumberFormatException, TriangleMatchingException, IOException {
        // Appeler la méthode playBackgroundMusic
        playMusicOnButtonClick(event);
        try {
            Thread.sleep(500); // Attend 1000 millisecondes (1 seconde)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Appeler la méthode handleRecognition
        handleRecognition(event);

        
    }




    @FXML
    public void handleMaximiserAndPlayMusic(ActionEvent event) throws NumberFormatException, TriangleMatchingException, IOException {
        // Appeler la méthode playBackgroundMusic
        playMusicOnButtonClick(event);
        try {
            Thread.sleep(500); // Attend 1000 millisecondes (1 seconde)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Appeler la méthode handleRecognition
        handleMaximiser(event);

        
    }

    @FXML
    public void handleConstellationAndPlayMusic(ActionEvent event) throws NumberFormatException, TriangleMatchingException, IOException {
        // Appeler la méthode playBackgroundMusic
        playMusicOnButtonClick(event);
        try {
            Thread.sleep(500); // Attend 1000 millisecondes (1 seconde)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Appeler la méthode handleRecognition
        handleConstellation(event);

        
    }




/*activation de musique lorsque l'on clique sur un bouton*/
    @FXML
    public void handlePlayMusic(ActionEvent event) {
        try {
            // Charger le fichier audio
            URL musicUrl = getClass().getResource("/audio/clickSound.mp3");
            if (musicUrl == null) {
                System.err.println("ERREUR: Fichier audio introuvable dans /audio/clickSound.mp3");
                return;
            }

        Media media = new Media(musicUrl.toExternalForm());
        MediaPlayer musicPlayer = new MediaPlayer(media);

        // Configurer la musique
        musicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Répéter en boucle
        musicPlayer.setVolume(0.5); // Volume (0.0 à 1.0)

        // Démarrer la musique
        musicPlayer.play();
        System.out.println("Musique démarrée.");
    } catch (Exception e) {
        System.err.println("Erreur lors de la lecture de la musique : " + e.getMessage());
        e.printStackTrace();
    }

}
  

    /**
     * Charge et affiche le contenu d'un fichier texte dans la zone de texte.
     * @param cheminTexte Chemin relatif du fichier texte
     */
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
    
                    // Lecture des sorties et redirection vers le terminal
                    try (BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            System.out.println("[Python] " + line);  // Sortie vers le terminal
                        }
                    }
    
                    int exitCode = process.waitFor();
                    if (exitCode == 0) {
                        System.out.println("[Java] 5. Script exécuté avec succès !");
                        
                        // Lancement de la reconnaissance
                        try {
                            runJavaScript();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        } catch (TriangleMatchingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
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
    
        // On supprime l'écouteur qui écrivait dans consoleOutput
        new Thread(pythonTask).start();
    }

    private void runJavaScript() throws TriangleMatchingException, NumberFormatException, IOException {
        Recognition.run();
    }
}