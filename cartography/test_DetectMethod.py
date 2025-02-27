import unittest
from ThresholdDetectMethod import cree_une_forme, determine_formes, enregistre_les_etoiles, erase_txt, inverse_cor

class Test_Inverse_cor(unittest.TestCase):
    def test_inverse_matrix(self):
            self.assertEqual(inverse_cor([[0,2],[1,2]]), [[1,2],[0,2]] )   
            
class Test_Cree_une_forme(unittest.TestCase):
    def test_cree_une_forme(self):
        self.assertEqual(cree_une_forme((0,0),[[0,1],[1,1]],[[False,False],[False,False]]), [(0,0)] )


class Test_determine_formes(unittest.TestCase):
    def test_determine_formes(self):
        self.assertEqual(determine_formes([[0,1],[1,1]]), [[(0,1)],[(1,1)]] )
        
        
class Test_enregistre_les_etoiles(unittest.TestCase):
    def test_enregistre_les_etoiles(self):
        self.assertEqual(enregistre_les_etoiles([[0,1],[1,1]],[[0,1],[1,1]]), [[0,1],[1,1]] )   
        
class Test_erase_txt(unittest.TestCase):
    def test_erase_txt(self):
        self.assertEqual(erase_txt("cartography/image_aTraiter/output.txt"), None )