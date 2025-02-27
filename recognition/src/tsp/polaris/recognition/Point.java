package tsp.polaris.recognition;

/**
 * Classe de point
 * @author Emma M., Chadi A.
 */

public class Point
{
    private double[] point;

    /**
     * Constructeur qui initialise les coordonnees du point
     * @param x 1ère coordonnee du point
     * @param y 2e coordonnee du point
     */
    public Point(double x, double y)
    {
        this.point = new double[]{x,y};
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
     * Methode qui renvoie la distance entre 2 points
     * @param p2 Point avec lequel on veut calculer la distance
     * @return double : Calcule la distance entre deux points
     */
    public double distance(Point p2)
    {
        return Math.sqrt( Math.pow(getPoint()[0] - p2.getPoint()[0], 2) + Math.pow(getPoint()[1] - p2.getPoint()[1], 2));
    }
}
