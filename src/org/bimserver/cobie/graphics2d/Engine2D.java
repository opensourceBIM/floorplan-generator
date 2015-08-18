package org.bimserver.cobie.graphics2d;

import org.bimserver.cobie.graphics.Engine;
import org.bimserver.cobie.graphics.For2D;
import org.bimserver.cobie.graphics.RenderData;
import org.bimserver.cobie.graphics.settings.GlobalSettings;
import org.bimserver.cobie.graphics.string.Default;

public class Engine2D extends Engine implements For2D
{
    public Engine2D(GlobalSettings settings)
    {
        super(settings);
    }

    @Override
    public void addToCanvas(RenderData entity)
    {
        if (entity instanceof RenderData2D)
        {
            ((COBieCanvas2D)getCanvas()).add((RenderData2D)entity);
        }

        else
        {
            throw new IllegalArgumentException(Default.INVALID_CANVAS_ADD.toString());
        }
    }

    @Override
    public void clearCanvas()
    {
        ((COBieCanvas2D)getCanvas()).clear();
    }

    @Override
    protected void initCanvas()
    {
        setCanvas(new COBieCanvas2D(getSettings()));
    }
}
