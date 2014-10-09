package org.erdc.cobie.graphics.entities;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcStair;
import org.erdc.cobie.graphics.Engine;

public final class Stair extends RenderableBranch
{
    protected Stair(IfcModelInterface model, Engine engine, IfcStair stair)
    {
        super(model, engine, stair);
    }
}
