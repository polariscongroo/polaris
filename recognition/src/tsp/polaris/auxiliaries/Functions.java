package tsp.polaris.auxiliaries;

/**
 * Classe utilitaire pour les fonctions.
 *
 * @author Chadi A., Emma M.
 */
public class Functions {

    /**
     * Renvoie l'indice du plus petit élément d'un tableau
     * @param tab tableau de nombres
     * @return l'indice du plus petit élément
     * @throws IllegalArgumentException si le tableau est vide ou null
     */
    public static int minIndex(double[] tab) {
        if (tab == null || tab.length == 0) {
            throw new IllegalArgumentException("Le tableau ne peut pas être vide ou null.");
        }
        int minIndex = 0;
        for (int i = 1; i < tab.length; i++) {
            if (tab[i] < tab[minIndex]) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    /**
     * Renvoie la somme d'un tableau
     * @param tab tableau
     * @return double : somme d'un tableau
     * @throws IllegalArgumentException si le tableau est null
     */
    public static double sum(double[] tab) {
        if (tab == null) {
            throw new IllegalArgumentException("Le tableau ne peut pas être null.");
        }
        double sum = 0;
        for (double value : tab) {
            sum += value;
        }
        return sum;
    }

    /**
     * Renvoie le plus petit des deux entiers
     * @param a 1er entier
     * @param b 2e entier
     * @return Le plus petit des deux entiers
     */
    public static int min(int a, int b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }
    
}