package org.bimserver.cobie.graphics.entities;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcEnergyConversionDevice;
import org.bimserver.cobie.graphics.Engine;

public final class EnergyConversionDevice extends RenderableIfcProduct
{
    protected EnergyConversionDevice(IfcModelInterface model, Engine engine, IfcEnergyConversionDevice energyConversionDevice)
    {
        super(model, engine, energyConversionDevice);
    }
}
