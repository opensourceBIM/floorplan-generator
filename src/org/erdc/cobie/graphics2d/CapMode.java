package org.erdc.cobie.graphics2d;

import java.awt.BasicStroke;

import org.erdc.cobie.shared.COBieIntConstantEnum;

public enum CapMode implements COBieIntConstantEnum
{
    BUTT(BasicStroke.CAP_BUTT), ROUND(BasicStroke.CAP_ROUND), SQUARE(BasicStroke.CAP_SQUARE);
    
    private final int capMode;
    
    private CapMode(int capMode)
    {
        this.capMode = capMode;
    }

    @Override
    public int toInt()
    {
        return capMode;
    }
}
