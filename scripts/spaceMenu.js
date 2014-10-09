var SpaceMenu = (function()
{	
	function SpaceMenu()
	{
		
	}
	
	SpaceMenu.prototype.getBase = function()
	{
		return new Menu();
	};
	
	SpaceMenu.prototype.getButton = function()
	{
		return $("#spaceMenuButton");
	};
	
	SpaceMenu.prototype.getForm = function()
	{
		return $("#spaceMenuOptions");
	};
	
	SpaceMenu.prototype.init = function()
	{
		this.getBase().init(this);
	};
	
	SpaceMenu.prototype.makeOption = function(item)
	{
		return { Label: item.SpaceName, Value: item.SpaceName };
	};
	
	SpaceMenu.prototype.populate = function()
	{	
		this.getBase().populate(this);
		var spaces = [];
		
		try
		{
			var selectedFloor = new LocalStore().getSelectedFloor();
			spaces = new JsonReader().getSpacesByFloor(selectedFloor);
		}
		
		catch (e)
		{
			console.log("There are no spaces associated with floor [" + selectedFloor.FloorName + "].");			
		}
		
		this.getBase().writeOptions(this, spaces, true);
	};
	
	SpaceMenu.prototype.select = function(selectedItem)
	{
		var selectedOption = this.getBase().select(this, selectedItem);
				
		var jsonReader = new JsonReader();
		var localStore = new LocalStore();
		var utils = new Utils();
		
		var image = localStore.getImage();
		var selectedSpace = jsonReader.getSpaceByKey(selectedOption.Value);				
		
		var areas = utils.getImageMapAreas(selectedSpace);
		utils.addHighlights(image, areas);
	};
	
	return SpaceMenu;
})();