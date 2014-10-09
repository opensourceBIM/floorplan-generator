package org.erdc.cobie.graphics2d;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

import javax.vecmath.Point2d;
import javax.vecmath.Point2f;

import org.erdc.cobie.shared.Common;

// TODO: This class should enforce 3 points.
public class Triangle
{
    private final Path2D points;

    public Triangle(Path2D points)
    {
        this.points = points;
    }

    public Triangle(Point2d a, Point2d b, Point2d c)
    {
        points = new Path2D.Double();

        points.moveTo(a.x, a.y);
        points.lineTo(b.x, b.y);
        points.lineTo(c.x, c.y);
        points.closePath();
    }

    public Triangle(Point2f a, Point2f b, Point2f c)
    {
        points = new Path2D.Float();

        points.moveTo(a.x, a.y);
        points.lineTo(b.x, b.y);
        points.lineTo(c.x, c.y);
        points.closePath();
    }
    
    public final Path2D getPath()
    {
        return points;
    }

    @Override
    public String toString()
    {
        String coords = Common.EMPTY_STRING.toString();

        PathIterator iterator = this.getPath().getPathIterator(null);

        while (!iterator.isDone())
        {
            float[] coordinates = new float[6];
            int segmentType = iterator.currentSegment(coordinates);

            switch (segmentType)
            {
                case PathIterator.SEG_MOVETO:

                    coords += coordinates[0] + Common.COMMA.toString() + coordinates[1];
                    break;

                case PathIterator.SEG_LINETO:

                    coords += Common.COMMA_TEXT.toString() + coordinates[0] + Common.COMMA.toString() + coordinates[1];
                    break;

                case PathIterator.SEG_CLOSE:

                    break;

                default:
            }

            iterator.next();
        }

        return coords;
    }
}
