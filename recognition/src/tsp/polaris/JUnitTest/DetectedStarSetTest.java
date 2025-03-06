package tsp.polaris.JUnitTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tsp.polaris.auxiliaries.Combinatorics;
import tsp.polaris.recognition.other.Star;
import tsp.polaris.recognition.starSet.DetectedStarSet;

/**
 * Tests unitaires de la classe DetectedStarSet
 *
 * @author Emma M.
 */

public class DetectedStarSetTest {
    private DetectedStarSet workingDetectedStarSet;
	
    /**
     * Partie exécuté avant chaque test :
     * On définie une constellation avec 6 points distincts
     */
	@BeforeEach
	public void setUp() {
		workingDetectedStarSet = new DetectedStarSet(new Star[6]);
		for(int i = 0; i < 6; i += 1) {
			workingDetectedStarSet.getStars()[i] = new Star(i,i,0);
		}
	}
	
	/**
	 * Test 1 : CombinationStar renvoie une liste à k parmi n liste de k éléments
	 */
    @Test
    @DisplayName("Test 1 : CombinationStar renvoie une liste à k parmi n liste de k éléments")
    public void lengthCombinationStar() {
    	int lengthSet = Combinatorics.combination(workingDetectedStarSet.getStars().length, 3); // Nombre de combinaison à 3 éléments
    	Star[][] resultCombination = new Star[lengthSet][3];
    	// On met dans resultCombination, toutes les combinaisons à k élément d'éléments de workingDetectedStarSet
    	workingDetectedStarSet.combinationStar(3,resultCombination,workingDetectedStarSet.getStars(),new Star[0]);
    	// Il y a bien le bon nombre de combinaison
        Assertions.assertEquals(lengthSet,resultCombination.length);
        for(int i = 0; i < lengthSet; i += 1) {
        	// Les combinaisons sont toutes de tailles 3
        	Assertions.assertEquals(3, resultCombination[i].length);
        }
    }

	/**
	 * Test 2 : CombinationStar renvoie une liste de combinaison toutes non vides
	 */
	@Test
	@DisplayName("Test 2 : CombinationStar renvoie une liste de combinaison toutes non vides")
	public void notEmptyCombinationStar() {
		int lengthSet = Combinatorics.combination(workingDetectedStarSet.getStars().length, 3); // Nombre de combinaison à 3 éléments
		Star[][] resultCombination = new Star[lengthSet][3];
		// On met dans resultCombination, toutes les combinaisons à k élément d'éléments de workingDetectedStarSet
		workingDetectedStarSet.combinationStar(3,resultCombination,workingDetectedStarSet.getStars(),new Star[0]);
		for(int i = 0; i < lengthSet; i += 1){
			for(int j = 0; j < 3; j += 1){
				// Les éléments sont tous non nuls
				Assertions.assertNotNull(resultCombination[i][j]);
			}
		}
	}
    
    /**
     * Test 3 : CombinationStar renvoie une liste de combinaison toutes différentes
     */
    @Test
    @DisplayName("Test 3 : CombinationStar renvoie une liste de combinaison toutes différentes")
    public void differentCombinationStar() {
    	int lengthSet = Combinatorics.combination(workingDetectedStarSet.getStars().length, 3); // Nombre de combinaison à 3 éléments
    	Star[][] resultCombination = new Star[lengthSet][3];
    	// On met dans resultCombination, toutes les combinaisons à k élément d'éléments de workingDetectedStarSet
    	workingDetectedStarSet.combinationStar(3, resultCombination, workingDetectedStarSet.getStars(), new Star[0]);
    	for(int i = 0; i < lengthSet; i += 1) {
    		for(int j = i + 1; j < lengthSet; j += 1) {
    			Assertions.assertNotEquals(resultCombination[i], resultCombination[j]); // Vérifie que les combinaisons sont toutes différentes
    		}
    	}
    }
}
