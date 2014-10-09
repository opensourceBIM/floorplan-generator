package org.erdc.cobie.graphics.serializers;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcBuilding;
import org.bimserver.models.ifc2x3tc1.IfcProduct;
import org.bimserver.plugins.serializers.SerializerException;
import org.erdc.cobie.graphics.Engine;
import org.erdc.cobie.graphics.EngineSelector;
import org.erdc.cobie.graphics.EngineUser;
import org.erdc.cobie.graphics.FloorArranger;
import org.erdc.cobie.graphics.FloorArrangerSelector;
import org.erdc.cobie.graphics.RenderMode;
import org.erdc.cobie.graphics.entities.Facility;
import org.erdc.cobie.graphics.entities.Floor;
import org.erdc.cobie.graphics.entities.RenderableIfcProduct;
import org.erdc.cobie.graphics.filewriter.FacilityWriter;
import org.erdc.cobie.graphics.filewriter.ImageMapWriter;
import org.erdc.cobie.graphics.filewriter.IndexWriter;
import org.erdc.cobie.graphics.filewriter.JSONWriter;
import org.erdc.cobie.graphics.filewriter.ResourceWriter;
import org.erdc.cobie.graphics.settings.FontSettings;
import org.erdc.cobie.graphics.settings.GlobalSettings;
import org.erdc.cobie.graphics.settings.MaterialSettings;
import org.erdc.cobie.graphics.settings.OutputSettings;
import org.erdc.cobie.graphics.settings.PolygonSettings;
import org.erdc.cobie.graphics.settings.RenderSettings;
import org.erdc.cobie.graphics.settings.RenderSettings2D;
import org.erdc.cobie.graphics.settings.RenderSettings3D;
import org.erdc.cobie.graphics.string.Default;
import org.erdc.cobie.graphics3d.Capability;
import org.erdc.cobie.graphics3d.CullMode;
import org.erdc.cobie.graphics3d.FillMode;
import org.erdc.cobie.shared.Zipper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacilitySerializer implements EngineUser
{
    private Engine engine;
    private Facility facility;
    private final IfcModelInterface model;
    private final Zipper zipper;
    
    private static final RenderSettings renderSettings;
    private static final PolygonSettings polygonAttributes;
    private static final GlobalSettings settings;

    static
    {
        renderSettings = new RenderSettings(
		                    EnumSet.of(RenderMode.RENDER_2D, RenderMode.RENDER_TO_FILE),
		                    new Dimension(RenderSettings.DEFAULT_WIDTH, RenderSettings.DEFAULT_HEIGHT), 
		                    RenderSettings.DEFAULT_MARGIN, 
		                    RenderSettings.DEFAULT_ANTIALIAS,
		                    RenderSettings.DEFAULT_ENTITY_SETTINGS, 
		                    new RenderSettings2D(), 
		                    new RenderSettings3D());

        polygonAttributes = new PolygonSettings(FillMode.LINE, CullMode.CULL_FRONTFACES, Capability.ALLOW_MODE_READ);

        settings = new GlobalSettings(new FontSettings(), new MaterialSettings(), new OutputSettings(), polygonAttributes, renderSettings);
    }

    public FacilitySerializer(Map<String, File> resources, IfcModelInterface model, Zipper zipper)
	{
    	this.model = model;
    	this.zipper = zipper;
    	
    	// TODO At least some of these should be set via BimServer.
    	settings.getOutputSettings().setResourceFiles(resources);
	}
    
    private void addToCanvas(List<? extends RenderableIfcProduct> products)
    {
        for (RenderableIfcProduct product : products)
        {
            getEngine().addToCanvas(product.for2D());
        }
    }

    private void arrangeFloor(Floor floor)
    {
        FloorArranger floorArranger = new FloorArrangerSelector(getEngine(), floor).for2D();
        floorArranger.arrange();
    }
    
    @Override
    public final Engine getEngine()
    {
        if (engine == null)
        {
            // Note that we are passing in this class's settings here, not
            // getting the Engine's as we normally would.
            engine = new EngineSelector(settings).for2D();
        }

        return engine;
    }
    
    private final Facility getFacility()
    {
        if (facility == null)
        {
            List<IfcBuilding> buildings = model.getAllWithSubTypes(IfcBuilding.class);
            facility = new Facility(model, getEngine(), buildings.get(0));
        }

        return facility;
    }
    
    @Override
    public final Logger getLogger()
    {
        return LoggerFactory.getLogger(getClass());
    }
    
    @Override
    public final GlobalSettings getSettings()
    {
        return getEngine().getSettings();
    }
    
    private final Zipper getZipper()
    {
        return zipper;
    }
  	    
    // TODO Rendering should probably be done in RenderData2D.
    private void renderFloor(Floor floor) throws IOException
    {
    	List<Class<? extends IfcProduct>> supportedTypes = getSettings().getRenderSettings().getSupportedTypes();
    	List<RenderableIfcProduct> renderableEntities = new ArrayList<>();
    	
    	for (Class<? extends IfcProduct> supportedType : supportedTypes)
    	{
    		for (RenderableIfcProduct renderableEntity : floor.getTree())
            {
                if (supportedType.isAssignableFrom(renderableEntity.getProductType()))
                {
                    renderableEntities.add(renderableEntity);
                }
            }
    	}
    	
    	// TODO Probably should sort entities w/ painter's algorithm before adding to canvas, but it seems to work.
        addToCanvas(renderableEntities);
        getEngine().renderToArchive(floor.getFileName(settings.getOutputSettings().getImageInfo().getExtension()), getZipper());
    }
    
    public void write() throws SerializerException
    {
    	getLogger().info(Default.WRITING.toString());
    	
        try
        {
            writeFacilityHTML();
            writeIndexHTML();
            writeResources();
            writeJSON();
            
            for (RenderableIfcProduct branch : getFacility().getBranches())
            {
                if (branch instanceof Floor)
                {
                    Floor floor = (Floor)branch;

                    if (floor.hasLeaves() || floor.hasBranches())
                    {
                        arrangeFloor(floor);
                        renderFloor(floor);
                        writeHTMLMap(floor);
                        getEngine().clearCanvas();
                    }
                }
            }

            getZipper().writeZipArchive();
        }

        catch (IOException e)
        {
            throw new SerializerException(e);
        }
    }
    
    private void writeFacilityHTML() throws IOException
    {
        FacilityWriter facilityWriter = new FacilityWriter(getFacility(), settings);
        facilityWriter.write(getZipper());
    }

    private void writeIndexHTML() throws IOException
    {
        IndexWriter indexWriter = new IndexWriter(getFacility(), settings);
        indexWriter.write(getZipper());
    }
    
    private void writeJSON() throws IOException
    {
        JSONWriter jsonWriter = new JSONWriter(model, settings);
        jsonWriter.write(getZipper());
    }
    
    private void writeHTMLMap(Floor floor) throws IOException
    {
        ImageMapWriter imageMapWriter = new ImageMapWriter(floor, settings);
        imageMapWriter.write(getZipper());
    }
    
    private void writeResources() throws IOException
    {
        ResourceWriter resourceWriter = new ResourceWriter(settings);
        resourceWriter.write(getZipper());
    }
}
