package tsp.polaris.recognition;
import java.util.Arrays;
import tsp.polaris.auxiliaries.Combinatorics;

public class List_point
{
    private Point[] points;
    
    public List_point(Point[] points)
    {
        this.points = points;
    }
    
    // Fonction auxiliaire qui permet de créer une liste de toutes les combinaisons d'étoiles d'une taille donnée
    // Le pseudo-code de cette fonction vient de : http://jm.davalan.org/mots/comb/comb/combalgo.html (Je l'ai quelque peu modifié)
    private void combinationPoint(int k,Point[][] resultPoints, Point[] copyPoints,int indice, Point[] tempPointList) {
    	// Cas ou on demande des combinaisons de K parmi N avec K > N
    	if(k > copyPoints.length) {
    		return;
    	// Cas ou on a terminé de faire une combinaison
    	} else if(k <= 0) {
    		resultPoints[indice] = tempPointList;
    	} else {
    		for(int i = 0; i < copyPoints.length; i += 1) {
    			// g est la liste des points se situant après l'indice i
    			Point[] g = new Point[copyPoints.length - i - 1];
    			for(int j = i+1; j < copyPoints.length; j += 1) {
    				g[i] = copyPoints[i];
    			}
    			
    			// l2 est la liste tempPointList auquel on rajoute l'élément en indice i de copyPoints
    			Point[] l2 = new Point[tempPointList.length + 1];
    			for(int j = 0; j < tempPointList.length; j += 1) {
    				l2[j] = tempPointList[j];
    			}
    			l2[l2.length - 1] = copyPoints[i];
    			
    			combinationPoint(k-1, resultPoints, g, indice+i, l2);
    		}
    	}
    }
    
    public List_point findRightListPoint(int k, double[] coutMinParTaille, Constellation...constellations) throws TriangleMatchingException {
    	// On crée une liste composée de tous les ensembles de points à k éléments :
    	
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

    public String toString()
    {
        return Arrays.toString(points);
    }

	// Ancien constellation

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

    static double couts(double[] cout_triangle)
    {
        double cout_constellation = 0;
        for (int cout = 0; cout < cout_triangle.length; cout++)
        {
            cout_constellation += cout_triangle[cout];
        }
        return cout_constellation;
    }

    public Constellation selectConstellation(Constellation... constellations) throws TriangleMatchingException
    {
        // Initialiser le "winner" à null
        Constellation winner = null;
        double minimum_cout = Double.MAX_VALUE; // Utiliser une valeur maximale pour commencer.

        // Parcourir les constellations passées en argument
        for (Constellation c : constellations)
        {
            // Générer les triangles pour la photo et la constellation c
            Triangle[] triangles_photo = generateTriangles(); // Triangles de la photo
            Triangle[] triangles_c = c.generateTriangles();         // Triangles de la constellation c

            List_triangle listPhoto = new List_triangle(triangles_photo);
            List_triangle listTriangle = new List_triangle(triangles_c);

            // Calculer les coûts entre les triangles de la photo et ceux de la constellation c
            double[] liste_cout = listPhoto.couts(listTriangle);
            double total = couts(liste_cout);  // Calculez le total des coûts

            // Vérifiez si le total des coûts de cette constellation est le plus bas
            if (minimum_cout > total)
            {
                minimum_cout = total;  // Mettez à jour le coût minimal
                winner = c;             // Mettez à jour la constellation gagnante
            }
        }
        return winner;  // Retourner la constellation avec le coût minimal
    }
    
    // Renvoie le coût minimale entre notre ensemble de points et les différentes constellations
    public double coutConstellation(Constellation... constellations) throws TriangleMatchingException
    {
        double minimum_cout = Double.MAX_VALUE; // Utiliser une valeur maximale pour commencer.

        // Parcourir les constellations passées en argument
        for (Constellation c : constellations)
        {
            // Générer les triangles pour la photo et la constellation c
            Triangle[] triangles_photo = generateTriangles(); // Triangles de la photo
            Triangle[] triangles_c = c.generateTriangles();         // Triangles de la constellation c

            List_triangle listPhoto = new List_triangle(triangles_photo);
            List_triangle listTriangle = new List_triangle(triangles_c);

            // Calculer les coûts entre les triangles de la photo et ceux de la constellation c
            double[] liste_cout = listPhoto.couts(listTriangle);
            double total = couts(liste_cout);  // Calculez le total des coûts

            // Vérifiez si le total des coûts de cette constellation est le plus bas
            if (minimum_cout > total)
            {
                minimum_cout = total;  // Mettez à jour le coût minimal
            }
        }
        return minimum_cout;  // Retourner la constellation avec le coût minimal
    }
}
