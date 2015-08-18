package org.bimserver.cobie.graphics2d;

import java.math.BigDecimal;

import javax.vecmath.Point2d;

@Deprecated
public class PolarCoordinate implements Comparable<PolarCoordinate>
{
    public double r;
    public double theta;
    public Point2d origin;
 
    public PolarCoordinate(double r, double theta)
    {
        this.r = r;
        this.theta = theta;
    }
    
    @Override
    public int compareTo(PolarCoordinate o)
    {
        BigDecimal theta = new BigDecimal(this.theta);
        BigDecimal compareTheta = new BigDecimal(o.theta);
                
        return theta.compareTo(compareTheta);
    }
}
