package org.bimserver.cobie.graphics.entities;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcBuildingStorey;
import org.bimserver.cobie.graphics.Engine;

public final class Floor extends RenderableSpatialStructureBranch
{
    public Floor(IfcModelInterface model, Engine engine, IfcBuildingStorey storey)
    {
        super(model, engine, storey);
    }
}
