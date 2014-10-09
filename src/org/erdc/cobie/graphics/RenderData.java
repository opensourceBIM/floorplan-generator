package org.erdc.cobie.graphics;

import org.erdc.cobie.graphics.entities.RenderableIfcProduct;
import org.erdc.cobie.graphics.settings.GlobalSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.j3d.utils.geometry.GeometryInfo;

public abstract class RenderData implements SettingsUser
{
    private final GlobalSettings settings;
    private final RenderableIfcProduct product;
    private final GeometryInfo mesh;
    
    /**
     * NOTE: It really isn't proper for this class to have a reference to it's parent, but it
     * is necessary b/c some "fudging" needs to occur when rendering based on the product type.
     * 
     * @param settings
     * @param product
     * @param mesh
     */
    protected RenderData(GlobalSettings settings, RenderableIfcProduct product)
    {
        this.settings = settings;
        this.product = product;
        this.mesh = getProduct().getMesh();
    }
    
    public abstract double getBottom();    
    public abstract double getHeight();
    public abstract double getLeft();
    
    @Override
    public final Logger getLogger()
    {
        return LoggerFactory.getLogger(getClass());
    }
    
    public final GeometryInfo getMesh()
    {
        return mesh;
    }
    
    public final RenderableIfcProduct getProduct()
    {
        return product;
    }
    
    public abstract double getRight();
    
    @Override
    public final GlobalSettings getSettings()
    {
        return settings;
    }
    
    public abstract double getTop();    
    public abstract double getWidth();
}
