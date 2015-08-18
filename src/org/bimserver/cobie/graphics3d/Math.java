package org.bimserver.cobie.graphics3d;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.GeometryArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import org.bimserver.cobie.shared.utility.CollectionUtils;

import com.sun.j3d.utils.geometry.GeometryInfo;

public class Math
{
    public static final int NUM_TRIANGLE_VERTEXES = 3;
    public static final int NUM_VERTEX_COMPONENTS = 3;

    public static Color3f attenuateColor(Color3f color, float attenuation)
    {
        return new Color3f(
                java.lang.Math.max(0, color.x - attenuation), 
                java.lang.Math.max(0, color.y - attenuation), 
                java.lang.Math.max(0, color.z - attenuation));
    }

    public static Point3d getCenter(GeometryInfo mesh, Orientation orientation)
    {
        Point3f[] vertices = mesh.getCoordinates();
        List<Double> xCoordinates = new ArrayList<Double>(vertices.length);
        List<Double> yCoordinates = new ArrayList<Double>(vertices.length);
        List<Double> zCoordinates = new ArrayList<Double>(vertices.length);

        for (Point3f vertex : vertices)
        {
            double x = Math.getHorizontalComponent(new Point3d(vertex), orientation);
            double y = Math.getVerticalComponent(new Point3d(vertex), orientation);
            double z = Math.getDepthComponent(new Point3d(vertex), orientation);

            xCoordinates.add(x);
            yCoordinates.add(y);
            zCoordinates.add(z);
        }

        double xMean = org.bimserver.cobie.graphics.Math.getMean(xCoordinates);
        double yMean = org.bimserver.cobie.graphics.Math.getMean(yCoordinates);
        double zMean = org.bimserver.cobie.graphics.Math.getMean(zCoordinates);

        return new Point3d(xMean, yMean, zMean);
    }
    
    /**
     * Returns the component of the supplied 3D-point that represents "depth" in
     * relation to the scene's orientation.
     * 
     * Be aware that this method is arbitrary in preferring the z-axis as
     * depth, but any other axis can be defined as such.
     * 
     * @param point
     *            The source of the horizontal component.
     * @return The horizontal component "point" as defined above.
     */
    // TODO These might need more work; no consideration was giving to the
    // direction of the axes.
    public static double getDepthComponent(Point3d point, Orientation orientation)
    {
        double component = 0.0;

        switch (orientation)
        {
            case X_UP:  component = point.z; break;
            case Y_UP:  component = point.z; break;
            case Z_UP:  component = point.y; break;
            default:    throw new IllegalArgumentException("The supplied orientation is not supported.");
        }

        return component;
    }

    /**
     * Returns the component of the supplied 3D-point that is horizontal in
     * relation to the scene's orientation.
     * 
     * Be aware that this method is arbitrary in preferring the x-axis as
     * horizontal, but any other axis can be defined as such.
     * 
     * Also, be aware that using an axis to represent vertical affects the
     * direction of other axes.
     * 
     * e.g. If the x-axis is defined as "up", the y-axis becomes horizontal, but
     * is negative.
     * 
     * If necessary, these axis negations can be "fixed" via rotation(s).
     * 
     * @param point
     *            The source of the horizontal component.
     * @return The horizontal component "point" as defined above.
     */
    // TODO These might need more work; no consideration was giving to the
    // direction of the axes.
    public static double getHorizontalComponent(Point3d point, Orientation orientation)
    {
        double component = 0.0;

        switch (orientation)
        {
            case X_UP:  component = point.y; break;
            case Y_UP:  component = point.x; break;
            case Z_UP:  component = point.x; break;
            default:    throw new IllegalArgumentException("The supplied orientation is not supported.");
        }

        return component;
    }

    public static double getLength(Vector3d vector)
    {
        double aSguared = vector.x * vector.x;
        double bSquared = vector.y * vector.y;
        double cSquared = vector.z * vector.z;

        return java.lang.Math.sqrt(aSguared + bSquared + cSquared);
    }

    // TODO This method should handle exceptions in a more meaningful way.
    public static Point3d getMaximums(GeometryInfo mesh)
    {
        GeometryArray geometry = mesh.getGeometryArray();

        Point3d[] vertexes = new Point3d[geometry.getVertexCount()];
        try
        {
            CollectionUtils.instantiateArray(Point3d.class, vertexes);
        }
        catch (InstantiationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        geometry.getCoordinates(0, vertexes);

        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;

        for (Point3d vertex : vertexes)
        {
            maxX = java.lang.Math.max(maxX, vertex.x);
            maxY = java.lang.Math.max(maxY, vertex.y);
            maxZ = java.lang.Math.max(maxZ, vertex.z);
        }

        return new Point3d(maxX, maxY, maxZ);
    }

    public static Point3d getMidpoint(Point3d point1, Point3d point2)
    {
        double x = (point1.x + point2.x) / 2;
        double y = (point1.y + point2.y) / 2;
        double z = (point1.z + point2.z) / 2;

        return new Point3d(x, y, z);
    }

    // TODO This method should handle exceptions in a more meaningful way.
    public static Point3d getMinimums(GeometryInfo mesh)
    {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;

        try
        {
            GeometryArray geometry = mesh.getGeometryArray();

            Point3d[] vertexes = new Point3d[geometry.getVertexCount()];
            CollectionUtils.instantiateArray(Point3d.class, vertexes);
            geometry.getCoordinates(0, vertexes);

            for (Point3d vertex : vertexes)
            {
                minX = java.lang.Math.min(minX, vertex.x);
                minY = java.lang.Math.min(minY, vertex.y);
                minZ = java.lang.Math.min(minZ, vertex.z);
            }
        }

        catch (NullPointerException e)
        {
            minX = Double.NaN;
            minY = Double.NaN;
            minZ = Double.NaN;
        }
        catch (InstantiationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new Point3d(minX, minY, minZ);
    }
    
    public static Vector3d getUpVector(Orientation orientation)
    {
        Vector3d upVector = new Vector3d(0.0, 0.0, 0.0);
        double componentLength = 1.0;
        
        switch (orientation)
        {
            case X_UP:  upVector.x = componentLength; break;
            case Y_UP:  upVector.y = componentLength; break;
            case Z_UP:  upVector.z = componentLength; break;
            default:    throw new IllegalArgumentException("The supplied orientation is not supported.");            
        }
        
        return upVector;
    }
    
    // TODO These might need more work; no consideration was giving to the direction of the axes.
    public static double getVerticalComponent(Point3d point, Orientation orientation)
    {
        double component = 0.0;

        switch (orientation)
        {
            case X_UP:  component = point.x; break;
            case Y_UP:  component = point.y; break;
            case Z_UP:  component = point.z; break;
            default: throw new IllegalArgumentException("The supplied orientation is not supported.");
        }

        return component;
    }

    public static Vector3d normalize(Vector3d vector)
    {
        double length = Math.getLength(vector);

        double xN = vector.x / length;
        double yN = vector.y / length;
        double zN = vector.z / length;

        return new Vector3d(xN, yN, zN);
    }

    public static void scale(TransformGroup transformGroup, double scale)
    {
        Transform3D scaleTransform = new Transform3D();
        scaleTransform.setScale(scale);
        transformGroup.setTransform(scaleTransform);
    }

    public static void scale(TransformGroup transformGroup, Vector3d scale)
    {
        Transform3D scaleTransform = new Transform3D();
        scaleTransform.setScale(scale);
        transformGroup.setTransform(scaleTransform);
    }

    // TODO These might need more work; no consideration was giving to the direction of the axes.
    public static void setHorizontalComponent(Point3d point, double value, Orientation orientation)
    {
        switch (orientation)
        {
            case X_UP:  point.y = value; break;
            case Y_UP:  point.x = value; break;
            case Z_UP:  point.x = value; break;
            default:    throw new IllegalArgumentException("The supplied orientation is not supported.");
        }
    }

    // TODO These might need more work; no consideration was giving to the direction of the axes.
    public static void setVerticalComponent(Point3d point, double value, Orientation orientation)
    {
        switch (orientation)
        {
            case X_UP:  point.x = value; break;
            case Y_UP:  point.y = value; break;
            case Z_UP:  point.z = value; break;
            default: throw new IllegalArgumentException("The supplied orientation is not supported.");
        }
    }

    @Deprecated
    public static void transformLocalToWorld(TransformGroup transformGroup, Shape3D shape, Point3d worldPosition)
    {
        Vector3d translation = new Vector3d(worldPosition.x, worldPosition.y, worldPosition.z);

        Transform3D transform = new Transform3D();
        transform.setTranslation(translation);
        transformGroup.setTransform(transform);
        transformGroup.addChild(shape);
    }

    @Deprecated
    public static void translateLocalSpaceToWorldSpace(TransformGroup transformGroup, Shape3D shape, Point3d position)
    {
        Transform3D localToWorldTransform = new Transform3D();
        Point3d center = Utils.getCenter(shape);

        double dx = position.x - center.x;
        double dy = position.y - center.y;
        double dz = position.z - center.z;

        localToWorldTransform.setTranslation(new Vector3d(dx, dy, dz));
        transformGroup.setTransform(localToWorldTransform);
        transformGroup.addChild(shape);
    }
}
