package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    
    /**
     * Point d'entree principal de l'application.
     * Cree et affiche la fenêtre de l'application.
     * 
     * @param args Les arguments de la ligne de commande.
     * @throws IOException 
     * @throws InterruptedException 
     */             
    public static void main(String args[]) throws IOException, InterruptedException {
        // Crée et affiche l'interface
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoaderUI ui = new LoaderUI();  // Création de la fenêtre
            ui.setVisible(true);

            // 🔹 Attendre que l'interface soit bien affichée avant d'appliquer le flou
            /*SwingUtilities.invokeLater(() -> {
                Button myButton = loader.getMonBouton();  // Récupérer le bouton
                myButton.setBlur(loader.getContentPane());  // Appliquer le flou sur l'arrière-plan du bouton
            });*/
            
        });
    
        String pathString;
        pathString = "cartography/image_aTraiter";
        // Chemin vers le dossier contenant output.txt
        Path dir = Paths.get(pathString);
        // Création du WatchService
        WatchService watchService = FileSystems.getDefault().newWatchService();
        // Enregistrement du dossier pour surveiller les modifications de fichiers
        dir.register(watchService, ENTRY_MODIFY);
    
        System.out.println("0. Surveillance de modifications de output.txt pour relancer le python");
    
        long lastModifiedTime = 0;
        
        while (true) {
            // Récupérer les événements du watch service
            WatchKey key = watchService.take();
    
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
    
                // Récupère le nom du fichier modifié
                Path fileName = (Path) event.context();
    
                // Si c'est output.txt qui a été modifié
                if (kind == ENTRY_MODIFY && fileName.toString().equals("output.txt")) {
                    long currentModifiedTime = Files.getLastModifiedTime(dir.resolve(fileName)).toMillis();
                    if (currentModifiedTime - lastModifiedTime > 1000) { // 1 seconde de délai
                        lastModifiedTime = currentModifiedTime;
                        System.out.println("4. Dans la boucle infinie de l'interface. Lancement du script Python imminent");
    
                        // Lancement du script Python
                        try {
                            Thread.sleep(500);
                            // Récupère le chemin absolu du projet courant
                            String projectPath = new File("").getAbsolutePath();
    
                            // Chemin relatif vers le script Python
                            String scriptRelativePath = "cartography/ThresholdDetectMethod.py";
    
                            // Construit le chemin complet vers le script
                            String scriptFullPath = projectPath + File.separator + scriptRelativePath;
    
                            // Définir la commande pour lancer le script Python
                            List<String> commands = new ArrayList<>();
                            // "python3" pour macOS/Linux, "python" pour Windows.
                            commands.add("python3");
                            commands.add(scriptFullPath);
    
                            // Vérifie la commande construite
                            System.out.println("5. Commande exécutée : " + commands);
    
                            // Crée le ProcessBuilder avec le dossier du projet comme répertoire de travail
                            ProcessBuilder pb = new ProcessBuilder(commands);
                            pb.directory(new File(projectPath));
                            pb.redirectErrorStream(true); // Fusionne la sortie d'erreur avec la sortie standard
    
                            // Lance le processus
                            Process process = pb.start();
    
                            // Capturer la sortie du processus
                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                            }
    
                            // Vérifie si l'exécution est réussie
                            if (process.waitFor() == 0) {
                                System.out.println("10. Script python exécuté avec succès !");
                            } else {
                                System.out.println("6. Une erreur est survenue lors de l'exécution du script.");
                            }
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            // Réinitialise la clé pour continuer à écouter
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
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

}