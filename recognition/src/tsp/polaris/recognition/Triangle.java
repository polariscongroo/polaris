package tsp.polaris.recognition;
import java.util.Arrays;

/**
 * Classe de triangles
 * @author Emma M., Chadi A.
 */

public class Triangle
{
    private Point p1, p2, p3;

    /**
     * Constructeur qui initialise les coordonnées des points du triangle
     * @param p1 1er point du triangle
     * @param p2 2e point du triangle
     * @param p3 3e point du triangle
     */
    public Triangle(Point p1, Point p2, Point p3)
    {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
    
    /**
     * Getteur du 1er point
     * @return Point : 1er point du triangle
     */
    public Point getPoint1() {
    	return p1;
    }
    
    /**
     * Getteur du 2e point
     * @return Point : 2e point du triangle
     */
    public Point getPoint2() {
    	return p2;
    }
    
    /**
     * Getteur du 3e point
     * @return Point : 3e point du triangle
     */
    public Point getPoint3() {
    	return p3;
    }

    /**
     * Méthode d'affichage
     * @return String : Affiche les points du triangle
     */
    public String toString()
    {
        return "Triangle(" + p1 + ", " + p2 + ", " + p3 + ")";
    }

    /**
     * Méthode qui calcule les longueurs des cotés du triangle
     * @return double[] : Liste des longueurs des côtés du triangle, rangés dans l'ordre croissant 
     */
    private double[] getSides()
    {
        double[] sides = new double[3];
        sides[0] = p1.distance(p2);
        sides[1] = p2.distance(p3);
        sides[2] = p3.distance(p1);
        Arrays.sort(sides);
        return sides;
    }

    /**
     * Méthode qui calcule les angles du triangle
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
     * Méthode qui calcule les longueurs des côtés normalisés du triangle
     * @return double[] : Distances normalisées (par la distance la plus grande du triangle) 
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
     * Méthode qui compare 2 triangles
     * @param t2 Triangle à comparer
     * @return double : Coût entre 2 triangles
     */
    public double cout(Triangle t2)
    {
    	// Tableau d'angles
        double[] angles1 = getAngles();
        double[] angles2 = t2.getAngles();

        // Rapports des côtés
        double[] ratios1 = getRatios();
        double[] ratios2 = t2.getRatios();

        // Pondération des critères (ratio longueur et angle) (alpha et beta à définir)
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
