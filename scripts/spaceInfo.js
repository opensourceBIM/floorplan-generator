var SpaceInfo = (function()
{
	function SpaceInfo()
	{
		
	}
	
	SpaceInfo.prototype.getArea = function(area)
	{
		var areaValue = area.DecimalValue;
		var areaUnit = area.Unit;
		var areaString;
		
		if (areaUnit) 
		{
			areaString = areaValue + " " + areaUnit;
		}
		
		else
		{
			areaString = areaValue + " " + new JsonReader().getFacility().FacilityDefaultAreaUnit;
		}
		
		return areaString;
	};
	
	SpaceInfo.prototype.getBase = function()
	{
		return new Panel();
	}
	
	SpaceInfo.prototype.getElement = function()
	{
		return $("#spaceInfoTabContainer");
	}
	
	SpaceInfo.prototype.getGrossArea = function(space)
	{
		return this.getArea(space.SpaceGrossAreaValue);
	};
	
	SpaceInfo.prototype.getNetArea = function(space)
	{
		return this.getArea(space.SpaceNetAreaValue);
	};
	
	SpaceInfo.prototype.getGrossAreaElement = function()
	{
		return $("#spaceGrossArea");
	};
	
	SpaceInfo.prototype.getCloseButton = function()
	{
		return $("#spaceInfoCloseButton");
	};
	
	SpaceInfo.prototype.getNetAreaElement = function()
	{
		return $("#spaceNetArea");
	};
	
	SpaceInfo.prototype.getDescriptionElement = function()
	{
		return $("#spaceDescription");
	};
	
	SpaceInfo.prototype.getCategoryElement = function()
	{
		return $("#spaceCategory");
	};
	
	SpaceInfo.prototype.getSpaceDocumentsElement = function()
	{
		return $("#spaceDocuments");
	};
	
	SpaceInfo.prototype.getSpaceInfoPanelSpaceCategory = function()
	{
		return $("#spaceInfoPanelSpaceCategory");
	};
	
	SpaceInfo.prototype.getSpaceInfoPanelSpaceDescription = function()
	{
		return $("#spaceInfoPanelSpaceDescription");
	};
	
	SpaceInfo.prototype.getSpaceInfoPanelSpaceName = function()
	{
		return $("#spaceInfoPanelSpaceName");
	};
	
	SpaceInfo.prototype.getTabLabel = function()
	{
		return $("#spaceInfoTabLabel");
	};
	
	SpaceInfo.prototype.hide = function()
	{
		this.getBase().hide(this);
	};
	
	SpaceInfo.prototype.init = function(space)
	{					
		this.getBase().init(this);
		
		new LocalStore().addClickedSpace(space);
		new SpaceEquipmentMenu().init();
		
		this.initCloseButton();
		this.getSpaceInfoPanelSpaceName().html(space.SpaceName);
		this.getSpaceInfoPanelSpaceCategory().html(space.SpaceCategory);
		this.getSpaceInfoPanelSpaceDescription().html(space.SpaceDescription);
		this.getCategoryElement().html(space.SpaceCategory);
		this.getGrossAreaElement().html(this.getGrossArea(space));
		this.getNetAreaElement().html(this.getNetArea(space));	
	};
	
	SpaceInfo.prototype.initCloseButton = function()
	{
		var spaceInfo = this;
		this.getCloseButton().button({ icons: { primary: "ui-icon-closethick" }, text: false });
		
		this.getCloseButton().click(function()
		{
			spaceInfo.hide();
		});
	};
	
	SpaceInfo.prototype.reset = function()
	{
		this.hide();
		new SpaceEquipmentMenu().reset();
	};
	
	SpaceInfo.prototype.show = function()
	{
		this.getBase().show(this);		
	};
	
	return SpaceInfo;
})();