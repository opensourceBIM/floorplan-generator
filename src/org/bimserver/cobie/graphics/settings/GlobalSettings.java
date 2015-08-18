package org.bimserver.cobie.graphics.settings;

public class GlobalSettings extends Settings
{
    private FontSettings fontSettings;
    private MaterialSettings materialSettings;
    private OutputSettings outputSettings;
    private PolygonSettings polygonSettings;
    private RenderSettings renderSettings;

    public GlobalSettings()
    {
        this(new FontSettings(), new MaterialSettings(), new OutputSettings(), new PolygonSettings(), new RenderSettings());
    }
    
    public GlobalSettings(
            FontSettings fontAttributes, 
            MaterialSettings materialSettings, 
            OutputSettings outputSettings,
            PolygonSettings polygonAttributes, 
            RenderSettings renderSettings)
    {
        setFontAttributes(fontAttributes);
        setMaterialSettings(materialSettings);
        setOutputSettings(outputSettings);
        setPolygonSettings(polygonAttributes);
        setRenderSettings(renderSettings);
    }

    public FontSettings getFontAttributes()
    {
        return fontSettings;
    }

    public MaterialSettings getMaterialSettings()
    {
        return materialSettings;
    }

    public OutputSettings getOutputSettings()
    {
        return outputSettings;
    }
    
    public PolygonSettings getPolygonSettings()
    {
        return polygonSettings;
    }
    
    public RenderSettings getRenderSettings()
    {
        return renderSettings;
    }

    public void setFontAttributes(FontSettings fontAttributes)
    {
        this.fontSettings = fontAttributes;
    }
    
    public void setMaterialSettings(MaterialSettings materialSettings)
    {
        this.materialSettings = materialSettings;
    }
    
    public void setOutputSettings(OutputSettings outputSettings)
    {
        this.outputSettings = outputSettings;
    }
    
    public void setPolygonSettings(PolygonSettings polygonAttributes)
    {
        this.polygonSettings = polygonAttributes;
    }

    public void setRenderSettings(RenderSettings renderSettings)
    {
        this.renderSettings = renderSettings;
    }
    
    public RenderSettings2D getRenderSettings2D()
    {
        return renderSettings.for2D();
    }

    public RenderSettings3D getRenderSettings3D()
    {
        return renderSettings.for3D();
    }
}
