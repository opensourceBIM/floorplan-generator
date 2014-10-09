var DocumentsMenu = (function ()
{
    function DocumentsMenu()
    {

    }

    DocumentsMenu.prototype.getBase = function ()
    {
        return new Menu();
    };

    DocumentsMenu.prototype.getButton = function ()
    {
        return $("#documentsMenuButton");
    };

    DocumentsMenu.prototype.getForm = function ()
    {
        return $("#documentsMenuOptions");
    };

    DocumentsMenu.prototype.init = function ()
    {
        this.getBase().init(this);
    };

    DocumentsMenu.prototype.populate = function ()
    {
        this.getBase().populate(this);

        var instance = this;
        var form = this.getForm();
        var html = "<li><a id='{id}' class='menuItem' href='{link}' target='_blank'>{name}</a></li>";
        var documents = [];

documents.push({ Link: "doc/2013-03-28-Clinic-Handover-v13.xlsx", Name: "COBie Spreadsheet"});
documents.push({ Link: "doc/Clinic Handover_COBie QC Report - Construction Deliverable_262147.html", Name: "COBie QC Report (Construction)"});
documents.push({ Link: "doc/Clinic Handover_COBie QC Report - Design Deliverable_262147.html", Name: "COBie QC Report (Design)"});
documents.push({ Link: "doc/Clinic Handover_COBieRoomDataSheet_262147.html", Name: "Room Datasheet"});
documents.push({ Link: "doc/Clinic Handover_COBieSpatialDecompositionReport_262147.html", Name: "Spatial Decomposition"});
documents.push({ Link: "doc/Clinic Handover_COBieSystemReport_262147.html", Name: "System Report"});
documents.push({ Link: "doc/Clinic Handover_COBieZoneReport_262147.html", Name: "Zone Report"});
documents.push({ Link: "doc/Clinic_A_20110906.ifc", Name: "Architectural Model (IFC)"});
documents.push({ Link: "doc/dd1354.html", Name: "Form DD1354"});
documents.push({ Link: "doc/dd1391.html", Name: "Form DD1391"});


        $.each(documents, function (i, document)
        {
            var menuItemHtml = html.replace("{id}", document.Name);
            menuItemHtml = menuItemHtml.replace("{link}", document.Link);
            menuItemHtml = menuItemHtml.replace("{name}", document.Name);

            $("#documentsMenuOptions").append(menuItemHtml);
        });

        form.menu(
		{
		    select: function (event, selectedItem)
		    {
		        instance.select(selectedItem.item);
		    }
		});

        form.menu("refresh");
    };

    DocumentsMenu.prototype.select = function (selectedItem)
    {
        this.getBase().close(this);
    };

    return DocumentsMenu;
})();