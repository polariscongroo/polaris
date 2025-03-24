package tsp.polaris.drawConstellation;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import tsp.polaris.recognition.other.Point;

public class MainDrawTest {
    public static void main(String[] args) throws IOException {
        Color col = new Color(79, 177, 205, 200); // Couleur de la ligne
        DrawConstellation outputDrawing = new DrawConstellation(new File("recognition/src/tsp/polaris/drawConstellation/inputs/1_cancer.png")); // Nouvelle image
        outputDrawing.drawLine(new Point(213, 238), new Point(476, 531), col); // Trace la ligne
    }
}
