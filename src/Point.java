import java.util.ArrayList;
import java.util.List;

public class Point
{
    double[] point;

    Point(double x, double y)
    {
        this.point = new double[]{x,y};
    }

    public String toString()
    {
        return "(" + this.point[0] + ", " + this.point[1] + ")";
    }
}
