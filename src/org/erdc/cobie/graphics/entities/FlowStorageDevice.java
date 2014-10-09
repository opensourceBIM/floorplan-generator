package org.erdc.cobie.graphics.entities;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcFlowStorageDevice;
import org.erdc.cobie.graphics.Engine;

public final class FlowStorageDevice extends RenderableIfcProduct
{
    public FlowStorageDevice(IfcModelInterface model, Engine engine, IfcFlowStorageDevice flowStorageDevice)
    {
        super(model, engine, flowStorageDevice);
    }
}
