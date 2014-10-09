package org.erdc.cobie.graphics2d;

import java.awt.BasicStroke;

import org.erdc.cobie.shared.COBieIntConstantEnum;

public enum JoinMode implements COBieIntConstantEnum
{
    BEVEL(BasicStroke.JOIN_BEVEL), MITER(BasicStroke.JOIN_MITER), ROUND(BasicStroke.JOIN_ROUND);

    private final int joinMode;
    
    private JoinMode(int joinMode)
    {
        this.joinMode = joinMode;
    }
    
    @Override
    public int toInt()
    {
        return joinMode;
    }
}
