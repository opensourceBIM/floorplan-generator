package org.erdc.cobie.graphics.entities;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcCovering;
import org.erdc.cobie.graphics.Engine;

public final class Covering extends RenderableIfcProduct
{
    public Covering(IfcModelInterface model, Engine engine, IfcCovering covering)
    {
        super(model, engine, covering);
    }
}
