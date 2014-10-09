package org.erdc.cobie.graphics.settings;

import java.awt.Font;

import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.vecmath.Color3f;

import org.erdc.cobie.graphics.COBieColor;

public class FontSettings extends Settings
{
    public static final Font DEFAULT_FONT = new Font("Futura", Font.PLAIN, 1);
    public static final Font3D DEFAULT_FONT_3D = new Font3D(DEFAULT_FONT, new FontExtrusion());
    public static final Color3f DEFAULT_FONT_COLOR = COBieColor.WHITE.toColor3f();

    private Font font;
    private Font3D font3D;
    private Color3f color;

    public FontSettings()
    {
        font = DEFAULT_FONT;
        font3D = DEFAULT_FONT_3D;
        color = DEFAULT_FONT_COLOR;
    }

    public FontSettings(Font font, Font3D font3D, Color3f color)
    {
        setFont(font);
        setFont3D(font3D);
        setColor(color);
    }

    public Color3f getColor()
    {
        return color;
    }

    public Font getFont()
    {
        return font;
    }

    public Font3D getFont3D()
    {
        return font3D;
    }

    public void setColor(Color3f color)
    {
        this.color = color;
    }

    public void setFont(Font font)
    {
        this.font = font;
    }

    public void setFont3D(Font3D font3d)
    {
        this.font3D = font3d;
    }
}
