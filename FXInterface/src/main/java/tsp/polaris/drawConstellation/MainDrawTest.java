package tsp.polaris.drawConstellation;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import tsp.polaris.recognition.starSet.DetectedStarSet;

public class MainDrawTest {
    public static void drawConstellation(String path, DetectedStarSet detectedStarSet) throws IOException {
        Color col = new Color(79, 177, 205, 200); // Couleur de la ligne
        DrawConstellation outputDrawing = new DrawConstellation(new File(path)); // Nouvelle image
        outputDrawing.drawConstellation(detectedStarSet, col);
    }
}
