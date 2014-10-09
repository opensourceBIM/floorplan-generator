package org.erdc.cobie.graphics.entities;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcDoor;
import org.erdc.cobie.graphics.Engine;

public final class Door extends RenderableIfcProduct
{    
    public Door(IfcModelInterface model, Engine engine, IfcDoor door)
    {
        super(model, engine, door);
    }
}
