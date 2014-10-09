package org.erdc.cobie.graphics2d;

import javax.vecmath.Color3f;

public class Stroke
{
    public float width;
    public Color3f color;
    public CapMode capMode;
    public JoinMode joinMode;
    
    public Stroke(float width, Color3f color, CapMode capMode, JoinMode joinMode)
    {
        this.width = width;
        this.color = color;
        this.capMode = capMode;
        this.joinMode = joinMode;
    }
    
    public boolean isVisible()
    {
        return width > 0.0f;
    }
}
