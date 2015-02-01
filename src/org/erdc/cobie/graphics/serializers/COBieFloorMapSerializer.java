package org.erdc.cobie.graphics.serializers;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;

import org.bimserver.plugins.serializers.AbstractGeometrySerializer;
import org.bimserver.plugins.serializers.ProgressReporter;
import org.bimserver.plugins.serializers.SerializerException;
import org.erdc.cobie.shared.Zipper;

public class COBieFloorMapSerializer extends AbstractGeometrySerializer
{	    
	private final Map<String, File> resources;
	
    public COBieFloorMapSerializer(Map<String, File> resources)
    {
    	this.resources = resources;
    }

    @Override
    public void reset()
    {
        setMode(Mode.BODY);
    }

	@Override
	protected boolean write(OutputStream outputStream,
			ProgressReporter progressReporter) throws SerializerException 
	{
		boolean rval = false;
        FacilitySerializer writer = new FacilitySerializer(resources, getModel(), new Zipper(outputStream));
        
        if (getMode() != Mode.FINISHED)
        {
        	writer.write();
            setMode(Mode.FINISHED);
            rval = true;
        }

        return rval;
	}
}
