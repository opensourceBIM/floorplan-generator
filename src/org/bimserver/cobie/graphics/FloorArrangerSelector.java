package org.bimserver.cobie.graphics;

import org.bimserver.cobie.graphics.entities.Floor;
import org.bimserver.cobie.graphics.settings.GlobalSettings;
import org.bimserver.cobie.graphics2d.FloorArranger2D;
import org.bimserver.cobie.graphics3d.FloorArranger3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FloorArrangerSelector implements Selector<FloorArranger2D, FloorArranger3D>, EngineUser
{
    private final Engine engine;
    private final Floor floor;
    private FloorArranger2D floorArranger2D;
    private FloorArranger3D floorArranger3D;

    public FloorArrangerSelector(Engine engine, Floor floor)
    {
        this.engine = engine;
        this.floor = floor;
    }

    @Override
    public FloorArranger2D for2D()
    {
        if (floorArranger2D == null)
        {
            floorArranger2D = new FloorArranger2D(getEngine(), getFloor());
        }

        return floorArranger2D;
    }

    @Override
    public FloorArranger3D for3D()
    {
        if (floorArranger3D == null)
        {
            floorArranger3D = new FloorArranger3D(getEngine(), getFloor());
        }

        return floorArranger3D;
    }

    @Override
    public final Engine getEngine()
    {
        return engine;
    }

    public final Floor getFloor()
    {
        return floor;
    }

    @Override
    public final Logger getLogger()
    {
        return LoggerFactory.getLogger(getClass());
    }

    @Override
    public final GlobalSettings getSettings()
    {
        return getEngine().getSettings();
    }
}
