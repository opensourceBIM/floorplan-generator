package org.erdc.cobie.graphics;

import javax.vecmath.Color3f;

import org.bimserver.models.ifc2x3tc1.IfcProduct;
import org.erdc.cobie.graphics2d.Stroke;

public class EntitySetting
{
    private final Class<? extends IfcProduct> product;
    private final String friendlyName;
    private final Color3f fillColor;
    private final Stroke stroke;

    public EntitySetting(Class<? extends IfcProduct> product, String friendlyName, Color3f fillColor, Stroke stroke)
    {
        this.product = product;
        this.friendlyName = friendlyName;
        this.fillColor = fillColor;
        this.stroke = stroke;
    }

    public final Color3f getFillColor()
    {
        return fillColor;
    }

    public final String getFriendlyName()
    {
        return friendlyName;
    }
    
    public final Class<? extends IfcProduct> getProductType()
    {
        return product;
    }

    public final Stroke getStroke()
    {
        return stroke;
    }
}
