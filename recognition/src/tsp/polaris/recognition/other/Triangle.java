package tsp.polaris.recognition.other;
import tsp.polaris.recognition.starSet.StarSet;

import java.util.Arrays;

/**
 * Classe de triangles
 * @author Emma M., Chadi A.
 */

public class Triangle extends StarSet
{

    /**
     * Constructeur qui initialise les coordonnées des étoiles du triangle
     * @param stars Points du triangle
     */
    public Triangle(Star... stars)
    {
        super(stars);
        // Vérification que 3 points on été donnés
        if(stars.length != 3) {
            throw new IllegalArgumentException("Le triangle doit avoir 3 étoiles.");
        }
        // Vérification que ces points forment bien un triangle
        if(stars[0].isCollinear(stars[1],stars[2])){
            throw new IllegalArgumentException("Les points ne forment pas un triangle");
        }
    }
    
    /**
     * Getteur de l'étoile i
     * @return Point : La ième étoile du triangle
     */
    public Star[] getStars() {
        return stars;
    }

    /**
     * Methode d'affichage
     * @return String : Affiche les étoiles du triangle
     */
    public String toString()
    {
        return "Triangle(" + stars[0] + ", " + stars[1] + ", " + stars[2] + ")";
    }

    /**
     * Méthode qui regarde si 2 triangles sont égaux
     * @param t2 Triangle avec lequel on veut comparer
     * @return boolean : Renvoie vrai si les triangles sont égaux, faux sinon
     */
    public boolean equals(Triangle t2) {
        return stars[0].equals(t2.stars[0]) && stars[1].equals(t2.stars[1]) && stars[2].equals(t2.stars[2]);
    }

    /**
     * Methode qui calcule les longueurs des côtés du triangle
     * @return double[] : Liste des longueurs des côtes du triangle, ranges dans l'ordre croissant 
     */
    private double[] getSides()
    {
        double[] sides = new double[3];
        sides[0] = stars[0].distance(stars[1]);
        sides[1] = stars[1].distance(stars[2]);
        sides[2] = stars[2].distance(stars[0]);
        Arrays.sort(sides);
        return sides;
    }

    /**
     * Methode qui calcule les angles du triangle
     * @return double[] : Liste des angles du triangle
     */
    public double[] getAngles()
    {
        double[] sides = getSides();
        double a = sides[0], b = sides[1], c = sides[2];
        double[] angles = new double[3];
        angles[0] = Math.acos((b * b + c * c - a * a) / (2 * b * c)); 
        angles[1] = Math.acos((a * a + c * c - b * b) / (2 * a * c));
        angles[2] = Math.acos((a * a + b * b - c * c) / (2 * a * b));
        return angles;
    }
    
    /**
     * Methode qui calcule les longueurs des côtes normalises du triangle
     * @return double[] : Distances normalisees (par la distance la plus grande du triangle) 
     */
    public double[] getRatios() 
    {
    	double[] sides = getSides();
    	double[] ratios = new double[3];
    	for(int i = 0; i < 3; i += 1) {
    		ratios[i] = sides[i]/sides[(i+1)%3];
    	}
    	return ratios;
    }

    /**
     * Methode qui compare 2 triangles
     * @param t2 Triangle à comparer
     * @return double : Coût entre 2 triangles
     */
    public double cost(Triangle t2)
    {
    	// Tableau d'angles
        double[] angles1 = getAngles();
        double[] angles2 = t2.getAngles();

        // Rapports des côtes
        double[] ratios1 = getRatios();
        double[] ratios2 = t2.getRatios();

        // Ponderation des critères (ratio longueur et angle) (alpha et beta à definir)
        double alpha = 1;
        double beta = 1;

        double cout = 0;
        for (int i = 0; i < 3; i++)
        {
            cout += alpha*Math.abs(ratios1[i] - ratios2[i]) + beta*Math.abs(angles1[i] - angles2[i])/Math.PI; //Normalisation par PI
        }
        return cout;
    }
}
