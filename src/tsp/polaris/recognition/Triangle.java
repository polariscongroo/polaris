package tsp.polaris.recognition;
import java.util.Arrays;

public class Triangle
{
    private Point p1, p2, p3;

    public Triangle(Point p1, Point p2, Point p3)
    {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
    
    public Point getPoint1() {
    	return p1;
    }
    
    public Point getPoint2() {
    	return p2;
    }
    
    public Point getPoint3() {
    	return p3;
    }

    public String toString()
    {
        return "Triangle(" + p1 + ", " + p2 + ", " + p3 + ")";
    }

    // Calcule les longueurs des côtés
    private double[] getSides()
    {
        double[] sides = new double[3];
        sides[0] = p1.distance(p2);
        sides[1] = p2.distance(p3);
        sides[2] = p3.distance(p1);
        Arrays.sort(sides);
        return sides;
    }

    // Calcule les angles internes
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
    
    // Renvoie les distances normalisées (par la distance la plus grande du triangle) (changer cette méthode pour éviter de perdre des données)
    public double[] getRatios() 
    {
    	double[] sides = getSides();
    	double[] ratios = new double[3];
    	double max = sides[2];
    	for(int i = 0; i < 3; i += 1) {
    		ratios[i] = sides[i]/max;
    	}
    	return ratios;
    }

    // Vérifie si deux triangles sont similaires
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
