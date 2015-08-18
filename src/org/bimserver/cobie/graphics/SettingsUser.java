package org.bimserver.cobie.graphics;

import org.bimserver.cobie.graphics.settings.GlobalSettings;
import org.bimserver.cobie.shared.LoggerUser;

public interface SettingsUser extends LoggerUser
{
    GlobalSettings getSettings();
}
