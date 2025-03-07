package tsp.polaris.recognition.other;

import tsp.polaris.recognition.exceptions.TriangleMatchingException;

/**
 * Classe qui va contenir des listes de triangles
 * @author Emma M., Chadi A.
 */

public class ListTriangle
{
	private Triangle[] listTriangle;
	
	/**
	 * Constructeur qui initialise le tableau des triangles
	 * @param listTriangle Liste des triangles
	 */
	public ListTriangle(Triangle[] listTriangle) {
		this.listTriangle = listTriangle;
	}
	
	/**
	 * Methode d'affichage
	 * @return String : Coordonnees des points des triangles
	 */
	public String toString() {
		String str = "";
		for(Triangle triangle : listTriangle) {
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
    public double[] couts(ListTriangle listeTriangle2) throws TriangleMatchingException
    {
        // Taille des triangles
        int taille1 = listTriangle.length;
        int taille2 = listeTriangle2.listTriangle.length;
        
        // Cas ou les listes n'ont pas la même taille (donc ne peuvent pas representer la même constellation)
        if (taille1 != taille2)
        {
            throw new TriangleMatchingException("Les tableaux de triangles ont des tailles differentes : " + taille1 + " et " + taille2);
        }
        
        // On va comparer chaque triangle entre eux et mettre leurs couts dans une liste
        double[] tab = new double[taille1 * taille2];
        int index = 0; // Indice du triangle courant
        for (int triangle1 = 0; triangle1 < taille1; triangle1++)
        {
            for (int triangle2 = 0; triangle2 < taille1; triangle2++)
            {
                tab[index] = listTriangle[triangle1].cout(listeTriangle2.listTriangle[triangle2]); // Cout entre les 2 triangles
                index ++;
            }
        }
        return tab;
    }
    
    /**
     * Methode qui calcule l'indice de correspondance entre 2 listes de triangles
     * @param listeTriangle2 Liste de triangles avec laquelle on veut calculer le cout
     * @return int[] : L'indice de correspondance entre deux listes de triangles
     * @throws TriangleMatchingException Problème d'appariement de liste de triangles
     */
    public int[] indice(ListTriangle listeTriangle2) throws TriangleMatchingException
    {
        double[] couts = couts(listeTriangle2); // Couts entre les 2 triangles
        int nombre_combinaisons = couts.length;

        int nb_triangles = (int) Math.sqrt(nombre_combinaisons);
        int[] tab_indices = new int[nb_triangles];
        boolean[] usedTrianglesSecondSet = new boolean[nb_triangles]; // Tableau de booléen qui représente les triangles déjà associés

        for (int indice_triangle = 0; indice_triangle < nb_triangles; indice_triangle++) {
            int start = indice_triangle * nb_triangles;
            int end = start + nb_triangles;

            double minValue = Double.MAX_VALUE;
            int minIndex = -1;

            // Parcourir les coûts pour trouver le minimum non utilise
            for (int i = start; i < end; i++) {
                int secondSetIndex = i % nb_triangles;
                if (!usedTrianglesSecondSet[secondSetIndex] && couts[i] < minValue) {
                    minValue = couts[i];
                    minIndex = secondSetIndex; // Stocker l'indice dans le second set
                }
            }

            if (minIndex == -1) {
                throw new IllegalStateException("Aucun minimum trouve. Verifiez les donnees.");
            }

            // Marquer le triangle du second set comme utilise
            usedTrianglesSecondSet[minIndex] = true;
            tab_indices[indice_triangle] = minIndex; // Associer l'indice trouve
        }

        return tab_indices;
    }
}
