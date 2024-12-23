import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main
{
        public static void main(String[] args)
        {
            /* Hypothèses :
            - Les triangles formés par les constellations sont tous différents
            i.e la donnée des coordonnées est assez précise pour qu'il n'y est pas de confusion dans le calcul du coût
             */

            Point A = new Point(0, 0);
            Point B = new Point(1, 0);
            Point C = new Point(0, 1);
            Point D = new Point(1, 1);
            Point E = new Point(2, 0);
            Point F = new Point(2, 1);
            Point G = new Point(1, 2);
            Point H = new Point(2, 2);

            //Test affichage detous les triangles possibles avec le set de point set1
            Point[] set1 = {A,B,C,D};
            List_point ABCD = new List_point(set1);
            Triangle[] triangles = ABCD.generateTriangles();
            // Utilisation de Arrays.toString pour afficher les triangles
            System.out.println("Triangles formés par les points en arguments : " + Arrays.toString(triangles));

            //Test fonction de cout triangles
            Triangle trig = new Triangle(A, B, C);
            Triangle trig2 = new Triangle(E, B, C);
            double cost = Triangle.areTrianglesSimilar(trig, trig2);
            System.out.println("Coût de similarité : " + cost);

        }
}