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
    protected BufferedImage img;
    protected File outputFile;

    public Draw(File file) throws IOException {
        String outputPath = "FXInterface/src/main/java/tsp/polaris/drawConstellation/outputs/output.png";
        copyImage(file, outputPath);
        outputFile = new File(outputPath);
        img = ImageIO.read(outputFile);
    }

    /**
     * Effectue une copie d'un fichier
     *
     * @param imgFile fichier à copier
     * @param outputPath    nom du nouveau fichier copié
     * @throws IOException erreur lancée lors de la copie de l'image
     */
    public static void copyImage(File imgFile, String outputPath) throws IOException {
        Path imgCopyPath = Paths.get(outputPath); // Chemin de la nouvelle image
        Path imgPath = imgFile.toPath(); // Chemin de l'ancienne image
        Files.copy(imgPath, imgCopyPath, StandardCopyOption.REPLACE_EXISTING); // Duplication de l'image (et remplacement si l'image existe déjà)
    }
}
