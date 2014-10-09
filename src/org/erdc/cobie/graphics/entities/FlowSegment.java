package org.erdc.cobie.graphics.entities;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcFlowSegment;
import org.erdc.cobie.graphics.Engine;

public final class FlowSegment extends RenderableIfcProduct
{
    protected FlowSegment(IfcModelInterface model, Engine engine, IfcFlowSegment flowSegment)
    {
        super(model, engine, flowSegment);
    }
}
