package tsp.polaris.drawConstellation;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import tsp.polaris.recognition.other.Point;
import tsp.polaris.recognition.starSet.Constellation;

import java.util.Arrays;
import java.util.List;

/**
 * Crée une nouvelle image avec la constellation tracée à partir de l'image donnée et des coordonnées des points formant la constellation
 *
 * @author Mell E.
 */

public class DrawConstellation extends Draw {

    public DrawConstellation(File file) throws IOException{
        super(file);
    }

    /**
     * Renvoie vrai si les coordonnées du pixels sont valides
     *
     * @param coor   coordonnées du pixel
     * @return vrai si les coordonées du pixels sont valides, faux sinon
     */
    private boolean isValid(int[] coor) {
        int width = img.getWidth(); // Dimensions de l'image
        int height = img.getHeight();
        return coor[0] >= 0 && coor[0] < width && coor[1] >= 0 && coor[1] < height;
    }

    /**
     * Colorie les points adjacents aux point de coordonnées (x,y)
     *
     * @param x     abscisse du point
     * @param y     ordonnée du point
     * @param color couleur à appliquer
     */
    private void drawPoint(int x, int y, Color color) {
        int[][] coorColorPoint = {{x, y}, {x + 1, y}, {x, y + 1}, {x - 1, y}, {x, y - 1}}; // Coordonnées des points qui vont être coloré

        for (int[] coor : coorColorPoint) { // Colorie les points autours
            if (isValid(coor)) { // Vérifie que les coordonnées sont valides
                img.setRGB(coor[0], coor[1], color.getRGB()); // Colorie le point
            }
        }
    }
    
    /**
     * Dessine une ligne entre 2 points en divisant l'espace selon x
     *
     * @param p1 1er point
     * @param p2 2e point
     * @param color couleur à appliquer
     */
    private void drawLineX(Point p1, Point p2, Color color) {
    	double[] coeff = p1.coefficients(p2); // Calcul des coefficients de la droite passant par les 2 points
    	
        for (int x = (int) p1.getPoint()[0] + 10; x < p2.getPoint()[0] - 10; x += 1) {
            int y = (int) (coeff[0] * x + coeff[1]); // Calcul de la position du prochain point sur la droite
            drawPoint(x, y, color);
        }
    }
    
    /**
     * Dessine une ligne entre 2 points en divisant l'espace selon y
     *
     * @param p1 1er point
     * @param p2 2e point
     * @param color couleur à appliquer
     */
    private void drawLineY(Point p1, Point p2, Color color) {
    	double[] coeff = p1.coefficients(p2); // Calcul des coefficients de la droite passant par les 2 points
    	
        for (int y = (int) p1.getPoint()[1] + 10; y < p2.getPoint()[1] - 10; y += 1) {
            int x = (int) ((y - coeff[1])/coeff[0]); // Calcul de la position du prochain point sur la droite
            drawPoint(x, y, color);
        }
    }
    
    /**
     * Dessine une ligne passant entre 2 points sur l'image
     *
     * @param p1      1er point
     * @param p2      2e point
     * @param color   couleur à appliquer
     * @throws IOException erreur lancée lors de la copie de l'image
     */
    public void drawLine(Point p1, Point p2, Color color) throws IOException {
    	double dx = p2.getPoint()[0] - p1.getPoint()[0]; // Distance entre les points en x et en y
    	double dy = p2.getPoint()[1] - p1.getPoint()[1];
        
    	if(Math.abs(dx) > Math.abs(dy)) { // Cas ou on divise l'espace selon x
    		if(dx > 0) { // Cas ou p2 se trouve après p1 en x
    			drawLineX(p1,p2,color);
    		} else {
    			drawLineX(p2,p1,color);
    		}
    	} else { // Cas ou on divise l'espace selon y
    		if(dy > 0) { // Cas ou p2 se trouve après p1 en y
    			drawLineY(p1,p2,color);
    		} else {
    			drawLineY(p2,p1,color);
    		}
    	}

        ImageIO.write(img, "PNG", outputFile); // Enregistrement de l'image
    }

    /**
     * Dessine la constellation tracée
     *
     * @param constellation constellation ou on va tracer
     * @param color  couleur à appliquer
     * @throws IOException erreur lancée lors de la copie de l'image
     */
    public void drawConstellation(Constellation constellation, Color color) throws IOException {
        // On récupère la liste d'adjacence
        List<List<Integer>> adjacencyList = constellation.getAdjacencyList();

        // On trace toutes les lignes
        for (int i = 0; i < adjacencyList.size(); i += 1) {
            for(int j = 0; j < adjacencyList.get(i).size(); j += 1) {
                drawLine(constellation.getStars()[i], constellation.getStars()[adjacencyList.get(i).get(j)], color);
            }
        }
    }

}