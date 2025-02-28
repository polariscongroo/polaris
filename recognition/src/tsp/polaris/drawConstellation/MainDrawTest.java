package tsp.polaris.drawConstellation;

import tsp.polaris.recognition.Point;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainDrawTest {
    public static void main(String[] args) throws IOException {
        Color col = new Color(79, 177, 205, 200); // Couleur de la ligne
        DrawLine outputDrawing = new DrawLine(new File("src/tsp/polaris/drawConstellation/1_cancer.png")); // Nouvelle image
        outputDrawing.drawLine(new tsp.polaris.recognition.Point(213, 238), new Point(476, 531), col); // Trace la ligne
    }
}
