var EquipmentMenu = (function()
{	
	function EquipmentMenu()
	{
		
	}
		
	EquipmentMenu.prototype.getBase = function()
	{
		return new Menu();
	};
	
	EquipmentMenu.prototype.getButton = function()
	{
		return $("#equipmentMenuButton");
	};
	
	EquipmentMenu.prototype.getForm = function()
	{
		return $("#equipmentMenuOptions");
	};
	
	EquipmentMenu.prototype.highlight = function(asset)
	{
		var image = new LocalStore().getImage();		
		var jsonReader = new JsonReader();
		var spaceAssignments = jsonReader.getEquipmentSpaceAssignments(asset);
		var utils = new Utils();
		
		$.each(spaceAssignments, function(i, spaceAssignment)
		{
			var space = jsonReader.getSpaceByKey(spaceAssignment.SpaceName);
			var areas = utils.getImageMapAreas(space);
			utils.addHighlights(image, areas);
		});	
	};
	
	EquipmentMenu.prototype.init = function()
	{
		this.getBase().init(this);
	};
	
	EquipmentMenu.prototype.makeOption = function(item)
	{
		var label = item.AssetTypeName;
		var value = item.AssetTypeName + new Utils().getDefaultSeparator() + item.AssetTypeCategory;
		
		return { Label: label, Value: value };
	};
	
	EquipmentMenu.prototype.populate = function()
	{	
		this.getBase().populate(this);
		var equipmentTypes = [];
		
		try
		{
			var selectedFloor = new LocalStore().getSelectedFloor();
			equipmentTypes = new JsonReader().getEquipmentTypesByFloor(selectedFloor);
		}
		
		catch (e)
		{
			console.log("There are no equipment types associated with floor [" + selectedFloor.FloorName + "].");
		}	
			
		this.writeOptions(equipmentTypes);
	};
	
	EquipmentMenu.prototype.select = function(selectedItem)
	{	
		var selectedOption = this.getBase().select(this, selectedItem);
		
		var equipmentMenu = this;
		var jsonReader = new JsonReader();
		var utils = new Utils();
		
		var selectedEquipmentTypeKeys = utils.getCompoundKeys(selectedOption);		
		var selectedEquipmentType = jsonReader.getEquipmentTypeByKey(selectedEquipmentTypeKeys[0], selectedEquipmentTypeKeys[1]);
		
		if (selectedEquipmentType)
		{
			new LocalStore().addSelectedEquipmentType(selectedEquipmentType);		
			var equipment = jsonReader.getEquipmentByType(selectedEquipmentType);
			
			$.each(equipment, function(i, asset)
			{
				equipmentMenu.highlight(asset);
			});
		}
		
		else
		{
			var selectedEquipment = jsonReader.getEquipmentByKey(selectedEquipmentTypeKeys[0]);			
			equipmentMenu.highlight(selectedEquipment);
		}
	};
	
	// TODO: This could be done generically...
	EquipmentMenu.prototype.writeOptions = function(items)
	{				
		var menu = this;
		var form = this.getForm();		
		var options = this.getBase().filter(this, items);
		var jsonReader = new JsonReader();	
		var utils = new Utils();
		var selectedFloor = new LocalStore().getSelectedFloor();
		
		$.each(options, function(i, option)
		{		
			var equipmentTypeOptionHtml = "<li>" + menu.getBase().makeOptionHtml(option) + "</li>";
			var equipmentTypeKeys = utils.getCompoundKeys(option);
			var equipmentType = jsonReader.getEquipmentTypeByKey(equipmentTypeKeys[0], equipmentTypeKeys[1]);
			var equipment = jsonReader.getEquipmentByTypeOnFloor(equipmentType, selectedFloor);		
			
			if (equipment.length > 0)
			{
				// Need to add a sub-list.
				equipmentTypeOptionHtml = equipmentTypeOptionHtml.replace("</li>", "");
				equipmentTypeOptionHtml += "<ul>";
				
				var equipmentOptionsHtml = "";
				
				$.each(equipment, function(i, asset)
				{
					equipmentOptionsHtml += "<li>" + menu.getBase().makeOptionHtml({ Label: asset.AssetName, Value: asset.AssetName }) + "</li>";
				});
				
				equipmentTypeOptionHtml += equipmentOptionsHtml + "</ul></li>";
			}
			
			form.append(equipmentTypeOptionHtml);
		});
		
		form.menu(
		{ 			
			select: function(event, selectedItem)
			{ 
				menu.select(selectedItem.item); 
			} 
		});
		
		form.menu("refresh");
	};
	
	return EquipmentMenu;
})();