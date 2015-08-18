package org.bimserver.cobie.graphics.string;

import org.bimserver.cobie.shared.Common;
import org.bimserver.cobie.shared.utility.StringTable;
import org.bimserver.cobie.shared.utility.StringUtils;

public enum Default implements StringTable
{
    DEFAULT_CONTENT_TYPE("application/zip"),
    DEFAULT_EXTENSION("zip"),
    DESCRIPTION("Produces an HTML ImageMap containing the floor-plan(s) contained within the BIM."),
    NAME("COBieFloorMapSerializer"),
    VERSION("1.0"),
    
	COLOR_CAST_FAILED("The supplied Color3f value cannot be cast to COBieColor."),
    
	DEFAULT_FILENAME("default"),
    DEFAULT_FOLDER("serializerOutput"),
    DEFAULT_KEY_POSTFIX("_key"),
	
    EMPTY_FILENAME("The supplied parameter value 'name' cannot be empty or null. The default filename is being used."),
	EMPTY_MESH_FALLBACK("A mesh cannot be generated from the supplied product. An empty mesh has been generated."),
	FILE_RENDER_FAILED("The scene could not be rendered to a file. Make sure the path '%s' exists and you have permission to access it."),
	FILE_RENDER_SUCCEEDED("Scene rendered to output file '%s'."),
	HEX_COLOR_PREFIX("#"),
	HEX_COLOR_EMPTY_DIGIT("0"),
	ILLEGAL_CHARACTERS_IN_FILENAME("The supplied parameter value 'name' cannot contain illegal filename characters.\nIllegal filename characters replaced with '%s'."),
	INIT_FAILED("Failed to initialize..."),
	INDEX_FILE_NAME("index"),
	INVALID_CANVAS_ADD("Engine2D.addToCanvas requires a parameter that is an instance of type 'RenderableEntityView2D'."),
	INVALID_MESH("The supplied mesh is invalid."),
	JSON_PREFIX("\"@"),
	KEY_SEPARATOR("-"),
	MESH_NAME_SEPARATOR(": "),
	NO_GEOMETRY("Product does not have geometry."),
	NULL_COLOR("Warning! the supplied color is null. Using default."),
	NULL_MESH("The supplied mesh is null."),
	NULL_STROKE("Warning! the supplied stroke is null.  Using default."),
	PACKAGE("interface org.bimserver.models.ifc2x3tc1."),
	POLYGON_GENERATION_FAILED("A polygon cannot be generated from the supplied mesh. An empty polygon has been generated."),
	RENDER_MODE_FAILED("Engine's render mode cannot be set to 'RENDER_2D' and 'RENDER_3D' simultaneously."),
	RENDER_MODE_TARGET_FAILED("Engine's render mode cannot be set to 'RENDER_TO_FILE' and 'RENDER_TO_FRAME' simultaneously."),
	SCENE_RENDERED("Scene rendered to frame. You should see some stuff now."),
	SPACE_INFO("[%s]"),
	SPACE_TAG("\t\t\t<div title=\"%s\" class=\"spaceContainer\">\n%s\t\t\t</div>\n"),
	WRITING("Writing...");
	
	private final String string;
	
	private Default(String s)
	{
		string = s;
	}
	
	@Override
	public String format(Object... args)
	{
		return String.format(string, args);
	}

	@Override
	public String postfix(String s)
	{
		return StringUtils.concatenate(string, s, Common.TEXT_SEPARATOR.toString());
	}

	@Override
	public String prefix(String s)
	{
		return StringUtils.concatenate(s, string, Common.TEXT_SEPARATOR.toString());
	}

	@Override
	public String toString()
	{
		return string;
	}
}
