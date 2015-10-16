package org.bimserver.cobie.graphics.entities;

import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Color3f;

import org.bimserver.cobie.graphics.Engine;
import org.bimserver.cobie.graphics.EngineUser;
import org.bimserver.cobie.graphics.EntitySetting;
import org.bimserver.cobie.graphics.InvalidMeshException;
import org.bimserver.cobie.graphics.Parser;
import org.bimserver.cobie.graphics.Selector;
import org.bimserver.cobie.graphics.settings.GlobalSettings;
import org.bimserver.cobie.graphics.settings.OutputSettings;
import org.bimserver.cobie.graphics.string.Default;
import org.bimserver.cobie.graphics2d.RenderData2D;
import org.bimserver.cobie.graphics2d.Stroke;
import org.bimserver.cobie.graphics3d.RenderData3D;
import org.bimserver.cobie.graphics3d.Utils;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.j3d.utils.geometry.GeometryInfo;

/***
 * Represents a renderable representation of a COBie entity.
 */
public abstract class RenderableIfcProduct implements EngineUser, Selector<RenderData2D, RenderData3D>
{
    private Map<String, String> fileNames = new HashMap<String, String>();
    private RenderData2D for2D;
    private RenderData3D for3D;
    private final Engine engine;
    private final IfcModelInterface model;
    private final IfcProduct product;
    private GeometryInfo mesh;
    private Color3f color;
    private Stroke stroke;
    private Parser parser;
    
    protected RenderableIfcProduct(IfcModelInterface model, Engine engine, IfcProduct product)
    {
        this.model = model;
        this.engine = engine;
        this.product = product;
        parser = new Parser(product);

        setColor(getEntitySetting().getFillColor());
        setStroke(getEntitySetting().getStroke());
    }

    @Override
    public RenderData2D for2D()
    {
        if (for2D == null)
        {
            for2D = new RenderData2D(getSettings(), this, getColor(), getStroke());
        }

        return for2D;
    }

    @Override
    public RenderData3D for3D()
    {
        if (for3D == null)
        {
            for3D = new RenderData3D(getSettings(),	this, Utils.makeAppearance(getSettings().getPolygonSettings().toJ3DPolygonAttributes(), getColor()));
        }

        return for3D;
    }

    protected final Color3f getColor()
    {
        return color;
    }

    @Override
    public Engine getEngine()
    {
        return engine;
    }

    protected final EntitySetting getEntitySetting()
    {
        return getSettings().getRenderSettings().getEntitySetting(getProductType());
    }

    public String getFileName(String fileExtension)
    {           
        if (!fileNames.containsKey(fileExtension))
        {
             fileNames.put(fileExtension, OutputSettings.checkName(getName(), fileExtension));
        }
    
        return fileNames.get(fileExtension);
    }

	@Override
	public final Logger getLogger()
	{
		return LoggerFactory.getLogger(getClass());
	}
	
    public final GeometryInfo getMesh()
    {
        if (mesh == null)
        {
            try
            {      	
                mesh = makeMesh();            
            }

            catch (InvalidMeshException ex)
            {
                mesh = new GeometryInfo(GeometryInfo.TRIANGLE_ARRAY);
                getLogger().warn(Default.EMPTY_MESH_FALLBACK.toString());
            }
        }

        return mesh;
    }

    
	protected final IfcModelInterface getModel()
    {
        return model;
    }

    public final String getName()
    {
        return getProduct().getName();
    }

    public final IfcProduct getProduct()
    {
        return product;
    }

    public final Class<? extends IfcProduct> getProductType()
    {
        return getProduct().getClass();
    }

    @Override
    public final GlobalSettings getSettings()
    {
        return getEngine().getSettings();
    }

    protected final Stroke getStroke()
    {
        return stroke;
    }

    protected GeometryInfo makeMesh() throws InvalidMeshException
    {        
		parser.parse();
    	GeometryInfo mesh = org.bimserver.cobie.graphics3d.Utils.makeMesh(parser.getVertices(), parser.getNormals());   	
        Utils.transformMesh(parser.getTransform(), mesh);
        return mesh;
    }

    protected final void setColor(Color3f color)
    {
        this.color = color;
    }

    protected final void setStroke(Stroke stroke)
    {
        this.stroke = stroke;
    }
    
    protected final Parser getParser()
    {
    	return parser;
    }
}
