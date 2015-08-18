package org.bimserver.cobie.graphics.settings;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.vecmath.Color3f;

import org.bimserver.models.ifc2x3tc1.IfcBuilding;
import org.bimserver.models.ifc2x3tc1.IfcBuildingStorey;
import org.bimserver.models.ifc2x3tc1.IfcDoor;
import org.bimserver.models.ifc2x3tc1.IfcEnergyConversionDevice;
import org.bimserver.models.ifc2x3tc1.IfcFlowController;
import org.bimserver.models.ifc2x3tc1.IfcFlowFitting;
import org.bimserver.models.ifc2x3tc1.IfcFlowMovingDevice;
import org.bimserver.models.ifc2x3tc1.IfcFlowSegment;
import org.bimserver.models.ifc2x3tc1.IfcFlowStorageDevice;
import org.bimserver.models.ifc2x3tc1.IfcFlowTerminal;
import org.bimserver.models.ifc2x3tc1.IfcFurnishingElement;
import org.bimserver.models.ifc2x3tc1.IfcProduct;
import org.bimserver.models.ifc2x3tc1.IfcSpace;
import org.bimserver.models.ifc2x3tc1.IfcStair;
import org.bimserver.models.ifc2x3tc1.IfcStairFlight;
import org.bimserver.models.ifc2x3tc1.IfcWall;
import org.bimserver.models.ifc2x3tc1.IfcWindow;
import org.bimserver.cobie.graphics.COBieColor;
import org.bimserver.cobie.graphics.EntitySetting;
import org.bimserver.cobie.graphics.RenderMode;
import org.bimserver.cobie.graphics.Selector;
import org.bimserver.cobie.graphics2d.CapMode;
import org.bimserver.cobie.graphics2d.JoinMode;
import org.bimserver.cobie.graphics2d.Stroke;

public class RenderSettings extends Settings implements Selector<RenderSettings2D, RenderSettings3D>
{
    public static final boolean DEFAULT_ANTIALIAS = false;
    public static final Color3f DEFAULT_COLOR = COBieColor.BLUE.toColor3f();
    public static final EnumSet<RenderMode> DEFAULT_RENDER_MODE = EnumSet.of(RenderMode.RENDER_3D, RenderMode.RENDER_TO_FRAME);

    public static final int DEFAULT_WIDTH = 1024;
    public static final int DEFAULT_HEIGHT = 1024;
    public static final int DEFAULT_MARGIN = 25;

    public static final List<EntitySetting> DEFAULT_ENTITY_SETTINGS = new ArrayList<EntitySetting>();
    public static final Stroke DEFAULT_STROKE = new Stroke(2.0f, COBieColor.BLACK.toColor3f(), CapMode.BUTT, JoinMode.BEVEL);
    public static final Stroke NO_STROKE = new Stroke(0, COBieColor.BLACK.toColor3f(), CapMode.BUTT, JoinMode.BEVEL);

    static
    {
        DEFAULT_ENTITY_SETTINGS.add(new EntitySetting(IfcBuilding.class, "Facility", COBieColor.BLUE.toColor3f(), NO_STROKE));
        DEFAULT_ENTITY_SETTINGS.add(new EntitySetting(IfcBuildingStorey.class, "Floor", COBieColor.BLUE.toColor3f(), NO_STROKE));
        DEFAULT_ENTITY_SETTINGS.add(new EntitySetting(IfcSpace.class, "Space", COBieColor.BLUE.toColor3f(), makeStroke(COBieColor.BLUE, 1.0f)));
        DEFAULT_ENTITY_SETTINGS.add(new EntitySetting(IfcWall.class, "Wall", COBieColor.GRAY.toColor3f(), DEFAULT_STROKE));
        DEFAULT_ENTITY_SETTINGS.add(new EntitySetting(IfcDoor.class, "Door", COBieColor.MAGENTA.toColor3f(), DEFAULT_STROKE));
        // DEFAULT_ENTITY_SETTINGS.add(new EntitySetting(IfcCovering.class, DEFAULT_STROKE));
        DEFAULT_ENTITY_SETTINGS.add(new EntitySetting(IfcStair.class, "Stair", COBieColor.GRAY.toColor3f(), NO_STROKE));
        DEFAULT_ENTITY_SETTINGS.add(new EntitySetting(IfcStairFlight.class, "Stair Flight", COBieColor.GRAY.toColor3f(), makeStroke(COBieColor.BLACK, 1.0f)));
        DEFAULT_ENTITY_SETTINGS.add(new EntitySetting(IfcWindow.class, "Window", COBieColor.CYAN.toColor3f(), DEFAULT_STROKE));
        DEFAULT_ENTITY_SETTINGS.add(new EntitySetting(IfcFurnishingElement.class, "Furnishing", COBieColor.BROWN_NOSE.toColor3f(), DEFAULT_STROKE));
        DEFAULT_ENTITY_SETTINGS.add(new EntitySetting(IfcFlowSegment.class, "Flow Segment", COBieColor.DARK_ORANGE.toColor3f(), DEFAULT_STROKE));
        DEFAULT_ENTITY_SETTINGS.add(new EntitySetting(IfcFlowFitting.class, "Flow Fitting", COBieColor.DARK_ORANGE.toColor3f(), DEFAULT_STROKE));
        DEFAULT_ENTITY_SETTINGS.add(new EntitySetting(IfcFlowTerminal.class, "Flow Terminal", COBieColor.VERMILION.toColor3f(), DEFAULT_STROKE));
        DEFAULT_ENTITY_SETTINGS.add(new EntitySetting(IfcFlowController.class, "Flow Controller", COBieColor.YELLOW.toColor3f(), DEFAULT_STROKE));
        DEFAULT_ENTITY_SETTINGS.add(new EntitySetting(IfcFlowMovingDevice.class, "Flow Moving Device", COBieColor.GREEN.toColor3f(), DEFAULT_STROKE));
        DEFAULT_ENTITY_SETTINGS.add(new EntitySetting(IfcFlowStorageDevice.class, "Flow Storage Device", COBieColor.PIGMENT_BLUE.toColor3f(), DEFAULT_STROKE));
        DEFAULT_ENTITY_SETTINGS.add(new EntitySetting(IfcEnergyConversionDevice.class, "Energy Conversion Device", COBieColor.GRAY.toColor3f(), DEFAULT_STROKE));
    }

    private boolean antialias;
    private Dimension dimension;
    private List<EntitySetting> entitySettings;
    private final RenderSettings2D for2D;
    private final RenderSettings3D for3D;
    private int margin;
    private EnumSet<RenderMode> renderMode;

    public RenderSettings()
    {
        this(
                DEFAULT_RENDER_MODE, 
                new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT), 
                DEFAULT_MARGIN, 
                DEFAULT_ANTIALIAS, 
                DEFAULT_ENTITY_SETTINGS,
                new RenderSettings2D(), 
                new RenderSettings3D());
    }

    public RenderSettings(
            EnumSet<RenderMode> renderMode, 
            Dimension dimension, 
            int margin, 
            boolean antialias, 
            List<EntitySetting> entityColors,
            RenderSettings2D for2D, 
            RenderSettings3D for3D)
    {
        setRenderMode(renderMode);
        setDimension(dimension);
        setMargin(margin);
        setAntialias(antialias);

        this.entitySettings = entityColors;
        this.for2D = for2D;
        this.for3D = for3D;
    }
    
    private static Stroke makeStroke(COBieColor color, float width)
    {
        return new Stroke(width, color.toColor3f(), CapMode.BUTT, JoinMode.BEVEL);
    }

    @Override
    public RenderSettings2D for2D()
    {
        return for2D;
    }

    @Override
    public RenderSettings3D for3D()
    {
        return for3D;
    }

    public boolean getAntialias()
    {
        return antialias;
    }

    public final int getBothMargins()
    {
        return margin * 2;
    }

    public final Dimension getDimension()
    {
        return dimension;
    }

    public EntitySetting getEntitySetting(Class<? extends IfcProduct> productType)
    {
        EntitySetting setting = null;

        for (EntitySetting entitySetting : getEntitySettings())
        {
            if (entitySetting.getProductType().isAssignableFrom(productType))
            {
                setting = entitySetting;
                break;
            }
        }

        return setting;
    }

    public final List<EntitySetting> getEntitySettings()
    {
        return entitySettings;
    }

    public final int getMargin()
    {
        return margin;
    }

    public final EnumSet<RenderMode> getRenderMode()
    {
        return renderMode;
    }

    public List<Class<? extends IfcProduct>> getSupportedTypes()
    {
        List<Class<? extends IfcProduct>> supportedTypes = new ArrayList<Class<? extends IfcProduct>>();

        for (EntitySetting entitySetting : getEntitySettings())
        {
            supportedTypes.add(entitySetting.getProductType());
        }

        return supportedTypes;
    }

    public final void setAntialias(boolean antialias)
    {
        this.antialias = antialias;
    }

    public final void setDimension(Dimension dimension)
    {
        this.dimension = dimension;
    }

    public final void setMargin(int margin)
    {
        this.margin = margin;
    }

    public final void setRenderMode(EnumSet<RenderMode> renderMode)
    {
        this.renderMode = renderMode;
    }
}
