package org.bimserver.cobie.graphics.filewriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import org.bimserver.cobie.graphics.entities.Facility;
import org.bimserver.cobie.graphics.settings.GlobalSettings;
import org.bimserver.cobie.graphics.settings.OutputSettings.FileInfo;
import org.bimserver.cobie.graphics.string.Default;
import org.bimserver.cobie.graphics.string.Pattern;
import org.bimserver.plugins.VirtualFile;

import com.google.common.base.Charsets;

public class IndexWriter extends TemplateWriter
{
    private final Facility facility;
    
    public IndexWriter(Path rootPath, Facility facility, GlobalSettings settings)
    {
        super(rootPath, settings);
        this.facility = facility;
    }

    public final Facility getFacility()
    {
        return facility;
    }
    
    public void write() throws IOException
    {
        write(null);        
    }

    @Override
    public void write(VirtualFile virtualFile) throws IOException
    {
    	FileInfo htmlInfo = getSettings().getOutputSettings().getHtmlInfo();

        virtualFile.createFile(Default.INDEX_FILE_NAME.toString() + htmlInfo.getExtension()).setData(writeFacilityPath());
    }
    
    private byte[] writeFacilityPath() throws IOException
    {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	FileInfo htmlInfo = getSettings().getOutputSettings().getHtmlInfo();
        String template = getTemplate(Resource.INDEX);
        
        String indexPath = htmlInfo.path + getFacility().getFileName(htmlInfo.getExtension());
        template = template.replace(Pattern.FACILITY_PATH.toString(), indexPath);
        
        baos.write(template.getBytes(Charsets.UTF_8));
        return baos.toByteArray();
    }
}
