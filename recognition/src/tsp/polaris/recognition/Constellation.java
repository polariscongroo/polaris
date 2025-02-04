package tsp.polaris.recognition;
import java.util.Arrays;
import tsp.polaris.auxiliaries.Combinatorics;

public class Constellation
{
    private Point[] points;
    public Constellation(Point[] points)
    {
        this.points = points;
    }
    // Génère tous les triangles possibles à partir d'un ensemble de points
    public Triangle[] generateTriangles()
    {
        int size = points.length;
        int nb_triangles = Combinatorics.combination(size, 3);
        Triangle[] triangles = new Triangle[nb_triangles];
        int index = 0;
        for (int i = 0; i < size - 2; i++)
        {
            for (int j = i + 1; j < size- 1; j++)
            {
                for (int k = j + 1; k < size; k++)
                {
                    triangles[index] = new Triangle(points[i], points[j], points[k]);
                    index++;
                }
            }
        }
        return triangles;
    }

    public String toString()
    {
        Triangle[] triangles = generateTriangles();
        return Arrays.toString(triangles);
    }

    static double couts(double[] cout_triangle)
    {
        double cout_constellation = 0;
        for (int cout = 0; cout < cout_triangle.length; cout++)
        {
            cout_constellation += cout_triangle[cout];
        }
        return cout_constellation;
    }

    public Constellation selectConstellation(Constellation... constellations) throws TriangleMatchingException
    {
        // Initialiser le "winner" à null
        Constellation winner = null;
        double minimum_cout = Double.MAX_VALUE; // Utiliser une valeur maximale pour commencer.

        // Parcourir les constellations passées en argument
        for (Constellation c : constellations)
        {
            // Générer les triangles pour la photo et la constellation c
            Triangle[] triangles_photo = generateTriangles(); // Triangles de la photo
            Triangle[] triangles_c = c.generateTriangles();         // Triangles de la constellation c

            List_triangle listPhoto = new List_triangle(triangles_photo);
            List_triangle listTriangle = new List_triangle(triangles_c);

            // Calculer les coûts entre les triangles de la photo et ceux de la constellation c
            double[] liste_cout = listPhoto.couts(listTriangle);
            double total = Constellation.couts(liste_cout);  // Calculez le total des coûts

            // Vérifiez si le total des coûts de cette constellation est le plus bas
            if (minimum_cout > total)
            {
                minimum_cout = total;  // Mettez à jour le coût minimal
                winner = c;             // Mettez à jour la constellation gagnante
            }
        }
        return winner;  // Retourner la constellation avec le coût minimal
    }
    
    // Renvoie le coût minimale entre notre ensemble de points et les différentes constellations
    public double coutConstellation(Constellation... constellations) throws TriangleMatchingException
    {
        double minimum_cout = Double.MAX_VALUE; // Utiliser une valeur maximale pour commencer.

        // Parcourir les constellations passées en argument
        for (Constellation c : constellations)
        {
            // Générer les triangles pour la photo et la constellation c
            Triangle[] triangles_photo = generateTriangles(); // Triangles de la photo
            Triangle[] triangles_c = c.generateTriangles();         // Triangles de la constellation c

            List_triangle listPhoto = new List_triangle(triangles_photo);
            List_triangle listTriangle = new List_triangle(triangles_c);

            // Calculer les coûts entre les triangles de la photo et ceux de la constellation c
            double[] liste_cout = listPhoto.couts(listTriangle);
            double total = Constellation.couts(liste_cout);  // Calculez le total des coûts

            // Vérifiez si le total des coûts de cette constellation est le plus bas
            if (minimum_cout > total)
            {
                minimum_cout = total;  // Mettez à jour le coût minimal
            }
        }
        return minimum_cout;  // Retourner la constellation avec le coût minimal
    }

}