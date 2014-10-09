package org.erdc.cobie.graphics.entities;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcFlowTerminal;
import org.erdc.cobie.graphics.Engine;

public final class FlowTerminal extends RenderableIfcProduct
{
    protected FlowTerminal(IfcModelInterface model, Engine engine, IfcFlowTerminal flowTerminal)
    {
        super(model, engine, flowTerminal);
    }
}
