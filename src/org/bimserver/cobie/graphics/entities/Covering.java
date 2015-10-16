package org.bimserver.cobie.graphics.entities;

import org.bimserver.cobie.graphics.Engine;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcCovering;

public final class Covering extends RenderableIfcProduct
{
    public Covering(IfcModelInterface model, Engine engine, IfcCovering covering)
    {
        super(model, engine, covering);
    }
}
