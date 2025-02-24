package tsp.polaris.recognition;
import java.util.Arrays;
import tsp.polaris.auxiliaries.Combinatorics;
/**
 * Classe representant une liste de points dans l'espace.
 * Cette classe permet notamment de generer des combinaisons de points
 * et d'identifier l'ensemble de points correspondant le mieux à une constellation.
 *
 * @author Chadi A., Emma M.
 */
public class List_point
{
    private Point[] points;
    /**
     * Constructeur de la classe List_point.
     *
     * @param points Liste des points constituant l'objet.
     */
    public List_point(Point[] points)
    {
        this.points = points;
    }
    /**
     * Genère toutes les combinaisons possibles de k points parmi les points disponibles.
     * Cette fonction utilise un algorithme combinatoire base sur un pseudo-code disponible en ligne.
     *
     * @param k Nombre de points à selectionner.
     * @param resultPoints Tableau contenant toutes les combinaisons generees.
     * @param copyPoints Copie des points disponibles.
     * @param indice Indice de la combinaison en cours.
     * @param tempPointList Liste temporaire pour stocker les points en cours de combinaison.
     * @see <a href="http://jm.davalan.org/mots/comb/comb/combalgo.html">Pseudo-code utilise (modifie)</a>
     */
    // Fonction auxiliaire qui permet de creer une liste de toutes les combinaisons d'etoiles d'une taille donnee
    private void combinationPoint(int k,Point[][] resultPoints, Point[] copyPoints,int indice, Point[] tempPointList) {
    	// Cas ou on demande des combinaisons de K parmi N avec K > N
    	if(k > copyPoints.length) {
    		return;
    	// Cas ou on a termine de faire une combinaison
    	} else if(k <= 0) {
    		resultPoints[indice] = tempPointList;
    	} else {
    		for(int i = 0; i < copyPoints.length; i += 1) {
    			// g est la liste des points se situant après l'indice i
    			Point[] g = new Point[copyPoints.length - i - 1];
    			for(int j = i+1; j < copyPoints.length; j += 1) {
    				g[i] = copyPoints[i];
    			}
    			
    			// l2 est la liste tempPointList auquel on rajoute l'element en indice i de copyPoints
    			Point[] l2 = new Point[tempPointList.length + 1];
    			for(int j = 0; j < tempPointList.length; j += 1) {
    				l2[j] = tempPointList[j];
    			}
    			l2[l2.length - 1] = copyPoints[i];
    			
    			combinationPoint(k-1, resultPoints, g, indice+i, l2);
    		}
    	}
    }
    
    /**
     * Recherche la meilleure liste de points correspondant à une constellation.
     *
     * @param k Nombre de points à selectionner.
     * @param coutMinParTaille Tableau stockant les coûts minimaux par taille de constellation.
     * @param constellations Liste des constellations de reference.
     * @return La liste de points ayant le coût minimal.
     * @throws TriangleMatchingException Si une erreur survient lors de l'appariement des constellations.
     */
    public List_point findRightListPoint(int k, double[] coutMinParTaille, Constellation...constellations) throws TriangleMatchingException {
    	// On cree une liste composee de tous les ensembles de points à k elements :
    	
    	// Nombre de combinaisons
    	int nbCombination = Combinatorics.combination(points.length, k);
    	
    	// Liste de toutes les combinaisons de constellations à k points
    	List_point[] listeConstellation = new List_point[nbCombination];
    	
    	// Liste de toutes les combinaisons de k points
    	Point[][] pointsListConstellation = new Point[nbCombination][k];
    	
    	combinationPoint(k,pointsListConstellation,points,0,new Point[k]);
    	
    	// On a la liste des combinaisons de tous les points, il faut maintenant faire de ces listes, des constellations
    	for(int i = 0; i < nbCombination; i += 1) {
    		listeConstellation[i] = new List_point(pointsListConstellation[i]);
    	}
    	
    	// On cherche l'ensemble de points de taille k qui ressemble le plus à une constellation -> on regarde le coût minimal
    	double minCoutConstellation = Double.MAX_VALUE;
    	int indConstellation = -1;
    	
    	for(int i = 0; i < listeConstellation.length; i += 1) {
    		double coutCons = listeConstellation[i].coutConstellation(constellations);
    		if(minCoutConstellation > coutCons) {
    			indConstellation = i;
    			minCoutConstellation = coutCons;
    		}
    	}
    	
    	coutMinParTaille[k-3] = minCoutConstellation;
    	return listeConstellation[indConstellation];
    }
    /**
     * Recherche la constellation la plus proche parmi un ensemble de constellations donnees.
     *
     * @param constellations Les constellations à comparer, venant de la base de donnees.
     * @return La liste de points correspondant à la constellation la plus proche.
     * @throws TriangleMatchingException Si une erreur se produit lors du calcul des coûts des triangles.
     */
    public List_point searchConstellation(Constellation... constellations) throws TriangleMatchingException {
    	// On va calculer pour chaque taille possible de constellation, le cout minimal entre toutes les constellations.
    	// LE 10 EST TOTALEMENT ARBITRAIRE, IL FAUDRAIT METTRE LA TAILLE DE LA PLUS GRANDE CONSTELLATION
    	double[] coutMinParTaille = new double[10];
    	// Liste des points choisis pour chaque constellation 
    	List_point[] selectedConstellations = new List_point[10];
    	// Pour chaque taille d'ensemble de points, on va chercher l'ensemble de points qui ressemble le point à une constellation
    	for(int i = 0; i < 10; i += 1) {
    		selectedConstellations[i] = findRightListPoint(i+3,coutMinParTaille,constellations);
    	}
    	// On recherche la taille de points qui a le cout le plus faible
    	// A METTRE DANS UNE FONCTION AUXILIAIRE
    	int min = 0;
    	for(int i = 0; i < 10; i += 1) {
    		if(coutMinParTaille[i] < coutMinParTaille[min]) {
    			min = i;
    		}
    	}
    	return selectedConstellations[min];
    	
    }

    /**
     * Retourne une representation sous forme de chaîne de caractères des points.
     *
     * @return La chaîne de caractères representant la liste des points.
     */
    public String toString()
    {
        return Arrays.toString(points);
    }

    /**
     * Genère les triangles possibles à partir des points d'une constellation.
     *
     * @return Un tableau de triangles generes à partir des points de la constellation.
     */
    public Triangle[] generateTriangles()
    {
        int size = points.length;
        int nb_triangles = Combinatorics.combination(size, 3);
        Triangle[] triangles = new Triangle[nb_triangles];
        int index = 0;
        for (int i = 0; i < size - 2; i++)
        {
            for (int j = i + 1; j < size- 1; j++)
            {
                for (int k = j + 1; k < size; k++)
                {
                    triangles[index] = new Triangle(points[i], points[j], points[k]);
                    index++;
                }
            }
        }
        return triangles;
    }

    /**
     * Calcule le coût total d'une constellation à partir des coûts des triangles.
     *
     * @param cout_triangle Tableau des coûts des triangles.
     * @return Le coût total de la constellation.
     */
    static double couts(double[] cout_triangle)
    {
        double cout_constellation = 0;
        for (int cout = 0; cout < cout_triangle.length; cout++)
        {
            cout_constellation += cout_triangle[cout];
        }
        return cout_constellation;
    }

    /**
     * Selectionne la constellation avec le coût minimal parmi un ensemble de constellations donnees.
     *
     * @param constellations Les constellations à comparer.
     * @return La constellation avec le coût minimal.
     * @throws TriangleMatchingException Si une erreur se produit lors du calcul des coûts des triangles.
     */
    public String selectConstellation(Constellation... constellations) throws TriangleMatchingException
    {
        // Initialiser le "winner" à null
        Constellation winner = null;
        double minimum_cout = Double.MAX_VALUE; // Utiliser une valeur maximale pour commencer.

        // Parcourir les constellations passees en argument
        for (Constellation c : constellations)
        {
            // Generer les triangles pour la photo et la constellation c
            Triangle[] triangles_photo = generateTriangles(); // Triangles de la photo
            Triangle[] triangles_c = c.generateTriangles();         // Triangles de la constellation c

            List_triangle listPhoto = new List_triangle(triangles_photo);
            List_triangle listTriangle = new List_triangle(triangles_c);

            // Calculer les coûts entre les triangles de la photo et ceux de la constellation c
            double[] liste_cout = listPhoto.couts(listTriangle);
            double total = couts(liste_cout);  // Calculez le total des coûts

            // Verifiez si le total des coûts de cette constellation est le plus bas
            if (minimum_cout > total)
            {
                minimum_cout = total;  // Mettez à jour le coût minimal
                winner = c;             // Mettez à jour la constellation gagnante
            }
        }
        return winner.getnom();  // Retourner la constellation avec le coût minimal
    }
    
    /**
     * Calcule le coût minimal entre l'ensemble de points de la photo et un ensemble de constellations donnees.
     *
     * @param constellations Les constellations à comparer.
     * @return Le coût minimal entre la photo et les constellations.
     * @throws TriangleMatchingException Si une erreur se produit lors du calcul des coûts des triangles.
     */
    // Renvoie le coût minimale entre notre ensemble de points et les differentes constellations
    public double coutConstellation(Constellation... constellations) throws TriangleMatchingException
    {
        double minimum_cout = Double.MAX_VALUE; // Utiliser une valeur maximale pour commencer.

        // Parcourir les constellations passees en argument
        for (Constellation c : constellations)
        {
            // Generer les triangles pour la photo et la constellation c
            Triangle[] triangles_photo = generateTriangles(); // Triangles de la photo
            Triangle[] triangles_c = c.generateTriangles();         // Triangles de la constellation c

            List_triangle listPhoto = new List_triangle(triangles_photo);
            List_triangle listTriangle = new List_triangle(triangles_c);

            // Calculer les coûts entre les triangles de la photo et ceux de la constellation c
            double[] liste_cout = listPhoto.couts(listTriangle);
            double total = couts(liste_cout);  // Calculez le total des coûts

            // Verifiez si le total des coûts de cette constellation est le plus bas
            if (minimum_cout > total)
            {
                minimum_cout = total;  // Mettez à jour le coût minimal
            }
        }
        return minimum_cout;  // Retourner la constellation avec le coût minimal
    }
}
