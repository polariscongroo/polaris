public class Constellation
{
    static double couts(double[] cout_triangle)
    {
        double cout_constellation = 0;
        for (int cout = 0; cout < cout_triangle.length; cout++)
        {
            cout_constellation += cout;
        }
        return cout_constellation;
    }
}
