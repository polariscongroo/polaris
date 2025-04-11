package com.example.interfacegraphique;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

public class HelloController {

    // Éléments FXML
    @FXML private StackPane root;
    @FXML private MediaView backgroundMediaView;
    //@FXML private TextArea consoleOutput; // Pour afficher les logs

    // Variables
    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() 
    {
        setupBackgroundVideo();
        //startPythonWatcher();
    }

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
    public void handleRecognition() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg"));
        
        File file = fileChooser.showOpenDialog(root.getScene().getWindow());
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
    public void handleConstellation() {
        System.out.println("Constellation");
    }

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
    /*
    private void startPythonWatcher() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String pathString = "cartography/image_aTraiter";
                Path dir = Paths.get(pathString);
                
                if (!Files.exists(dir)) {
                    updateMessage("Erreur: Dossier introuvable - " + dir.toAbsolutePath());
                    return null;
                }

                WatchService watchService = FileSystems.getDefault().newWatchService();
                dir.register(watchService, ENTRY_MODIFY);

                updateMessage("Surveillance de output.txt activée...");
                long lastModifiedTime = 0;

                while (true) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Path fileName = (Path) event.context();
                        
                        if (event.kind() == ENTRY_MODIFY && fileName.toString().equals("output.txt")) {
                            long currentTime = Files.getLastModifiedTime(dir.resolve(fileName)).toMillis();
                            if (currentTime - lastModifiedTime > 1000) {
                                lastModifiedTime = currentTime;
                                runPythonScript();
                            }
                        }
                    }
                    key.reset();
                }
            }
        };

        // Liaison avec l'interface
        task.messageProperty().addListener((obs, oldVal, newVal) -> {
            consoleOutput.appendText(newVal + "\n");
        });

        // Démarrer dans un thread séparé
        Thread thread = new Thread(task);
        thread.setDaemon(true); // Le thread s'arrête quand l'application se ferme
        thread.start();
    }

    private void runPythonScript() {
        Task<Void> pythonTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String projectPath = new File("").getAbsolutePath();
                String scriptPath = projectPath + File.separator + "cartography/ThresholdDetectMethod.py";

                updateMessage("Lancement du script Python...");
                
                ProcessBuilder pb = new ProcessBuilder("python3", scriptPath);
                pb.directory(new File(projectPath));
                pb.redirectErrorStream(true);

                try {
                    Process process = pb.start();
                    
                    // Lecture des sorties
                    try (BufferedReader reader = new BufferedReader(
                         new InputStreamReader(process.getInputStream()))) {
                        
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
    */
}