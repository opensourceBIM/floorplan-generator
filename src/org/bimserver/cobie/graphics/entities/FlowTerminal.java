package org.bimserver.cobie.graphics.entities;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcFlowTerminal;
import org.bimserver.cobie.graphics.Engine;

public final class FlowTerminal extends RenderableIfcProduct
{
    protected FlowTerminal(IfcModelInterface model, Engine engine, IfcFlowTerminal flowTerminal)
    {
        super(model, engine, flowTerminal);
    }
}
