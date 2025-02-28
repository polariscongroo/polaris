package tsp.polaris.recognition;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de données lues dans le fichier csv du script python
 *
 * @author Emma M., Chadi A., Ryane S.
 */

public class Data {
    private List<List<Float>> data;

    /**
     * Constructeur de la classe Data qui lit le fichier csv et le vide
     *
     * @param path Chemin vers le fichier csv
     */
    public Data(String path) {
        try{
            // Lis le fichier csv créé par le script python
            data = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                List<Float> row = new ArrayList<>();
                for (String val : values) {
                    row.add(Float.parseFloat(val.trim()));
                }
                data.add(row);
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
     * @return List<List<Float>> : Donne le contenu du fichier csv du script python
     */
    public List<List<Float>> getData() {
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
}
