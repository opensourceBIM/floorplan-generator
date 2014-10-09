package org.erdc.cobie.plugins;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.bimserver.plugins.PluginConfiguration;
import org.bimserver.plugins.PluginException;
import org.bimserver.plugins.PluginManager;
import org.bimserver.plugins.serializers.AbstractSerializerPlugin;
import org.bimserver.plugins.serializers.Serializer;
import org.erdc.cobie.graphics.filewriter.Resource;
import org.erdc.cobie.graphics.serializers.COBieFloorMapSerializer;
import org.erdc.cobie.graphics.string.Default;
import org.erdc.cobie.shared.LoggerUser;
import org.erdc.cobie.shared.bimserver.ResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class COBieFloorMapSerializerPlugin extends AbstractSerializerPlugin implements LoggerUser
{
    private boolean initialized;
    private PluginManager pluginManager;
    private ResourceManager resourceManager;
    
    @Override
    public Serializer createSerializer(PluginConfiguration plugin)
    {
        return new COBieFloorMapSerializer(resourceManager.getResourceMap());
    }

    public URI getAbsolutePath(URI relativePath) throws URISyntaxException
    {
        getLogger().info(relativePath.getPath());
        URI absolutePath = null;

        try
        {
            absolutePath = ResourceManager.getAbsolutePathFromFileSystem(relativePath, pluginManager.getPluginContext(this));
        }

        catch (Exception e)
        {
            absolutePath = ResourceManager.getAbsolutePathFromJar(relativePath, pluginManager.getPluginContext(this));
        }

        return absolutePath;
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
        initPluginManager(pluginManager);
        initResourceManager();
        initialized = true;
    }

    private void initPluginManager(PluginManager pluginManager) throws PluginException
    {
        this.pluginManager = pluginManager;
        pluginManager.requireSchemaDefinition();
    }

    private void initResourceManager() throws PluginException
    {
    	List<URI> runtimeDependencies = new ArrayList<URI>();

        try
        {
            for (Resource templateFile : Resource.values())
            {
                runtimeDependencies.add(templateFile.getRelativePath());
            }

            resourceManager = new ResourceManager(
                            	runtimeDependencies, 
                            	pluginManager.getTempDir().toURI(), 
                            	pluginManager.getPluginContext(this));
            
            resourceManager.copyResources();
        }

        catch (Exception e)
        {
            getLogger().error(Default.INIT_FAILED.toString() + e.getMessage());
            throw new PluginException(e);
        }
    }
    
    @Override
    public boolean isInitialized()
    {
        return initialized;
    }

    @Override
    public boolean needsGeometry()
    {
        return true;
    }
}
