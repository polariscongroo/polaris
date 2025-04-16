package tsp.polaris.recognition.dataTransmission;

import java.io.File;

/**
 * Classe répertoriant toutes les données de la base de données
 *
 * @author Chadi A., Emma M.
 */
public class Database {

    private Data[] dataSet;

    /**
     * Constructeur de la classe Database
     *
     * @param path Chemin vers le dossier contenant les fichiers CSV
     */
    public Database(String path){
        File repertory = new File(path);
        File[] files = repertory.listFiles();

        dataSet = new Data[files.length];
        for (int i = 0; i < files.length; i++) {
            dataSet[i] = new Data(files[i].getPath(), files[i].getName().substring(0, files[i].getName().lastIndexOf(".")));
        }
    }

    /**
     * Getteur de dataSet
     * @return Data[] : Retourne le contenu du dataSet
     */
    public Data[] getDataSet() {
        return dataSet;
    }
}
