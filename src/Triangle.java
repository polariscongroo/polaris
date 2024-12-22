import java.util.ArrayList;
import java.util.List;

public class Triangle
{
    Point p1, p2, p3;

    Triangle(Point p1, Point p2, Point p3)
    {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    // Calcule les longueurs des côtés
    double[] getSides()
    {
        double[] sides = new double[3];
        sides[0] = distance(p1, p2);
        sides[1] = distance(p2, p3);
        sides[2] = distance(p3, p1);
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
        return angles;
    }

    // Calcule la distance entre deux points
    static double distance(Point p1, Point p2)
    {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    // Vérifie si deux triangles sont similaires
    static boolean areTrianglesSimilar(Triangle t1, Triangle t2, double tolerance)
    {
        double[] sides1 = t1.getSides();
        double[] sides2 = t2.getSides();
        double[] angles1 = t1.getAngles();
        double[] angles2 = t2.getAngles();

        // Vérifie les rapports des côtés
        double[] ratios1 = {sides1[0] / sides1[1], sides1[1] / sides1[2], sides1[2] / sides1[0]};
        double[] ratios2 = {sides2[0] / sides2[1], sides2[1] / sides2[2], sides2[2] / sides2[0]};
        for (int i = 0; i < 3; i++)
        {
            if (Math.abs(ratios1[i] - ratios2[i]) > tolerance)
            {
                return false;
            }
        }

        // Vérifie les angles
        for (int i = 0; i < 3; i++)
        {
            if (Math.abs(angles1[i] - angles2[i]) > tolerance)
            {
                return false;
            }
        }

        return true;
    }

    // Génère tous les triangles possibles à partir d'un ensemble de points
    static List<Triangle> generateTriangles(List<Point> points)
    {
        List<Triangle> triangles = new ArrayList<>();
        int n = points.size();
        for (int i = 0; i < n - 2; i++)
        {
            for (int j = i + 1; j < n - 1; j++)
            {
                for (int k = j + 1; k < n; k++)
                {
                    triangles.add(new Triangle(points.get(i), points.get(j), points.get(k)));
                }
            }
        }
        return triangles;
    }
}
