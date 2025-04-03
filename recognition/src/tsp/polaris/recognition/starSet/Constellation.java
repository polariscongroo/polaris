package tsp.polaris.recognition.starSet;

import tsp.polaris.recognition.dataTransmission.Data;
import tsp.polaris.recognition.other.Star;

import java.util.Arrays;
import java.util.List;

/**
 * Représente une constellation composée de plusieurs points.
 * Permet de générer des triangles à partir des points.
 * 
 * @author Chadi A., Emma M.
 */
public class Constellation extends StarSet {
    private final String name;
    private List<List<Integer>> adjacencyList;

    /**
     * Constructeur de la classe Constellation.
     * 
     * @param stars Liste d'étoiles constituant la constellation
     * @param name Nom de la constellation
     * @param adjacencyList Liste d'adjacence de la constellation
     */
    public Constellation(Star[] stars, String name, List<List<Integer>> adjacencyList) {
        super(stars);
        this.name = name;
        this.adjacencyList = adjacencyList;
    }

    /**
     * Création d'une Constellation depuis des données d'étoiles.
     *
     * @param data Données des étoiles
     * @return Constellation : Ensemble d'étoiles correspondant aux données d'étoiles.
     */
    public static Constellation createConstellationWithData(Data data) {
        return new Constellation(data.getData().toArray(new Star[0]), data.getFileName(), data.getAdjacencyList());
    }

    /**
     * Retourne une representation sous forme de chaîne de caractères des étoiles.
     *
     * @return La chaîne de caractères representant la liste d'étoiles.
     */
    public String toString()
    {
        return name + " -> " + Arrays.toString(stars);
    }


    /**
     * Retourne une representation sous forme de chaîne de caractères de la constellation.
     * 
     * @return Une chaîne de caractères représentant la liste des points.
     */
    public String getName() {
        return name;
    }

    /**
     * Getteur d'adjacencyList
     * @return List<List<Integer>> : Retourne la liste d'adjacence de la constellation
     */
    public List<List<Integer>> getAdjacencyList() {
        return adjacencyList;
    }
}
