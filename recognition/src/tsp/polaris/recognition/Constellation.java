package tsp.polaris.recognition;

import java.util.Arrays;
import tsp.polaris.auxiliaries.Combinatorics;

/**
 * Represente une constellation composee de plusieurs points.
 * Permet de generer des triangles à partir des points.
 * 
 * @author Chadi A., Emma M.
 */
public class Constellation {
    private Point[] points;

    /**
     * Constructeur de la classe Constellation.
     * 
     * @param points Liste des points constituant la constellation.
     */
    public Constellation(Point[] points) {
        this.points = points;
    }

    /**
     * Retourne une representation sous forme de chaîne de caractères de la constellation.
     * 
     * @return Une chaîne de caractères representant la liste des points.
     */
    @Override
    public String toString() {
        return Arrays.toString(points);
    }

    /**
     * Genère tous les triangles possibles formes par trois points distincts de la constellation.
     * 
     * @return Un tableau de triangles construits à partir des points de la constellation.
     */
    public Triangle[] generateTriangles() {
        int size = points.length;
        int nb_triangles = Combinatorics.combination(size, 3);
        Triangle[] triangles = new Triangle[nb_triangles];
        int index = 0;

        for (int i = 0; i < size - 2; i++) {
            for (int j = i + 1; j < size - 1; j++) {
                for (int k = j + 1; k < size; k++) {
                    triangles[index] = new Triangle(points[i], points[j], points[k]);
                    index++;
                }
            }
        }
        return triangles;
    }
}
