package org.bimserver.cobie.graphics.filewriter;

import java.io.IOException;

import org.bimserver.cobie.graphics.settings.GlobalSettings;
import org.bimserver.cobie.graphics.settings.OutputSettings.NamedFileInfo;
import org.bimserver.cobie.graphics.string.Default;
import org.bimserver.cobie.shared.Common;
import org.bimserver.emf.IfcModelInterface;
import org.bimserver.plugins.VirtualFile;

import net.sf.json.JSON;

public class JSONWriter extends FileWriter
{
    private final IfcModelInterface modelInterface;
    
    public JSONWriter(IfcModelInterface modelInterface, GlobalSettings settings)
    {
        super(settings);
        this.modelInterface = modelInterface;
    }

    protected final IfcModelInterface getModelInterface()
    {
        return modelInterface;
    }
    
    @Override
    public void write() throws IOException
    {
        write(null);        
    }

    @Override
    // TODO This method needs to operate like all other FileGenerators, i.e. they generate
    // a File object that can optionally be added to a zip-archive.
    public void write(VirtualFile virtualFile) throws IOException
    {
        JSONFactory jsonFactory = new JSONFactory();
        JSON json = jsonFactory.parse(getModelInterface());
        
        String jsonPath = ((NamedFileInfo)getSettings().getOutputSettings().getJSONInfo()).getFilePath();
        
        virtualFile.createFile(jsonPath).setData(clean(json).getBytes());
    }
    
    private static String clean(JSON json)
    {
        return json.toString().replace(Default.JSON_PREFIX.toString(), Common.QUOTE_DOUBLE.toString());
    }
}
