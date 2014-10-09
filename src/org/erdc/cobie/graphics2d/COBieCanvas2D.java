package org.erdc.cobie.graphics2d;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.vecmath.Color3f;

import org.erdc.cobie.graphics.COBieCanvas;
import org.erdc.cobie.graphics.entities.Space;
import org.erdc.cobie.graphics.settings.GlobalSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class COBieCanvas2D extends Canvas implements COBieCanvas
{
    private static final long serialVersionUID = 1L;
    public static final int MINIMUM_DIMENSION = 32;

    private int height;

    private int width;
    Dimension minimumSize = new Dimension(COBieCanvas2D.MINIMUM_DIMENSION, COBieCanvas2D.MINIMUM_DIMENSION);

    private final GlobalSettings settings;
    private List<RenderData2D> entities = new ArrayList<RenderData2D>();

    public COBieCanvas2D(GlobalSettings settings)
    {
        this.settings = settings;
        this.initSize();
    }

    private static void initAntiAliasing(Graphics2D graphics)
    {
        HashMap<RenderingHints.Key, Object> renderingHints = new HashMap<RenderingHints.Key, Object>();
        renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.addRenderingHints(renderingHints);
    }

    private static boolean isSpecialCase(RenderData2D entity)
    {
        return (entity.getProduct() instanceof Space) || entity.paintsInReverseOrder();
    }

    public void add(RenderData2D entity)
    {
        getEntities().add(entity);
    }

    public void clear()
    {
        getEntities().clear();
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

    public List<RenderData2D> getEntities()
    {
        return entities;
    }

    @Override
    public int getHeight()
    {
        return height;
    }

    @Override
    public final Logger getLogger()
    {
        return LoggerFactory.getLogger(getClass());
    }

    @Override
    public GlobalSettings getSettings()
    {
        return settings;
    }

    @Override
    public int getWidth()
    {
        return width;
    }

    private void initSize()
    {
        width = getSettings().getRenderSettings().getDimension().width;
        height = getSettings().getRenderSettings().getDimension().height;
    }

    @Override
    public void paint(Graphics g)
    {
        Graphics2D graphics = (Graphics2D)g;

        if (getSettings().getRenderSettings().getAntialias())
        {
            COBieCanvas2D.initAntiAliasing(graphics);
        }

        paintSpaceLayer(graphics);
        paintInReverseOrder(graphics);
        paintStrokeLayer(graphics);
        paintFillLayer(graphics);
        graphics.finalize();
    }

    private void paintFillLayer(Graphics2D graphics)
    {
        for (RenderData2D entity : getEntities())
        {
            if (!COBieCanvas2D.isSpecialCase(entity))
            {
                Color3f color = entity.getColor();

                for (Triangle triangle : entity.getTransformedPolygon().toTriangles())
                {
                    graphics.setColor(new Color(color.x, color.y, color.z));
                    graphics.fill(triangle.getPath());
                }
            }
        }
    }

    private void paintInReverseOrder(Graphics2D graphics)
    {
        for (RenderData2D entity : getEntities())
        {
            if (entity.paintsInReverseOrder())
            {
                Color3f color = entity.getColor();
                Stroke stroke = entity.getStroke();

                for (Triangle triangle : entity.getTransformedPolygon().toTriangles())
                {
                    graphics.setColor(new Color(color.x, color.y, color.z));
                    graphics.fill(triangle.getPath());

                    if (stroke.isVisible())
                    {
                        graphics.setColor(new Color(color.x, color.y, color.z));
                        graphics.fill(triangle.getPath());
                        graphics.setStroke(new BasicStroke(stroke.width, stroke.capMode.toInt(), stroke.joinMode.toInt()));
                        graphics.setColor(new Color(stroke.color.x, stroke.color.y, stroke.color.z));
                        graphics.draw(triangle.getPath());
                    }
                }
            }
        }
    }

    private void paintSpaceLayer(Graphics2D graphics)
    {
        for (RenderData2D entity : getEntities())
        {
            if (entity.getProduct() instanceof Space)
            {
                Color3f color = entity.getColor();
                Stroke stroke = entity.getStroke();

                for (Triangle triangle : entity.getTransformedPolygon().toTriangles())
                {
                    graphics.setColor(new Color(color.x, color.y, color.z));
                    graphics.fill(triangle.getPath());

                    if (stroke.isVisible())
                    {
                        graphics.setStroke(new BasicStroke(stroke.width, stroke.capMode.toInt(), stroke.joinMode.toInt()));
                        graphics.setColor(new Color(stroke.color.x, stroke.color.y, stroke.color.z));
                        graphics.draw(triangle.getPath());
                    }
                }
            }
        }
    }

    private void paintStrokeLayer(Graphics2D graphics)
    {
        for (RenderData2D entity : getEntities())
        {
            if (!COBieCanvas2D.isSpecialCase(entity))
            {
                Stroke stroke = entity.getStroke();

                for (Triangle triangle : entity.getTransformedPolygon().toTriangles())
                {
                    if (stroke.isVisible())
                    {
                        graphics.setStroke(new BasicStroke(stroke.width, stroke.capMode.toInt(), stroke.joinMode.toInt()));
                        graphics.setColor(new Color(stroke.color.x, stroke.color.y, stroke.color.z));
                        graphics.draw(triangle.getPath());
                    }
                }
            }
        }
    }

    @Override
    public BufferedImage write()
    {
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.paint(image.createGraphics());

        return image;
    }
}
