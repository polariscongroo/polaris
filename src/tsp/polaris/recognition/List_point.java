package tsp.polaris.recognition;
import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class List_point
{
    Point[] points;
    List_point(Point[] points)
    {
        this.points = points;
    }
    // Génère tous les triangles possibles à partir d'un ensemble de points
    Triangle[] generateTriangles()
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

    public String toString()
    {
        Triangle[] triangles = generateTriangles();
        return Arrays.toString(triangles);
    }
}
