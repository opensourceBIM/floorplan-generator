package org.erdc.cobie.graphics.settings;

import org.erdc.cobie.graphics3d.Capability;
import org.erdc.cobie.graphics3d.CullMode;
import org.erdc.cobie.graphics3d.FillMode;

public class PolygonSettings extends Settings
{
    public static final FillMode DEFAULT_FILL_MODE = FillMode.FILL;
    public static final CullMode DEFAULT_CULL_MODE = CullMode.CULL_BACKFACES;
    public static final Capability DEFAULT_CAPABILITY = Capability.ALLOW_NORMAL_FLIP_READ;

    private FillMode fillMode;
    private CullMode cullMode;
    private Capability capability;

    public PolygonSettings()
    {
        this(DEFAULT_FILL_MODE, DEFAULT_CULL_MODE, DEFAULT_CAPABILITY);
    }

    public PolygonSettings(FillMode fillMode, CullMode cullMode, Capability capability)
    {
        setFillMode(fillMode);
        setCullMode(cullMode);
        setCapability(capability);
    }

    public Capability getCapability()
    {
        return capability;
    }

    public CullMode getCullMode()
    {
        return cullMode;
    }

    public FillMode getFillMode()
    {
        return fillMode;
    }

    public void setCapability(Capability capability)
    {
        this.capability = capability;
    }

    public void setCullMode(CullMode cullMode)
    {
        this.cullMode = cullMode;
    }

    public void setFillMode(FillMode fillMode)
    {
        this.fillMode = fillMode;
    }

    public javax.media.j3d.PolygonAttributes toJ3DPolygonAttributes()
    {
        return new javax.media.j3d.PolygonAttributes(fillMode.toInt(), cullMode.toInt(), capability.toInt());
    }
}
