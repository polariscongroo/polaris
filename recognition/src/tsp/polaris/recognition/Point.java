package tsp.polaris.recognition;

/**
 * @author Emma M., Chadi A.
 */

public class Point
{
    private double[] point;

    /**
     * 
     * @param x 1ère coordonnée du point
     * @param y 2e coordonnée du point
     */
    public Point(double x, double y)
    {
        this.point = new double[]{x,y};
    }
    
    /**
     * 
     * @return double[] : Les coordonnées du points
     */
    public double[] getPoint() {
    	return point;
    }
    
    /**
     * @return String : Affiche le point
     */
    public String toString()
    {
        return "(" + this.point[0] + ", " + this.point[1] + ")";
    }
    
    /**
     * 
     * @param p2 Point avec lequel on veut calculer la distance
     * @return double : Calcule la distance entre deux points
     */
    public double distance(Point p2)
    {
        return Math.sqrt( Math.pow(getPoint()[0] - p2.getPoint()[0], 2) + Math.pow(getPoint()[1] - p2.getPoint()[1], 2));
    }
}
