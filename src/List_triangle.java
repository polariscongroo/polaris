public class List_triangle
{
    Triangle[] triangles;

    static double[] couts(Triangle[] triangles1, Triangle[] triangles2)
    {
        int taille1 = triangles1.length;
        int taille2 = triangles2.length;
        if (taille1 != taille2)
        {
            System.out.println("Ce n'est pas la bonne constellation, il n'y a pas les même nombre de triangles");
            double[] error = {9999,9999};
            return  error;
        }
        double[] tab = new double[taille1*taille2];
        int index = 0;
        for (int triangle1 = 0; triangle1 < taille1; triangle1++)
        {
            for (int triangle2 = 0; triangle2 < taille1; triangle2++)
            {
                tab[index] = Triangle.cout(triangles1[triangle1],triangles2[triangle2]);
                index ++;
            }
        }
        return tab;
    }
}
