package org.bimserver.cobie.graphics.entities;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcFlowMovingDevice;
import org.bimserver.cobie.graphics.Engine;

public final class FlowMovingDevice extends RenderableIfcProduct
{
    public FlowMovingDevice(IfcModelInterface model, Engine engine, IfcFlowMovingDevice flowMovingDevice)
    {
        super(model, engine, flowMovingDevice);
    }
}
