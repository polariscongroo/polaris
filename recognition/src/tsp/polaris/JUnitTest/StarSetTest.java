package tsp.polaris.JUnitTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tsp.polaris.recognition.starSet.Constellation;
import tsp.polaris.recognition.starSet.StarSet;
import tsp.polaris.recognition.other.Star;
import tsp.polaris.recognition.other.Triangle;

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
        workingStarSet = new StarSet(new Star[6]);
        for(int i = 0; i < 6; i++){
            workingStarSet.getStars()[i] = new Star(i, i, 0);
        }
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
     * Test 2 : Les triangles sont tous différents
     */
    @Test
    @DisplayName("Test 2 : Les triangles sont tous différents")
    public void differentTrianglesCheckGenerateTriangles(){
        Triangle[] triangles = workingStarSet.generateTriangles();
        for (int i = 0; i < triangles.length - 1; i++) {
            for (int j = i + 1; j < triangles.length; j++) {
                Assertions.assertFalse(triangles[i].equals(triangles[j]));
            }
        }
    }

}
