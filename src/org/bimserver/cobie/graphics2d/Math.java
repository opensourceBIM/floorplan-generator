package org.bimserver.cobie.graphics2d;

import java.util.List;

import javax.vecmath.Point2d;

public class Math
{
    // public static Point2d convertToCartesian(PolarCoordinate polarCoordinate)
    // {
    // double x = polarCoordinate.r * Math.cos(polarCoordinate.theta);
    // double y = polarCoordinate.r * Math.sin(polarCoordinate.theta);
    //
    // return new Point2d(x, y);
    // }

    @Deprecated
    public static PolarCoordinate convertToPolar(Point2d point)
    {
        double r = java.lang.Math.hypot(point.x, point.y);
        double theta = java.lang.Math.atan2(point.y, point.x);

        return new PolarCoordinate(r, theta);
    }

    public static Point2d getMaximums(Polygon polygon)
    {
        List<Point2d> points = polygon.getPoints();

        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for (Point2d point : points)
        {
            maxX = java.lang.Math.max(maxX, point.x);
            maxY = java.lang.Math.max(maxY, point.y);
        }

        return new Point2d(maxX, maxY);
    }

    public static Point2d getMinimums(Polygon polygon)
    {
        List<Point2d> points = polygon.getPoints();

        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;

        for (Point2d point : points)
        {
            minX = java.lang.Math.min(minX, point.x);
            minY = java.lang.Math.min(minY, point.y);
        }

        return new Point2d(minX, minY);
    }
    
    public static Quadrant getQuadrant(Point2d point)
    {
        Quadrant quadrant = Quadrant.NONE;

        if (Double.isNaN(point.x) || Double.isNaN(point.y))
        {
            quadrant = Quadrant.NONE;
        }

        else 
        {
            if (point.x >= 0.0)
            {
                if (point.y >= 0.0)
                {
                    quadrant = Quadrant.I;
                }
    
                else
                {
                    quadrant = Quadrant.IV;
                }
            }
    
            else
            {
                if (point.y >= 0.0)
                {
                    quadrant = Quadrant.II;
                }
    
                else
                {
                    quadrant = Quadrant.III;
                }
            }
        }

        return quadrant;
    }
}
