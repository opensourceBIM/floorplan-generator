package org.bimserver.cobie.graphics.entities;

import org.bimserver.cobie.graphics.Engine;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcStairFlight;

public final class StairFlight extends RenderableIfcProduct
{
    public StairFlight(IfcModelInterface model, Engine engine, IfcStairFlight stairFlight)
    {
        super(model, engine, stairFlight);
    }
}
