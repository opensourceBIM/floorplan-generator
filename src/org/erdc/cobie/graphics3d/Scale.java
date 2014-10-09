package org.erdc.cobie.graphics3d;

import javax.vecmath.Vector3d;

@Deprecated 
// NOTE: This currently isn't being used due to changes in how the floormap is generated.
public enum Scale
{
    DEFAULT(new Vector3d(Scale.NONE, Scale.NONE, Scale.NONE)),
    COLLAPSE_X(new Vector3d(Scale.FLATTEN_AMOUNT, Scale.NONE, Scale.NONE)),
    COLLAPSE_Y(new Vector3d(Scale.NONE, Scale.FLATTEN_AMOUNT, Scale.NONE)),
    COLLAPSE_Z(new Vector3d(Scale.NONE, Scale.NONE, Scale.FLATTEN_AMOUNT));
    
    public static final double NONE = 1.0;
    public static final double FLATTEN_AMOUNT = 0.0;
    
    private Vector3d scale;
    
    private Scale(Vector3d scale)
    {
        this.scale = scale;
    }
    
    public Vector3d toVector3d()
    {
        return scale;
    }
}
