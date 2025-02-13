package tsp.polaris.recognition;

/**
 * Classe qui va contenir des listes de triangles
 * @author Emma M., Chadi A.
 */

public class List_triangle
{
	private Triangle[] listeT;
	
	/**
	 * Constructeur qui initialise le tableau des triangles
	 * @param listeT Liste des triangles
	 */
	public List_triangle(Triangle[] listeT) {
		this.listeT = listeT;
	}
	
	/**
	 * Méthode d'affichage
	 * @return String : Coordonnées des points des triangles
	 */
	public String toString() {
		String str = "";
		for(Triangle triangle : listeT) {
			str += " " + triangle.toString();
		}
		return str;
	}
	
	/**
	 * Méthodes qui calcule le coût entre des listes de triangles 
	 * @param listeTriangle2 Liste de triangles avec laquelle on veut calculer le cout
	 * @return double[] : Liste des couts entre les triangles des 2 listes
	 * @throws TriangleMatchingException Problème d'appariement de liste de triangle
	 */
    public double[] couts(List_triangle listeTriangle2) throws TriangleMatchingException
    {
        int taille1 = listeT.length;
        int taille2 = listeTriangle2.listeT.length;
        
        // Cas ou les listes n'ont pas la même taille (donc ne peuvent pas représenter la même constellation)
        if (taille1 != taille2)
        {
            throw new TriangleMatchingException("Les tableaux de triangles ont des tailles différentes : " + taille1 + " et " + taille2);
        }
        
        // On va comparer chaque triangle entre eux et mettre leurs couts dans une matrice
        double[] tab = new double[taille1 * taille2];
        int index = 0;
        for (int triangle1 = 0; triangle1 < taille1; triangle1++)
        {
            for (int triangle2 = 0; triangle2 < taille1; triangle2++)
            {
                tab[index] = listeT[triangle1].cout(listeTriangle2.listeT[triangle2]);
                index ++;
            }
        }
        return tab;
    }
    
    /**
     * Méthode qui calcule l'indice de correspondance entre 2 listes de triangles
     * @param listeTriangle2 Liste de triangles avec laquelle on veut calculer le cout
     * @return int[] : L'indice de correspondance entre deux listes de triangles
     * @throws TriangleMatchingException Problème d'appariement de liste de triangles
     */
    public int[] indice(List_triangle listeTriangle2) throws TriangleMatchingException
    {
        double[] couts = couts(listeTriangle2);
        int nombre_combinaisons = couts.length;

        int nb_triangles = (int) Math.sqrt(nombre_combinaisons);
        int[] tab_indices = new int[nb_triangles];
        boolean[] usedTrianglesSecondSet = new boolean[nb_triangles];

        for (int indice_triangle = 0; indice_triangle < nb_triangles; indice_triangle++) {
            int start = indice_triangle * nb_triangles;
            int end = start + nb_triangles;

            double minValue = Double.MAX_VALUE;
            int minIndex = -1;

            // Parcourir les coûts pour trouver le minimum non utilisé
            for (int i = start; i < end; i++) {
                int secondSetIndex = i % nb_triangles;
                if (!usedTrianglesSecondSet[secondSetIndex] && couts[i] < minValue) {
                    minValue = couts[i];
                    minIndex = secondSetIndex; // Stocker l'indice dans le second set
                }
            }

            if (minIndex == -1) {
                throw new IllegalStateException("Aucun minimum trouvé. Vérifiez les données.");
            }

            // Marquer le triangle du second set comme utilisé
            usedTrianglesSecondSet[minIndex] = true;
            tab_indices[indice_triangle] = minIndex; // Associer l'indice trouvé
        }

        return tab_indices;
    }
}
