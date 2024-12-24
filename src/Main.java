import java.util.Arrays;

public class Main
{
        public static void main(String[] args) throws TriangleMatchingException {
            /* Hypothèses :
            - Les triangles formés par les constellations sont tous différents sinon distance nulle et on divise par 0.
            i.e la donnée des coordonnées est assez précise pour qu'il n'y est pas de confusion dans le calcul du coût
            - La photo et le traitement de l'image ne traitent que les étoiles de la constellation et pas d'autres.
             */

            Point A = new Point(0, 0);
            Point B = new Point(1, 0);
            Point C = new Point(0, 1);
            Point D = new Point(1, 1);
            Point E = new Point(1, 1);
            Point F = new Point(0, 1);
            Point G = new Point(1, 0);
            Point H = new Point(0, 0);

            //Test affichage de tous les triangles possibles avec le set de point set
            Point[] set1 = {A,B,C,D};
            List_point ABCD = new List_point(set1);
            Triangle[] triangles1 = ABCD.generateTriangles();
            // Utilisation de Arrays.toString pour afficher les triangles
            System.out.println("Triangles formés par les points ABCD : " + Arrays.toString(triangles1));
            Point[] set2 = {E,F,G,H};
            List_point EFGH = new List_point(set2);
            Triangle[] triangles2 = EFGH.generateTriangles();
            // Utilisation de Arrays.toString pour afficher les triangles
            System.out.println("Triangles formés par les points EFGH : " + Arrays.toString(triangles2));

            /*/Test fonction de cout sur deux triangles
            Triangle trig = new Triangle(A, B, C);
            Triangle trig2 = new Triangle(A, B, C);
            double cost = Triangle.cout(trig, trig2);
            System.out.println("Coût de similarité : " + cost);/*/

            //Test fonction de couts de tous les triangles et minimisation
            double[] liste_cout = List_triangle.couts(triangles1, triangles2);
            int[] liste_indice = List_triangle.indice(liste_cout);
            System.out.println("Liste des couts : " + Arrays.toString(liste_cout));
            System.out.println("Indice des triangles similaires : " + Arrays.toString(liste_indice));
            //System.out.println("couples de triangles semblables : " + );
            //System.out.println("cout total de la constellation : " + );


        }
}