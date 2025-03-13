package tsp.polaris.JUnitTest;

import org.junit.jupiter.api.Test;
import tsp.polaris.auxiliaries.Functions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FunctionsTest {
        
        @Test
        void testMinIndexGeneralCase() {
            double[] tab = {5.0, 3.2, 7.8, 1.5, 9.3};
            assertEquals(3, Functions.minIndex(tab));
        }

        @Test
        void testMinIndexFirstElement() {
            double[] tab = {0.1, 2.2, 3.3, 4.4};
            assertEquals(0, Functions.minIndex(tab));
        }

        @Test
        void testMinIndexLastElement() {
            double[] tab = {5.5, 6.6, 7.7, -2.2};
            assertEquals(3, Functions.minIndex(tab));
        }

        @Test
        void testMinIndexAllEqual() {
            double[] tab = {4.4, 4.4, 4.4, 4.4};
            assertEquals(0, Functions.minIndex(tab));
        }

        @Test
        void testMinIndexSingleElement() {
            double[] tab = {42.0};
            assertEquals(0, Functions.minIndex(tab));
        }

        @Test
        void testMinIndexEmptyArray() {
            double[] tab = {};
            assertThrows(IllegalArgumentException.class, () -> Functions.minIndex(tab));
        }
        
        @Test
        void testSumGeneralCase() {
            double[] tab = {1.1, 2.2, 3.3, 4.4};
            assertEquals(11.0, Functions.sum(tab), 1e-9);
        }

        @Test
        void testSumEmptyArray() {
            double[] tab = {};
            assertEquals(0.0, Functions.sum(tab), 1e-9);
        }

        @Test
        void testSumSingleElement() {
            double[] tab = {7.7};
            assertEquals(7.7, Functions.sum(tab), 1e-9);
        }

        @Test
        void testSumNegativeValues() {
            double[] tab = {-1.1, -2.2, -3.3};
            assertEquals(-6.6, Functions.sum(tab), 1e-9);
        }

        @Test
        void testSumMixedPositiveNegative() {
            double[] tab = {-1.1, 2.2, -3.3, 4.4};
            assertEquals(2.2, Functions.sum(tab), 1e-9);
        }

}
