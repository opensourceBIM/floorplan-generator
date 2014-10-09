var LocalStore = (function()
{
	function LocalStore()
	{
		
	}
	
	LocalStore.prototype.add = function(key, data)
	{	
		$.data(window.top.document.body, key, data);
		console.log("Data added to local store with key [" + key + "].");
	};
	
	LocalStore.prototype.addClickedSpace = function(space)
	{
		this.add(this.getClickedSpaceKey(), space);
	};
	
	LocalStore.prototype.addImage = function(image)
	{
		this.add(this.getImageKey(), image);
	};
	
	LocalStore.prototype.addJSON = function(json)
	{
		this.add(this.getJSONKey(), json);	
	};
	
	LocalStore.prototype.addSelectedEquipmentType = function(selectedEquipmentType)
	{
		this.add(this.getSelectedEquipmentTypeKey(), selectedEquipmentType);
	};
	
	LocalStore.prototype.addSelectedFloor = function(selectedFloor)
	{
		this.add(this.getSelectedFloorKey(), selectedFloor);
	};

	LocalStore.prototype.get = function(key)
	{
		return $.data(window.top.document.body, key);		
	};
	
	LocalStore.prototype.getClickedSpace = function()
	{
		return this.get(this.getClickedSpaceKey());
	};
	
	LocalStore.prototype.getClickedSpaceKey = function()
	{
		return "ClickedSpace";
	};
	
	LocalStore.prototype.getImage = function()
	{
		return this.get(this.getImageKey());
	};
	
	LocalStore.prototype.getImageKey = function()
	{
		return "Image";
	};
	
	LocalStore.prototype.getJSON = function()
	{
		return this.get(this.getJSONKey());	
	};
	
	LocalStore.prototype.getJSONKey = function()
	{
		return "JSON";	
	};
	
	LocalStore.prototype.getSelectedEquipmentType = function()
	{
		return this.get(this.getSelectedEquipmentTypeKey());
	};
	
	LocalStore.prototype.getSelectedEquipmentTypeKey = function()
	{
		return "SelectedEquipmentType";
	};
	
	LocalStore.prototype.getSelectedFloor = function()
	{
		return this.get(this.getSelectedFloorKey());
	};
	
	LocalStore.prototype.getSelectedFloorKey = function()
	{
		return "SelectedFloor";
	};
	
	return LocalStore;
})();
