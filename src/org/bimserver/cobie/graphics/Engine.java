package org.bimserver.cobie.graphics;

import java.awt.Canvas;
import java.awt.GraphicsConfiguration;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumSet;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.bimserver.cobie.graphics.settings.GlobalSettings;
import org.bimserver.cobie.graphics.settings.RenderSettings;
import org.bimserver.cobie.graphics.string.Default;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The COBie graphics engine.
 * 
 * Engine allows the rendering of renderable COBie objects to a {@code COBieCanvas}, to a file, or to an archive.
 */
public abstract class Engine implements SettingsUser
{
    private final GlobalSettings settings;
    private COBieCanvas canvas;
    private JFrame frame;

    /**
     * Constructs an {@code Engine}.
     * 
     * @param settings Contains the Engine's {@code GlobalSettings}.
     */
    protected Engine(GlobalSettings settings)
    {
        this.settings = settings;
        verifySettings();
        initCanvas();
    }

    /**
     * Adds the supplied {@code RenderData} to the canvas.
     * 
     * @param renderData The {@code RenderData} to be added.
     */
    public abstract void addToCanvas(RenderData renderData);

    /**
     * Clears the canvas.
     */
    public abstract void clearCanvas();

    /**
     * Finalizes a {@code COBieEngine}'s canvas prior to rendering. By default, this 
     * method does nothing, but it can be overridden by sub-classes if necessary.
     */
    protected void finalizeCanvas()
    {
        // No code needed.
    }

    /**
     * Gets this Engine's {@code COBieCanvas}.
     * 
     * @return This Engine's {@code COBieCanvas}.
     */
    public final COBieCanvas getCanvas()
    {
        return canvas;
    }

    /**
     * Gets this Engine's Java Swing window.
     * 
     * @return This Engine's {@code JFrame}.
     */
    // TODO This method needs to take it's title bar and location into account.
    protected final JFrame getFrame()
    {
        if (frame == null)
        {
            frame = new JFrame(getGraphics());
            
            frame.add((Canvas)getCanvas());
            frame.setSize(getSettings().getRenderSettings().getDimension().width, getSettings().getRenderSettings().getDimension().height);
        }

        return frame;
    }

    /**
     * Gets this Engine's {@code GraphicsConfiguration}.
     * 
     * @return This Engine's {@code GraphicsConfiguration}.
     */
    // This doesn't need to be static b/c it is intended to be overridden.
    @SuppressWarnings("static-method")
    protected GraphicsConfiguration getGraphics()
    {
        // This will result in the default being used.
        return null;
    }

    /**
     * Gets this Engine's {@code Logger}.
     * 
     * @return This Engine's {@code Logger}.
     */
    @Override
    public final Logger getLogger()
    {
        return LoggerFactory.getLogger(getClass());
    }

    /**
     * Gets this Engine's {@code RenderSettings}.
     * 
     * @return This Engine's {@code RenderSettings}.
     */
    protected final RenderSettings getRenderSettings()
    {
        return getSettings().getRenderSettings();
    }

    /**
     * Gets this Engine's {@code GlobalSettings}.
     * 
     * @return This Engine's {@code GlobalSettings}.
     */
    @Override
    public final GlobalSettings getSettings()
    {
        return settings;
    }

    /**
     * Initializes this Engine's canvas.
     */
    protected abstract void initCanvas();

    /**
     * Renders this Engine's canvas to an archive.
     * 
     * @param path
     * @param zipper
     * @throws IOException
     */
//    public byte[] renderToArchive(String path) throws IOException
//    {
//    	VirtualFile file = virtualFile.createFile(getSettings().getOutputSettings().getImageInfo().path + path);
//    	file.setData(renderToFile(path));
//    }

    public byte[] renderToFile(String path)
    {
        try
        {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
			ImageIO.write(getCanvas().write(), getSettings().getOutputSettings().getImageInfo().format, output);
            getLogger().info(Default.FILE_RENDER_SUCCEEDED.format(path));
            return output.toByteArray();
        }

        catch (IOException e)
        {
            getLogger().error(Default.FILE_RENDER_FAILED.format(path));
        }

        return null;
    }

    public void renderToFrame()
    {
        getFrame().setVisible(true);
        ((Canvas)getCanvas()).repaint();

        getLogger().info(Default.SCENE_RENDERED.toString());
    }

    public final void setCanvas(COBieCanvas canvas)
    {
        this.canvas = canvas;
    }
    
    /**
     * Verifies a {@codeCOBieEngine}'s settings. By default, this method makes
     * sure 2D and 3D render modes are not both set, but it can be overridden by
     * sub-classes if necessary.
     */
    protected void verifySettings()
    {
        EnumSet<RenderMode> renderMode = getRenderSettings().getRenderMode();

        if (renderMode.contains(RenderMode.RENDER_2D) && renderMode.contains(RenderMode.RENDER_3D))
        {
            throw new IllegalArgumentException(Default.RENDER_MODE_FAILED.toString());
        }

        if (renderMode.contains(RenderMode.RENDER_TO_FILE) && renderMode.contains(RenderMode.RENDER_TO_FRAME))
        {
            throw new IllegalArgumentException(Default.RENDER_MODE_TARGET_FAILED.toString());
        }
    }
}
