package org.bimserver.cobie.graphics3d;

import javax.media.j3d.PolygonAttributes;

import org.bimserver.cobie.shared.utility.COBieIntConstantEnum;

public enum FillMode implements COBieIntConstantEnum
{
    FILL(PolygonAttributes.POLYGON_FILL), 
    LINE(PolygonAttributes.POLYGON_LINE), 
    POINT(PolygonAttributes.POLYGON_POINT);

    private int fillMode;

    private FillMode(int fillMode)
    {
        this.fillMode = fillMode;
    }

    @Override
    public int toInt()
    {
        return fillMode;
    }
}
