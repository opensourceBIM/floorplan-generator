package org.bimserver.cobie.graphics.string;

public enum Pattern
{
	AREAS("{areas}"),
	CLASS("{class}"),
	COLOR("{color}"),
	COORDS("{coords}"),
    FACILITY_NAME("{facilityName}"),
    FACILITY_PATH("{facilityPath}"),
    FLOOR_MAP_IMAGE("{floorMapImage}"),
    FLOOR_NAME("{floorName}"),
    ID("{id}"),
    IMAGE_HEIGHT("{imageHeight}"),
    IMAGE_WIDTH("{imageWidth}"),
    JSON_PATH("{jsonPath}"),
    KEY_INFOS("{keyInfos}"),
    LINK("{link}"),
    NAME("{name}"),
    REL("{rel}"),
    TITLE("{title}");
    
    private final String pattern;
    
    private Pattern(String pattern)
    {
    	this.pattern = pattern;
    }
    
    @Override
    public final String toString()
    {
    	return pattern;
    }
}
