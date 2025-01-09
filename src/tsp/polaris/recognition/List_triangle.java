package tsp.polaris.recognition;
import static java.lang.Math.sqrt;

public class List_triangle
{
    //La première liste de triangles en argument voit ses couts comparés à tous ceux du deuxième argument avant den passer au suivant
    static double[] couts(Triangle[] triangles1, Triangle[] triangles2) throws TriangleMatchingException
    {
        int taille1 = triangles1.length;
        int taille2 = triangles2.length;
        if (taille1 != taille2)
        {
            throw new TriangleMatchingException("Les tableaux de triangles ont des tailles différentes : " + taille1 + " et " + taille2);
        }
        double[] tab = new double[taille1*taille2];
        int index = 0;
        for (int triangle1 = 0; triangle1 < taille1; triangle1++)
        {
            for (int triangle2 = 0; triangle2 < taille1; triangle2++)
            {
                tab[index] = Triangle.cout(triangles1[triangle1],triangles2[triangle2]);
                index ++;
            }
        }
        return tab;
    }

    // Trouver l'indice de correspondance entre deux triangles
    static int[] indice(double[] couts) {
        int nombre_combinaisons = couts.length;
        if (Math.sqrt(nombre_combinaisons) % 1 != 0) {
            throw new IllegalArgumentException("Le tableau 'couts' doit être de taille carrée.");
        }

        int nb_triangles = (int) Math.sqrt(nombre_combinaisons);
        int[] tab_indices = new int[nb_triangles];
        boolean[] usedTrianglesSecondSet = new boolean[nb_triangles];

        for (int indice_triangle = 0; indice_triangle < nb_triangles; indice_triangle++) {
            int start = indice_triangle * nb_triangles;
            int end = start + nb_triangles;

            double minValue = Double.MAX_VALUE;
            int minIndex = -1;

            // Parcourir les coûts pour trouver le minimum non utilisé
            for (int i = start; i < end; i++) {
                int secondSetIndex = i % nb_triangles;
                if (!usedTrianglesSecondSet[secondSetIndex] && couts[i] < minValue) {
                    minValue = couts[i];
                    minIndex = secondSetIndex; // Stocker l'indice dans le second set
                }
            }

            if (minIndex == -1) {
                throw new IllegalStateException("Aucun minimum trouvé. Vérifiez les données.");
            }

            // Marquer le triangle du second set comme utilisé
            usedTrianglesSecondSet[minIndex] = true;
            tab_indices[indice_triangle] = minIndex; // Associer l'indice trouvé
        }

        return tab_indices;
    }
}
