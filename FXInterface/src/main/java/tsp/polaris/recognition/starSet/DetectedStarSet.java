package tsp.polaris.recognition.starSet;
import tsp.polaris.auxiliaries.Combinatorics;
import tsp.polaris.auxiliaries.Functions;
import static tsp.polaris.auxiliaries.Functions.sum;
import tsp.polaris.recognition.dataTransmission.Data;
import tsp.polaris.recognition.other.Star;

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
     * Cette méthode utilise un algorithme combinatoire base sur un pseudo-code disponible en ligne.
     *
     * @param k Nombre d'étoiles à selectionner.
     * @param resultStars Tableau contenant toutes les combinaisons générées.
     * @param copyStars Copie des étoiles disponibles.
     * @param tempStarList Liste temporaire pour stocker les étoiles en cours de combinaison.
     * @see <a href="http://jm.davalan.org/mots/comb/comb/combalgo.html">Pseudo-code utilise (adapté)</a>
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
     * Méthode qui permet de supprimer un élément de starSet.
     * @param index Index de l'étoile qu'on veut supprimer
     *
     * @return starSet sans l'étoile en position index
     */
    private DetectedStarSet deleteElement(int index) {
        Star[] newstarSet = new Star[stars.length - 1];
        for(int i = 0; i < index; i += 1) {
            newstarSet[i] = stars[i];
        }
        for(int i = index + 1; i < stars.length; i += 1) {
            newstarSet[i - 1] = stars[i];
        }

        return new DetectedStarSet(newstarSet);
    }

    /**
     * Méthode qui permet d'ajouter un élément dans starSet.
     *
     * @param starSetList Liste contenant toutes les permutations de starSet
     * @param newElement Set d'étoiles qu'on veut rajouter
     *
     * @return starSetList avec newElement
     */
    private DetectedStarSet[] addElement(DetectedStarSet[] starSetList, DetectedStarSet newElement) {
        // On crée une nouvelle liste plus grande
        DetectedStarSet[] newstarSetPermutation = new DetectedStarSet[starSetList.length + 1];

        // On copie l'ancienne liste dans la nouvelle
        for(int i = 0; i < starSetList.length; i += 1) {
            newstarSetPermutation[i] = starSetList[i];
        }

        // On ajoute le nouvel élément
        newstarSetPermutation[starSetList.length] = newElement;
        return newstarSetPermutation;
    }

    /**
     * Méthode qui copie un set d'étoiles
     *
     * @return Une copie du set d'étoile courant
     */
    private DetectedStarSet copyDetectedStarSet(){
        // On crée une nouvelle liste d'étoiles
        Star[] copyStar = new Star[stars.length];

        // On copie le contenu de l'ancienne liste dans la nouvelle
        for(int i = 0; i < stars.length; i += 1){
            copyStar[i] = stars[i];
        }
        return new DetectedStarSet(copyStar);
    }

    /**
     * Méthode qui permet de trouver toutes les permutations d'une liste d'étoiles.
     * Cette méthode utilise un algorithme combinatoire basé sur un programme disponible en ligne.
     *
     * @param starSet Set d'étoiles sur lequel on veut trouver les permutations
     *
     * @return Toutes les permutations de starSet
     * @see <a href="http://revue.sesamath.net/spip.php?article1020">Programme utilisé (adapté)</a>
     */
    public DetectedStarSet[] findAllPermutations(DetectedStarSet starSet) {
        if(starSet.getStars().length == 0) {
            // Cas ou la starSet est vide
            return new DetectedStarSet[0];
        } else if(starSet.getStars().length == 1) {
            // Cas ou starSet contient une seule étoile
            DetectedStarSet[] starSetPermutationTemp = new DetectedStarSet[1];
            starSetPermutationTemp[0] = starSet.copyDetectedStarSet();
            return starSetPermutationTemp;
        } else {
            // Cas général

            DetectedStarSet[] result = new DetectedStarSet[0];
            // On itère sur chaque étoile
            for(int i = 0; i < starSet.getStars().length; i += 1) {

                // On supprime l'étoile en position i et on regarde les permutations possibles
                DetectedStarSet[] newPerm = findAllPermutations(starSet.deleteElement(i));

                // On itère sur chaque permutation
                for (int j = 0; j < newPerm.length; j += 1) {

                    // On forme les permutations avec l'élément supprimé et les permutations obtenues dans le set sans l'élément
                    DetectedStarSet newElement = new DetectedStarSet(new Star[newPerm[j].getStars().length + 1]);
                    newElement.getStars()[0] = starSet.getStars()[i];
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

        // Parcours les constellations passées en argument
        for (Constellation cons : constellations)
        {

            // On calcule la liste des triangles du set d'étoiles
            ListTriangle listTriangleStarSet = new ListTriangle(generateTriangles());

            // Calcul les coûts entre les triangles du set d'étoiles et ceux de la constellation c
            double[] liste_cout = listTriangleStarSet.costs(cons.getFirstStarsListTriangle(),this,cons);

            double total = sum(liste_cout);  // Calcul le total des coûts

            // Verifiez si le total des coûts de cette constellation est le plus bas
            if (minimum_cout > total) {
                minimum_cout = total;  // Mettez à jour le coût minimal
                winningConstellation = cons;
            }
        }
        nearConstellation = winningConstellation;
        return minimum_cout;  // Retourner le cout minimal de la constellation
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
    	// On cree une liste composee de toutes les combinaisons d'étoiles à k éléments :

    	// Nombre de combinaisons
    	int nbCombination = Combinatorics.combination(stars.length, k);
    	
    	// Liste de toutes les combinaisons de k étoiles
    	DetectedStarSet[] starSetCombinations = new DetectedStarSet[nbCombination];

        // Liste de toutes les combinaisons et toutes leur bijection
        DetectedStarSet[][] starSetCombinationsPlusPermutations = new DetectedStarSet[nbCombination][Functions.factorial(k)];

        // On remplit la liste des combinaisons
    	combinationStar(k,starSetCombinations, stars,new Star[0]);

        // On remplit la liste des combinaisons et leurs différentes permutations
        for(int i = 0; i < nbCombination; i += 1){
            starSetCombinationsPlusPermutations[i] = findAllPermutations(starSetCombinations[i]);
        }

    	// On cherche l'ensemble d'étoiles de taille k qui ressemble le plus à une constellation -> on regarde le coût minimal
    	double minCoutConstellation = Double.MAX_VALUE;
    	int indConstellation = -1;
        int indPermutation = -1;
    	
    	for(int i = 0; i < nbCombination; i += 1) {
            for(int j = 0; j < Functions.factorial(k) ; j += 1) {
                
                // On calcule le cout minimal entre le set d'étoiles et les constellations
                double coutCons = starSetCombinationsPlusPermutations[i][j].costConstellation(constellations);

                if (minCoutConstellation > coutCons) {
                    indConstellation = i;
                    indPermutation = j;
                    minCoutConstellation = coutCons;
                }
            }
    	}

    	return starSetCombinationsPlusPermutations[indConstellation][indPermutation];
    }

    /**
     * Recherche la liste d'étoile qui ressemble le plus à une constellation.
     *
     * @param constellations Les constellations à comparer, venant de la base de donnees.
     * @return La liste d'étoile qui ressemble le plus à une constellation.
     * @throws TriangleMatchingException Si une erreur se produit lors du calcul des coûts des triangles.
     */
    public DetectedStarSet searchBestStarSet(int nbStudiedStars, Constellation... constellations) throws TriangleMatchingException {
        // Liste d'étoiles choisies pour chaque constellation (on choisit de ne regarder que les nbStudiedStars premières étoiles à chaque fois)

        DetectedStarSet bestFirstStars = findRightStarSet(nbStudiedStars,constellations);

        // On cherche ensuite les autres étoiles de la constellations
        DetectedStarSet bestStarSet = bestFirstStars.findOtherStars(nbStudiedStars);

        return bestStarSet;
    }

    /**
     * A partir des k premières étoiles qu'on a trouvées, on cherche les autres étoiles de la constellation.
     *
     * @param k Nombre d'étoiles déjà trouvées.
     * @return La meilleure liste d'étoiles qui ressemble le plus à la constellation.
     * @throws TriangleMatchingException
     */
    public DetectedStarSet findOtherStars(int k) throws TriangleMatchingException {
        // Meilleur set d'étoiles
        DetectedStarSet bestStarSet = new DetectedStarSet(new Star[nearConstellation.getStars().length]);
        bestStarSet.nearConstellation = nearConstellation;

        // On met les k premières étoiles qu'on a trouvé dans le set d'étoiles
        for(int i = 0; i < k; i += 1) {
            bestStarSet.stars[i] = stars[i];
        }

        // On va calculer la positions des autres étoiles grâce aux positions des 2 premières et des angles formées par les autres
        for(int i = k; i < nearConstellation.getStars().length; i += 1) {
            Star firstStar = stars[0];
            Star secondStar = stars[1];

            // On calcule ensuite les angles formées entre les 2 premières étoiles et l'étoile qu'on cherche
            Triangle triangle = new Triangle(nearConstellation.getStars()[0], nearConstellation.getStars()[1], nearConstellation.getStars()[i]);

            // On cherche les distances du triangle sur l'image
            double a = firstStar.distance(secondStar);
            double aNormalized = nearConstellation.getStars()[0].distance(nearConstellation.getStars()[1])/Functions.sum(triangle.getSides());
            double coef = a / aNormalized;

            double b = coef * triangle.getRatiosSides()[1];
            double c = coef * triangle.getRatiosSides()[2];

            double orientation = (triangle.getStars()[1].getPoint()[0] - triangle.getStars()[0].getPoint()[0])*(triangle.getStars()[2].getPoint()[1] - triangle.getStars()[0].getPoint()[1]) - (triangle.getStars()[1].getPoint()[1] - triangle.getStars()[0].getPoint()[1])*(triangle.getStars()[2].getPoint()[0] - triangle.getStars()[0].getPoint()[0]);

            double[] coor = firstStar.getCoordinate(a, c, b, secondStar, orientation);

            // On calcule la position de l'étoile

            bestStarSet.stars[i] = new Star(coor[0], coor[1], 0, 0);
        }

        return bestStarSet;
    }
}
