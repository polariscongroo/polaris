package tsp.polaris.drawConstellation;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import tsp.polaris.recognition.starSet.DetectedStarSet;

/**
 * Classe principale qui va dessiner la constellation
 *
 * @author Emma M.
 */
public class MainDrawTest {
    /**
     * Méthode principale qui va lancer le processus de dessin de la constellation
     *
     * @param path chemin de l'image à traiter
     * @param detectedStarSet ensemble d'étoiles détectées comme étant une constellation
     * @throws IOException erreur lancée lors de la lecture de l'image
     */
    public static void drawConstellation(String path, DetectedStarSet detectedStarSet) throws IOException {
        Color col = new Color(79, 177, 205, 200); // Couleur de la ligne
        DrawConstellation outputDrawing = new DrawConstellation(new File(path)); // Nouvelle image
        outputDrawing.drawConstellation(detectedStarSet, col);
    }
}
