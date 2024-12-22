import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main
{
        public static void main(String[] args)
        {
            Point A = new Point(0, 0);
            Point B = new Point(1, 0);
            Point C = new Point(0, 1);
            Point D = new Point(2, 2);

            //Point E = new Point(0, 0);
            //Point F = new Point(2, 0);
            //Point G = new Point(0, 2);
            //Point H = new Point(4, 4);

            Point[] set1 = {A,B,C,D};

            List_point ABCD = new List_point(set1);

            Triangle[] triangles = ABCD.generateTriangles();
            // Utilisation de Arrays.toString pour afficher les triangles
            System.out.println(Arrays.toString(triangles));
        }
}