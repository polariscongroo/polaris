package tsp.polaris.recognition;

import tsp.polaris.recognition.dataTransmission.Data;
import tsp.polaris.recognition.dataTransmission.Database;
import tsp.polaris.recognition.exceptions.TriangleMatchingException;
import tsp.polaris.recognition.other.Star;
import tsp.polaris.recognition.starSet.Constellation;
import tsp.polaris.recognition.starSet.DetectedStarSet;

import java.io.File;
import java.io.IOException;

/**
 * Classe principale qui va lancer notre programme
 *
 * @author Emma M., Chadi A., Ryane S.
 */

public class Main
{
    public static void main(String[] args) throws TriangleMatchingException, NumberFormatException, IOException {
        // Ensembles d'étoiles détectées
        Data data = new Data("recognition/coorPoints/liste_etoiles.csv", "liste_etoiles");
        DetectedStarSet detectedStarSet = DetectedStarSet.createDetectedStarSetWithData(data);

        // Lecture de notre base de données de constellations
        Database database = new Database("./baseDDonnees_csv");

        // Ensemble des constellations
        Constellation[] constellations = new Constellation[database.getDataSet().length];
        for (int i = 0; i < constellations.length; i++) {
            constellations[i] = Constellation.createConstellationWithData(database.getDataSet()[i]);
        }

        int N = 20;
        // On garde que les N étoiles les plus brillantes
        Star[] starsKept = new Star[N];
        for(int i = 0; i < N; i += 1) {
            starsKept[i] = detectedStarSet.getStars()[i];
        }

        DetectedStarSet detectedStarSetKept = new DetectedStarSet(starsKept);

        DetectedStarSet bestStarSet = detectedStarSetKept.searchBestStarSet(constellations);

        System.out.println(bestStarSet.getNearConstellation().getName());
    }
}