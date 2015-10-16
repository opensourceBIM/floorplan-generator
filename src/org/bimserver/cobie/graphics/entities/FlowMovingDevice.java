package org.bimserver.cobie.graphics.entities;

import org.bimserver.cobie.graphics.Engine;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcFlowMovingDevice;

public final class FlowMovingDevice extends RenderableIfcProduct
{
    public FlowMovingDevice(IfcModelInterface model, Engine engine, IfcFlowMovingDevice flowMovingDevice)
    {
        super(model, engine, flowMovingDevice);
    }
}
