package org.erdc.cobie.graphics2d;

import org.erdc.cobie.graphics.Engine;
import org.erdc.cobie.graphics.For2D;
import org.erdc.cobie.graphics.RenderData;
import org.erdc.cobie.graphics.settings.GlobalSettings;
import org.erdc.cobie.graphics.string.Default;

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
