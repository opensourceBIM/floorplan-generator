package org.bimserver.cobie.graphics.serializers;

import java.io.OutputStream;
import java.nio.file.Path;

import org.bimserver.plugins.serializers.AbstractGeometrySerializer;
import org.bimserver.plugins.serializers.ProgressReporter;
import org.bimserver.plugins.serializers.SerializerException;

public class COBieFloorMapSerializer extends AbstractGeometrySerializer
{	    
	private Path rootPath;
	
    public COBieFloorMapSerializer(Path rootPath)
    {
    	this.rootPath = rootPath;
    }

    @Override
    public void reset()
    {
        setMode(Mode.BODY);
    }

	@Override
	protected boolean write(OutputStream outputStream, ProgressReporter progressReporter) throws SerializerException 
	{
		boolean rval = false;
        FacilitySerializer writer = new FacilitySerializer(rootPath, getModel(), outputStream);
        
        if (getMode() != Mode.FINISHED)
        {
        	writer.write();
            setMode(Mode.FINISHED);
            rval = true;
        }

        return rval;
	}
}
