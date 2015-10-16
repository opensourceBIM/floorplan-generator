package org.bimserver.cobie.graphics.entities;

import org.bimserver.cobie.graphics.Engine;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcBuildingStorey;

public final class Floor extends RenderableSpatialStructureBranch
{
    public Floor(IfcModelInterface model, Engine engine, IfcBuildingStorey storey)
    {
        super(model, engine, storey);
    }
}
