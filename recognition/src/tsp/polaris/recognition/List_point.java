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
    private Constellation[] combinationConstellation(int taille, Constellation[] listeConstellation) {
    	// A REMPLIR
    	return null;                     
    }
    
    public Constellation findRightConstellation(int taille, double[] coutMinParTaille, Constellation...constellations) throws TriangleMatchingException {
    	// On crée une liste composé de tous les ensembles de points à taille éléments
    	int nbCombination = Combinatorics.combination(points.length, taille);
    	Constellation[] listeConstellation = new Constellation[nbCombination];
    	combinationConstellation(taille,listeConstellation);
    	
    	// On cherche l'ensemble de points de taille taille qui ressemble le plus à une constellation 
    	double minCoutConstellation = Double.MAX_VALUE;
    	int indConstellation = -1;
    	for(int i = 0; i < listeConstellation.length; i += 1) {
    		double coutCons = listeConstellation[i].coutConstellation(constellations);
    		if(minCoutConstellation > coutCons) {
    			indConstellation = i;
    			minCoutConstellation = coutCons;
    		}
    	}
    	coutMinParTaille[taille-3] = minCoutConstellation;
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
