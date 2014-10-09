package org.erdc.cobie.graphics;

import org.erdc.cobie.graphics.entities.Floor;
import org.erdc.cobie.graphics.settings.GlobalSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FloorArranger implements EngineUser
{    
    private final Engine engine;
    private final Floor floor;
    
    protected FloorArranger(Engine engine, Floor floor)
    {
        this.engine = engine;
        this.floor = floor;
    }
    
    public abstract void arrange();
    
    protected final Floor getFloor()
    {
        return floor;
    }
    
    @Override
    public final Engine getEngine()
    {
        return engine;
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
