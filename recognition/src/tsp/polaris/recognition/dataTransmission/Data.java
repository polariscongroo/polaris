package tsp.polaris.recognition.dataTransmission;

import tsp.polaris.recognition.other.Star;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de données lues dans le fichier csv du script python
 *
 * @author Emma M., Chadi A., Ryane S.
 */

public class Data {
    private List<Star> data;
    private final String fileName;
    private List<List<Integer>> adjacencyList;

    /**
     * Constructeur de la classe Data qui lit le fichier csv et le vide
     *
     * @param path Chemin vers le fichier csv
     */
    public Data(String path, String fileName) {
        this.fileName = fileName;
        try{
            // Lis le fichier csv créé par le script python
            data = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            boolean isThereAdjacencyList = false;
            while ((line = br.readLine()) != null) {
                if(line.equals("L")) {
                    isThereAdjacencyList = true;
                    break;
                }
                String[] values = line.split(",");
                Star newPoint = new Star(Float.parseFloat(values[0].trim()), Float.parseFloat(values[1].trim()), Float.parseFloat(values[2].trim()) * Float.parseFloat(values[3].trim()));
                data.add(newPoint);

            }

            // Maintenant on lit la liste d'adjacence si elle existe
            if(isThereAdjacencyList) {
                adjacencyList = new ArrayList<>();

                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    List<Integer> newList = new ArrayList<>();
                    for (String value : values) {
                        newList.add(Integer.parseInt(value));
                    }
                    adjacencyList.add(newList);
                }
            }
            // Ferme le buffer
            br.close();
        }
        catch (FileNotFoundException f) {
            System.out.println("Impossible de trouver le fichier");
        }
        catch (IOException e) {
            System.out.println("Impossible de modifier le fichier");
        }
    }

    /**
     * Affichage des points
     *
     * @return String : Affiche le contenu du fichier csv
     */
    public String toString() {
        return data.toString();
    }

    /**
     * Getteur de data
     *
     * @return List<Star> : Donne le contenu du fichier csv du script python
     */
    public List<Star> getData() {
        return data;
    }

    /**
     * Getteur d'adjacencyList
     * @return List<List<Integer>> : Retourne la liste d'adjacence du csv si elle existe
     */
    public List<List<Integer>> getAdjacencyList() {
        if(adjacencyList != null) {
            return adjacencyList;
        } else {
            throw new IllegalStateException("Ce n'est pas une constellation, donc il n'y a pas de liste d'adjacence");
        }
    }

    /**
     * Efface le contenu du csv
     *
     * @param path Chemin du fichier csv
     * @throws IOException Erreur lancée lors de la modification du fichier
     */
    public void eraseCsv(String path) throws IOException {
        new FileWriter(path).close();
    }

    /**
     * Getteur de fileName
     * @return String : Retourne le nom du fichier
     */
    public String getFileName() {
        return fileName;
    }
}
