package tsp.polaris.JUnitTest;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tsp.polaris.recognition.other.Star;
import tsp.polaris.recognition.other.Triangle;
import tsp.polaris.recognition.starSet.StarSet;

/**
 * Tests unitaires de la classe StarSet
 *
 * @author Emma M.
 */

public class StarSetTest {

    private StarSet workingStarSet;

    /**
     * Partie exécuté avant chaque test :
     * On définie une constellation avec 6 points distincts
     */
    @BeforeEach
    public void setUp() {
        Star[] stars = new Star[6];
        for(int i = 0; i < 6; i++){
            stars[i] = new Star(i, i, i);
        }
        workingStarSet = new StarSet(stars);
    }

    /**
     * Test 1 : Check de la taille de la liste renvoyé par generateTriangles()
     */
    @Test
    @DisplayName("Test 1 : Check de la taille de la liste renvoyé par generateTriangles()")
    public void lengthCheckGenerateTriangles(){
        Assertions.assertEquals(20, workingStarSet.generateTriangles().length);
    }

    /**
     * Test 2 : Aucun triangle n'est un pointeur null
     */
    @Test
    @DisplayName("Test 2 : Aucun triangle n'est un pointeur null")
    public void nullTriangleCheckGenerateTriangles(){
        Triangle[] triangles = workingStarSet.generateTriangles();
        for (Triangle triangle : triangles) {
            Assertions.assertNotNull(triangle);
        }
    }

    /**
     * Test 3 : Les triangles sont tous différents
     */
    @Test
    @DisplayName("Test 3 : Les triangles sont tous différents")
    public void differentTrianglesCheckGenerateTriangles() {
        Triangle[] triangles = workingStarSet.generateTriangles();
        for (int i = 0; i < triangles.length - 1; i++) {
            for (int j = i + 1; j < triangles.length; j++) {
                Assertions.assertFalse(triangles[i].equals(triangles[j]));
            }
        }
    }

    /**
     * Test 4 : Le tri des étoiles par luminsoté est bien décroissant
     */
    @Test
    @DisplayName("Test 4 : Le tri des étoiles par luminsoté est bien décroissant")
    public void luminosityOrderCheckStarSet() {
        for (int i = 0; i < workingStarSet.getStars().length - 1; i++) {
            Assertions.assertTrue(workingStarSet.getStars()[i].getBrightness() >= workingStarSet.getStars()[i + 1].getBrightness());
        }
    }

    /**
     * Test 5 : Méthode getIndex()
     */
    @Test
    @DisplayName("Test 5 : Méthode getIndex()")
    public void testGetIndex() {
        for(int i = 0; i < workingStarSet.getStars().length; i++){
            Assertions.assertEquals(i, workingStarSet.getIndex(workingStarSet.getStars()[i]));
        }
    }

    /**
     * Test 6 : La méthode findTriangle associe bien un triangle avec lui même
     */
    @Test
    @DisplayName("Test 6 : La méthode findTriangle associe bien un triangle avec lui meme")
    public void testFindTriangle() {
        Triangle[] triangles = workingStarSet.generateTriangles();
        for (Triangle triangle : triangles) {
            Assertions.assertTrue(triangle.equals(workingStarSet.findTriangle(triangle, workingStarSet)));
        }
    }
}
