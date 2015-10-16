package org.bimserver.cobie.graphics.entities;

import org.bimserver.cobie.graphics.Engine;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcWall;

public final class Wall extends RenderableIfcProduct
{
    public Wall(IfcModelInterface model, Engine engine, IfcWall wall)
    {
        super(model, engine, wall);
    }
}
