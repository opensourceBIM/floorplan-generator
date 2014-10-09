var EquipmentPanel = (function()
{
	function EquipmentPanel()
	{
		
	}
	
	EquipmentPanel.prototype.getBase = function()
	{
		return new Panel();
	};
	
	EquipmentPanel.prototype.getElement = function()
	{
		return $("#equipmentInfoSection");
	};
	
	EquipmentPanel.prototype.hide = function()
	{
		this.getBase().hide(this);
	};
	
	EquipmentPanel.prototype.init = function(equipment)
	{			
		this.getBase().init(this);
		
		this.initInformation(equipment);
		this.initAttributes(equipment);
		this.initDocuments(equipment);
		
		$("#equipmentExternalReferencesPanel").accordion(this.getBase().getOptions());
		$("#equipmentInformationPanel").accordion(this.getBase().getOptions());
		$("#equipmentAttributesPanel").accordion(this.getBase().getOptions());
		$("#equipmentDocumentsPanel").accordion(this.getBase().getOptions());
	};
	
	EquipmentPanel.prototype.initAttributes = function(equipment)
	{
		var attributes = new JsonReader().getEquipmentAttributes(equipment);
		var defaultInfo = this.getBase().getDefaultInfo();
		var converter = this.getBase().makeAttributeValue;
		var utils = new Utils();
				
		utils.setListHtml("#equipmentAttributes", attributes, defaultInfo, converter);
	};
	
	EquipmentPanel.prototype.initDocuments = function(equipment)
	{
		var documents = new JsonReader().getEquipmentDocuments(equipment);
		var defaultInfo = this.getBase().getDefaultInfo();
		var converter = this.getBase().makeDocumentValue;
		var utils = new Utils();
				
		utils.setListHtml("#equipmentDocuments", documents, defaultInfo , converter);
	};
	
	EquipmentPanel.prototype.initInformation = function(equipment)
	{
		var utils = new Utils();		
		
		utils.setElementHtml("#equipmentExternalEntityName", equipment.externalEntityName, utils.getDefaultValue());		
		utils.setElementHtml("#equipmentExternalID", equipment.externalID, utils.getDefaultValue());
		utils.setElementHtml("#equipmentExternalSystemName", equipment.externalSystemName, utils.getDefaultValue());
		utils.setElementHtml("#equipmentDescription", equipment.AssetDescription, utils.getDefaultValue());
		utils.setElementHtml("#equipmentLocationDescription", equipment.AssetLocationDescription, utils.getDefaultValue());
		utils.setElementHtml("#equipmentSerialNumber", equipment.AssetSerialNumber, utils.getDefaultValue());
		utils.setElementHtml("#equipmentTagNumber", equipment.AssetTagNumber, utils.getDefaultValue());
	};
	
	EquipmentPanel.prototype.show = function()
	{
		this.getBase().show(this);
	};
	
	return EquipmentPanel;
})();