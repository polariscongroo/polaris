package tsp.polaris.drawConstellation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Draw {
    protected BufferedImage img;
    protected File outputFile;

    public Draw(File file) throws IOException {
        copyImage(file, "output");
        outputFile = new File("recognition/src/tsp/polaris/drawConstellation/outputs/output.png");
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
        Path imgCopyPath = Paths.get("recognition/src/tsp/polaris/drawConstellation/outputs/" + name + ".png"); // Chemin de la nouvelle image
        Path imgPath = imgFile.toPath(); // Chemin de l'ancienne image
        Files.copy(imgPath, imgCopyPath, StandardCopyOption.REPLACE_EXISTING); // Duplication de l'image (et remplacement si l'image existe déjà)
    }
}
