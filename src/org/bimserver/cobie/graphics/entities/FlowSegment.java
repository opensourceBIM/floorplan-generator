package org.bimserver.cobie.graphics.entities;

import org.bimserver.cobie.graphics.Engine;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcFlowSegment;

public final class FlowSegment extends RenderableIfcProduct
{
    protected FlowSegment(IfcModelInterface model, Engine engine, IfcFlowSegment flowSegment)
    {
        super(model, engine, flowSegment);
    }
}
