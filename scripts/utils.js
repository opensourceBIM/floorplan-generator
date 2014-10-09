var Utils = (function()
{
	function Utils()
	{
		
	}

	Utils.prototype.addHighlights = function(image, areas)
	{
		$.each(areas, function()
		{
			document.getElementById("imageMapFrame").contentWindow.highlight(image.id, this.id);
		});
	};
	
	Utils.prototype.checkFileName = function(fileName)
	{
		var checkedFileName = "";
		
		if (!fileName)
		{
			checkedFileName = this.getDefaultFileName();	
		}
		
		else
		{
			checkedFileName = fileName;
			
			// I can't be arsed to figure out a regex for this.
			checkedFileName = checkedFileName.replace("[", "_");
			checkedFileName = checkedFileName.replace("]", "_");
			checkedFileName = checkedFileName.replace(":", "_");
			checkedFileName = checkedFileName.replace("\\", "_");
			checkedFileName = checkedFileName.replace("/", "_");
			checkedFileName = checkedFileName.replace("*", "_");
			checkedFileName = checkedFileName.replace("?", "_");
			checkedFileName = checkedFileName.replace("|", "_");
			checkedFileName = checkedFileName.replace("<", "_");
			checkedFileName = checkedFileName.replace(">", "_");
		}
		
		return checkedFileName;
	};
	
	Utils.prototype.defaultConverter = function(value)
	{
		return value;
	};
	
	Utils.prototype.getAttributeValue = function(attributeValue)
	{
		var value;
		
		if (attributeValue.AttributeBooleanValue)
		{					
			value = attributeValue.AttributeBooleanValue.BooleanValue;
		}
		
		else if (attributeValue.AttributeDecimalValue)
		{
			var decimalValue = attributeValue.AttributeDecimalValue;					
			value = decimalValue.DecimalValue;
			
			if (decimalValue.UnitName)
			{
				value += " " + decimalValue.UnitName;
			}
		}
		
		else if (attributeValue.AttributeIntegerValue)
		{
			var integerValue = attributeValue.AttributeIntegerValue;
			value = integerValue.IntegerValue;
			
			if (integerValue.UnitName)
			{
				value += " " + integerValue.UnitName;
			}
		}
		
		else if (attributeValue.AttributeStringValue)
		{
			value = attributeValue.AttributeStringValue.StringValue;
		}	
		
		else
		{
			console.log("Supplied AttributeValue not supported!");
		}
		
		return value;
	};
	
	Utils.prototype.getCompoundKeys = function(option)
	{
		return option.Value.split(this.getDefaultSeparator());
	};
	
	Utils.prototype.getDefaultFileName = function()
	{
		var defaultFileName = "default_";
		var localStore = new LocalStore();
		var numDefaultsUsed = localStore.get("defaults_used");	
		
		if (numDefaultsUsed)
		{
			numDefaultsUsed++;		
		}
		
		else
		{
			numDefaultsUsed = 1;
		}
		
		localStore.add("defaults_used", numDefaultsUsed);		
		return defaultFileName + numDefaultsUsed;
	};
	
	Utils.prototype.getDefaultSeparator = function()
	{
		// This has to be something that will not show up in the key values.
		return "[default_key_separator]";
	};
	
	Utils.prototype.getDefaultValue = function()
	{
		return "none";
	}
	
	Utils.prototype.getImageMap = function()
	{
		return $("#imageMapFrame").contents();
	};
	
	Utils.prototype.getImageMapAreas = function(space)
	{
		return this.getImageMap().find("area[class='" + space.SpaceName + "']");
	};

	// This function returns unique option objects for the supplied collection of items.
	// Options are of the form { Name, Value }. The supplied converter should be capable 
	// of putting an individual item into this form.
    Utils.prototype.getUniqueOptions = function(items, converter)
    {
    	if (!converter)
    	{
    		console.log("The supplied converter is not defined. Attempting to use default.");
    		converter = this.defaultConverter;
    	}
    	
        var hashedOptions = {};

        $.each(items, function(i, item)
        {
        	var option = converter(item);
        	
            // Hash options based on their values (labels might be duplicates, 
            // but values should be keys).
            hashedOptions[option.Value] = option.Label;
        });

        var uniques = [];

        $.each(hashedOptions, function(value, key)
        {
            uniques.push({ Label: key, Value: value });
        });

        return uniques;
    };
    
	Utils.prototype.getURLParameter = function(parameterName)
	{
		var url = window.location.search.substring(1);
		var parameters = url.split("&");
		var parameterValue;
		
		for (var i = 0; i < parameters.length; i++)
		{
			var parameterArray = parameters[i].split("=");
			
			if (parameterArray[0] === parameterName)
			{
				parameterValue = parameterArray[1];
				break;
			}
		}
		
		return parameterValue;
	};
	
	Utils.prototype.removeHighlights = function()
	{
		try
		{
			var localStore = new LocalStore();		
			var spaces = new JsonReader().getSpacesByFloor(localStore.getSelectedFloor());
			var image = localStore.getImage();
			
			$.each(spaces, function(i, space)
			{
				var areas = new Utils().getImageMapAreas(space);
				
				$.each(areas, function(i, area)
				{
					document.getElementById("imageMapFrame").contentWindow.unHighlight(image.id, area.id);
				});
			});
		}
		
		catch (e)
		{
			// This will occur if a floor has not been selected yet, i.e. when FloorMenu is initializing.
		}
	};
	
	Utils.prototype.setElementHtml = function(selector, value, defaultValue, converter)
	{		
		if (value)
		{
			if (!converter)
			{
				converter = this.defaultConverter;				
			}
			
			$(selector).html(converter(value));
		}
		
		else
		{
			$(selector).html(defaultValue);
		}
	};
	
	Utils.prototype.setListHtml = function(selector, values, defaultInfo, converter)
	{		
		var useDefault = defaultInfo.Force;
						                                                               
		if (!values || (values.length < 1))
		{
			useDefault = true;
		}
				
		if (!converter)
		{
			converter = this.defaultConverter;			
		}

		if (useDefault)
		{
			$(selector).append("<li>" + defaultInfo.Value + "</li>");
		}
		
		$.each(values, function(i, value)
		{
			$(selector).append("<li>" + converter(value) + "</li>");
		});
	};
	
	return Utils;
})();

