package tsp.polaris.recognition;

import tsp.polaris.recognition.dataTransmission.Data;
import tsp.polaris.recognition.exceptions.TriangleMatchingException;
import tsp.polaris.recognition.starSet.Constellation;
import tsp.polaris.recognition.starSet.DetectedStarSet;

import java.io.IOException;

/**
 * Classe principale qui va lancer notre programme
 *
 * @author Emma M., Chadi A., Ryane S.
 */

public class Main
{
    public static void main(String[] args) throws TriangleMatchingException, NumberFormatException, IOException {
        Data data = new Data("recognition/coorPoints/liste_etoiles.csv");
        DetectedStarSet detectedStarSet = DetectedStarSet.createDetectedStarSetWithData(data);
        System.out.println(detectedStarSet);

        /*String nomConstellation[] = new String[88];
        // Ajouter ailleurs qui remplit nomConstellation avec le nom de toutes les constellations (potentiellement en lisant un fichier texte ou autre)

        // Ensembles d'étoiles détectées
        Data data = new Data("coorPoints/liste_etoiles.csv");
        DetectedStarSet detectedStarSet = DetectedStarSet.createDetectedStarSetWithData(data);

        // Lecture de notre base de données de constellations
        Data[] dataBDD = new Data[88];

        for (int i = 0; i < 88; i++) {
            dataBDD[i] = new Data("../baseDDonnees_csv/" + nomConstellation[i] + ".csv");
        }

        // Ensemble des constellations
        Constellation[] constellations = new Constellation[88];
        for (int i = 0; i < 88; i++) {
            constellations[i] = Constellation.createConstellationWithData(dataBDD[i], nomConstellation[i]);
        }

        System.out.println(detectedStarSet.searchBestStarSet());*/

    }
}