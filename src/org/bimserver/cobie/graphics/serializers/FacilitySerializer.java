package org.bimserver.cobie.graphics.serializers;

import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.bimserver.cobie.graphics.Engine;
import org.bimserver.cobie.graphics.EngineSelector;
import org.bimserver.cobie.graphics.EngineUser;
import org.bimserver.cobie.graphics.FloorArranger;
import org.bimserver.cobie.graphics.FloorArrangerSelector;
import org.bimserver.cobie.graphics.RenderMode;
import org.bimserver.cobie.graphics.entities.Facility;
import org.bimserver.cobie.graphics.entities.Floor;
import org.bimserver.cobie.graphics.entities.RenderableIfcProduct;
import org.bimserver.cobie.graphics.filewriter.FacilityWriter;
import org.bimserver.cobie.graphics.filewriter.ImageMapWriter;
import org.bimserver.cobie.graphics.filewriter.IndexWriter;
import org.bimserver.cobie.graphics.filewriter.JSONWriter;
import org.bimserver.cobie.graphics.settings.FontSettings;
import org.bimserver.cobie.graphics.settings.GlobalSettings;
import org.bimserver.cobie.graphics.settings.MaterialSettings;
import org.bimserver.cobie.graphics.settings.OutputSettings;
import org.bimserver.cobie.graphics.settings.PolygonSettings;
import org.bimserver.cobie.graphics.settings.RenderSettings;
import org.bimserver.cobie.graphics.settings.RenderSettings2D;
import org.bimserver.cobie.graphics.settings.RenderSettings3D;
import org.bimserver.cobie.graphics.string.Default;
import org.bimserver.cobie.graphics3d.Capability;
import org.bimserver.cobie.graphics3d.CullMode;
import org.bimserver.cobie.graphics3d.FillMode;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcBuilding;
import org.bimserver.models.ifc2x3tc1.IfcProduct;
import org.bimserver.plugins.VirtualFile;
import org.bimserver.plugins.serializers.SerializerException;
import org.bimserver.utils.PathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacilitySerializer implements EngineUser
{
	private static final RenderSettings renderSettings;
	private static final PolygonSettings polygonAttributes;
	private static final GlobalSettings settings;

	private final IfcModelInterface model;

	private Engine engine;
    private Facility facility;
	private OutputStream outputStream;
	private Path rootPath;

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

    public FacilitySerializer(Path rootPath, IfcModelInterface model, OutputStream outputStream)
	{
    	this.rootPath = rootPath;
		this.model = model;
		this.outputStream = outputStream;
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
        getEngine().renderToFile(floor.getFileName(settings.getOutputSettings().getImageInfo().getExtension()));
    }
    
    public void write() throws SerializerException
    {
    	getLogger().info(Default.WRITING.toString());
    	
        try
        {
        	VirtualFile virtualFile = new VirtualFile();
            writeFacilityHTML(virtualFile);
            writeIndexHTML(virtualFile);
            writeResources(virtualFile);
            writeJSON(virtualFile);
            
            for (RenderableIfcProduct branch : getFacility().getBranches())
            {
                if (branch instanceof Floor)
                {
                    Floor floor = (Floor)branch;

                    if (floor.hasLeaves() || floor.hasBranches())
                    {
                        arrangeFloor(floor);
                        renderFloor(floor);
                        writeHTMLMap(virtualFile, floor);
                        getEngine().clearCanvas();
                    }
                }
            }

            virtualFile.createJar(outputStream);
        }

        catch (IOException e)
        {
            throw new SerializerException(e);
        }
    }
    
    private void writeFacilityHTML(VirtualFile virtualFile) throws IOException
    {
        FacilityWriter facilityWriter = new FacilityWriter(rootPath, getFacility(), settings);
        facilityWriter.write(virtualFile);
    }

    private void writeIndexHTML(VirtualFile virtualFile) throws IOException
    {
        IndexWriter indexWriter = new IndexWriter(rootPath, getFacility(), settings);
        indexWriter.write(virtualFile);
    }
    
    private void writeJSON(VirtualFile virtualFile) throws IOException
    {
        JSONWriter jsonWriter = new JSONWriter(model, settings);
        jsonWriter.write(virtualFile);
    }
    
    private void writeHTMLMap(VirtualFile virtualFile, Floor floor) throws IOException
    {
        ImageMapWriter imageMapWriter = new ImageMapWriter(rootPath, floor, settings);
        imageMapWriter.write(virtualFile);
    }

    private void copyDirectory(Path path, VirtualFile virtualFile) throws IOException {
    	if (Files.isDirectory(path)) {
    		for (Path p : PathUtils.list(path)) {
   				copyDirectory(p, virtualFile);
    		}
    	} else {
			InputStream inputStream = Files.newInputStream(path);
			virtualFile.createFile(path.toString()).setData(inputStream);
    	}
    }
    
    private void writeResources(VirtualFile virtualFile) throws IOException
    {
    	copyDirectory(rootPath.resolve("scripts"), virtualFile);
    	copyDirectory(rootPath.resolve("styles"), virtualFile);
    	copyDirectory(rootPath.resolve("html"), virtualFile);
    }
}