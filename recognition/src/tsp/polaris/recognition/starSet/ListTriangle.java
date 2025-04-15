package tsp.polaris.recognition.starSet;

/**
 * Classe qui va contenir des listes de triangles
 * @author Emma M., Chadi A.
 */

public class ListTriangle {
    private final Triangle[] listTriangle;

    /**
     * Constructeur qui initialise le tableau des triangles
     *
     * @param listTriangle Liste des triangles
     */
    public ListTriangle(Triangle[] listTriangle) {
        this.listTriangle = listTriangle;
    }

    /**
     * Methode d'affichage
     *
     * @return String : Coordonnees des points des triangles
     */
    public String toString() {
        String str = "";
        for (Triangle triangle : listTriangle) {
            str += " " + triangle.toString();
        }
        return str;
    }

    /**
     * Getteur de listTriangle
     *
     * @return Triangle[] : Retourne la liste des triangles
     */
    public Triangle[] getListTriangle() {
        return listTriangle;
    }

    /**
     * Méthodes qui calcule le coût entre des listes de triangles
     *
     * @param listeTriangle2 Liste de triangles avec laquelle on veut calculer le cout
     * @return double[] : Liste des couts entre les triangles des 2 listes
     * @throws TriangleMatchingException Problème d'appariement de liste de triangle
     */
    public double[] costs(ListTriangle listeTriangle2, DetectedStarSet detectedStars, Constellation dataStars) throws TriangleMatchingException {
        // Taille des triangles
        int taille1 = listTriangle.length;
        int taille2 = listeTriangle2.listTriangle.length;

        // Cas ou les listes n'ont pas la même taille (donc ne peuvent pas representer la même constellation)
        if (taille1 != taille2) {
            throw new TriangleMatchingException("Les tableaux de triangles ont des tailles differentes : " + taille1 + " et " + taille2);
        }

        // On va comparer chaque triangle entre eux et mettre leurs couts dans une liste
        double[] costs = new double[taille1];
        for (int i = 0; i < taille1; i++) {
            // On retrouve le triangle associé au triangle courant (listTriangle[i])
            Triangle triangleAssociated = detectedStars.findTriangle(listTriangle[i], dataStars);
            costs[i] = listTriangle[i].cost(triangleAssociated);
        }
        return costs;
    }

}