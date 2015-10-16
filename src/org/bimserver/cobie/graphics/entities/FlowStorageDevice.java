package org.bimserver.cobie.graphics.entities;

import org.bimserver.cobie.graphics.Engine;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcFlowStorageDevice;

public final class FlowStorageDevice extends RenderableIfcProduct
{
    public FlowStorageDevice(IfcModelInterface model, Engine engine, IfcFlowStorageDevice flowStorageDevice)
    {
        super(model, engine, flowStorageDevice);
    }
}
