import unittest
from ThresholdDetectMethod import cree_une_forme, determine_formes, determine_points_adjacents, enregistre_les_etoiles, erase_txt, inverse_cor
import numpy as np

class test_Inverse_cor(unittest.TestCase): ##verif ok
    def test_inverse_matrix(self):
            self.assertEqual(inverse_cor([[0,2],[1,2]]), [[1,2],[0,2]] )   

class test_erase_txt(unittest.TestCase): ##verif ok
    def test_erase_txt(self):
        self.assertEqual(erase_txt("cartography/image_aTraiter/output.txt"), None )
        
            
class test_Cree_une_forme(unittest.TestCase):
    class test_Inverse_cor(unittest.TestCase):
        def test_inverse_matrix(self):
            self.assertEqual(inverse_cor([[0,2],[1,2]]), [[1,2],[0,2]])

    class test_erase_txt(unittest.TestCase):
        def test_erase_txt(self):
            self.assertEqual(erase_txt("cartography/image_aTraiter/output.txt"), None)

    class test_Cree_une_forme(unittest.TestCase):
        def test_cree_une_forme_single_point(self):
            threshold_mask = np.array([[1, 0], [0, 0]])
            explored = np.zeros_like(threshold_mask, dtype=bool)
            self.assertEqual(cree_une_forme((0, 0), threshold_mask, explored), [(0, 0)])

        def test_cree_une_forme_multiple_points(self):
            threshold_mask = np.array([[1, 1], [1, 0]])
            explored = np.zeros_like(threshold_mask, dtype=bool)
            self.assertEqual(cree_une_forme((0, 0), threshold_mask, explored), [(0, 0), (0, 1), (1, 0)])

        def test_cree_une_forme_disconnected_points(self):
            threshold_mask = np.array([[1, 0], [0, 1]])
            explored = np.zeros_like(threshold_mask, dtype=bool)
            self.assertEqual(cree_une_forme((0, 0), threshold_mask, explored), [(0, 0)])

    class test_Determine_points_adjacents(unittest.TestCase):
        def test_determine_points_adjacents(self):
            self.assertEqual(determine_points_adjacents((0,0),2,2), [(1,0),(0,1),(1,1)])

    class test_determine_formes(unittest.TestCase):
        def test_determine_formes(self):
            threshold_mask = np.array([[0, 1], [1, 1]])
            self.assertEqual(determine_formes(threshold_mask), [[(0, 1)], [(1, 0), (1, 1)]])

    class test_enregistre_les_etoiles(unittest.TestCase):
        def test_enregistre_les_etoiles(self):
            self.assertEqual(enregistre_les_etoiles([[0,1],[1,1]],[[0,1],[1,1]]), [[0,1],[1,1]])

    if __name__ == '__main__':
        unittest.main()