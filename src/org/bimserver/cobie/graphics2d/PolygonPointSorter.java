package org.bimserver.cobie.graphics2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Point2d;

@Deprecated
public class PolygonPointSorter
{    
    private class OrderedPolarCoordinate implements Comparable<OrderedPolarCoordinate>
    {
        public final int ordinal;
        public final PolarCoordinate polar;
        
        public OrderedPolarCoordinate(int ordinal, PolarCoordinate polar)
        {
            this.ordinal = ordinal;
            this.polar = polar;
        }

        @Override
        public int compareTo(OrderedPolarCoordinate o)
        {
            return this.polar.compareTo(o.polar);
        }       
    }
    
    private List<Point2d> points;
    private Map<Integer, Point2d> orderedCartesianCoordinates = new HashMap<Integer, Point2d>();
    List<OrderedPolarCoordinate> orderedPolarCoordinates = new ArrayList<OrderedPolarCoordinate>();   
    
    private final double horizontalMean;
    private final double verticalMean;
    
    public PolygonPointSorter(List<Point2d> points)
    {
        this.points = points;
        this.orderedCartesianCoordinates = orderCartesianCoordinates();
        this.horizontalMean = calculateHorizontalMean();
        this.verticalMean = calculateVerticalMean();
        this.orderedPolarCoordinates = orderPolarCoordinates();
        
        reorder();
    }
    
    private double calculateHorizontalMean()
    {
        List<Double> xValues = new ArrayList<Double>(points.size());
        
        for (Point2d point : points)
        {            
            xValues.add(point.x);
        }
        
        return org.bimserver.cobie.graphics.Math.getMean(xValues);
    }
    
    private double calculateVerticalMean()
    {
        List<Double> yValues = new ArrayList<Double>(points.size());
        
        for (Point2d point : points)
        {            
            yValues.add(point.y);
        }
        
        return org.bimserver.cobie.graphics.Math.getMean(yValues);
    }
    
    private double getHorizontalMean()
    {
        return horizontalMean;
    }
    
    public final List<Point2d> getSortedPoints()
    {
        return points;
    }
    
    private double getVerticalMean()
    {
        return verticalMean;
    }
    
    private Map<Integer, Point2d> orderCartesianCoordinates()
    {        
        Map<Integer, Point2d> orderedCartesianCoordinates = new HashMap<Integer, Point2d>();
        
        for (int i = 0; i < points.size(); i++)
        {
            orderedCartesianCoordinates.put(i, points.get(i));
        }  
        
        return orderedCartesianCoordinates;
    }
    
    private List<OrderedPolarCoordinate> orderPolarCoordinates()
    {
        List<OrderedPolarCoordinate> orderedPolarCoordinates = new ArrayList<OrderedPolarCoordinate>(); 
        
        for (int i = 0; i < points.size(); i++)
        {            
            // Make sure not to alias this point or it will mess up orderedCartesianCoordinates.
            Point2d point = new Point2d(points.get(i));
            point.x -= getHorizontalMean();
            point.y -= getVerticalMean();
            
            PolarCoordinate polarCoordinate = Math.convertToPolar(point);
            OrderedPolarCoordinate orderedPolarCoordinate = new OrderedPolarCoordinate(i, polarCoordinate);
            
            orderedPolarCoordinates.add(orderedPolarCoordinate);
        }
        
        Collections.sort(orderedPolarCoordinates);  
        
        return orderedPolarCoordinates;
    }
    
    private void reorder()
    {             
        points.clear();
        
        for (OrderedPolarCoordinate orderedPolarCoordinate : orderedPolarCoordinates)
        {
            points.add(orderedCartesianCoordinates.get(orderedPolarCoordinate.ordinal));
        }
    }
}
