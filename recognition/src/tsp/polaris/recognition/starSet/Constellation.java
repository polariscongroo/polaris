package tsp.polaris.recognition.starSet;

import tsp.polaris.recognition.other.Star;

/**
 * Représente une constellation composée de plusieurs points.
 * Permet de générer des triangles à partir des points.
 * 
 * @author Chadi A., Emma M.
 */
public class Constellation extends StarSet {
    private String nom;

    /**
     * Constructeur de la classe Constellation.
     * 
     * @param stars Liste des points constituant la constellation.
     */
    public Constellation(Star[] stars, String nom) {
        super(stars);
        this.nom = nom;
    }

    /**
     * Getteur de points
     *
     */
    public Star[] getStars() {
        return stars;
    }

    /**
     * Retourne une representation sous forme de chaîne de caractères de la constellation.
     * 
     * @return Une chaîne de caractères représentant la liste des points.
     */
    public String getNom() {
        return nom;
    }
}
