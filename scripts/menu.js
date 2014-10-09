var Menu = (function()
{
    function Menu()
    {

    }

    Menu.prototype.close = function(instance)
    {
        instance.getForm().hide();
    };

    Menu.prototype.filter = function(instance, values)
    {    	
        values = new Utils().getUniqueOptions(values, instance.makeOption);
        values.sort(this.sortOptions);

        return values;
    };

    Menu.prototype.getDefaultInfo = function(force)
    {
    	return { Value: new Utils().getDefaultValue(), Force: force };
    };
    
    Menu.prototype.getFirstOption = function(instance)
    {
    	return $(instance.getForm().selector + " li").first();
    };
    
    Menu.prototype.getOption = function(selectedItem)
    {
        var label;
        var value;
        var item = $("a", selectedItem);

        // For items that have sub-menus...
        if (item.attr("aria-haspopup"))
        {
            label = item[0].text;
            value = item[0].id;
        }

        else
        {
            label = selectedItem.text();
            value = item.attr("id");
        }

        return { Label: label, Value: value };
    };

    Menu.prototype.init = function(instance)
    {
        var menu = this;

        instance.getButton().button({ icons: { primary: "ui-icon-triangle-1-s"} });
        instance.getButton().click(function() { menu.open(instance); });
        instance.populate();
        
        this.reset(instance);
    };

    Menu.prototype.makeOptionHtml = function(option)
    {
        var html = "<a id='{id}' class='menuItem' href='#'>{text}</a>";
        html = html.replace("{id}", option.Value);
        html = html.replace("{text}", option.Label);

        return html;
    };
    
    Menu.prototype.open = function(instance)
    {
        instance.getForm().show();
    };

    Menu.prototype.populate = function(instance)
    {
        this.close(instance);
    };

    Menu.prototype.reset = function(instance)
    {
    	this.select(instance, this.getFirstOption(instance));
    	this.close(instance);
    };
    
    Menu.prototype.select = function(instance, selectedItem)
    {
        if (!selectedItem)
        {
            selectedItem = this.getFirstOption(instance);
        }

        var selectedOption = this.getOption(selectedItem);
        instance.getButton().button("option", "label", selectedOption.Label);

        this.unSelect();
        this.close(instance);

        return selectedOption;
    };

    Menu.prototype.sortOptions = function(a, b)
    {
        return (a.Label > b.Label);
    };

    Menu.prototype.unSelect = function()
    {
        new Utils().removeHighlights();
    };

    Menu.prototype.writeOptions = function(instance, items, writeDefaultOptions)
    {    	
    	var utils = new Utils();    	
        var form = instance.getForm();
        var options = this.filter(instance, items);
        var defaultInfo = this.getDefaultInfo(writeDefaultOptions);
        var converter = this.makeOptionHtml;

        utils.setListHtml(instance.getForm(), options, defaultInfo, converter);
        
        form.menu(
		{
		    select: function(event, selectedItem)
		    {
		        instance.select(selectedItem.item);
		    }
		});

        form.menu("refresh");
    };

    return Menu;
})();