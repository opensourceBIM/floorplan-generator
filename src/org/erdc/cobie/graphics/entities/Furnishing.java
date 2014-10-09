package org.erdc.cobie.graphics.entities;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcFurnishingElement;
import org.erdc.cobie.graphics.Engine;

public final class Furnishing extends RenderableIfcProduct
{
    protected Furnishing(IfcModelInterface model, Engine engine, IfcFurnishingElement furnishingElement)
    {
        super(model, engine, furnishingElement);
    }
}
