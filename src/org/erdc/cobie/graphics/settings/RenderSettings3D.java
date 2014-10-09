package org.erdc.cobie.graphics.settings;

import javax.media.j3d.BoundingSphere;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

import org.erdc.cobie.graphics.COBieColor;
import org.erdc.cobie.graphics.For3D;
import org.erdc.cobie.graphics3d.Orientation;

public class RenderSettings3D extends Settings implements For3D
{
    public static final double DEFAULT_MODEL_TO_SCREEN_CONVERSION = 0.0254 / 90.0;
    public static final Color3f DEFAULT_BACKGROUND = COBieColor.WHITE.toColor3f();
    public static final Orientation DEFAULT_ORIENTATION = Orientation.Y_UP;
    public static final Point3d DEFAULT_ORIGIN = new Point3d(0.0, 0.0, 0.0);
    public static final double DEFAULT_SCALE = 0.05;
    public static final BoundingSphere DEFAULT_BOUNDS = new BoundingSphere(DEFAULT_ORIGIN, 1000.0);
    public static final double DEFAULT_CAMERA_DISTANCE = 25.0;

    private double modelToWorldConversion;
    private Color3f background;
    private Orientation orientation;
    private Point3d origin;
    private double scale;
    private BoundingSphere bounds;
    private double cameraDistance;

    public RenderSettings3D()
    {
        this(
                DEFAULT_MODEL_TO_SCREEN_CONVERSION, 
                DEFAULT_BACKGROUND, 
                DEFAULT_ORIENTATION,
                DEFAULT_ORIGIN, 
                DEFAULT_SCALE, 
                DEFAULT_BOUNDS,
                DEFAULT_CAMERA_DISTANCE);
    }

    public RenderSettings3D(
            double modelToWorldConversion, 
            Color3f background, 
            Orientation orientation, 
            Point3d origin, 
            double scale,
            BoundingSphere bounds, 
            double cameraDistance)
    {
        setModelToWorldConversion(modelToWorldConversion);
        setBackground(background);
        setOrientation(orientation);
        setOrigin(origin);
        setScale(scale);
        setBounds(bounds);
        setCameraDistance(cameraDistance);
    }

    public Color3f getBackground()
    {
        return background;
    }

    public BoundingSphere getBounds()
    {
        return bounds;
    }

    public double getCameraDistance()
    {
        return cameraDistance;
    }

    public double getModelToWorldConversion()
    {
        return modelToWorldConversion;
    }

    public Orientation getOrientation()
    {
        return orientation;
    }

    public Point3d getOrigin()
    {
        return origin;
    }

    public double getScale()
    {
        return scale;
    }

    public void setBackground(Color3f background)
    {
        this.background = background;
    }

    public void setBounds(BoundingSphere bounds)
    {
        this.bounds = bounds;
    }

    public void setCameraDistance(double cameraDistance)
    {
        this.cameraDistance = cameraDistance;
    }

    public void setModelToWorldConversion(double modelToWorldConversion)
    {
        this.modelToWorldConversion = modelToWorldConversion;
    }

    public void setOrientation(Orientation orientation)
    {
        this.orientation = orientation;
    }

    public void setOrigin(Point3d origin)
    {
        this.origin = origin;
    }

    public void setScale(double scale)
    {
        this.scale = scale;
    }
}
