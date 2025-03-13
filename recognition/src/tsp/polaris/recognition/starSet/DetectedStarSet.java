package tsp.polaris.recognition.starSet;
import tsp.polaris.auxiliaries.Combinatorics;
import tsp.polaris.auxiliaries.Functions;
import tsp.polaris.recognition.dataTransmission.Data;
import tsp.polaris.recognition.other.ListTriangle;
import tsp.polaris.recognition.other.Star;
import tsp.polaris.recognition.other.Triangle;
import tsp.polaris.recognition.exceptions.TriangleMatchingException;

import static tsp.polaris.auxiliaries.Functions.sum;

/**
 * Classe représentant une liste d'étoile dans l'image à analyser
 * Cette classe permet notamment de générer des combinaisons d'étoiles
 * et d'identifier l'ensemble d'étoiles correspondant le mieux à une constellation.
 *
 * @author Chadi A., Emma M.
 */
public class DetectedStarSet extends StarSet
{
    Constellation nearConstellation;

    /**
     * Constructeur de la classe StarSet.
     *
     * @param stars Liste d'étoiles constituant l'objet.
     */
    public DetectedStarSet(Star[] stars)
    {
        super(stars);
        nearConstellation = null; // Constellation la plus proche de la liste d'étoiles
    }

    /**
     * Création d'une DetectedStarSet depuis des données d'étoiles.
     * @param data Données des étoiles
     * @return DetectedStarSet : Ensemble d'étoiles correspondant aux données d'étoiles.
     */
    public static DetectedStarSet createDetectedStarSetWithData(Data data) {
        return new DetectedStarSet(data.getData().toArray(new Star[0]));
    }
    
    /**
     * Liste auquelle on rajoute un élément 
     * 
     * @param list La liste d'étoile
     * @param star L'étoile qu'on va rajouter
     * @return Une liste avec les éléments de list auquelle on rajoute star
     */
    private Star[] listAddElement(Star[] list, Star star) {
    	Star[] newList = new Star[list.length + 1];
    	for(int i = 0; i < list.length; i += 1) {
    		newList[i] = list[i];
    	}
    	newList[list.length] = star;
    	return newList;
    }
    
    /**
     * Renvoie la liste des étoiles situées après l'index i dans list
     * 
     * @param i Index à partir duquel on veut copier la liste
     * @param list Liste à copier
     * @return La liste des étoiles situées après l'index i dans list 
     */
    private Star[] listAfterIndex(int i, Star[] list) {
    	Star[] listAfterIndex = new Star[list.length - i - 1];
    	for(int j = 0 ; j < list.length-i - 1; j += 1) {
    		listAfterIndex[j] = list[j + i + 1];
    	}
    	return listAfterIndex;
    }

    /**
     * Renvoie l'index de la première liste d'étoiles null de resultStars
     *
     * @param resultStars Tableau contenant toutes les combinaisons à générer.
     * @return L'index de la première combinaison d'étoiles null de resultStars
     */
    private int firstNull(DetectedStarSet[] resultStars){
        for(int i = 0; i < resultStars.length; i += 1){
            if(resultStars[i] == null){
                return i;
            }
        }
        return -1;
    }

    /**
     * Génère toutes les combinaisons possibles de k étoiles parmi les étoiles disponibles.
     * Cette fonction utilise un algorithme combinatoire base sur un pseudo-code disponible en ligne.
     *
     * @param k Nombre d'étoiles à selectionner.
     * @param resultStars Tableau contenant toutes les combinaisons générées.
     * @param copyStars Copie des étoiles disponibles.
     * @param tempStarList Liste temporaire pour stocker les étoiles en cours de combinaison.
     * @see <a href="http://jm.davalan.org/mots/comb/comb/combalgo.html">Pseudo-code utilise (modifie)</a>
     */
    public void combinationStar(int k, DetectedStarSet[] resultStars, Star[] copyStars, Star[] tempStarList) {
    	if(k > copyStars.length) { // Cas ou on demande des combinaisons de K parmi N avec K > N
    		return;
    	} else if(k <= 0) { // Cas ou on a termine de faire une combinaison
            // On ajoute cette combinaison dans resultStars en recherchant d'abord le premier indice qui ne pointe pas vers une liste nulle
            int indice = firstNull(resultStars);
    		resultStars[indice] = new DetectedStarSet(tempStarList);
    	} else {
    		for(int i = 0; i < copyStars.length; i += 1) {
    			Star[] g = listAfterIndex(i, copyStars); // g est la liste des étoiles de copyStars se situant après l'indice i
    			
    			Star[] l2 = listAddElement(tempStarList,copyStars[i]); // l2 est la liste tempStarList auquel on rajoute l'élément en indice i de copyStars
    			
    			combinationStar(k-1, resultStars, g, l2);
    		}
    	}
    }
    
    /**
     * Recherche la meilleure liste d'étoiles qui minimise le cout entre elle et les constellations.
     *
     * @param k Nombre d'étoiles à selectionner.
     * @param coutMinParTaille Tableau stockant les coûts minimaux par taille de constellation.
     * @param constellations Liste des constellations de reference.
     * @return La liste d'étoiles ayant le coût minimal.
     * @throws TriangleMatchingException Si une erreur survient lors de l'appariement des constellations.
     */
    public DetectedStarSet findRightStarSet(int k, double[] coutMinParTaille, Constellation...constellations) throws TriangleMatchingException {
    	// On cree une liste composee de toutes les combinaisons d'étoiles à k elements :
    	
    	// Nombre de combinaisons
    	int nbCombination = Combinatorics.combination(stars.length, k);
    	
    	// Liste de toutes les combinaisons de k étoiles
    	DetectedStarSet[] starsSetCombinations = new DetectedStarSet[nbCombination];

        // On remplit la liste
    	combinationStar(k,starsSetCombinations, stars,new Star[k]);
    	
    	// On cherche l'ensemble d'étoiles de taille k qui ressemble le plus à une constellation -> on regarde le coût minimal
    	double minCoutConstellation = Double.MAX_VALUE;
    	int indConstellation = -1;
    	
    	for(int i = 0; i < starsSetCombinations.length; i += 1) {
    		double coutCons = starsSetCombinations[i].coutConstellation(constellations);
    		if(minCoutConstellation > coutCons) {
    			indConstellation = i;
    			minCoutConstellation = coutCons;
    		}
    	}
    	
    	coutMinParTaille[k-3] = minCoutConstellation;
    	return starsSetCombinations[indConstellation];
    }
    
    /**
     * Calcule le coût minimal entre l'ensemble d'étoiles de la combinaison et un ensemble de constellations donnees.
     *
     * @param constellations Les constellations à comparer.
     * @return Le coût minimal entre la photo et les constellations.
     * @throws TriangleMatchingException Si une erreur se produit lors du calcul des coûts des triangles.
     */
    public double coutConstellation(Constellation... constellations) throws TriangleMatchingException
    {
        Constellation winningConstellation = null; // Garde en mémoire la constellation la plus proche
        double minimum_cout = Double.MAX_VALUE; // Utilise une valeur maximale pour commencer.

        // Parcour les constellations passees en argument
        for (Constellation c : constellations)
        {
            // On ne regarde que les constellations qui ont le meme nombre d'étoiles
            if(c.getStars().length == getStars().length) {
                // Genere les triangles du set d'étoiles et la constellation c
                Triangle[] trianglesStarSet = generateTriangles(); // Triangles du set d'étoiles
                Triangle[] trianglesConstellation = c.generateTriangles(); // Triangles de la constellation c

                ListTriangle listTriangleStarSet = new ListTriangle(trianglesStarSet);
                ListTriangle listTriangleConstellation = new ListTriangle(trianglesConstellation);

                // Calcul les coûts entre les triangles du set d'étoiles et ceux de la constellation c
                double[] liste_cout = listTriangleStarSet.couts(listTriangleConstellation,stars,c.getStars());

                double total = sum(liste_cout);  // Calcul le total des coûts

                // Verifiez si le total des coûts de cette constellation est le plus bas
                if (minimum_cout > total) {
                    minimum_cout = total;  // Mettez à jour le coût minimal
                    winningConstellation = c;
                }
            }
        }
        nearConstellation = winningConstellation;
        return minimum_cout;  // Retourner le cout minimal de la constellation
    }

    /**
     * Recherche la liste d'étoile qui ressemble le plus à une constellation.
     *
     * @param constellations Les constellations à comparer, venant de la base de donnees.
     * @return La liste d'étoile qui ressemble le plus à une constellation.
     * @throws TriangleMatchingException Si une erreur se produit lors du calcul des coûts des triangles.
     */
    public DetectedStarSet searchBestStarSet(Constellation... constellations) throws TriangleMatchingException {
        // On va calculer pour chaque taille possible de constellation, le cout minimal entre toutes les constellations.

        // Liste d'étoiles choisies pour chaque constellation
        DetectedStarSet[] selectedStarSet = new DetectedStarSet[10]; // LE 10 EST TOTALEMENT ARBITRAIRE, IL FAUDRAIT METTRE LA TAILLE DE LA PLUS GRANDE CONSTELLATION
        // Cout minimal entre la liste d'étoile selectionne et les constellations
        double[] minCostPerLength = new double[10];

        // Pour chaque taille d'ensemble d'étoiles, on va chercher l'ensemble d'étoiles qui ressemble le plus à une constellation
        for(int i = 0; i < 10; i += 1) {
            selectedStarSet[i] = findRightStarSet(i+3,minCostPerLength,constellations);
        }

        // On recherche la taille d'étoiles qui a le cout le plus faible
        int minIndex = Functions.minIndex(minCostPerLength);

        return selectedStarSet[minIndex];

    }
}
