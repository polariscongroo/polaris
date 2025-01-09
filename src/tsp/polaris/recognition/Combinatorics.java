package tsp.polaris.recognition;
public class Combinatorics {

    // Méthode pour calculer C(n, k)
    static int combination(int n, int k)
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
