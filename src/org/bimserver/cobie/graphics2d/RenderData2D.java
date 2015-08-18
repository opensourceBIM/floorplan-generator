package org.bimserver.cobie.graphics2d;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Color3f;
import javax.vecmath.Point2d;

import org.bimserver.cobie.graphics.For2D;
import org.bimserver.cobie.graphics.InvalidMeshException;
import org.bimserver.cobie.graphics.RenderData;
import org.bimserver.cobie.graphics.entities.EnergyConversionDevice;
import org.bimserver.cobie.graphics.entities.RenderableIfcProduct;
import org.bimserver.cobie.graphics.entities.Stair;
import org.bimserver.cobie.graphics.entities.StairFlight;
import org.bimserver.cobie.graphics.settings.GlobalSettings;
import org.bimserver.cobie.graphics.settings.RenderSettings;
import org.bimserver.cobie.graphics.string.Default;

public class RenderData2D extends RenderData implements For2D
{
    private Color3f color;
    private Stroke stroke;
    private Polygon polygon;
    private Polygon transformedPolygon;
    private AffineTransform transform2D;

    public RenderData2D(GlobalSettings settings, RenderableIfcProduct product)
    {
        this(settings, product, RenderSettings.DEFAULT_COLOR, RenderSettings.DEFAULT_STROKE);
    }

    public RenderData2D(GlobalSettings settings, RenderableIfcProduct product, Color3f color, Stroke stroke)
    {
        super(settings, product);
        setColor(color);
        setStroke(stroke);
    }

    private void applyTransform(AffineTransform transform)
    {
        getTransform().concatenate(transform);

        List<Triangle> triangles = getPolygon().toTriangles();
        List<Triangle> transformedTriangles = new ArrayList<Triangle>(triangles.size());

        for (Triangle triangle : triangles)
        {
            Path2D transformedTriangle = (Path2D)getTransform().createTransformedShape(triangle.getPath());
            transformedTriangles.add(new Triangle(transformedTriangle));
        }

        transformedPolygon = new Polygon(transformedTriangles);
    }

    @Override
    public final double getBottom()
    {
        Point2d minimums = Math.getMinimums(this.getTransformedPolygon());
        return minimums.y;
    }

    public Point2d getCenter()
    {
        double horizontalCenter = (getRight() + getLeft()) / 2;
        double verticalCenter = (getTop() + getBottom()) / 2;

        return new Point2d(horizontalCenter, verticalCenter);
    }

    public Color3f getColor()
    {
        return color;
    }

    @Override
    public final double getHeight()
    {
        return java.lang.Math.abs(getTop() - getBottom());
    }

    @Override
    public final double getLeft()
    {
        Point2d minimums = Math.getMinimums(getTransformedPolygon());
        return minimums.x;
    }

    /**
     * Gets a {@code RenderableEntity}'s polygon representation.
     * 
     * NOTE: Due to difficulties with rendering concave polygons with unknown
     * point order, a {@code RenderableEntity}'s polygon representation is
     * composed of individual triangles (which can be rendered in any order).
     * 
     * While it is possible to get a {@code RenderableEntity}'s polygon
     * representation in correct order directly from an IFC model, doing so
     * would be much more complex than the current approach (which gets the
     * geometry out of a mesh provided by {@code BimServer}).
     * 
     * @return A collection of triangles defining a {@code RenderableEntity}'s
     *         polygon representation.
     */
    public final Polygon getPolygon()
    {
        if (polygon == null)
        {
            PolygonBuilder polygonBuilder = new PolygonBuilder(getMesh(), getSettings());

            try
            {
                polygon = polygonBuilder.getPolygon();
            }

            // A RenderableEntity might not have a mesh, if so just make an
            // empty polygon.
            catch (InvalidMeshException e)
            {
                polygon = new Polygon();
                getLogger().warn(Default.POLYGON_GENERATION_FAILED.toString());
            }
        }

        return polygon;
    }

    @Override
    public final double getRight()
    {
        Point2d maximums = Math.getMaximums(getTransformedPolygon());
        return maximums.x;
    }

    public Stroke getStroke()
    {
        return stroke;
    }

    @Override
    public final double getTop()
    {
        Point2d maximums = Math.getMaximums(getTransformedPolygon());
        return maximums.y;
    }

    /**
     * Gets a {@code RenderableEntity}'s 2D transform.
     * 
     * The transform returned by this method is a concatenation of all the 2D
     * rotations, scalings, and translations applied to a
     * {@code RenderableEntity}. It is generally not necessary to call this
     * method externally, because a {@code RenderableEntity}'s transformed 2D
     * representation can be accessed via
     * {@code RenderableEntity.getTransformedPolygon()}.
     * 
     * NOTE: Due to how transforms are applied internally, this method will not
     * return a null transform.
     * 
     * @return The {@code RenderableEntity}'s 2D transform.
     */
    public final AffineTransform getTransform()
    {
        if (transform2D == null)
        {
            transform2D = new AffineTransform();
        }

        return transform2D;
    }

    public final Polygon getTransformedPolygon()
    {
        return (transformedPolygon != null) ? transformedPolygon : getPolygon();
    }

    @Override
    public final double getWidth()
    {
        return java.lang.Math.abs(getRight() - getLeft());
    }

    public final void move(double tx, double ty)
    {
        applyTransform(AffineTransform.getTranslateInstance(tx, ty));
    }

    public final void rotate(double theta)
    {
        applyTransform(AffineTransform.getRotateInstance(theta));
    }

    public final void scale(double sx, double sy)
    {
        applyTransform(AffineTransform.getScaleInstance(sx, sy));
    }

    protected final void setColor(Color3f color)
    {
        if (color != null)
        {
            this.color = color;
        }

        else
        {
            this.color = RenderSettings.DEFAULT_COLOR;
            getLogger().warn(Default.NULL_COLOR.toString());
        }
    }

    public void setStroke(Stroke stroke)
    {
        if (stroke != null)
        {
            this.stroke = stroke;
        }

        else
        {
            this.stroke = RenderSettings.DEFAULT_STROKE;
            getLogger().warn(Default.NULL_STROKE.toString());
        }
    }

    boolean paintsInReverseOrder()
    {
        RenderableIfcProduct product = getProduct();    
        return (product instanceof Stair) || (product instanceof StairFlight) || (product instanceof EnergyConversionDevice);
    }
}
