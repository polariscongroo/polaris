package tsp.polaris.recognition.starSet;

import tsp.polaris.auxiliaries.Combinatorics;
import tsp.polaris.recognition.other.Star;
import tsp.polaris.recognition.other.Triangle;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Classe représentant une constellation composée de plusieurs étoiles.
 * Permet de générer des triangles à partir des points.
 *
 * @author Chadi A., Emma M.
 */

public class StarSet {
    protected Star[] stars;

    /**
     * Constructeur de StarSet
     * @param stars Tableau d'étoiles de la constellation
     */
    public StarSet(Star[] stars) {
        this.stars = stars;

        // Trie la liste d'étoiles par luminosité
        sortByBrightness();
    }

    /**
     * Getteur d'étoiles
     *
     * @return Star[] : Retourne la liste des étoiles.
     */
    public Star[] getStars() {
        return stars;
    }

    /**
     * Retourne une representation sous forme de chaîne de caractères des étoiles.
     *
     * @return La chaîne de caractères representant la liste d'étoiles.
     */
    public String toString()
    {
        return Arrays.toString(stars);
    }

    /**
     * Génère tous les triangles possibles formes par trois étoiles distinctes de la constellation.
     *
     * @return Un tableau de triangles construits à partir des points de la constellation.
     */
    public Triangle[] generateTriangles() {
        int size = stars.length;
        int nb_triangles = Combinatorics.combination(size, 3); // Nombre de triangles à générer
        Triangle[] triangles = new Triangle[nb_triangles];
        int index = 0; // Indice du triangle courant

        for (int i = 0; i < size - 2; i++) {
            for (int j = i + 1; j < size - 1; j++) {
                for (int k = j + 1; k < size; k++) {
                    triangles[index] = new Triangle(stars[i], stars[j], stars[k]);
                    index++;
                }
            }
        }
        return triangles;
    }

    /**
     * Trie la liste d'étoile en attribut par luminosité décroissante
     */
    public void sortByBrightness(){
        Arrays.sort(stars, new Comparator<Star>() {
            public int compare(Star firstStar, Star secondStar) {
                return firstStar.compareTo(secondStar);
            }
        });
    }

    /**
     * Méthode qui retourne l'index d'une étoile dans la liste des étoiles
     * @param star Etoile dont on veut connaitre l'index
     * @return Index de l'etoile
     */
    public int getIndex(Star star){
        return Arrays.asList(stars).indexOf(star);
    }

    /**
     * Méthode qui cherche le triangle dans la liste de triangles, correspondant au triangle passé en parametre
     * @param triangle Triangle dont on cherche le triangle correspondant
     * @param dataStars Constellation dont on cherche le triangle correspondant
     * @return Triangle correspondant
     */
    public Triangle findTriangle(Triangle triangle, StarSet dataStars) {
        // Liste d'index des étoiles du triangle dans la liste detectedStars
        int[] indexTriangle = {getIndex(triangle.getStars()[0]), getIndex(triangle.getStars()[1]), getIndex(triangle.getStars()[2])};

        // On n'a pas besoin de retourner l'instance du triangle, on peut en créer un nouveau
        // Cela permet de simplifier le code et de l'optimiser en évitant de parcourir la liste des triangles
        return new Triangle(dataStars.getStars()[indexTriangle[0]], dataStars.getStars()[indexTriangle[1]], dataStars.getStars()[indexTriangle[2]]);
    }
}
