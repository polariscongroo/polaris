package tsp.polaris.JUnitTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tsp.polaris.auxiliaries.Functions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour les fonctions de la classe Functions.
 *
 * Cette classe teste les méthodes minIndex et sum avec divers cas de figures.
 *
 * @author Chadi A.
 */
public class FunctionsTest {

    /**
     * Test 1 : minIndex trouve le plus petit élément dans un tableau général
     */
    @Test
    @DisplayName("Test 1 : minIndex trouve le plus petit élément dans un tableau général")
    void testMinIndexGeneralCase() {
        double[] tab = {5.0, 3.2, 7.8, 1.5, 9.3};
        assertEquals(3, Functions.minIndex(tab));
        System.out.println("Test réussi : testMinIndexGeneralCase");
    }

    /**
     * Test 2 : minIndex renvoie le premier élément lorsqu'il est le plus petit
     */
    @Test
    @DisplayName("Test 2 : minIndex renvoie le premier élément lorsqu'il est le plus petit")
    void testMinIndexFirstElement() {
        double[] tab = {0.1, 2.2, 3.3, 4.4};
        assertEquals(0, Functions.minIndex(tab));
        System.out.println("Test réussi : testMinIndexFirstElement");
    }

    /**
     * Test 3 : minIndex renvoie le dernier élément lorsqu'il est le plus petit
     */
    @Test
    @DisplayName("Test 3 : minIndex renvoie le dernier élément lorsqu'il est le plus petit")
    void testMinIndexLastElement() {
        double[] tab = {5.5, 6.6, 7.7, -2.2};
        assertEquals(3, Functions.minIndex(tab));
        System.out.println("Test réussi : testMinIndexLastElement");
    }

    /**
     * Test 4 : minIndex renvoie le premier élément lorsque tous les éléments sont égaux
     */
    @Test
    @DisplayName("Test 4 : minIndex renvoie le premier élément lorsque tous les éléments sont égaux")
    void testMinIndexAllEqual() {
        double[] tab = {4.4, 4.4, 4.4, 4.4};
        assertEquals(0, Functions.minIndex(tab));
        System.out.println("Test réussi : testMinIndexAllEqual");
    }

    /**
     * Test 5 : minIndex renvoie l'index 0 pour un tableau à un seul élément
     */
    @Test
    @DisplayName("Test 5 : minIndex renvoie l'index 0 pour un tableau à un seul élément")
    void testMinIndexSingleElement() {
        double[] tab = {42.0};
        assertEquals(0, Functions.minIndex(tab));
        System.out.println("Test réussi : testMinIndexSingleElement");
    }

    /**
     * Test 6 : minIndex lève une exception pour un tableau vide
     */
    @Test
    @DisplayName("Test 6 : minIndex lève une exception pour un tableau vide")
    void testMinIndexEmptyArray() {
        double[] tab = {};
        assertThrows(IllegalArgumentException.class, () -> Functions.minIndex(tab));
        System.out.println("Test réussi : testMinIndexEmptyArray");
    }

    /**
     * Test 7 : sum calcule correctement la somme d'un tableau général
     */
    @Test
    @DisplayName("Test 7 : sum calcule correctement la somme d'un tableau général")
    void testSumGeneralCase() {
        double[] tab = {1.1, 2.2, 3.3, 4.4};
        assertEquals(11.0, Functions.sum(tab), 1e-9);
        System.out.println("Test réussi : testSumGeneralCase");
    }

    /**
     * Test 8 : sum renvoie 0 pour un tableau vide
     */
    @Test
    @DisplayName("Test 8 : sum renvoie 0 pour un tableau vide")
    void testSumEmptyArray() {
        double[] tab = {};
        assertEquals(0.0, Functions.sum(tab), 1e-9);
        System.out.println("Test réussi : testSumEmptyArray");
    }

    /**
     * Test 9 : sum renvoie la valeur unique pour un tableau d'un seul élément
     */
    @Test
    @DisplayName("Test 9 : sum renvoie la valeur unique pour un tableau d'un seul élément")
    void testSumSingleElement() {
        double[] tab = {7.7};
        assertEquals(7.7, Functions.sum(tab), 1e-9);
        System.out.println("Test réussi : testSumSingleElement");
    }

    /**
     * Test 10 : sum gère les valeurs négatives correctement
     */
    @Test
    @DisplayName("Test 10 : sum gère les valeurs négatives correctement")
    void testSumNegativeValues() {
        double[] tab = {-1.1, -2.2, -3.3};
        assertEquals(-6.6, Functions.sum(tab), 1e-9);
        System.out.println("Test réussi : testSumNegativeValues");
    }

    /**
     * Test 11 : sum gère un mélange de valeurs positives et négatives
     */
    @Test
    @DisplayName("Test 11 : sum gère un mélange de valeurs positives et négatives")
    void testSumMixedPositiveNegative() {
        double[] tab = {-1.1, 2.2, -3.3, 4.4};
        assertEquals(2.2, Functions.sum(tab), 1e-9);
        System.out.println("Test réussi : testSumMixedPositiveNegative");
    }
}