package tsp.polaris.recognition;

import java.io.IOException;
import java.util.List;

/**
 * Classe principale qui va lancer notre programme
 *
 * @author Emma M., Chadi A., Ryane S.
 */

public class Main
{
    public static void main(String[] args) throws TriangleMatchingException, NumberFormatException, IOException {

        Data data = new Data("coorPoints/liste_etoiles.csv");

        System.out.println(data);

        /* Hypothèses :
        - Les triangles formes par les List_points sont tous differents sinon distance nulle et on divise par 0.
        i.e la donnee des coordonnees est assez precise pour qu'il n'y est pas de confusion dans le calcul du coût
        - La photo et le traitement de l'image ne traitent que les etoiles de la List_point et pas d'autres.

        Point A = new Point(0, 0);
        Point B = new Point(1, 0);
        Point C = new Point(0, 1);
        Point D = new Point(1, 1);
        Point E = new Point(1, 1);
        Point F = new Point(0, 1);
        Point G = new Point(1, 0);
        Point H = new Point(0, 0);
        Point I = new Point(1, 1);
        Point J = new Point(0, 1);
        Point K = new Point(1, 0);
        Point L = new Point(10, 0);
        */

        //Test affichage de tous les triangles possibles avec le set de point set
        /* Point[] set1 = {A,B,C,D};
        List_point ABCD = new List_point(set1);
        Triangle[] triangles1 = ABCD.generateTriangles();
        List_triangle list1 = new List_triangle(triangles1);
        // Utilisation de Arrays.toString pour afficher les triangles
        System.out.println("Triangles formes par les points ABCD : " + Arrays.toString(triangles1));
        Point[] set2 = {E,F,G,H};
        Constellation EFGH = new Constellation(set2);
        Triangle[] triangles2 = EFGH.generateTriangles();
        List_triangle list2 = new List_triangle(triangles2);
        // Utilisation de Arrays.toString pour afficher les triangles
        System.out.println("Triangles formes par les points EFGH : " + Arrays.toString(triangles2));
        //Test affichage de tous les triangles possibles avec le set de point set
        Point[] set3 = {I,J,K,L};
        Constellation IJKL = new Constellation(set3);
        Triangle[] triangles3 = IJKL.generateTriangles();
        List_triangle list3 = new List_triangle(triangles3);
        // Utilisation de Arrays.toString pour afficher les triangles
        System.out.println("Triangles formes par les points IJKL : " + Arrays.toString(triangles3));

        System.out.println("");


        /*Test fonction de cout sur deux triangles
        Triangle trig = new Triangle(A, B, C);
        Triangle trig2 = new Triangle(A, B, C);
        double cost = Triangle.cout(trig, trig2);
        System.out.println("Coût de similarite : " + cost);/*/

        /*/Test fonction de couts de tous les triangles et identifications des triangles similaires et calcul du cout total de la List_point
        double[] liste_cout = List_triangle.couts(triangles1, triangles2);
        int[] liste_indice = List_triangle.indice(liste_cout);
        System.out.println("Liste des couts : " + Arrays.toString(liste_cout));
        System.out.println("Indice des triangles similaires : " + Arrays.toString(liste_indice));
        System.out.println("cout total de la List_point : " + List_point.couts(liste_cout)); /*/

        /*Test fonction de couts de tous les triangles et identifications des triangles similaires et calcul du cout total de la List_point
        double[] liste_cout = list1.couts(list2);
        int[] liste_indice = list1.indice(list2);
        System.out.println("Liste des couts : " + Arrays.toString(liste_cout));
        System.out.println("Indice des triangles similaires : " + Arrays.toString(liste_indice));
        System.out.println("cout total de la List_point : " + List_point.couts(liste_cout)); //

        System.out.println("");

        double[] liste_cout2 = list1.couts(list3);
        int[] liste_indice2 = list1.indice(list3);
        System.out.println("Liste des couts : " + Arrays.toString(liste_cout2));
        System.out.println("Indice des triangles similaires : " + Arrays.toString(liste_indice2));
        System.out.println("cout total de la List_point : " + List_point.couts(liste_cout2)); //

        System.out.println("");

        //Test de la comparaison de plusieurs List_points (points) à une reference (la photo)
        Constellation winner = ABCD.selectConstellation(EFGH, IJKL);
        System.out.println("La List_point la plus proche est : " + winner); */



        /*
        String path = "/Users/chadiaitekioui/Coding/Polaris/polaris/liste_etoiles.json";
        List_point list_of_stars = new List_point();
        Triangle[] triangles_formed = list_of_stars.generateTriangles();
        List_triangle triangles = new List_triangle(triangles_formed);
        // Utilisation de Arrays.toString pour afficher les triangles
        System.out.println("Triangles formes par les points de list_of_stars : " + Arrays.toString(triangles));
        double cout_total = ;
        */

    }
}