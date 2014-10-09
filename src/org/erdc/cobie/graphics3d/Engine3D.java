package org.erdc.cobie.graphics3d;

import java.awt.Font;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Background;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Font3D;
import javax.media.j3d.Shape3D;
import javax.media.j3d.SpotLight;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.apache.commons.lang.NotImplementedException;
import org.erdc.cobie.graphics.Engine;
import org.erdc.cobie.graphics.For3D;
import org.erdc.cobie.graphics.RenderData;
import org.erdc.cobie.graphics.RenderMode;
import org.erdc.cobie.graphics.settings.GlobalSettings;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Text2D;
import com.sun.j3d.utils.universe.PlatformGeometry;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

// TODO 3D rendering was broken during last refactor; I suspect the problem is in initView().
public class Engine3D extends Engine implements For3D
{
    private SimpleUniverse universe;
    private BranchGroup scene;
    
    public Engine3D(GlobalSettings settings)
    {
        super(settings);
    }
    
    @Deprecated
    public void addAmbientLight(Color3f color)
    {
        AmbientLight ambientLight = new AmbientLight(color);
        ambientLight.setInfluencingBounds(getSettings().getRenderSettings3D().getBounds());

        getScene().addChild(ambientLight);
        getLogger().info("Light (ambient) added to scene.");
    }

    @Deprecated
    public void addDirectionalLight(Color3f color, Vector3f direction)
    {
        DirectionalLight directionalLight = new DirectionalLight(color, direction);
        directionalLight.setInfluencingBounds(getSettings().getRenderSettings3D().getBounds());

        getScene().addChild(directionalLight);
        getLogger().info("Light (directional) added to scene.");
    }

    @Deprecated
    public void addSpotLight(Color3f color, Point3f position, Point3f attenuation, Vector3f direction, float spreadAngle, float concentration)
    {
        SpotLight spotLight = new SpotLight(color, position, attenuation, direction, spreadAngle, concentration);
        spotLight.setInfluencingBounds(getSettings().getRenderSettings3D().getBounds());

        getScene().addChild(spotLight);
        getLogger().info("Light (spot) added to scene.");
    }

    @Deprecated
    public void addText(String text, Font3D font, Color3f color, Point3f position)
    {
        Text3D renderedText = new Text3D(font, text);
        renderedText.setAlignment(Text3D.ALIGN_CENTER);

        Shape3D textShape = new Shape3D();
        textShape.setGeometry(renderedText);
        textShape.setAppearance(Utils.makeAppearance(getSettings().getPolygonSettings().toJ3DPolygonAttributes(), color));
        textShape.setBounds(getSettings().getRenderSettings3D().getBounds());

        Transform3D transform = new Transform3D();
        transform.setTranslation(new Vector3f(position));
        // Math3D.scale(transformations, DEFAULT_SCALE);

        TransformGroup transformations = new TransformGroup();

        transformations.setTransform(transform);
        transformations.addChild(textShape);

        getScene().addChild(transformations);
        getLogger().info("Text ('" + text + "') added to scene.");
    }

    @Deprecated
    public void addText(String text, Point3f position)
    {
        addText(text, getSettings().getFontAttributes().getFont3D(), getSettings().getFontAttributes().getColor(), position);
    }

    @Deprecated
    public void addText2D(String text, Font font, Color3f color, Point3f position)
    {
        Text2D renderedText = new Text2D(text, color, font.getName(), font.getSize(), font.getStyle());

        Transform3D transform = new Transform3D();
        transform.setTranslation(new Vector3f(position));

        TransformGroup transformations = new TransformGroup();
        transformations.setTransform(transform);
        transformations.addChild(renderedText);

        PlatformGeometry platformGeometry = new PlatformGeometry();
        platformGeometry.addChild(transformations);

        getViewingPlatform().setPlatformGeometry(platformGeometry);
    }

    @Override
    public void addToCanvas(RenderData entity)
    {
        //getScene().addChild(entity.getRenderTree());
    }
    
//    private double checkCameraDistance(double cameraDistance)
//    {
//        if (cameraDistance >= getSettings().getRenderSettings3D().getBounds().getRadius())
//        {
//            this.getLogger().warn("The supplied camera distance (" + cameraDistance + ") is beyond the render boundary!");
//        }
//
//        return cameraDistance;
//    }
    
    @Override
    public void clearCanvas()
    {
        throw new NotImplementedException();
    }
    
    @Override
    protected void finalize()
    {
        getScene().compile();
        getUniverse().addBranchGraph(getScene());

        universe.getViewingPlatform().setNominalViewingTransform();
    }
    
    @Override
    protected GraphicsConfiguration getGraphics()
    {
        return SimpleUniverse.getPreferredConfiguration();
    }
    
    private BranchGroup getScene()
    {
        if (scene == null)
        {
            scene = new BranchGroup();
        }
        
        return scene;
    }
    
    private SimpleUniverse getUniverse()
    {
        return universe;
    }
    
    private ViewingPlatform getViewingPlatform()
    {
        return getUniverse().getViewingPlatform();
    }
    
    @Deprecated
    public final Transform3D getWorldTransform()
    {
        Transform3D worldTransform = new Transform3D();
        ((Canvas3D)getCanvas()).getVworldProjection(worldTransform, worldTransform);

        return worldTransform;
    }
    
    private void initBackground()
    {
        Background background = new Background();
        background.setColor(getSettings().getRenderSettings3D().getBackground());
        background.setCapability(Background.ALLOW_COLOR_WRITE);
        background.setApplicationBounds(getSettings().getRenderSettings3D().getBounds());

        getScene().addChild(background);
        this.getLogger().info("Background initialized to '" + getSettings().getRenderSettings3D().getBackground().toString() + "'.");
    }
    
    @Override
    protected void initCanvas()
    {
        setCanvas(new COBieCanvas3D(getGraphics(), getSettings()));
        
        initView();
        initMouseControl();
        initBackground();
    }
    
    private void initMouseControl()
    {
        if (getSettings().getRenderSettings().getRenderMode().contains(RenderMode.RENDER_TO_FRAME))
        {
            OrbitBehavior orbitBehavior = new OrbitBehavior();
            orbitBehavior.setSchedulingBounds(getSettings().getRenderSettings3D().getBounds());
            getViewingPlatform().setViewPlatformBehavior(orbitBehavior);

            getLogger().info("Simple mouse control initialized.");
        }
    }
    
    private final void initView()
    {        
//        ViewingPlatform viewingPlatform = new ViewingPlatform();
//        
//        viewingPlatform.
//        getViewPlatform().
//        setActivationRadius((float)getRenderSettings().bounds.getRadius());
//        
//        TransformGroup viewTransformations = viewingPlatform.getViewPlatformTransform();
//
//        Transform3D viewTransform = new Transform3D();
//        viewTransformations.getTransform(viewTransform);
//
//        viewTransform.lookAt(
//                new Point3d(0.0, 0.0, this.checkCameraDistance(getRenderSettings().cameraDistance)),
//                getRenderSettings().origin, Math3D.getUpVector(getRenderSettings().orientation));
//
//        viewTransform.invert();
//        viewTransformations.setTransform(viewTransform);
//
//        Viewer viewer = new Viewer((Canvas3D)getCanvas());
//        View view = viewer.getView();
//        view.setBackClipDistance(getRenderSettings().bounds.getRadius());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas3D = new Canvas3D(config);
        universe = new SimpleUniverse(canvas3D);
        
        getLogger().info("View initialized.");
    }
}
