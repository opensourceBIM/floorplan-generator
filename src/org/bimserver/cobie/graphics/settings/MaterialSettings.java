package org.bimserver.cobie.graphics.settings;

import javax.vecmath.Color3f;

import org.bimserver.cobie.graphics.COBieColor;

public class MaterialSettings extends Settings
{
    public static final float DEFAULT_AMBIENT_ATTENUATION = 0.6f;
    public static final Color3f DEFAULT_MATERIAL_COLOR = COBieColor.BLUE.toColor3f();
    public static final float DEFAULT_SHINYNESS = 75.0f;
    public static final Color3f DEFAULT_LIGHT_COLOR = COBieColor.WHITE.toColor3f();

    private float ambientAttenuation;
    private Color3f materialColor;
    private float shinyness;
    private Color3f lightColor;

    public MaterialSettings()
    {
        this(MaterialSettings.DEFAULT_AMBIENT_ATTENUATION, MaterialSettings.DEFAULT_MATERIAL_COLOR, MaterialSettings.DEFAULT_SHINYNESS,
                MaterialSettings.DEFAULT_LIGHT_COLOR);
    }

    public MaterialSettings(float ambientAttenuation, Color3f materialColor, float shinyness, Color3f lightColor)
    {
        setAmbientAttenuation(ambientAttenuation);
        setMaterialColor(materialColor);
        setShinyness(shinyness);
        setLightColor(lightColor);
    }

    public float getAmbientAttenuation()
    {
        return ambientAttenuation;
    }

    public Color3f getLightColor()
    {
        return lightColor;
    }

    public Color3f getMaterialColor()
    {
        return materialColor;
    }

    public float getShinyness()
    {
        return shinyness;
    }

    public void setAmbientAttenuation(float ambientAttenuation)
    {
        this.ambientAttenuation = ambientAttenuation;
    }

    public void setLightColor(Color3f lightColor)
    {
        this.lightColor = lightColor;
    }

    public void setMaterialColor(Color3f materialColor)
    {
        this.materialColor = materialColor;
    }

    public void setShinyness(float shinyness)
    {
        this.shinyness = shinyness;
    }
}
