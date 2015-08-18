package org.bimserver.cobie.graphics.entities;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcWall;
import org.bimserver.cobie.graphics.Engine;

public final class Wall extends RenderableIfcProduct
{
    public Wall(IfcModelInterface model, Engine engine, IfcWall wall)
    {
        super(model, engine, wall);
    }
}
