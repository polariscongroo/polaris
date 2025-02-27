import unittest
from ThresholdDetectMethod import cree_une_forme, inverse_cor

class Test_Inverse_cor(unittest.TestCase):
    def test_inverse_matrix(self):
            self.assertEqual(inverse_cor([[0,2],[1,2]]), [[1,2],[0,2]] )   
            
class Test_Cree_une_forme(unittest.TestCase):
    def test_cree_une_forme(self):
        self.assertEqual(cree_une_forme((0,0),[[0,1],[1,1]],[[False,False],[False,False]]), [(0,0)] )
