package org.erdc.cobie.graphics2d;

import java.awt.Dimension;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import org.erdc.cobie.graphics.Engine;
import org.erdc.cobie.graphics.FloorArranger;
import org.erdc.cobie.graphics.For2D;
import org.erdc.cobie.graphics.entities.Floor;
import org.erdc.cobie.graphics.entities.RenderableIfcProduct;

public class FloorArranger2D extends FloorArranger implements For2D
{
    private static final boolean DEFAULT_FLIP_Y = true;
    private static final boolean DEFAULT_FLIP_X = false;
    private static final boolean DEFAULT_INCLUDE_MARGIN = true;

    private Vector2d originOffset;
    private Vector2d scaling;

    public FloorArranger2D(Engine engine, Floor floor)
    {
        super(engine, floor);
    }

    /**
     * NOTE: This method assumes the y-axis is negative (the top left corner of
     * the frame/image is (0,0).
     */
    @Override
    public void arrange()
    {
        boolean includeMargin = DEFAULT_INCLUDE_MARGIN;
        boolean flipX = DEFAULT_FLIP_X;
        boolean flipY = DEFAULT_FLIP_Y;

        // NOTE:
        // In order to fit a floor within the canvas, it must be moved to it's
        // desired location, moved to the origin, scaled, then the move to the 
        // origin undone. Also note that transforms must be applied in reverse 
        // order due to how transforms are concatenated!
        invertCenterOnOrigin();
        scale(includeMargin, flipX, flipY);
        centerOnOrigin();
        centerOnCanvas(includeMargin, flipX, flipY);
    }

    private void centerOnCanvas(boolean includeMargin, boolean flipX, boolean flipY)
    {
        Dimension canvasCenter = getEngine().getCanvas().getCenter();
        Point2d floorCenter = getFloor().for2D().getCenter();
        Vector2d scaling = getScaling(includeMargin, flipX, flipY);

        double tx = (canvasCenter.getWidth() - floorCenter.x) / scaling.x;
        double ty = (canvasCenter.getHeight() - floorCenter.y) / scaling.y;

        for (RenderableIfcProduct renderableEntity : getFloor().getTree())
        {
            renderableEntity.for2D().move(tx, ty);
        }
    }

    private void centerOnOrigin()
    {
        for (RenderableIfcProduct renderableEntity : getFloor().getTree())
        {
            renderableEntity.for2D().move(getOffsetToOrigin().x, getOffsetToOrigin().y);
        }
    }

    private Dimension getCanvasDimensions(boolean includeMargin)
    {
        Dimension canvasDimensions = getEngine().getCanvas().getDimensions();

        if (includeMargin)
        {
            int margin = getSettings().getRenderSettings().getBothMargins();

            canvasDimensions.width -= margin;
            canvasDimensions.height -= margin;
        }

        return canvasDimensions;
    }

    private Dimension getCanvasOrigin(boolean includeMargin)
    {
        Dimension canvasOrigin = new Dimension(0, 0);

        if (includeMargin)
        {
            canvasOrigin.width += getSettings().getRenderSettings().getMargin();
            canvasOrigin.height += getSettings().getRenderSettings().getMargin();
        }

        return canvasOrigin;
    }

    private Vector2d getOffsetToOrigin()
    {
        if (originOffset == null)
        {
            Dimension canvasCenter = getCanvasOrigin(false);
            Point2d floorCenter = getFloor().for2D().getCenter();

            double tx = canvasCenter.getWidth() - floorCenter.x;
            double ty = canvasCenter.getHeight() - floorCenter.y;

            originOffset = new Vector2d(tx, ty);
        }

        return originOffset;
    }

    private Vector2d getScaling(boolean includeMargin, boolean flipX, boolean flipY)
    {
        if (scaling == null)
        {
            double floorWidth = getFloor().for2D().getWidth();
            double floorHeight = getFloor().for2D().getHeight();

            double widthRatio = getCanvasDimensions(includeMargin).getWidth() / floorWidth;
            double heightRatio = getCanvasDimensions(includeMargin).getHeight() / floorHeight;

            double scale = (widthRatio <= heightRatio) ? widthRatio : heightRatio;
            double scaleX = flipX ? -scale : scale;
            double scaleY = flipY ? -scale : scale;
            
            scaling = new Vector2d(scaleX, scaleY);
        }

        return scaling;
    }

    private void invertCenterOnOrigin()
    {
        Vector2d originOffset = getOffsetToOrigin();

        for (RenderableIfcProduct renderableEntity : getFloor().getTree())
        {
            renderableEntity.for2D().move(-originOffset.x, -originOffset.y);
        }
    }

    private void scale(boolean includeMargin, boolean flipX, boolean flipY)
    {
        Vector2d scaling = getScaling(includeMargin, flipX, flipY);

        for (RenderableIfcProduct renderableEntity : getFloor().getTree())
        {
            renderableEntity.for2D().scale(scaling.x, scaling.y);
        }
    }
}
