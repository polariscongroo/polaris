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
     * On définie un set de point avec 6 points distincts
     */
	public void setUp1() {
		Star[] stars = new Star[6];
		for(int i = 0; i < 6; i += 1) {
			stars[i] = new Star(i,i,0);
		}
		workingDetectedStarSet = new DetectedStarSet(stars);
	}

	/**
	 * On définit un set de 7 points distincts (set qui provoque un bug qui est à résoudre)
	 */
	public void setUp2() {
		Star[] stars = new Star[7];
		stars[0] = new Star(112.54545454545455,16.636363636363637,0.9233870967741935);
		stars[1] = new Star(168.0,33.0,1.0);
		stars[2] = new Star(17.0,65.0,0.6370967741935484);
		stars[3] = new Star(179.0,75.0,0.9959677419354839);
		stars[4] = new Star(215.0,75.0,0.8266129032258065);
		stars[5] = new Star(233.83333333333334,84.16666666666667,0.782258064516129);
		stars[6] = new Star(213.6,139.4,0.9919354838709677);
		workingDetectedStarSet = new DetectedStarSet(stars);
	}
	
	/**
	 * Test 1 : CombinationStar renvoie une liste à k parmi n liste de k éléments
	 */
    @Test
    @DisplayName("Test 1 : CombinationStar renvoie une liste à k parmi n liste de k éléments")
    public void lengthCombinationStar1() {
		setUp1();
    	int lengthSet = Combinatorics.combination(workingDetectedStarSet.getStars().length, 3); // Nombre de combinaison à 3 éléments
		DetectedStarSet[] resultCombination = new DetectedStarSet[lengthSet];
    	// On met dans resultCombination, toutes les combinaisons à k élément d'éléments de workingDetectedStarSet
    	workingDetectedStarSet.combinationStar(3,resultCombination,workingDetectedStarSet.getStars(),new Star[0]);
    	// Il y a bien le bon nombre de combinaison
        Assertions.assertEquals(lengthSet,resultCombination.length);
        for(int i = 0; i < lengthSet; i += 1) {
        	// Les combinaisons sont toutes de tailles 3
        	Assertions.assertEquals(3, resultCombination[i].getStars().length);
        }
    }

	/**
	 * Test 2 : CombinationStar renvoie une liste de combinaison toutes non vides
	 */
	@Test
	@DisplayName("Test 2 : CombinationStar renvoie une liste de combinaison toutes non vides")
	public void notEmptyCombinationStar1() {
		setUp1();
		int lengthSet = Combinatorics.combination(workingDetectedStarSet.getStars().length, 3); // Nombre de combinaison à 3 éléments
		DetectedStarSet[] resultCombination = new DetectedStarSet[lengthSet];
		// On met dans resultCombination, toutes les combinaisons à k élément d'éléments de workingDetectedStarSet
		workingDetectedStarSet.combinationStar(3,resultCombination,workingDetectedStarSet.getStars(),new Star[0]);
		for(int i = 0; i < lengthSet; i += 1){
			for(int j = 0; j < 3; j += 1){
				// Les éléments sont tous non nuls
				Assertions.assertNotNull(resultCombination[i].getStars()[j]);
			}
		}
	}
    
    /**
     * Test 3 : CombinationStar renvoie une liste de combinaison toutes différentes
     */
    @Test
    @DisplayName("Test 3 : CombinationStar renvoie une liste de combinaison toutes différentes")
    public void differentCombinationStar1() {
		setUp1();
    	int lengthSet = Combinatorics.combination(workingDetectedStarSet.getStars().length, 3); // Nombre de combinaison à 3 éléments
    	DetectedStarSet[] resultCombination = new DetectedStarSet[lengthSet];
    	// On met dans resultCombination, toutes les combinaisons à k élément d'éléments de workingDetectedStarSet
    	workingDetectedStarSet.combinationStar(3, resultCombination, workingDetectedStarSet.getStars(), new Star[0]);
    	for(int i = 0; i < lengthSet; i += 1) {
    		for(int j = i + 1; j < lengthSet; j += 1) {
				for(int k = 0; k < 3; k += 1) {
					Assertions.assertNotEquals(resultCombination[i].getStars(), resultCombination[j].getStars()); // Vérifie que les combinaisons sont toutes différentes
				}
    		}
    	}
    }

	/**
	 * Test 4 : CombinationStar renvoie une liste à k parmi n liste de k éléments
	 */
	@Test
	@DisplayName("Test 4 : CombinationStar renvoie une liste à k parmi n liste de k éléments")
	public void lengthCombinationStar2() {
		setUp2();
		int lengthSet = Combinatorics.combination(workingDetectedStarSet.getStars().length, 3); // Nombre de combinaison à 3 éléments
		DetectedStarSet[] resultCombination = new DetectedStarSet[lengthSet];
		// On met dans resultCombination, toutes les combinaisons à k élément d'éléments de workingDetectedStarSet
		workingDetectedStarSet.combinationStar(3,resultCombination,workingDetectedStarSet.getStars(),new Star[0]);
		// Il y a bien le bon nombre de combinaison
		Assertions.assertEquals(lengthSet,resultCombination.length);
		for(int i = 0; i < lengthSet; i += 1) {
			// Les combinaisons sont toutes de tailles 3
			Assertions.assertEquals(3, resultCombination[i].getStars().length);
		}
	}

	/**
	 * Test 5 : CombinationStar renvoie une liste de combinaison toutes non vides
	 */
	@Test
	@DisplayName("Test 5 : CombinationStar renvoie une liste de combinaison toutes non vides")
	public void notEmptyCombinationStar2() {
		setUp2();
		int lengthSet = Combinatorics.combination(workingDetectedStarSet.getStars().length, 3); // Nombre de combinaison à 3 éléments
		DetectedStarSet[] resultCombination = new DetectedStarSet[lengthSet];
		// On met dans resultCombination, toutes les combinaisons à k élément d'éléments de workingDetectedStarSet
		workingDetectedStarSet.combinationStar(3,resultCombination,workingDetectedStarSet.getStars(),new Star[0]);
		for(int i = 0; i < lengthSet; i += 1){
			for(int j = 0; j < 3; j += 1){
				// Les éléments sont tous non nuls
				Assertions.assertNotNull(resultCombination[i].getStars()[j]);
			}
		}
	}

	/**
	 * Test 6 : CombinationStar renvoie une liste de combinaison toutes différentes
	 */
	@Test
	@DisplayName("Test 6 : CombinationStar renvoie une liste de combinaison toutes différentes")
	public void differentCombinationStar2() {
		setUp2();
		int lengthSet = Combinatorics.combination(workingDetectedStarSet.getStars().length, 3); // Nombre de combinaison à 3 éléments
		DetectedStarSet[] resultCombination = new DetectedStarSet[lengthSet];
		// On met dans resultCombination, toutes les combinaisons à k élément d'éléments de workingDetectedStarSet
		workingDetectedStarSet.combinationStar(3, resultCombination, workingDetectedStarSet.getStars(), new Star[0]);
		for(int i = 0; i < lengthSet; i += 1) {
			for(int j = i + 1; j < lengthSet; j += 1) {
				for(int k = 0; k < 3; k += 1) {
					Assertions.assertNotEquals(resultCombination[i].getStars(), resultCombination[j].getStars()); // Vérifie que les combinaisons sont toutes différentes
				}
			}
		}

		for(int i = 0; i < lengthSet; i += 1) {
			System.out.println("blabla " + i);
			System.out.println(resultCombination[i]);
		}
	}
}
