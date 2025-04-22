package tsp.polaris.recognition.starSet;
import tsp.polaris.JUnitTest.DetectedStarSetTest;
import tsp.polaris.auxiliaries.Combinatorics;
import tsp.polaris.auxiliaries.Functions;
import tsp.polaris.recognition.dataTransmission.Data;
import tsp.polaris.recognition.other.Star;


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
    private Constellation nearConstellation;

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
     * Getteur de la constellation la plus proche.
     *
     * @return La constellation la plus proche.
     */
    public Constellation getNearConstellation() {
        return nearConstellation;
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
        System.arraycopy(list, 0, newList, 0, list.length);
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
     * Méthode qui permet de supprimer un élément de starsSet.
     * @param index Index de l'étoile qu'on veut supprimer
     *
     * @return starsSet sans l'étoile en position index
     */
    private DetectedStarSet deleteElement(int index) {
        Star[] newStarsSet = new Star[stars.length - 1];
        for(int i = 0; i < index; i += 1) {
            newStarsSet[i] = stars[i];
        }
        for(int i = index + 1; i < stars.length; i += 1) {
            newStarsSet[i - 1] = stars[i];
        }

        return new DetectedStarSet(newStarsSet);
    }

    /**
     * Méthode qui permet d'ajouter un élément dans starsSet.
     * @param starsSet Tableau contenant toutes les permutations de starsSet
     * @param newElement Liste d'étoiles qu'on veut rajouter
     *
     * @return starsSet avec newElement
     */
    private DetectedStarSet[] addElement(DetectedStarSet[] starsSet, DetectedStarSet newElement) {
        DetectedStarSet[] newStarsSetPermutation = new DetectedStarSet[starsSet.length + 1];
        for(int i = 0; i < starsSet.length; i += 1) {
            newStarsSetPermutation[i] = starsSet[i];
        }
        newStarsSetPermutation[starsSet.length] = newElement;
        return newStarsSetPermutation;
    }

    /**
     * @return Une copie du set d'étoile courant
     */
    private DetectedStarSet copyDetectedStarSet(){
        Star[] copyStar = new Star[stars.length];
        for(int i = 0; i < stars.length; i += 1){
            copyStar[i] = stars[i];
        }
        return new DetectedStarSet(copyStar);
    }

    /**
    * Méthode qui permet de trouver toutes les permutations d'une liste d'étoiles.
    * @param starsSet Set d'étoiles sur lequel on veut trouver les permutations
    *
    * @return Toutes les permutations de starsSet
    */
    public DetectedStarSet[] findAllPermutations(DetectedStarSet starsSet) {
        if(starsSet.getStars().length == 0) {
            // Cas ou la starSet est vide
            return new DetectedStarSet[0];
        } else if(starsSet.getStars().length == 1) {
            // Cas ou starSet contient une seule étoile
            DetectedStarSet[] starsSetPermutationTemp = new DetectedStarSet[1];
            starsSetPermutationTemp[0] = starsSet.copyDetectedStarSet();
            return starsSetPermutationTemp;
        } else {
            // Cas général

            // Resultat renvoyé
            DetectedStarSet[] result = new DetectedStarSet[0];
            // On itère sur chaque étoile
            for(int i = 0; i < starsSet.getStars().length; i += 1) {

                // On supprime l'étoile en position i et on regarde les permutations possibles
                DetectedStarSet[] newPerm = findAllPermutations(starsSet.deleteElement(i));

                // On itère sur chaque permutation
                for (int j = 0; j < newPerm.length; j += 1) {

                    // On forme les permutations avec l'élément supprimé et les permutations obtenues dans le set sans l'élément
                    DetectedStarSet newElement = new DetectedStarSet(new Star[newPerm[j].getStars().length + 1]);
                    newElement.getStars()[0] = starsSet.getStars()[i];
                    for(int k = 1; k <= newPerm[j].getStars().length; k += 1) {
                        newElement.getStars()[k] = newPerm[j].getStars()[k - 1];
                    }

                    // On ajoute la permutation au resultat
                    result = addElement(result, newElement);
                }
            }
            return result;
        }
    }
    
    /**
     * Recherche la meilleure liste d'étoiles qui minimise le cout entre elle et les constellations.
     *
     * @param k Nombre d'étoiles à selectionner.
     * @param constellations Liste des constellations de reference.
     * @return La liste d'étoiles ayant le coût minimal.
     * @throws TriangleMatchingException Si une erreur survient lors de l'appariement des constellations.
     */
    private DetectedStarSet findRightStarSet(int k, Constellation...constellations) throws TriangleMatchingException {
    	// On cree une liste composee de toutes les combinaisons d'étoiles à k elements :
    	
    	// Nombre de combinaisons
    	int nbCombination = Combinatorics.combination(stars.length, k);
    	
    	// Liste de toutes les combinaisons de k étoiles
    	DetectedStarSet[] starsSetCombinations = new DetectedStarSet[nbCombination];

        // Liste de toutes les combinaisons et toutes leur bijection
        DetectedStarSet[][] starsSetCombinationsPlusPermutations = new DetectedStarSet[nbCombination][120];

        // On remplit la liste des combinaisons
    	combinationStar(k,starsSetCombinations, stars,new Star[0]);

        // On remplit la liste des combinaisons et leurs différents ordres
        for(int i = 0; i < nbCombination; i += 1){
            starsSetCombinationsPlusPermutations[i] = findAllPermutations(starsSetCombinations[i]);
        }

    	// On cherche l'ensemble d'étoiles de taille k qui ressemble le plus à une constellation -> on regarde le coût minimal
    	double minCoutConstellation = Double.MAX_VALUE;
    	int indConstellation = -1;
        int indPermutation = -1;
    	
    	for(int i = 0; i < nbCombination; i += 1) {
            System.out.println(i);
            for(int j = 0; j < 120 ; j += 1) {
                double coutCons = starsSetCombinationsPlusPermutations[i][j].costConstellation(constellations);
                if (minCoutConstellation > coutCons) {
                    indConstellation = i;
                    indPermutation = j;
                    minCoutConstellation = coutCons;
                }
            }
    	}
    	return starsSetCombinationsPlusPermutations[indConstellation][indPermutation];
    }

    /**
     * Retourne les 5 premiers éléments d'un tableau d'étoiles.
     *
     * @param stars Tableau d'étoiles.
     * @return Les 5 premiers éléments du tableau.
     */
    private Star[] firstFiveStars(Star[] stars) {
        Star[] result = new Star[5];
        for(int i = 0; i < 5; i += 1) {
            result[i] = stars[i];
        }
        return result;
    }

    /**
     * Calcule le coût minimal entre l'ensemble d'étoiles de la combinaison et un ensemble de constellations donnees.
     *
     * @param constellations Les constellations à comparer.
     * @return Le coût minimal entre la photo et les constellations.
     * @throws TriangleMatchingException Si une erreur se produit lors du calcul des coûts des triangles.
     */
    public double costConstellation(Constellation... constellations) throws TriangleMatchingException
    {
        Constellation winningConstellation = null; // Garde en mémoire la constellation la plus proche
        double minimum_cout = Double.MAX_VALUE; // Utilise une valeur maximale pour commencer.

        // Parcour les constellations passees en argument
        for (Constellation cons : constellations)
        {
            // On ne regarde pas les constellations avec moins de 5 étoiles
            if(cons.getStars().length < 5) {
                continue;
            }

            Constellation c = new Constellation(firstFiveStars(cons.getStars()), cons.getName(), cons.getAdjacencyList());

            // Genere les triangles du set d'étoiles et la constellation c
            Triangle[] trianglesStarSet = generateTriangles(); // Triangles du set d'étoiles
            Triangle[] trianglesConstellation = c.generateTriangles(); // Triangles de la constellation c


            ListTriangle listTriangleStarSet = new ListTriangle(trianglesStarSet);
            ListTriangle listTriangleConstellation = new ListTriangle(trianglesConstellation);

            // Calcul les coûts entre les triangles du set d'étoiles et ceux de la constellation c
            double[] liste_cout = listTriangleStarSet.costs(listTriangleConstellation,this,c);

            double total = sum(liste_cout);  // Calcul le total des coûts

            // Verifiez si le total des coûts de cette constellation est le plus bas
            if (minimum_cout > total) {
                minimum_cout = total;  // Mettez à jour le coût minimal
                winningConstellation = c;
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
        // Liste d'étoiles choisies pour chaque constellation
        DetectedStarSet selectedStarSet = findRightStarSet(5,constellations);

        return selectedStarSet;

    }

    /**
     * Verifie s'il existe une constellation a k étoiles.
     * @param k
     * @param constellations
     * @return boolean : vrai s'il existe une constellation à k étoiles, faux sinon
     */
    private boolean existConstellationWithKStars(int k, Constellation[] constellations) {
    	for(Constellation c : constellations) {
    		if(c.getStars().length == k) {
    			return true;
    		}
    	}
    	return false;
    }
}
