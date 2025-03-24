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
    private String fileName;

    /**
     * Constructeur de la classe Data qui lit le fichier csv et le vide
     *
     * @param path Chemin vers le fichier csv
     */
    public Data(String path, String fileName) {
        this.fileName = fileName;
        System.out.println(fileName);
        try{
            // Lis le fichier csv créé par le script python
            data = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] values = line.split(",");
                Star newPoint = new Star(Float.parseFloat(values[0].trim()), Float.parseFloat(values[1].trim()), Float.parseFloat(values[2].trim()));
                data.add(newPoint);
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
