package org.bimserver.cobie.graphics.entities;

import org.bimserver.cobie.graphics.Engine;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcFurnishingElement;

public final class Furnishing extends RenderableIfcProduct
{
    protected Furnishing(IfcModelInterface model, Engine engine, IfcFurnishingElement furnishingElement)
    {
        super(model, engine, furnishingElement);
    }
}
