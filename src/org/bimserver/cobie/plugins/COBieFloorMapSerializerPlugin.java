package org.bimserver.cobie.plugins;

import java.util.Set;

import org.bimserver.cobie.graphics.serializers.COBieFloorMapSerializer;
import org.bimserver.cobie.graphics.string.Default;
import org.bimserver.cobie.shared.LoggerUser;
import org.bimserver.emf.Schema;
import org.bimserver.plugins.PluginConfiguration;
import org.bimserver.plugins.PluginContext;
import org.bimserver.plugins.PluginException;
import org.bimserver.plugins.PluginManager;
import org.bimserver.plugins.serializers.AbstractSerializerPlugin;
import org.bimserver.plugins.serializers.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class COBieFloorMapSerializerPlugin extends AbstractSerializerPlugin implements LoggerUser
{
    private boolean initialized;
	private PluginContext pluginContext;

    @Override
    public Serializer createSerializer(PluginConfiguration plugin)
    {
        return new COBieFloorMapSerializer(pluginContext.getRootPath());
    }
    
    @Override
    public final String getDefaultContentType()
    {
        return Default.DEFAULT_CONTENT_TYPE.toString();
    }

    @Override
    public final String getDefaultExtension()
    {
        return Default.DEFAULT_EXTENSION.toString();
    }

    @Override
    public final String getDefaultName()
    {
        return Default.NAME.toString();
    }

    @Override
    public final String getDescription()
    {
        return Default.DESCRIPTION.toString();
    }

    @Override
    public final Logger getLogger()
    {
        return LoggerFactory.getLogger(getClass());
    }

    @Override
    public final String getVersion()
    {
        return Default.VERSION.toString();
    }

    @Override
    public void init(PluginManager pluginManager) throws PluginException
    {
        pluginContext = pluginManager.getPluginContext(this);
        pluginManager.requireSchemaDefinition(Schema.IFC2X3TC1.name().toLowerCase());
        initialized = true;
    }

	@Override
    public boolean isInitialized()
    {
        return initialized;
    }

    @Override
    public boolean needsGeometry()
    {
        return false;
    }

	@Override
	public Set<Schema> getSupportedSchemas() 
	{
		return Schema.IFC2X3TC1.toSet();
	}
}
