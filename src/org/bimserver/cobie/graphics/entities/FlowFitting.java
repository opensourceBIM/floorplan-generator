package org.bimserver.cobie.graphics.entities;

import org.bimserver.cobie.graphics.Engine;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcFlowFitting;

public final class FlowFitting extends RenderableIfcProduct
{
    public FlowFitting(IfcModelInterface model, Engine engine, IfcFlowFitting fitting)
    {
        super(model, engine, fitting);
    }
}
