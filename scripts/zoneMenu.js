var ZoneMenu = (function()
{	
	function ZoneMenu()
	{
		
	}
	
	ZoneMenu.prototype.getBase = function()
	{
		return new Menu();
	};
	
	ZoneMenu.prototype.getButton = function()
	{
		return $("#zoneMenuButton");
	};
	
	ZoneMenu.prototype.getForm = function()
	{
		return $("#zoneMenuOptions");
	};
	
	ZoneMenu.prototype.init = function()
	{
		this.getBase().init(this);
	};
	
	ZoneMenu.prototype.makeOption = function(item)
	{
		var label = item.ZoneName;
		var value = item.ZoneName + new Utils().getDefaultSeparator() + item.ZoneCategory;
		
		return { Label: label, Value: value };
	};
	
	ZoneMenu.prototype.populate = function()
	{	
		this.getBase().populate(this);
		var zones = [];
		
		try
		{
			var selectedFloor = new LocalStore().getSelectedFloor();
			zones = new JsonReader().getZonesByFloor(selectedFloor);
		}
		
		catch (e)
		{
			console.log("There are no zones associated with floor [" + selectedFloor.FloorName + "].");				
		}
		
		this.getBase().writeOptions(this, zones, true);
	};
	
	ZoneMenu.prototype.select = function(selectedItem)
	{		
		var selectedOption = this.getBase().select(this, selectedItem);

		var jsonReader = new JsonReader();
		var localStore = new LocalStore();
		var utils = new Utils();
		
		var image = localStore.getImage();
		var selectedZoneKeys = utils.getCompoundKeys(selectedOption);
		var selectedZone = jsonReader.getZoneByKey(selectedZoneKeys[0], selectedZoneKeys[1]);		
		var spaces = jsonReader.getSpacesByZone(localStore.getSelectedFloor(), selectedZone);
		
		$.each(spaces, function(i, space) 
		{
			var areas = utils.getImageMapAreas(space);
			utils.addHighlights(image, areas);
		});
	};
	
	return ZoneMenu;
})();