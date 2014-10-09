package org.erdc.cobie.graphics;


/**
 * EngineUser defines a class that requires access to an {@code Engine} reference.
 * 
 * {@code EngineUser} extends {@code SettingsUser} mostly for convenience, because
 * any class that uses an {@code Engine} is likely to use {@code Settings} as well. 
 * 
 * It is recommended that any class that implements this interface implement its
 * {@code getSettings()} method so that it returns the {@code Settings} reference
 * contained within the {@code Engine} reference returned by {@code getEngine()}.
 */
public interface EngineUser extends SettingsUser
{
    Engine getEngine();
}
