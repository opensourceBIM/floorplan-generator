package org.erdc.cobie.graphics;

import java.util.List;

public class Math
{
    public static final double DEFAULT_TOLERANCE = 0.00001;
    
    public static boolean approxEqual(double value1, double value2)
    {
        return approxEqual(value1, value2, DEFAULT_TOLERANCE);
    }
    
    public static boolean approxEqual(double value1, double value2, double tolerance)
    {
        return java.lang.Math.abs(value1 - value2) < tolerance;
    }
    
    public static double getMean(List<Double> values)
    {
        double primitiveValues[] = new double[values.size()];
        
        for (int i = 0; i < values.size(); i++)
        {
            primitiveValues[i] = values.get(i);
        }
        
        return Math.getMean(primitiveValues);
    }
    
    public static double getMean(double values[])
    {
        double sum = 0;
        
        for (double value : values)
        {
            sum += value;
        }
        
        return sum / values.length;
    }

    @Deprecated
    public static double scaleToDefaultRange(double value, double min, double max)
    {
        return Math.scaleToRange(value, min, max, 0, 1);
    }

    @Deprecated
    public static double scaleToRange(double value, double min, double max, double rangeMin, double rangeMax)
    {
        return (((value - min) * (rangeMax - rangeMin)) / (max - min)) + rangeMin;
    }
}
