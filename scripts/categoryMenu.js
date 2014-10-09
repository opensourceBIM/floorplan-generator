var CategoryMenu = (function()
{	
	function CategoryMenu()
	{
		
	}
	
	CategoryMenu.prototype.getBase = function()
	{
		return new Menu();
	};
	
	CategoryMenu.prototype.getButton = function()
	{
		return $("#categoryMenuButton");
	};
	
	CategoryMenu.prototype.getForm = function()
	{
		return $("#categoryMenuOptions");
	};
	
	CategoryMenu.prototype.init = function()
	{
		this.getBase().init(this);
	};
	
	CategoryMenu.prototype.makeOption = function(item)
	{		 
		return { Label: item, Value: item };
	};
	
	CategoryMenu.prototype.populate = function()
	{			
		this.getBase().populate(this);
		var categories = [];
		
		try
		{
			var selectedFloor = new LocalStore().getSelectedFloor();
			var jsonReader = new JsonReader();
			var spaces = jsonReader.getSpacesByFloor(selectedFloor);
			categories = jsonReader.getSpaceCategories(spaces);
		}
		
		catch (e)
		{
			console.log("There are no categories associated with floor [" + selectedFloor.FloorName + "].");	
		}
		
		this.getBase().writeOptions(this, categories, true);
	};
	
	CategoryMenu.prototype.select = function(selectedItem)
	{
		var selectedOption = this.getBase().select(this, selectedItem);
		
        var localStore = new LocalStore();
        var utils = new Utils();        
        var image = localStore.getImage();
        var spaces = new JsonReader().getSpacesByCategory(localStore.getSelectedFloor(), selectedOption.Value);
        
        $.each(spaces, function(i, space) 
        {
            var areas = utils.getImageMapAreas(space);
            utils.addHighlights(image, areas);
        });
	};
	
	return CategoryMenu;
})();


