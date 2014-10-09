var JsonReader = (function()
{
	function JsonReader()
	{
		
	}
	
	JsonReader.prototype.getEquipment = function(facility)
	{
		var equipment = [];
		var equipmentTypes = this.getEquipmentTypes();
		var jsonReader = this;
		
		$.each(equipmentTypes, function(i, assetType)
		{				
			var assets = jsonReader.getEquipmentByType(assetType);
			
			$.each(assets, function(i, asset)
			{
				equipment.push(asset);
			});
		});
		
		return equipment;
	};

	JsonReader.prototype.getEquipmentAttributes = function(equipment)
	{
		var attributes = [];
		
		try
		{
			// Make sure this is a proper array, b/c a single Asset object will be malformed.	
			attributes = $.makeArray(equipment.AssetAttributes.Attribute);
		}
		
		catch (e)
		{
			console.log("Asset [" + equipment.AssetName + "] has no attributes.");
		}
		
		return attributes;			
	};
	
	JsonReader.prototype.getEquipmentByKey = function(equipmentName)
	{
		var equipment = this.getEquipment(this.getFacility());
		
		equipment = $.grep(equipment, function(asset)
		{
			return (asset.AssetName === equipmentName);
		});
		
		return equipment[0];
	};
	
	JsonReader.prototype.getEquipmentBySpace = function(space)
	{
		var equipment = this.getEquipment(this.getFacility());
		var jsonReader = this;
		
		equipment = $.grep(equipment, function(asset)
		{
			var equipmentInSpace = false;
			var spaceAssignments = jsonReader.getEquipmentSpaceAssignments(asset);
						
			$.each(spaceAssignments, function(i, spaceAssignment)
			{
				if (spaceAssignment.SpaceName === space.SpaceName)
				{
					equipmentInSpace = true;
					return false; // Breaks out of each().
				}
			});
			
			return equipmentInSpace;
		});
		
		return equipment;
	};
	
	JsonReader.prototype.getEquipmentByType = function(equipmentType)
	{
		var equipment = [];
		
		try
		{
			// Make sure this is a proper array, b/c a single Asset object will be malformed.			
			equipment = $.makeArray(equipmentType.Assets.Asset);
		}
		
		catch (e)
		{
			console.log("AssetType [" + equipmentType.AssetTypeName + "] has no associated assets.");
		}
		
		return equipment;
	};
	
	JsonReader.prototype.getEquipmentByTypeInSpace = function(equipmentType, space)
	{
		var equipment = this.getEquipmentByType(equipmentType);
		var jsonReader = this;
		
		equipment = $.grep(equipment, function(asset)
		{
			var spaceAssignments = jsonReader.getEquipmentSpaceAssignments(asset);
			var equipmentInSpace = false;
			
			$.each(spaceAssignments, function(i, spaceAssignment)
			{
				if (spaceAssignment.SpaceName === space.SpaceName)
				{
					equipmentInSpace = true;
					return false; // Breaks out of each().
				}
			});
			
			return equipmentInSpace;			
		});
		
		return equipment;
	};
	
	JsonReader.prototype.getEquipmentByTypeOnFloor = function(equipmentType, floor)
	{
		var jsonReader = this;
		var equipment = this.getEquipmentByType(equipmentType);
		
		equipment = $.grep(equipment, function(asset)
		{
			var equipmentInFloor = false;		
			var spaceAssignments = jsonReader.getEquipmentSpaceAssignments(asset);
			
			$.each(spaceAssignments, function(i, spaceAssignment)
			{
				if (spaceAssignment.FloorName === floor.FloorName)
				{
					equipmentInFloor = true;
					return false; // Breaks out of each().
				}
			});
			
			return equipmentInFloor;
		});		
		
		return equipment;
	};

	JsonReader.prototype.getEquipmentDocuments = function(equipment)
	{		
		var documents = [];
		
		try
		{
			// Make sure this is a proper array, b/c a single Asset object will be malformed.	
			documents = $.makeArray(equipment.AssetDocuments.Document);
		}
		
		catch (e)
		{
			console.log("Asset [" + equipment.AssetName + "] has no documents.");
		}
		
		return documents;
	};
	
	JsonReader.prototype.getEquipmentSpaceAssignments = function(equipment)
	{
		var spaceAssignments = [];
	
		try
		{
			// Make sure this is a proper array, b/c a single SpaceAssignment object will be malformed.
			spaceAssignments = $.makeArray(equipment.AssetSpaceAssignments.SpaceAssignment);
		}
		
		catch (e)
		{
			console.log("Asset [" + equipment.AssetName + "] has no space assignments.");
		}
		
		return spaceAssignments;
	};
	
	JsonReader.prototype.getEquipmentTypeAttributes = function(equipmentType)
	{
		var attributes = [];
		
		try
		{
			// Make sure this is a proper array, b/c a single Asset object will be malformed.	
			attributes = $.makeArray(equipmentType.AssetTypeAttributes.Attribute);
		}
		
		catch (e)
		{
			console.log("AssetType [" + equipmentType.AssetTypeName + "] has no attributes.");
		}
		
		return attributes;			
	};
	
	JsonReader.prototype.getEquipmentTypeByKey = function(equipmentTypeName, equipmentTypeCategory)
	{
		var equipmentTypes = this.getEquipmentTypes();
		
		equipmentTypes = $.grep(equipmentTypes, function(equipmentType)
		{
			return ((equipmentType.AssetTypeName === equipmentTypeName) && (equipmentType.AssetTypeCategory === equipmentTypeCategory));
		});
		
		return equipmentTypes[0];
	};
	
	JsonReader.prototype.getEquipmentTypeDocuments = function(equipmentType)
	{		
		var documents = [];
		
		try
		{
			// Make sure this is a proper array, b/c a single Asset object will be malformed.	
			documents = $.makeArray(equipmentType.AssetTypeDocuments.Document);
		}
		
		catch (e)
		{
			console.log("AssetType [" + equipmentType.AssetTypeName + "] has no documents.");
		}
		
		return documents;
	};
	
	JsonReader.prototype.getEquipmentTypes = function()
	{
		var facility = this.getFacility();	
		var equipmentTypes = [];
		
		try
		{
			// Make sure this is a proper array, b/c a single AssetType object will be malformed.
			equipmentTypes = $.makeArray(facility.AssetTypes.AssetType);
		}
		
		catch (e)
		{
			console.log("Facility [" + facility.FacilityName + "] has no equipment types.");
		}
		
		return equipmentTypes;
	};
	
	JsonReader.prototype.getEquipmentTypesByFloor = function(floor)
	{
		var equipmentTypes = this.getEquipmentTypes();
		var jsonReader = this;
		
		equipmentTypes = $.grep(equipmentTypes, function(equipmentType)
		{
			var equipmentOnFloor = false;
			var equipment = jsonReader.getEquipmentByType(equipmentType);
			
			$.each(equipment, function(i, asset)
			{
				var spaceAssignments = jsonReader.getEquipmentSpaceAssignments(asset);
				
				$.each(spaceAssignments, function(i, spaceAssignment)
				{
					if ((spaceAssignment.FloorName === floor.FloorName) && (spaceAssignment.SpaceName))
					{
						equipmentOnFloor = true;
						return false; // Breaks out of each().
					}
				});
			});
			
			return equipmentOnFloor;
		});		
		
		return equipmentTypes;
	};
	
	JsonReader.prototype.getEquipmentTypesBySpace = function(space)
	{
		var equipmentTypes = this.getEquipmentTypes();
		var jsonReader = this;
		var utils = new Utils();
		
		equipmentTypes = $.grep(equipmentTypes, function(equipmentType)
		{
			var equipmentInSpace = false;
			var equipment = jsonReader.getEquipmentByType(equipmentType);

			$.each(equipment, function(i, asset)
			{
				var spaceAssignments = jsonReader.getEquipmentSpaceAssignments(asset);
				
				$.each(spaceAssignments, function(i, spaceAssignment)
				{
					if (spaceAssignment.SpaceName === space.SpaceName)
					{
						equipmentInSpace = true;
						return false; // Breaks out of each().
					}
				});
			});

			return equipmentInSpace;
		});		

		
		var uniqueEquipmentTypes = {};
				
		$.each(equipmentTypes, function(i, assetType)
		{
			var key = assetType.AssetTypeName + utils.getDefaultSeparator() + assetType.AssetTypeCategory;
			uniqueEquipmentTypes[key] = assetType;
		});
		
		equipmentTypes = $.map(uniqueEquipmentTypes, function(assetType)
		{
			return assetType;
		});
		
		return equipmentTypes;
	};
	
	JsonReader.prototype.getFacility = function()
	{	
		return  new LocalStore().getJSON();
	};

	JsonReader.prototype.getFloorByKey = function(floorName)
	{	
		var facility = this.getFacility();
		var floors = this.getFloors(facility);
		
		floors = $.grep(floors, function(floor)
		{
			return (floor.FloorName === floorName);
		});
		
		return floors[0];
	};

	JsonReader.prototype.getFloors = function(facility)
	{		
		var floors = [];
		
		try
		{
			// Make sure this is a proper array, b/c a single Floor object will be malformed.
			floors = $.makeArray(facility.Floors.Floor);
		}
		
		catch (e)
		{
			console.log("Facility [" + facility.FacilityName + "] has no floors.");
		}
		
		return floors;
	};

	JsonReader.prototype.getFloorsWithSpaces = function(facility)
	{		
		var floors = this.getFloors(facility);
		var floorsWithSpaces = [];
		var jsonReader = this;
		
		$.each(floors, function(i, floor)
		{		
			var spacesInFloor = jsonReader.getSpacesByFloor(floor);
			
			if (spacesInFloor.length > 0)
			{
				floorsWithSpaces.push(floor);
			}
		});
		
		return floorsWithSpaces;
	};
	
	JsonReader.prototype.getSpaceByKey = function(spaceName)
	{
		var facility = this.getFacility();
		var spaces = this.getSpaces(facility);
		
		spaces = $.grep(spaces, function(space)
		{
			return (space.SpaceName === spaceName);
		});
		
		return spaces[0];
	};
	
	JsonReader.prototype.getSpaceCategories = function(spaces)
	{
		var categories = [];
		
		$.each(spaces, function(i, space)
		{
			categories.push(space.SpaceCategory);
		});
		
		return categories;
	};

	JsonReader.prototype.getSpaces = function(facility)
	{
		var floors = this.getFloors(facility);	
		var spaces = [];
		var jsonReader = this;
		
		$.each(floors, function(i, floor)
		{
			var floorSpaces = jsonReader.getSpacesByFloor(floor);	
		
			if (floorSpaces)
			{
				spaces = spaces.concat(floorSpaces);
			}				
		});

		return spaces;
	};

	JsonReader.prototype.getSpacesByCategory = function(floor, category) 
	{
		var spacesOnFloor = this.getSpacesByFloor(floor);
		var spaces = [];
		
		try 
		{
			$.each(spacesOnFloor, function(i, space) 
			{
				if (space.SpaceCategory === category)
				{
					spaces.push(space);
				}
			});
		} 
		
		catch (e) 
		{
			console.log("Category [" + category + "] has no spaces.");
		}
		
		return spaces;
	};
	
	JsonReader.prototype.getSpacesByFloor = function(floor)
	{
		var spaces = [];
		
		try
		{
			// Make sure this is a proper array, b/c a single Space object will be malformed.
			spaces = floor.Spaces.Space;
		}
		
		catch (e)
		{
			console.log("Floor [" +	floor.FloorName + "] has no spaces.");
		}
		
		return spaces;
	};
	
	JsonReader.prototype.getSpacesByZone = function(floor, zone)
	{		
		var jsonReader = this;
		
		var zoneSpaces = $.grep(this.getSpacesByFloor(floor), function(space)
		{												
			var zoneIncludesSpace = false;
			var zoneAssignments = jsonReader.getZoneAssignmentsBySpace(space);
			
			$.each(zoneAssignments, function(i, zoneAssignment)
			{
				if ((zoneAssignment.ZoneName === zone.ZoneName) && (zoneAssignment.ZoneCategory === zone.ZoneCategory))
				{
					zoneIncludesSpace = true;
					return false; // Break out of each().
				}
			});
			
			return zoneIncludesSpace;
		});
		
		return zoneSpaces;
	};
	
	JsonReader.prototype.getZoneAssignmentsBySpace = function(space)
	{
		var zoneAssignments = [];
		
		try
		{
			// Make sure this is a proper array, b/c a single ZoneAssignment object will be malformed.
			zoneAssignments = $.makeArray(space.SpaceZoneAssignments.ZoneAssignment);
		}
		
		catch (e)
		{
			console.log("Space [" + space.SpaceName + "] has no zone assignments.");
		}
		
		return zoneAssignments;
	};
	
	JsonReader.prototype.getZoneByKey = function(zoneName, zoneCategory)
	{
		var facility = this.getFacility();
		var zones = this.getZones(facility);
		
		zones = $.grep(zones, function(zone)
		{
			return ((zone.ZoneName === zoneName) && (zone.ZoneCategory === zoneCategory));
		});
		
		return zones[0];
	};
	
	JsonReader.prototype.getZones = function(facility)
	{
		var zones = [];
		
		try
		{
			// Make sure this is a proper array, b/c a single Zone object will be malformed.
			zones = $.makeArray(facility.Zones.Zone);
		}
		
		catch (e)
		{
			console.log("Facility [" + facility.FacilityName + "] has no zones.");
		}
		
		return zones;		
	};
	
	JsonReader.prototype.getZonesByFloor = function(floor)
	{
		var allZones = this.getZones(this.getFacility());
		var floorSpaces = this.getSpacesByFloor(floor);		
		var floorZones = [];
		var jsonReader = this;
		
		$.each(floorSpaces, function(i, space)
		{
			var zoneAssignments = jsonReader.getZoneAssignmentsBySpace(space);
			
			floorZones = $.map(zoneAssignments, function(zoneAssignment)
			{				
				var matchingZone;
				
				$.each(allZones, function(i, zone)
				{
					if ((zone.ZoneName === zoneAssignment.ZoneName) && (zone.ZoneCategory === zoneAssignment.ZoneCategory))
					{
						matchingZone = zone;
						return false; // Break out of each().
					}
				});
				
				return matchingZone;
			});		
		});

		return floorZones;		
	};
	
	JsonReader.prototype.loadJSON = function(jsonFileName)
	{	
		$.ajaxSetup({ async: false });
		
		$.getJSON(jsonFileName, function(json)
		{
			console.log("JSON loaded...");	
			new LocalStore().addJSON(json);
		});
	};
	
	return JsonReader;
})();

