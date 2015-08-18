package org.bimserver.cobie.graphics3d;

import javax.media.j3d.PolygonAttributes;

import org.bimserver.cobie.shared.utility.COBieIntConstantEnum;

public enum Capability implements COBieIntConstantEnum
{
    // Specifies that this PolygonAttributes object allows reading its cull face information.
    ALLOW_CULL_FACE_READ(PolygonAttributes.ALLOW_CULL_FACE_READ),

    // Specifies that this PolygonAttributes object allows writing its cull face information.
    ALLOW_CULL_FACE_WRITE(PolygonAttributes.ALLOW_CULL_FACE_WRITE),

    // Specifies that this PolygonAttributes object allows reading its polygon mode information.
    ALLOW_MODE_READ(PolygonAttributes.ALLOW_MODE_READ),

    // Specifies that this PolygonAttributes object allows writing its polygon mode information.
    ALLOW_MODE_WRITE(PolygonAttributes.ALLOW_MODE_WRITE),

    // Specifies that this PolygonAttributes object allows reading its back face normal flip flag.
    ALLOW_NORMAL_FLIP_READ(PolygonAttributes.ALLOW_NORMAL_FLIP_READ),

    // Specifies that this PolygonAttributes object allows writing its back face normal flip flag.
    ALLOW_NORMAL_FLIP_WRITE(PolygonAttributes.ALLOW_NORMAL_FLIP_WRITE),

    // Specifies that this PolygonAttributes object allows reading its polygon offset information.
    ALLOW_OFFSET_READ(PolygonAttributes.ALLOW_OFFSET_READ),

    // Specifies that this PolygonAttributes object allows writing its polygon offset information.
    ALLOW_OFFSET_WRITE(PolygonAttributes.ALLOW_OFFSET_WRITE);

    private int capability;

    private Capability(int capability)
    {
        this.capability = capability;
    }
    
    @Override
    public int toInt()
    {
        return capability;
    }
}
