package org.erdc.cobie.graphics.filewriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.erdc.cobie.graphics.entities.Facility;
import org.erdc.cobie.graphics.settings.GlobalSettings;
import org.erdc.cobie.graphics.settings.OutputSettings;
import org.erdc.cobie.graphics.settings.OutputSettings.FileInfo;
import org.erdc.cobie.graphics.settings.OutputSettings.NamedFileInfo;
import org.erdc.cobie.graphics.string.Pattern;
import org.erdc.cobie.shared.Common;
import org.erdc.cobie.shared.Zipper;

public class FacilityWriter extends TemplateWriter
{	
	private final Facility facility;

    public FacilityWriter(Facility facility, GlobalSettings settings)
    {
        super(settings);
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
    public void write(Zipper zipper) throws IOException
    {
    	FileInfo htmlInfo = getSettings().getOutputSettings().getHtmlInfo();
        File index = new File(getFacility().getFileName(htmlInfo.getExtension()));
        writeHtml(index);

        if (zipper != null)
        {
            zipper.addEntry(index, htmlInfo.path + index.getPath(), true);
        }
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

    private void writeHtml(File indexFile) throws IOException
    {       
        FileWriter fileWriter = new FileWriter(indexFile);
        String htmlBuffer = getTemplate(Resource.FLOORMAP_DEMO);
        htmlBuffer = writeTitle(htmlBuffer);
        htmlBuffer = writeDimensions(htmlBuffer);
        htmlBuffer = writeJSON(htmlBuffer);
        fileWriter.write(htmlBuffer);
        fileWriter.close();
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
