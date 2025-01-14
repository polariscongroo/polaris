package tsp.polaris.recognition;
import tsp.polaris.auxiliaries.Combinatorics;
import java.lang.module.FindException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tsp.polaris.auxiliaries.Combinatorics;

public class List_point
{
    private Point[] points;
    
    public List_point(Point[] points)
    {
        this.points = points;
    }
    
    // Génère tous les triangles possibles à partir d'un ensemble de points
    public Triangle[] generateTriangles()
    {
        int size = points.length;
        int nb_triangles = 0;
        try {
        	nb_triangles = Combinatorics.combination(size, 3);
        } catch(Exception e) {
        	System.out.println(e.getMessage());
        }
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
