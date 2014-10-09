var SpaceEquipmentMenu = (function()
{	
	function SpaceEquipmentMenu()
	{
		
	}
	
	SpaceEquipmentMenu.prototype.getBase = function()
	{
		return new Menu();
	};
	
	SpaceEquipmentMenu.prototype.getButton = function()
	{
		return $("#spaceEquipmentMenuButton");
	};
	
	SpaceEquipmentMenu.prototype.getEquipmentInfoDialog = function()
	{
		return $("#equipmentInfoDialog");
	};
	
	SpaceEquipmentMenu.prototype.getForm = function()
	{
		return $("#spaceEquipmentMenuOptions");
	};
	
	SpaceEquipmentMenu.prototype.init = function()
	{
		this.getBase().init(this);
	};
	
	SpaceEquipmentMenu.prototype.makeOption = function(item)
	{
		var label = item.AssetTypeName;
		var value = item.AssetTypeName + new Utils().getDefaultSeparator() + item.AssetTypeCategory;
		
		return { Label: label, Value: value };
	};
	
	SpaceEquipmentMenu.prototype.openEquipmentInfoDialog = function(contentUri, title, width, height)
	{		
		$(this.getEquipmentInfoDialog().selector + " iframe").attr("src", contentUri);	
		this.getEquipmentInfoDialog().dialog({ title: title, modal: true, width: width, height: height });
		this.getEquipmentInfoDialog().dialog("open");
	};
	
	SpaceEquipmentMenu.prototype.populate = function()
	{	
		this.getBase().populate(this);
		var equipmentTypes = [];
		
		try
		{
			var selectedSpace = new LocalStore().getClickedSpace();
			equipmentTypes = new JsonReader().getEquipmentTypesBySpace(selectedSpace);
		}
		
		catch (e)
		{
			console.log("There are no equipment types associated with space [" + selectedSpace.SpaceName + "].");
		}
		
		this.writeOptions(equipmentTypes);
	};
	
	SpaceEquipmentMenu.prototype.select = function(selectedItem)
	{	
		var selectedOption = this.getBase().select(this, selectedItem);
		var contentUri = "equipmentInfo.html?equipmentKey=" + selectedOption.Value;
		var equipmentDialogTitle = " Details for Equipment: " + selectedOption.Label; 

		this.openEquipmentInfoDialog(contentUri, equipmentDialogTitle, 650, 500);		
	};
	
	// TODO: This could be done generically...
	SpaceEquipmentMenu.prototype.writeOptions = function(items)
	{			
		var menu = this;
		var form = this.getForm();		
		var options = this.getBase().filter(this, items);
		var jsonReader = new JsonReader();	
		var utils = new Utils();
		var selectedSpace = new LocalStore().getClickedSpace();
		
		$.each(options, function(i, option)
		{		
			var equipmentTypeOptionHtml = "<li>" + menu.getBase().makeOptionHtml(option) + "</li>";
			var equipmentTypeKeys = utils.getCompoundKeys(option);
			var equipmentType = jsonReader.getEquipmentTypeByKey(equipmentTypeKeys[0], equipmentTypeKeys[1]);
			var equipment = jsonReader.getEquipmentByTypeInSpace(equipmentType, selectedSpace);		
			
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
	
	Utils.prototype.writeNestedOptions = function(items, queryInfo)
	{
		var menu = this;
		var form = this.getForm();
		var utils = new Utils();
		var options = this.getBase().filter(this, items);
				
		$.each(options, function(i, option)
		{		
			var optionHtml = "<li>" + menu.getBase().makeOptionHtml(option) + "</li>";
			var optionKeys = utils.getCompoundKeys(option);
			var equipmentType = jsonReader.getEquipmentTypeByKey(equipmentTypeKeys[0], equipmentTypeKeys[1]);
			var equipment = jsonReader.getEquipmentByTypeInSpace(equipmentType, selectedSpace);		
			
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
	};
	
	return SpaceEquipmentMenu;
})();