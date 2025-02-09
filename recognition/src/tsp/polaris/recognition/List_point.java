package tsp.polaris.recognition;
import tsp.polaris.auxiliaries.Combinatorics;
import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private void combinationConstellation(int k,Point[][] resultPoints, Point[] copyPoints,int indice, Point[] tempPointList) {
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
    			
    			combinationConstellation(k-1, resultPoints, g, indice+i, l2);
    		}
    	}
    }
    
    public Constellation findRightConstellation(int k, double[] coutMinParTaille, Constellation...constellations) throws TriangleMatchingException {
    	// On crée une liste composée de tous les ensembles de points à k éléments :
    	
    	// Nombre de combinaisons
    	int nbCombination = Combinatorics.combination(points.length, k);
    	
    	// Liste de toutes les combinaisons de constellations à k points
    	Constellation[] listeConstellation = new Constellation[nbCombination];
    	
    	// Liste de toutes les combinaisons de k points
    	Point[][] pointsListConstellation = new Point[nbCombination][k];
    	
    	combinationConstellation(k,pointsListConstellation,points,0,new Point[k]);
    	
    	// On a la liste des combinaisons de tous les points, il faut maintenant faire de ces listes, des constellations
    	for(int i = 0; i < nbCombination; i += 1) {
    		listeConstellation[i] = new Constellation(pointsListConstellation[i]);
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
    
    public Constellation searchConstellation(Constellation... constellations) throws TriangleMatchingException {
    	// On va calculer pour chaque taille possible de constellation, le cout minimal entre toutes les constellations.
    	// LE 10 EST TOTALEMENT ARBITRAIRE, IL FAUDRAIT METTRE LA TAILLE DE LA PLUS GRANDE CONSTELLATION
    	double[] coutMinParTaille = new double[10];
    	// Liste des points choisis pour chaque constellation 
    	Constellation[] selectedConstellations = new Constellation[10];
    	// Pour chaque taille d'ensemble de points, on va chercher l'ensemble de points qui ressemble le point à une constellation
    	for(int i = 0; i < 10; i += 1) {
    		selectedConstellations[i] = findRightConstellation(i+3,coutMinParTaille,constellations);
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
}
