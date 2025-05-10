package tsp.polaris.recognition.other;

/**
 * Classe de points
 * @author Emma M., Chadi A.
 */

public class Point {
    protected final double[] point; // Coordonnees du point
    protected final double size; // Taille du point

    /**
     * Constructeur qui initialise les coordonnees du point
     * @param x 1ère coordonnee du point
     * @param y 2e coordonnee du point
     */
    public Point(double x, double y, double size)
    {
        point = new double[]{x,y};
        this.size = size;
    }
    
    /**
     * Getteur des coordonnees
     * @return double[] : Les coordonnees du points
     */
    public double[] getPoint() {
    	return point;
    }

    /**
     * Méthode qui regarde si 2 points sont égaux
     * @param p2 Point avec lequel on veut comparer
     * @return boolean : Renvoie vrai si les points sont égaux, faux sinon
     */
    public boolean equals(Point p2) {
        return this.point[0] == p2.getPoint()[0] && this.point[1] == p2.getPoint()[1];
    }

    /**
     * Methode d'affichage 
     * @return String : Affiche le point
     */
    public String toString()
    {
        return "(" + this.point[0] + ", " + this.point[1] + ")";
    }

    /**
     * Vérifie si les vecteurs formé par 3 points ne sont pas colinéaires
     */
    public boolean isCollinear(Point p2, Point p3){
        double[] vector1 = {p2.point[0] - point[0], p2.point[1] - point[1]};
        double[] vector2 = {p3.point[0] - p2.point[0], p3.point[1] - p2.point[1]};
        return vector1[0]*vector2[1] == vector1[1]*vector2[0];
    }

    /**
     * Methode qui renvoie la distance entre 2 points
     * @param p2 Point avec lequel on veut calculer la distance
     * @return double : Calcule la distance entre deux points
     */
    public double distance(Point p2)
    {
        return Math.sqrt( Math.pow(getPoint()[0] - p2.getPoint()[0], 2) + Math.pow(getPoint()[1] - p2.getPoint()[1], 2));
    }

    /**
     * Calcule le coefficient directeur et l'ordonnée à l'origine de la droite passant par ces 2 points
     *
     * @param p2 2e point
     * @return le coefficient directeur et l'ordonnée à l'origine de la droite passant par ces 2 points
     */
    public double[] coefficients(Point p2) {
        double[] p1Coo = getPoint();
        double[] p2Coo = p2.getPoint();
        double[] coeff = new double[2];
        coeff[0] = (p2Coo[1] - p1Coo[1]) / (p2Coo[0] - p1Coo[0]);
        coeff[1] = p1Coo[1] - coeff[0] * p1Coo[0];
        return coeff;
    }

    /**
     * Calcule les coordonnees d'un point à partir de 2 autres points et de la distance entre eux
     * @param a distance entre le point et le 1er point
     * @param b distance entre le point et le 2e point
     * @param c distance entre le 1er et le 2e point
     * @param secondPoint 2e point
     * @param orientation orientation du point
     * @return double[] : Coordonnees du point
     */
    public double[] getCoordinate(double a, double b, double c, Point secondPoint, double orientation){
        double temp = (b*b - c*c + a*a)/(2*a);

        Point p = new Point(point[0] + temp*(secondPoint.point[0] - point[0])/a, point[1] + temp*(secondPoint.point[1] - point[1])/a, size);

        double h = Math.sqrt(b*b - temp*temp);

        double x,y;

        // En fonction de l'orientation (à la base on a 2 solutions pour le point), on choisit les bonnes coordonnées
        if(orientation > 0) {
            x = p.point[0] + h * (point[1] - secondPoint.point[1]) / a;
            y = p.point[1] - h * (point[0] - secondPoint.point[0]) / a;
        } else {
            x = p.point[0] - h * (point[1] - secondPoint.point[1]) / a;
            y = p.point[1] + h * (point[0] - secondPoint.point[0]) / a;
        }

        return new double[]{x, y};
    }

}
