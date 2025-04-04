import unittest
from ThresholdDetectMethod import *
import ThresholdDetectMethod as TDM
import numpy as np
import os
import csv
           
class test_Cree_une_forme(unittest.TestCase):
    # S'exécute avant chaque test
    def setUp(self):
        pass

    # S'exécute après chaque test
    def tearDown(self):
        pass
    
    class test_Inverse_cor(unittest.TestCase):
        def test_inverse_matrix(self):
            self.assertEqual(inverse_cor([[0,2],[1,2]]), [[1,2],[0,2]] )   

    class test_erase_txt(unittest.TestCase):
        def test_erase_txt(self):
            # Call the function without any arguments
            erase_txt()
        
            # Define the path to the file that should be erased
            file_path = "cartography/image_aTraiter/output.txt"
        
            # Check if the file does not exist after calling the function
            self.assertFalse(os.path.exists(file_path), "File was not erased")
 
    class test_Cree_une_forme(unittest.TestCase):
        def test_cree_une_forme_single_point(self):
            threshold_mask = np.array([[1, 0], [0, 0]])
            explored = np.zeros_like(threshold_mask, dtype=bool)
            self.assertEqual(cree_une_forme((0, 0), threshold_mask, explored), [(0, 0)])

        def test_cree_une_forme_multiple_points(self):
            threshold_mask = np.array([[1, 1], [1, 0]])
            explored = np.zeros_like(threshold_mask, dtype=bool)
            self.assertEqual(cree_une_forme((1, 1), threshold_mask, explored), [(0, 0), (0, 1), (1, 0)])

        def test_cree_une_forme_disconnected_points(self):
            threshold_mask = np.array([[1, 0], [0, 1]])
            explored = np.zeros_like(threshold_mask, dtype=bool)
            self.assertEqual(cree_une_forme((1, 1), threshold_mask, explored), [(1, 1)])

    class test_Determine_points_adjacents(unittest.TestCase):
        def test_determine_points_adjacents(self):
            self.assertEqual(determine_points_adjacents((0,0),2,2), [(1,0),(0,1),(1,1)])

    class test_determine_formes(unittest.TestCase):
        def test_determine_formes(self):
            threshold_mask = np.array([[0, 1], [1, 1]])
            self.assertEqual(determine_formes(threshold_mask), [[(0, 1)], [(1, 0), (1, 1)]])

    class test_enregistre_les_etoiles(unittest.TestCase):
        def test_enregistre_les_etoiles_creates_non_empty_csv(self):
        # Call the function that generates the CSV
            enregistre_les_etoiles([[0,1],[1,1]],[[0,1],[1,1]])

        # Define the path to the generated CSV file
            csv_file_path = "recognition/coorPoints/liste_etoiles.csv"

        # Check if the file exists
            self.assertTrue(os.path.exists(csv_file_path), "CSV file does not exist")

        # Check if the file is not empty
            self.assertGreater(os.path.getsize(csv_file_path), 0, "CSV file is empty")

        # Read the CSV file and check its contents
            with open(csv_file_path, newline='') as csvfile:
                reader = csv.reader(csvfile)
                csv_content = list(reader)

        # Define the expected coordinates
            expected_coordinates = [["0", "1"], ["1", "1"]]

        # Check if the contents of the CSV file match the expected coordinates
            self.assertEqual(csv_content, expected_coordinates, "CSV file contents are incorrect")

class test_exceptions(unittest.TestCase):
    # S'exécute avant chaque test
    def setUp(self):
        pass

    # S'exécute après chaque test
    def tearDown(self):
        pass

    def test_FileNotFound_outputtxt(self):
        file_path="cartography/image_aTraiter/output.txt"
        os.remove(file_path)
        with self.assertRaises(OutputNotFound) as context:
            TDM.main()
        # Vérification du message de l'exception
        self.assertEqual(str(context.exception), "Le fichier output.txt n'existe pas encore...")

    def test_EmptyFile_outputtxt(self):
        file_path="cartography/image_aTraiter/output.txt"
        with open(file_path, "w") as f:
            f.write("")
        with self.assertRaises(EmptyFile) as context:
            TDM.main()
        # Vérification du message de l'exception
        self.assertEqual(str(context.exception), "Le fichier output.txt est vide")
        
    def test_FileNotFound_image(self):
        image_path="random_txt.png"
        with open(file_path, "w") as f:
            f.write(image_path)
        with self.assertRaises(ImageNotFound) as context:
            TDM.main()
        # Vérification du message de l'exception
        self.assertEqual(str(context.exception), f"l'image {image_path} n'existe pas.")


if __name__ == '__main__':
    unittest.main()