package org.bimserver.cobie.graphics;

import org.bimserver.cobie.graphics.settings.GlobalSettings;
import org.bimserver.cobie.graphics2d.Engine2D;
import org.bimserver.cobie.graphics3d.Engine3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EngineSelector implements Selector<Engine2D, Engine3D>, SettingsUser
{
    private final GlobalSettings settings;
    private Engine2D engine2D;
    private Engine3D engine3D;

    public EngineSelector(GlobalSettings settings)
    {
        this.settings = settings;
    }

    @Override
    public Engine2D for2D()
    {
        if (engine2D == null)
        {
            engine2D = new Engine2D(getSettings());
        }

        return engine2D;
    }

    @Override
    public Engine3D for3D()
    {
        if (engine3D == null)
        {
            engine3D = new Engine3D(getSettings());
        }

        return engine3D;
    }

    @Override
    public final Logger getLogger()
    {
        return LoggerFactory.getLogger(getClass());
    }

    @Override
    public final GlobalSettings getSettings()
    {
        return settings;
    }
}
