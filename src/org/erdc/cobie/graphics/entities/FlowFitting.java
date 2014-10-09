package org.erdc.cobie.graphics.entities;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcFlowFitting;
import org.erdc.cobie.graphics.Engine;

public final class FlowFitting extends RenderableIfcProduct
{
    public FlowFitting(IfcModelInterface model, Engine engine, IfcFlowFitting fitting)
    {
        super(model, engine, fitting);
    }
}
