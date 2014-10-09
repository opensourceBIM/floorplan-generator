var EquipmentTypePanel = (function()
{
	function EquipmentTypePanel()
	{
		
	}
	
	EquipmentTypePanel.prototype.getBase = function()
	{
		return new Panel();
	};
	
	EquipmentTypePanel.prototype.getElement = function()
	{
		return $("#equipmentTypeInfoSection");
	};
	
	EquipmentTypePanel.prototype.hide = function()
	{
		this.getBase().hide(this);
	};
	
	EquipmentTypePanel.prototype.init = function(equipmentType)
	{					
		this.getBase().init(this);
		
		this.initInformation(equipmentType);
		this.initAttributes(equipmentType);
		this.initDocuments(equipmentType);
		
		$("#equipmentTypeInformationPanel").accordion(this.getBase().getOptions());
		$("#equipmentTypeAttributesPanel").accordion(this.getBase().getOptions());
		$("#equipmentTypeDocumentsPanel").accordion(this.getBase().getOptions());
	};
	
	EquipmentTypePanel.prototype.initAttributes = function(equipmentType)
	{
		var attributes = new JsonReader().getEquipmentTypeAttributes(equipmentType);
		var defaultInfo = this.getBase().getDefaultInfo();
		var converter = this.getBase().makeAttributeValue;
		var utils = new Utils();
		
		utils.setListHtml("#equipmentTypeAttributes", attributes, defaultInfo, converter);
	};
	
	EquipmentTypePanel.prototype.initDocuments = function(equipmentType)
	{
		var documents = new JsonReader().getEquipmentTypeDocuments(equipmentType);
		var defaultInfo = this.getBase().getDefaultInfo();
		var converter = this.getBase().makeDocumentValue;
		var utils = new Utils();
		
		utils.setListHtml("#equipmentTypeDocuments", documents, defaultInfo, converter);
	};
	
	EquipmentTypePanel.prototype.initInformation = function(equipmentType)
	{
		var utils = new Utils();
		
		utils.setElementHtml("#equipmentTypeDescription", equipmentType.AssetTypeDescription, utils.getDefaultValue());		
	};
	
	EquipmentTypePanel.prototype.show = function()
	{
		this.getBase().show(this);
	};
	
	return EquipmentTypePanel;
})();