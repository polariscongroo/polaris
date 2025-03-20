package tsp.polaris.JUnitTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tsp.polaris.recognition.exceptions.TriangleMatchingException;
import tsp.polaris.recognition.other.ListTriangle;
import tsp.polaris.recognition.other.Star;
import tsp.polaris.recognition.starSet.Constellation;
import tsp.polaris.recognition.starSet.DetectedStarSet;

/**
 * Tests unitaires de la classe ListTriangle
 *
 * @author Emma M.
 */

public class ListTriangleTest {
    private DetectedStarSet workingDetectedStarSet;
    private ListTriangle workingDetectedStarSetListTriangle;
    private Constellation workingConstellation;
    private ListTriangle workingConstellationListTriangle;

    /**
     * Partie exécuté avant chaque test :
     * On définie un set d'étoiles avec 6 points distincts
     * Et on en extrait les triangles
     */
    @BeforeEach
    public void setUp() {
        Star[] starsDetected = new Star[6];
        Star[] starsConstellation = new Star[6];
        for(int i = 0; i < 6; i++){
            // Il faut déclarer des étoiles qui ne forment pas des vecteurs colinéaires (sinon c'est pas un triangle)
            starsDetected[i] = new Star(i, (double)5/(i+1), i);
            starsConstellation[i] = new Star(i, (double)5/(i+1), i);
        }
        workingDetectedStarSet = new DetectedStarSet(starsDetected);
        workingConstellation = new Constellation(starsConstellation, "TestConstellation");
        workingDetectedStarSetListTriangle = new ListTriangle(workingDetectedStarSet.generateTriangles());
        workingConstellationListTriangle = new ListTriangle(workingConstellation.generateTriangles());
    }

    /**
     * Test 1 : Le cout entre un set d'étoiles et elle-même est toujours 0
     */
    @Test
    @DisplayName("Test 1 : Le cout entre un set d'étoiles et elle meme est toujours 0")
    public void costBetweenStarSetAndItselfIsZero() throws TriangleMatchingException {
        for (int i = 0; i < workingDetectedStarSetListTriangle.getListTriangle().length; i++) {
            System.out.println(workingDetectedStarSetListTriangle.costs(workingConstellationListTriangle, workingDetectedStarSet, workingConstellation)[i]);
            System.out.println("-----------");
            Assertions.assertEquals(0, workingDetectedStarSetListTriangle.costs(workingConstellationListTriangle, workingDetectedStarSet, workingConstellation)[i]);
        }
    }

}
