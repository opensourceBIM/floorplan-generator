function init(jsonPath) 
{	
	new JsonReader().loadJSON(jsonPath);
	new FloorMenu().init();
	
	var tabOptions = { collapsible: true, heightStyle: "content" };
	
	$("#filtersTabContainer").tabs(tabOptions);
	$("#spaceInfoTabContainer").tabs(tabOptions);
	$("#spaceInfoPanel").accordion({ heightStyle: "content" });
	$("#imageMapTabContainer").tabs(tabOptions);
	$("#imageMapKeyTabContainer").tabs(tabOptions);	
	$(".footer").position({ my: "right bottom", at: "right bottom", of: ".body" });
	
	console.log("JavaScript initialized.");
}

function registerImage(image)
{
	new LocalStore().addImage(image);
}

function resize()
{
	$(".footer")
}

function updateSpaceInfo(spaceName)
{
	var space = new JsonReader().getSpaceByKey(spaceName);
	new SpaceInfo().init(space);
}

