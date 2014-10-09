var EquipmentInfo = (function()
{		
	function EquipmentInfo()
	{
		
	}
	
	EquipmentInfo.prototype.init = function()
	{	
		var utils = new Utils();		
		var equipmentKey = utils.getURLParameter("equipmentKey").replace(/\%20/g, " ");
		var equipmentKeys = utils.getCompoundKeys({ Label: equipmentKey, Value: equipmentKey });
		
		var jsonReader = new JsonReader();
		jsonReader.loadJSON("cobie.json");
		
		var equipmentType = jsonReader.getEquipmentTypeByKey(equipmentKeys[0], equipmentKeys[1]);
		var equipment = jsonReader.getEquipmentByKey(equipmentKeys[0]);		
		var equipmentPanel = new EquipmentPanel();
		var equipmentTypePanel = new EquipmentTypePanel();
		
		if (equipmentType)
		{		
			equipmentTypePanel.init(equipmentType);
			equipmentPanel.hide();
		}
		
		if (equipment)
		{			
			equipmentPanel.init(equipment);
			equipmentTypePanel.hide();			
		}
	};
	
	return EquipmentInfo;
})();
