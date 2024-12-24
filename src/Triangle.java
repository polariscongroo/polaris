import java.util.Arrays;

public class Triangle
{
    Point p1, p2, p3;

    Triangle(Point p1, Point p2, Point p3)
    {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public String toString()
    {
        return "Triangle(" + p1 + ", " + p2 + ", " + p3 + ")";
    }

    // Calcule les longueurs des côtés
    double[] getSides()
    {
        double[] sides = new double[3];
        sides[0] = distance(p1, p2);
        sides[1] = distance(p2, p3);
        sides[2] = distance(p3, p1);
        Arrays.sort(sides);
        return sides;
    }

    // Calcule les angles internes
    double[] getAngles()
    {
        double[] sides = getSides();
        double a = sides[0], b = sides[1], c = sides[2];
        double[] angles = new double[3];
        angles[0] = Math.acos((b * b + c * c - a * a) / (2 * b * c));
        angles[1] = Math.acos((a * a + c * c - b * b) / (2 * a * c));
        angles[2] = Math.acos((a * a + b * b - c * c) / (2 * a * b));
        for (int angle = 0; angle < 3; angle++)
        {
            if (angles[angle] < 0 || angles[angle] > Math.PI)
            {
                throw new IllegalArgumentException("L'angle calculé est invalide.");
            }
        }
        return angles;
    }

    // Calcule la distance entre deux points
    static double distance(Point p1, Point p2)
    {
        double distance = Math.sqrt( Math.pow(p1.point[0] - p2.point[0], 2) + Math.pow(p1.point[1] - p2.point[1], 2));
        if (distance < 0)
        {
            throw new IllegalArgumentException("Les côtés d'un triangle doivent être strictement positifs.");
        }
        return distance;
    }

    // Vérifie si deux triangles sont similaires
    static double cout(Triangle t1, Triangle t2)
    {
        double[] sides1 = t1.getSides();
        double[] sides2 = t2.getSides();
        double[] angles1 = t1.getAngles();
        double[] angles2 = t2.getAngles();

        // Rapports des côtés
        double[] ratios1 = {sides1[0] / sides1[1], sides1[1] / sides1[2], sides1[2] / sides1[0]};
        double[] ratios2 = {sides2[0] / sides2[1], sides2[1] / sides2[2], sides2[2] / sides2[0]};

        //Pondération des critères (ratio longueur et angle)
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
