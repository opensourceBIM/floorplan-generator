package org.bimserver.cobie.graphics.filewriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.bimserver.cobie.graphics.entities.Facility;
import org.bimserver.cobie.graphics.settings.GlobalSettings;
import org.bimserver.cobie.graphics.settings.OutputSettings;
import org.bimserver.cobie.graphics.settings.OutputSettings.FileInfo;
import org.bimserver.cobie.graphics.settings.OutputSettings.NamedFileInfo;
import org.bimserver.cobie.graphics.string.Pattern;
import org.bimserver.cobie.shared.Common;
import org.bimserver.plugins.VirtualFile;

import com.google.common.base.Charsets;

public class FacilityWriter extends TemplateWriter
{	
	private final Facility facility;

    public FacilityWriter(Path rootPath, Facility facility, GlobalSettings settings)
    {
        super(rootPath, settings);
        this.facility = facility;
    }

    public Facility getFacility()
    {
        return facility;
    }

    @Override
    public void write() throws IOException
    {
        write(null);
    }

    @Override
    public void write(VirtualFile virtualFile) throws IOException
    {
    	FileInfo htmlInfo = getSettings().getOutputSettings().getHtmlInfo();
        File index = new File(getFacility().getFileName(htmlInfo.getExtension()));

        virtualFile.createFile(htmlInfo.path + index.getPath()).setData(writeHtml());
    }

    private String writeDimensions(String template)
    {
        String html = Common.EMPTY_STRING.toString();

        String width = Integer.toString(getSettings().getRenderSettings().getDimension().width);
        String height = Integer.toString(getSettings().getRenderSettings().getDimension().height);

        html = template.replace(Pattern.IMAGE_WIDTH.toString(), width);
        html = html.replace(Pattern.IMAGE_HEIGHT.toString(), height);

        return html;
    }

    private byte[] writeHtml() throws IOException
    {       
        String htmlBuffer = getTemplate(Resource.FLOORMAP_DEMO);
        htmlBuffer = writeTitle(htmlBuffer);
        htmlBuffer = writeDimensions(htmlBuffer);
        htmlBuffer = writeJSON(htmlBuffer);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(htmlBuffer.getBytes(Charsets.UTF_8));
        return baos.toByteArray();
    }

    private String writeJSON(String template)
    {
        NamedFileInfo jsonInfo = (NamedFileInfo) getSettings().getOutputSettings().getJSONInfo();
        String jsonPath = OutputSettings.PARENT_DIRECTORY + jsonInfo.path + jsonInfo.name + jsonInfo.getExtension();
        
        return template.replace(Pattern.JSON_PATH.toString(), jsonPath);
    }
    
    private String writeTitle(String template)
    {
        // Using the facility's filename instead of it's actual name, b/c getName() can
        // return a null, whereas getFileName() will return the checked name (that is used
        // to generate it's HTML file).
        return template.replace(Pattern.FACILITY_NAME.toString(), getFacility().getFileName(Common.EMPTY_STRING.toString()));
    }
}
