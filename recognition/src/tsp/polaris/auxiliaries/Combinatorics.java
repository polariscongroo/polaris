package tsp.polaris.auxiliaries;
/**
 * Classe utilitaire pour les opérations combinatoires.
 * Contient des méthodes pour calculer des combinaisons et d'autres opérations combinatoires.
 * 
 * * @author Chadi A., Emma M.
*/
public class Combinatorics {
    /**
     * Calcule la combinaison C(n, k), c'est-à-dire le nombre de façons de choisir k éléments parmi n éléments.
     * La formule utilisée est : C(n, k) = n! / (k! * (n - k)!)
     *
     * @param n Le nombre total d'éléments.
     * @param k Le nombre d'éléments à choisir parmi n.
     * @return Le nombre de combinaisons C(n, k).
     * @throws IllegalArgumentException Si k est supérieur à n ou si n ou k sont négatifs.
     */
    // Méthode pour calculer C(n, k)
    public static int combination(int n, int k) throws IllegalArgumentException
    {
        if (k > n || n < 0 || k < 0)
        {
            throw new IllegalArgumentException("n et k doivent respecter 0 <= k <= n.");
        }
        // C(n, k) = C(n, n-k), donc on peut réduire les calculs
        k = Math.min(k, n - k);

        int result = 1;
        for (int i = 0; i < k; i++)
        {
            result = result * (n - i) / (i + 1);
        }
        return result;
    }
}
