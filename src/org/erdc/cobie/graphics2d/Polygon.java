package org.erdc.cobie.graphics2d;

import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point2d;

public class Polygon
{
    private List<Triangle> triangles;

    public Polygon()
    {
        triangles = new ArrayList<Triangle>();
    }

    public Polygon(List<Triangle> triangles)
    {
        this.triangles = triangles;
    }

    public List<Point2d> getPoints()
    {
        List<Point2d> coordinates = new ArrayList<Point2d>();

        for (Triangle triangle : toTriangles())
        {
            PathIterator iterator = triangle.getPath().getPathIterator(null);

            while (!iterator.isDone())
            {
                float[] coords = new float[6];
                int segmentType = iterator.currentSegment(coords);

                switch (segmentType)
                {
                    case PathIterator.SEG_MOVETO:
                    case PathIterator.SEG_LINETO:

                        coordinates.add(new Point2d(coords[0], coords[1]));
                        break;

                    case PathIterator.SEG_CLOSE:

                        break;

                    default:
                }

                iterator.next();
            }
        }

        return coordinates;
    }

    public List<Triangle> toTriangles()
    {
        return triangles;
    }
}
