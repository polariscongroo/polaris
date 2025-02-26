package tsp.polaris.drawConstellation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;
import tsp.polaris.recognition.Point;

/**
 * Crée une nouvelle image avec la constellation tracée à partir de l'image donnée et des coordonnées des points formant la constellation
 *
 * @author Mell E.
 */

public class DrawConstellation {
	
    /**
     * Effectue une copie d'un fichier
     *
     * @param imgFile fichier à copier
     * @param name    nom du nouveau fichier copié
     * @throws IOException erreur lancée lors de la copie de l'image
     */
    public static void copyImage(File imgFile, String name) throws IOException {
        Path imgCopyPath = Paths.get("tsp/polaris/drawConstellation/" + name + ".png"); // Chemin de la nouvelle image
        Path imgPath = imgFile.toPath(); // Chemin de l'ancienne image
        Files.copy(imgPath, imgCopyPath, StandardCopyOption.REPLACE_EXISTING); // Duplication de l'image (et remplacement si l'image existe déjà)
    }

    /**
     * Calcule le coefficient directeur et l'ordonnée à l'origine de la droite passant par ces 2 points
     *
     * @param p1 1er point
     * @param p2 2e point
     * @return le coefficient directeur et l'ordonnée à l'origine de la droite passant par ces 2 points
     */
    public static double[] coefficients(Point p1, Point p2) {
        double[] p1Coo = p1.getPoint();
        double[] p2Coo = p2.getPoint();
        double[] coeff = new double[2];
        coeff[0] = (p2Coo[1] - p1Coo[1]) / (p2Coo[0] - p1Coo[0]);
        coeff[1] = p1Coo[1] - coeff[0] * p1Coo[0];
        return coeff;
    }

    /**
     * Renvoie vrai si les coordonnées du pixels sont valides
     *
     * @param coor   coordonnées du pixel
     * @param width  largeur de l'image
     * @param height hauteur de l'image
     * @return vrai si les coordonées du pixels sont valides, faux sinon
     */
    private static boolean isValid(int[] coor, int width, int height) {
        return coor[0] >= 0 && coor[0] < width && coor[1] >= 0 && coor[1] < height;
    }

    /**
     * Colorie les points adjacents aux point de coordonnées (x,y)
     *
     * @param img   image qui va être modifiée
     * @param x     abcisse du point
     * @param y     ordonnée du point
     * @param color couleur à appliquer
     */
    private static void drawPoint(BufferedImage img, int x, int y, Color color) {
        int width = img.getWidth(); // Dimensions de l'image
        int height = img.getHeight();

        int[][] coorColorPoint = {{x, y}, {x + 1, y}, {x, y + 1}, {x - 1, y}, {x, y - 1}}; // Coordonnées des points qui vont être coloré

        for (int i = 0; i < coorColorPoint.length; i += 1) { // Colorie les points autours
            if (isValid(coorColorPoint[i], width, height)) { // Vérifie que les coordonnées sont valides
                img.setRGB(coorColorPoint[i][0], coorColorPoint[i][1], color.getRGB()); // Colorie le point
            }
        }
    }
    
    /**
     * Dessine une ligne entre 2 points en divisant l'espace selon x
     * 
     * @param img image qui va être modifiée
     * @param p1 1er point
     * @param p2 2e point
     * @param color couleur à appliquer
     */
    private static void drawLineX(BufferedImage img, Point p1, Point p2, Color color) {
    	double[] coeff = coefficients(p1, p2); // Calcul des coefficients de la droite passant par les 2 points
    	
        for (int x = (int) p1.getPoint()[0] + 10; x < p2.getPoint()[0] - 10; x += 1) {
            int y = (int) (coeff[0] * x + coeff[1]); // Calcul de la position du prochain point sur la droite
            drawPoint(img, x, y, color);
        }
    }
    
    /**
     * Dessine une ligne entre 2 points en divisant l'espace selon y
     * 
     * @param img image qui va être modifiée
     * @param p1 1er point
     * @param p2 2e point
     * @param color couleur à appliquer
     */
    private static void drawLineY(BufferedImage img, Point p1, Point p2, Color color) {
    	double[] coeff = coefficients(p1, p2); // Calcul des coefficients de la droite passant par les 2 points
    	
        for (int y = (int) p1.getPoint()[1] + 10; y < p2.getPoint()[1] - 10; y += 1) {
            int x = (int) ((y - coeff[1])/coeff[0]); // Calcul de la position du prochain point sur la droite
            drawPoint(img, x, y, color);
        }
    }
    
    /**
     * Dessine une ligne passant entre 2 points sur l'image
     *
     * @param imgFile image qui va être modifiée
     * @param p1      1er point
     * @param p2      2e point
     * @param color   couleur à appliquer
     * @throws IOException erreur lancée lors de la copie de l'image
     */
    public static void drawLine(File imgFile, Point p1, Point p2, Color color) throws IOException {
        BufferedImage img = ImageIO.read(imgFile); // Lecture de l'image

    	double dx = p2.getPoint()[0] - p1.getPoint()[0]; // Distance entre les points en x et en y
    	double dy = p2.getPoint()[1] - p1.getPoint()[1];
        
    	if(Math.abs(dx) > Math.abs(dy)) { // Cas ou on divise l'espace selon x
    		if(dx > 0) { // Cas ou p2 se trouve après p1 en x
    			drawLineX(img,p1,p2,color);
    		} else {
    			drawLineX(img,p2,p1,color);
    		}
    	} else { // Cas ou on divise l'espace selon y
    		if(dy > 0) { // Cas ou p2 se trouve après p1 en y
    			drawLineY(img,p1,p2,color);
    		} else {
    			drawLineY(img,p2,p1,color);
    		}
    	}

        ImageIO.write(img, "PNG", imgFile); // Enregistrement de l'image
    }

    public static void main(String[] args) throws IOException {
        File file = new File("tsp/polaris/drawConstellation/1_cancer.png");
        copyImage(file, "output");
        File outputFile = new File("tsp/polaris/drawConstellation/output.png");
        Color col = new Color(79, 177, 205, 200);
        drawLine(outputFile, new Point(213, 238), new Point(476, 531), col);
    }

}