import unittest
from cartography.DetectMethod import DetectMethod

class TestDetectMethod(unittest.TestCase):
    def test_detect_method(self):
        self.assertEqual(DetectMethod.detect_method("
        def test():
            pass
        "), "def")
        self.assertEqual(DetectMethod.detect_method("
        class Test:
            pass
        "), "class")
        self.assertEqual(DetectMethod.detect_method("
        import test
        "), "import")
        self.assertEqual(DetectMethod.detect_method("
        from test import test
        "), "from")
        self.assertEqual(DetectMethod.detect_method("
        test = 1
        "), "variable")
        self.assertEqual(DetectMethod.detect_method("
        test = test
        "), "variable")
        self.assertEqual(DetectMethod.detect_method("
        test = test()
        "), "variable")
        self.assertEqual(DetectMethod.detect_method("
        test = test.test()
        "), "variable")     