package org.bimserver.cobie.graphics.entities;

import org.bimserver.cobie.graphics.Engine;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcWindow;

public final class Window extends RenderableIfcProduct
{
    protected Window(IfcModelInterface model, Engine engine, IfcWindow window)
    {
        super(model, engine, window);
    }
}
