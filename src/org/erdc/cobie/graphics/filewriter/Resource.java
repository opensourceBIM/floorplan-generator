package org.erdc.cobie.graphics.filewriter;

import java.net.URI;
import java.net.URISyntaxException;

public enum Resource
{
    AREA("templates/area.template"),
    FLOORMAP("templates/floor.template"), 
    FLOORMAP_DEMO("templates/facility.template"), 
    INDEX("templates/index.template"),
    KEY("templates/key.template"), 
    KEY_INFO("templates/keyInfo.template"),
    
    // Just need to copy these folders and their contents...
    HTML("html"),
    SCRIPTS("scripts"),
    STYLES("styles");

    private URI relativePath;

    private Resource(String relativePath)
    {
        try
        {
            this.relativePath = new URI(relativePath);
        }

        catch (URISyntaxException e)
        {
            this.relativePath = null;
        }
    }

    private Resource(URI relativePath)
    {
        this.relativePath = relativePath;
    }

    public final URI getRelativePath()
    {
        return relativePath;
    }
}
