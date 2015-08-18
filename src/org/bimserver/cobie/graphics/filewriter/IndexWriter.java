package org.bimserver.cobie.graphics.filewriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bimserver.cobie.graphics.entities.Facility;
import org.bimserver.cobie.graphics.settings.GlobalSettings;
import org.bimserver.cobie.graphics.settings.OutputSettings.FileInfo;
import org.bimserver.cobie.graphics.string.Default;
import org.bimserver.cobie.graphics.string.Pattern;
import org.bimserver.cobie.shared.utility.Zipper;

public class IndexWriter extends TemplateWriter
{
    private final Facility facility;
    
    public IndexWriter(Facility facility, GlobalSettings settings)
    {
        super(settings);
        this.facility = facility;
    }

    public final Facility getFacility()
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
        File indexFile = new File(Default.INDEX_FILE_NAME.toString() + htmlInfo.getExtension());
        writeFacilityPath(indexFile);

        if (zipper != null)
        {
            zipper.addEntry(indexFile, indexFile.getName(), true);
        }
    }
    
    private void writeFacilityPath(File indexFile) throws IOException
    {
    	FileInfo htmlInfo = getSettings().getOutputSettings().getHtmlInfo();
        FileWriter fileWriter = new FileWriter(indexFile);
        String template = getTemplate(Resource.INDEX);
        
        String indexPath = htmlInfo.path + getFacility().getFileName(htmlInfo.getExtension());
        template = template.replace(Pattern.FACILITY_PATH.toString(), indexPath);
        
        fileWriter.write(template);
        fileWriter.close();
    }
}
