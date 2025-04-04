package tsp.polaris.recognition.other;

/**
 * Classe de points
 * @author Emma M., Chadi A.
 */

public class Point
{
    protected final double[] point;

    /**
     * Constructeur qui initialise les coordonnees du point
     * @param x 1ère coordonnee du point
     * @param y 2e coordonnee du point
     */
    public Point(double x, double y)
    {
        point = new double[]{x,y};
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


}
