package org.bimserver.cobie.plugins;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.bimserver.cobie.graphics.filewriter.Resource;
import org.bimserver.cobie.graphics.filewriter.ResourceManager;
import org.bimserver.cobie.graphics.serializers.COBieFloorMapSerializer;
import org.bimserver.cobie.graphics.string.Default;
import org.bimserver.cobie.shared.LoggerUser;
import org.bimserver.emf.Schema;
import org.bimserver.plugins.PluginConfiguration;
import org.bimserver.plugins.PluginException;
import org.bimserver.plugins.PluginManager;
import org.bimserver.plugins.serializers.AbstractSerializerPlugin;
import org.bimserver.plugins.serializers.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class COBieFloorMapSerializerPlugin extends AbstractSerializerPlugin implements LoggerUser
{
    private boolean initialized;
    private PluginManager pluginManager;
    private ResourceManager resourceManager;
    private static String TMP_FOLDER = "FloorplanGenerator";

    @Override
    public Serializer createSerializer(PluginConfiguration plugin)
    {
        return new COBieFloorMapSerializer(resourceManager.getResourceMap());
    }

    
    /**
     * This functionality is in {@link ResourceManager}
     * @param relativePath
     * @return
     * @throws URISyntaxException
     * @see ResourceManager
     */
    @Deprecated
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
        pluginManager.requireSchemaDefinition(Schema.IFC2X3TC1.name().toLowerCase());
    }

    private void initResourceManager() throws PluginException
    {
    	List<URI> runtimeDependencies = new ArrayList<URI>();
    	File tempDirectory = new File(pluginManager.getTempDir(), TMP_FOLDER);
    	//File tempDirectory = pluginManager.getTempDir();
        try
        {
            for (Resource resource : Resource.values())
            {
            	if(resource == Resource.HTML_SCRIPTS_STYLES)
            	{
            		InputStream stream = this.pluginManager.getPluginContext(this).getResourceAsInputStream(resource.getRelativePath().toString());
            		ZipInputStream zipFile = new ZipInputStream(stream);
            		while(zipFile.available() !=0)
            		{
            			ZipEntry entry = zipFile.getNextEntry();
            			if(entry != null && !entry.isDirectory())
            			runtimeDependencies.add(new URI(entry.getName()));
            		}
            		zipFile.close();
            		
            	}
            	else
            	{
            		runtimeDependencies.add(resource.getRelativePath());
            	}
                
            }
            
            resourceManager = new ResourceManager(
                            	runtimeDependencies, 
                            	tempDirectory.toURI(), 
                            	pluginManager, this);
            
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

	@Override
	public Set<Schema> getSupportedSchemas() 
	{
		return Schema.IFC2X3TC1.toSet();
	}
}
