package tsp.polaris.recognition;
import java.util.Arrays;
import tsp.polaris.auxiliaries.Combinatorics;

public class Constellation
{
    private Point[] points;
    public Constellation(Point[] points)
    {
        this.points = points;
    }
    public String toString()
    {
        return Arrays.toString(points);
    }

    	public Triangle[] generateTriangles()
    {
        int size = points.length;
        int nb_triangles = Combinatorics.combination(size, 3);
        Triangle[] triangles = new Triangle[nb_triangles];
        int index = 0;
        for (int i = 0; i < size - 2; i++)
        {
            for (int j = i + 1; j < size- 1; j++)
            {
                for (int k = j + 1; k < size; k++)
                {
                    triangles[index] = new Triangle(points[i], points[j], points[k]);
                    index++;
                }
            }
        }
        return triangles;
    }
}