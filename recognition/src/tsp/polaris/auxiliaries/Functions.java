package tsp.polaris.auxiliaries;

import tsp.polaris.recognition.other.Star;

/**
 * Classe utilitaire pour les fonctions.
 *
 * @author Emma M.
 */
public class Functions {
	
    /**
     * Renvoie l'index du minimum d'un tableau
     * @param tab tableau
     * @return int : index du minimum d'un tableau
     */
    public static int minIndex(double[] tab) {
        int min = 0;
        for(int i = 0; i < 10; i += 1) {
            if(tab[i] < tab[min]) {
                min = i;
            }
        }
        return min;
    }
    
}
