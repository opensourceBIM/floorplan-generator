package org.bimserver.cobie.graphics3d;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Screen3D;

import org.bimserver.cobie.graphics.COBieCanvas;
import org.bimserver.cobie.graphics.RenderMode;
import org.bimserver.cobie.graphics.settings.GlobalSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class COBieCanvas3D extends Canvas3D implements COBieCanvas
{
    private static final Logger LOGGER = LoggerFactory.getLogger(COBieCanvas3D.class);

    private static final long serialVersionUID = 1L;
    private final GlobalSettings settings;

    public COBieCanvas3D(GraphicsConfiguration graphicsConfiguration, GlobalSettings settings)
    {
        super(graphicsConfiguration, settings.getRenderSettings().getRenderMode().contains(RenderMode.RENDER_TO_FILE));
        this.settings = settings;

        if (getSettings().getRenderSettings().getRenderMode().contains(RenderMode.RENDER_TO_FILE))
        {
            initScreen();
        }
    }

    @Override
    public Dimension getCenter()
    {
        int horizontalCenter = getWidth() / 2;
        int verticalCenter = getHeight() / 2;

        return new Dimension(horizontalCenter, verticalCenter);
    }

    @Override
    public Dimension getDimensions()
    {
        return new Dimension(getWidth(), getHeight());
    }

    @Override
    public int getHeight()
    {
        return getSettings().getRenderSettings().getDimension().height;
    }

    @Override
    public final Logger getLogger()
    {
        return LOGGER;
    }

    @Override
    public GlobalSettings getSettings()
    {
        return settings;
    }

    @Override
    public int getWidth()
    {
        return getSettings().getRenderSettings().getDimension().width;
    }

    private void initScreen()
    {
        Screen3D screen = getScreen3D();
        screen.setSize(getWidth(), getHeight());
        screen.setPhysicalScreenWidth(getSettings().getRenderSettings3D().getModelToWorldConversion() * getWidth());
        screen.setPhysicalScreenHeight(getSettings().getRenderSettings3D().getModelToWorldConversion() * getHeight());
    }

    @Override
    public BufferedImage write()
    {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        ImageComponent2D buffer = new ImageComponent2D(ImageComponent.FORMAT_RGB, image);
        buffer.setCapability(ImageComponent.ALLOW_FORMAT_READ);

        setOffScreenBuffer(buffer);
        renderOffScreenBuffer();
        waitForOffScreenRendering();

        image = getOffScreenBuffer().getImage();

        // Release the buffer.
        setOffScreenBuffer(null);

        return image;
    }
}
