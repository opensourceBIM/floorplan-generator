function init(mapperOptions)
{
	var image = document.getElementById("floorMapImage");
	parent.registerImage(image);
	cvi_map.add(image, mapperOptions);	
}

function highlight(imageID, areaID)
{
	extAreaOver(imageID, areaID);
}

function unHighlight(imageID, areaID)
{
	extAreaOut(imageID, areaID);
}

function updateSpaceInfo(e)
{
	// This is rather hacky.
	var spaceTitle = e.currentTarget.title.split("\n");
	parent.updateSpaceInfo(spaceTitle[0]);
}