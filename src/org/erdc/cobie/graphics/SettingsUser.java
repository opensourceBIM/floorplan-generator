package org.erdc.cobie.graphics;

import org.erdc.cobie.graphics.settings.GlobalSettings;
import org.erdc.cobie.shared.LoggerUser;

public interface SettingsUser extends LoggerUser
{
    GlobalSettings getSettings();
}
