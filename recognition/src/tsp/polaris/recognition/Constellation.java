package tsp.polaris.recognition;

import tsp.polaris.auxiliaries.Combinatorics;

/**
 * Représente une constellation composée de plusieurs points.
 * Permet de générer des triangles à partir des points.
 * 
 * @author Chadi A., Emma M.
 */
public class Constellation {
    private Point[] points;
    private String nom;

    /**
     * Constructeur de la classe Constellation.
     * 
     * @param points Liste des points constituant la constellation.
     */
    public Constellation(Point[] points, String nom) {
        this.points = points;
        this.nom = nom;
    }

    /**
     * Getteur de points
     *
     */
    public Point[] getPoints() {
        return points;
    }

    /**
     * Retourne une representation sous forme de chaîne de caractères de la constellation.
     * 
     * @return Une chaîne de caractères représentant la liste des points.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Génère tous les triangles possibles formes par trois points distincts de la constellation.
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
