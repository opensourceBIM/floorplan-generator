package org.bimserver.cobie.graphics3d;

import javax.media.j3d.Appearance;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import org.bimserver.cobie.graphics.For3D;
import org.bimserver.cobie.graphics.RenderData;
import org.bimserver.cobie.graphics.entities.RenderableIfcProduct;
import org.bimserver.cobie.graphics.settings.GlobalSettings;
import org.bimserver.cobie.graphics.settings.MaterialSettings;
import org.bimserver.cobie.graphics.settings.PolygonSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RenderData3D extends RenderData implements For3D
{    
    private static final Logger LOGGER = LoggerFactory.getLogger(RenderData3D.class);

    private static Appearance defaultAppearance;

    static
    {
        RenderData3D.defaultAppearance = 
                Utils.makeAppearance(
                        new PolygonAttributes(
                                PolygonSettings.DEFAULT_FILL_MODE.toInt(),
                                PolygonSettings.DEFAULT_CULL_MODE.toInt(), 
                                PolygonSettings.DEFAULT_CAPABILITY.toInt()),
                        MaterialSettings.DEFAULT_MATERIAL_COLOR);
    }

    protected Appearance appearance;
    private Shape3D shape;
    private Transform3D transform3D;
    private TransformGroup transformations;
    
    public RenderData3D(GlobalSettings settings, RenderableIfcProduct product)
    {
        this(settings, product, defaultAppearance);
    }
    
    public RenderData3D(GlobalSettings settings, RenderableIfcProduct product, Appearance appearance)
    {
        super(settings, product);
        setAppearance(appearance);
    } 
    
    private void addTransform(Transform3D transform)
    {
        if (getTransform3D() == null)
        {
            transform3D = transform;
        }

        else
        {
            transform3D.mul(transform);
        }
    }

    public final Appearance getAppearance()
    {
        return appearance;
    }

    @Override
    public final double getBottom()
    {
        Point3d minimums = Math.getMinimums(getMesh());
        return Math.getVerticalComponent(minimums, getSettings().getRenderSettings3D().getOrientation());
    }
    
    public Point3d getCenter()
    {
        return Math.getCenter(getMesh(), getSettings().getRenderSettings().for3D().getOrientation());
    }
    
    @Override
    public final double getHeight()
    {
        return java.lang.Math.abs(getTop() - getBottom());
    }

    @Override
    public final double getLeft()
    {
        Point3d minimums = Math.getMinimums(getMesh());
        return Math.getHorizontalComponent(minimums, getSettings().getRenderSettings3D().getOrientation());
    }

    public final Point3d GetLocalPosition()
    {
        Transform3D localTransform = new Transform3D();
        getShape().getLocalToVworld(localTransform);
        
        Vector3d position = new Vector3d();
        localTransform.get(position);
        
        return new Point3d(position.x, position.y, position.z);
    }
   
    /**
     * Returns the complete render tree for this RenderableEntity, including the transformed mesh.
     * This is probably the only method you will need to call to render a RenderableEntity, but the
     * other "get..." methods are available for more customized rendering options.
     * 
     * @return
     */
    public final TransformGroup getRenderTree()
    {
        if (transformations == null)
        {
            transformations = new TransformGroup();
            transformations.setTransform(getTransform3D());            
            transformations.addChild(getShape()); 
        }
        
        return transformations;
    }
    
    @Override
    public final double getRight()
    {
        Point3d maximums = Math.getMaximums(getMesh());
        return Math.getHorizontalComponent(maximums, getSettings().getRenderSettings3D().getOrientation());
    }
    
    public final Shape3D getShape()
    {
        if (shape == null)
        {
            shape = makeShape();
        }

        return shape;
    }

    @Override
    public final double getTop()
    {
        Point3d maximums = Math.getMaximums(getMesh());
        return Math.getVerticalComponent(maximums, getSettings().getRenderSettings3D().getOrientation());
    }
    
    /**
     * Gets a {@code RenderableEntity}'s 3D transform.
     * 
     * The transform returned by this method is a concatenation of all the 3D
     * rotations, scalings, and translations applied to a
     * {@code RenderableEntity}. It is generally not necessary to call this
     * method externally, because a {@code RenderableEntity}'s complete 3D
     * render tree (including its transforms) can be accessed via
     * {@code RenderableEntity.getRenderTree()}.
     * 
     * NOTE: Due to how transforms are applied internally, this method will not
     * return a null transform.
     * 
     * @return The {@code RenderableEntity}'s 3D transform.
     */
    public final Transform3D getTransform3D()
    {
        if (transform3D == null)
        {
            transform3D = new Transform3D();
        }

        return transform3D;
    }

    @Override
    public final double getWidth()
    {
        return java.lang.Math.abs(getRight() - getLeft());
    }
    
    private Shape3D makeShape()
    {
        Shape3D shape = new Shape3D(getMesh().getGeometryArray(), getAppearance());
        shape.setAppearance(getAppearance());
        shape.setBounds(getSettings().getRenderSettings3D().getBounds());

        return shape;
    }

    public final void move(Vector3d translation)
    {
        Transform3D translationTransform = new Transform3D();
        translationTransform.setTranslation(translation);

        addTransform(translationTransform);
    }

    public final void rotate(double rotation)
    {
        Transform3D rotationTransform = new Transform3D();
        rotationTransform.rotZ(rotation);

        addTransform(rotationTransform);
    }

    public final void scale(Vector3d scaling)
    {
        Transform3D scalingTransform = new Transform3D();
        scalingTransform.setScale(scaling);

        addTransform(scalingTransform);
    }
    
    protected final void setAppearance(Appearance appearance)
    {
        if (appearance != null)
        {
            this.appearance = appearance;
        }

        else
        {
            this.appearance = RenderData3D.defaultAppearance;
            LOGGER.warn("Appearance is null. Using default.");
        }
    }
}
