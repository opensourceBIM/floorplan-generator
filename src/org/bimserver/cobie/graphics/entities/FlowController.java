package org.bimserver.cobie.graphics.entities;

import org.bimserver.cobie.graphics.Engine;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcFlowController;

public final class FlowController extends RenderableIfcProduct
{
    protected FlowController(IfcModelInterface model, Engine engine, IfcFlowController flowController)
    {
        super(model, engine, flowController);
    }
}
