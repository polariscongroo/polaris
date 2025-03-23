package tsp.polaris.JUnitTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tsp.polaris.recognition.dataTransmission.Data;
import tsp.polaris.recognition.other.Star;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour la classe Data
 *
 * @author Chadi A., Emma M.
 */
public class DataTest {
    private static final String TEST_CSV = "test_data.csv";

    /**
     * Création d'un fichier CSV de test avant chaque test
     */
    @BeforeEach
    void setUp() throws IOException {
        FileWriter writer = new FileWriter(TEST_CSV);
        writer.write("1.0,2.0,3.0\n4.0,5.0,6.0\n7.0,8.0,9.0\n");
        writer.close();
    }

    /**
     * Suppression du fichier CSV de test après chaque test
     */
    @AfterEach
    void tearDown() {
        File file = new File(TEST_CSV);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Test 1 : Vérification du chargement des données CSV via le constructeur et de la méthode getData
     */
    @Test
    @DisplayName("Test 1 : Vérification du chargement des données CSV")
    void testDataLoading() {
        Data data = new Data(TEST_CSV,"test_data");
        List<Star> stars = data.getData();
        assertEquals(3, stars.size(), "Le nombre de lignes lues ne correspond pas");
        assertTrue(new Star(1.0, 2.0, 3.0).equals(stars.get(0)), "Premier point incorrect");
        assertTrue(new Star(4.0, 5.0, 6.0).equals(stars.get(1)), "Deuxième point incorrect");
        assertTrue(new Star(7.0, 8.0, 9.0).equals(stars.get(2)), "Troisième point incorrect");
    }

    /**
     * Test 2 : Vérification de l'effacement du fichier CSV via la méthode eraseCsv
     */
    @Test
    @DisplayName("Test 2 : Vérification de l'effacement du fichier CSV")
    void testEraseCsv() throws IOException {
        Data data = new Data(TEST_CSV, "test_data");
        data.eraseCsv(TEST_CSV);
        assertTrue(Files.readAllLines(Paths.get(TEST_CSV)).isEmpty(), "Le fichier n'a pas été vidé");
    }
}
