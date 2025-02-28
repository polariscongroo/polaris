package tsp.polaris.JUnitTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tsp.polaris.recognition.Constellation;
import tsp.polaris.recognition.Point;
import tsp.polaris.recognition.Triangle;

/**
 * @author Emma M.
 */

public class ConstellationTest {

    private Constellation workingConstellation;

    /**
     * Partie exécuté avant chaque test :
     * On définie une constellation avec 6 points distincts
     */
    @BeforeEach
    public void setUp() {
        workingConstellation = new Constellation(new Point[6], "test");
        for(int i = 0; i < 6; i++){
            workingConstellation.getPoints()[i] = new Point(i, i);
        }
    }

    /**
     * Test 1 : Check de la taille de la liste renvoyé par generateTriangles()
     */
    @Test
    @DisplayName("Test 1 : Check de la taille de la liste renvoyé par generateTriangles()")
    public void lengthCheckGenerateTriangles(){
        Assertions.assertEquals(20, workingConstellation.generateTriangles().length);
    }

    /**
     * Test 2 : Les triangles sont tous différents
     */
    @Test
    @DisplayName("Test 2 : Les triangles sont tous différents")
    public void differentTrianglesCheckGenerateTriangles(){
        Triangle[] triangles = workingConstellation.generateTriangles();
        for (int i = 0; i < triangles.length - 1; i++) {
            for (int j = i + 1; j < triangles.length; j++) {
                Assertions.assertFalse(triangles[i].equals(triangles[j]));
            }
        }
    }

}
