package tsp.polaris.drawConstellation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

public class Draw {
    protected BufferedImage img; // Image à traiter
    protected File outputFile; // Fichier de sortie

    /**
     * Constructeur de la classe Draw qui initialise le buffer
     *
     * @param file image à traiter
     * @throws IOException erreur lancée lors de la lecture de l'image
     */
    public Draw(File file) throws IOException {
        copyImage(file, "output");
        outputFile = new File("FXInterface/src/main/resources/images/output.png");
        img = ImageIO.read(outputFile);
    }

    /**
     * Effectue une copie d'un fichier
     *
     * @param imgFile fichier à copier
     * @param name    nom du nouveau fichier copié
     * @throws IOException erreur lancée lors de la copie de l'image
     */
    public static void copyImage(File imgFile, String name) throws IOException {
        Path imgCopyPath = Paths.get("FXInterface/src/main/resources/images/" + name + ".png"); // Chemin de la nouvelle image
        Path imgPath = imgFile.toPath(); // Chemin de l'ancienne image
        Files.copy(imgPath, imgCopyPath, StandardCopyOption.REPLACE_EXISTING); // Duplication de l'image (et remplacement si l'image existe déjà)
    }
}
