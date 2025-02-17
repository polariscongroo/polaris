package tsp.polaris.auxiliaries;
/**
 * Classe utilitaire pour les operations combinatoires.
 * Contient des methodes pour calculer des combinaisons et d'autres operations combinatoires.
 * 
 * * @author Chadi A., Emma M.
*/
public class Combinatorics {
	
	/**
	 * Constructeur vide
	 */
	public Combinatorics() {
		
	}
	
    /**
     * Calcule la combinaison C(n, k), c'est-à-dire le nombre de façons de choisir k elements parmi n elements.
     * La formule utilisee est : C(n, k) = n! / (k! * (n - k)!)
     *
     * @param n Le nombre total d'elements.
     * @param k Le nombre d'elements à choisir parmi n.
     * @return Le nombre de combinaisons C(n, k).
     * @throws IllegalArgumentException Si k est superieur à n ou si n ou k sont negatifs.
     */
    // Methode pour calculer C(n, k)
    public static int combination(int n, int k) throws IllegalArgumentException
    {
        if (k > n || n < 0 || k < 0)
        {
            throw new IllegalArgumentException("n et k doivent respecter 0 <= k <= n.");
        }
        // C(n, k) = C(n, n-k), donc on peut reduire les calculs
        k = Math.min(k, n - k);

        int result = 1;
        for (int i = 0; i < k; i++)
        {
            result = result * (n - i) / (i + 1);
        }
        return result;
    }
}
