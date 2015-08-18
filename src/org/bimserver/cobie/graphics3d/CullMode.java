package org.bimserver.cobie.graphics3d;

import javax.media.j3d.PolygonAttributes;

import org.bimserver.cobie.shared.utility.COBieIntConstantEnum;

public enum CullMode implements COBieIntConstantEnum
{
    CULL_BACKFACES(PolygonAttributes.CULL_BACK), 
    CULL_FRONTFACES(PolygonAttributes.CULL_FRONT), 
    NONE(PolygonAttributes.CULL_NONE);

    private int cullMode;

    private CullMode(int cullMode)
    {
        this.cullMode = cullMode;
    }

    @Override
    public int toInt()
    {
        return cullMode;
    }
}
