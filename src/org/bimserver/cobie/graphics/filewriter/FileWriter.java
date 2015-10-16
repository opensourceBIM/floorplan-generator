package org.bimserver.cobie.graphics.filewriter;

import java.io.IOException;

import org.bimserver.cobie.graphics.SettingsUser;
import org.bimserver.cobie.graphics.settings.GlobalSettings;
import org.bimserver.plugins.VirtualFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FileWriter implements SettingsUser
{
    private final GlobalSettings settings;
    
    protected FileWriter(GlobalSettings settings)
    {
        this.settings = settings;
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

    public abstract void write() throws IOException;
    public abstract void write(VirtualFile virtualFile) throws IOException;
}
