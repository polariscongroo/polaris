package tsp.polaris.recognition;
public class Point
{
    private double[] point;

    public Point(double x, double y)
    {
        this.point = new double[]{x,y};
    }
    
    public double[] getPoint() {
    	return point;
    }

    public String toString()
    {
        return "(" + this.point[0] + ", " + this.point[1] + ")";
    }
    
    // Calcule la distance entre deux points
    public double distance(Point p2)
    {
        return Math.sqrt( Math.pow(getPoint()[0] - p2.getPoint()[0], 2) + Math.pow(getPoint()[1] - p2.getPoint()[1], 2));
    }
}
