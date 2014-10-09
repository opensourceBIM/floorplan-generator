var Panel = (function()
{
	function Panel()
	{
		
	}
	
	Panel.prototype.getDefaultInfo = function()
	{
		return { Value: new Utils().getDefaultValue(), Force: false };
	};
	
	Panel.prototype.getOptions = function()
	{
		return { heightStyle: "content" };
	};
	
	Panel.prototype.hide = function(instance)
	{
		instance.getElement().hide();
	};
	
	Panel.prototype.init = function(instance)
	{
		instance.show();
	};
	
	Panel.prototype.makeAttributeValue = function(attribute)
	{
		var attributeValue = new Utils().getAttributeValue(attribute.AttributeValue);
		return "<div class='label'>" + attribute.AttributeName + ": </div><span class='value'>" + attributeValue + "</span><br/>";
	};
	
	Panel.prototype.makeDocumentValue = function(document)
	{		
		return "<a href=" + document.DocumentURI + " target='_blank'>" + document.DocumentName + " (" + document.DocumentCategory + ")</a>";
	};
	
	Panel.prototype.show = function(instance)
	{
		instance.getElement().show();
	};
	
	return Panel;
})();