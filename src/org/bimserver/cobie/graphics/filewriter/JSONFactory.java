package org.bimserver.cobie.graphics.filewriter;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

import org.bimserver.cobie.shared.serialization.COBieTabSerializer;
import org.bimserver.cobie.shared.transform.cobietab.cobielite.FacilityFactory;
import org.bimserver.emf.IfcModelInterface;
import org.buildingsmartalliance.docs.nbims03.cobie.cobielite.FacilityDocument;
import org.nibs.cobie.tab.COBIEDocument;


public class JSONFactory
{

    private XMLSerializer xmlSerializer;
    private boolean ignoreNonAssets = false;
    public JSONFactory()
    {
        setSerializer(newXMLSerializer());
    }
    
    public JSONFactory(boolean ignoreNonAssets)
    {
    	this();
    	this.ignoreNonAssets = ignoreNonAssets;
    }
    
    public JSON parse(FacilityDocument facility)
    {
//    	try
//		{
//			facility.save(new File("bigcobielite.xml"));
//		}
//		catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        return  xmlSerializer.readFromStream(facility.newInputStream());
              
    }

    private XMLSerializer newXMLSerializer()
    {
        XMLSerializer serializer = new XMLSerializer();
        applySerializerSettings(serializer);
        return serializer;
    }
    
    public JSON parse(File file) throws FileNotFoundException
    {
        JSON json =  xmlSerializer.readFromFile(file);   
        return json;
              
    }
    
    private void applySerializerSettings(XMLSerializer serializer)
    {
        serializer.setRemoveNamespacePrefixFromElements(true);
        serializer.setSkipNamespaces(true);
    }
    
    public JSON parse(IfcModelInterface model)
    {
        JSON json = null;
        FacilityDocument facility = getFacility(model);     
        json =parse(facility);        
        return json;
    }

	private FacilityDocument getFacility(IfcModelInterface model) 
	{
		COBieTabSerializer cobieFactory = new COBieTabSerializer(ignoreNonAssets);
        COBIEDocument cobie = cobieFactory.parse(model);
        FacilityFactory facilityFactory = new FacilityFactory();
        FacilityDocument facility = facilityFactory.parse(cobie);
		return facility;
	}

    public XMLSerializer getSerializer()
    {
        return xmlSerializer;
    }

    public void setSerializer(XMLSerializer serializer)
    {
        this.xmlSerializer = serializer;
    }
}
