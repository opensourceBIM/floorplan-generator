var FloorMenu = (function ()
{
    function FloorMenu()
    {

    }

    FloorMenu.prototype.getBase = function ()
    {
        return new Menu();
    };

    FloorMenu.prototype.getForm = function ()
    {
        return $("#floorMenuOptions");
    };

    FloorMenu.prototype.getButton = function ()
    {
        return $("#floorMenuButton");
    };

    FloorMenu.prototype.getImageMapFrame = function ()
    {
        return $("#imageMapFrame");
    };

    FloorMenu.prototype.getImageMapKeyFrame = function ()
    {
        return $("#imageMapKeyFrame");
    };

    FloorMenu.prototype.init = function ()
    {
        this.getBase().init(this);
        this.select("");
    };

    FloorMenu.prototype.initChildMenus = function ()
    {
        new CategoryMenu().init();
        new ZoneMenu().init();
        new SpaceMenu().init();
        new EquipmentMenu().init();
        //new DocumentsMenu().init();
        new SpaceInfo().hide();
    };

    FloorMenu.prototype.makeOption = function (item)
    {
        return { Label: item.FloorName, Value: item.FloorName };
    };

    FloorMenu.prototype.populate = function ()
    {
        this.getBase().populate(this);
        var floors = [];

        try
        {
            var facility = new JsonReader().getFacility();
            floors = new JsonReader().getFloorsWithSpaces(facility);
        }

        catch (e)
        {
            console.log("Facility [" + facility.FacilityName + "] has no floors.");
        }

        this.getBase().writeOptions(this, floors, false);
    };

    FloorMenu.prototype.select = function (selectedItem)
    {
        var selectedOption = this.getBase().select(this, selectedItem);
        var fileName = new Utils().checkFileName(selectedOption.Value);

        this.updateImageMapFrame(fileName);
        this.updateImageMapKeyFrame(fileName);

        var selectedFloor = new JsonReader().getFloorByKey(selectedOption.Value);
        new LocalStore().addSelectedFloor(selectedFloor);

        this.initChildMenus();
    };

    FloorMenu.prototype.updateImageMapFrame = function (fileName)
    {
        var imageMapFrameUrl = fileName + ".html";
        this.getImageMapFrame().attr("src", imageMapFrameUrl);
    };

    FloorMenu.prototype.updateImageMapKeyFrame = function (fileName)
    {
        var imageMapKeyFrameUrl = fileName + "_key.html";
        this.getImageMapKeyFrame().attr("src", imageMapKeyFrameUrl);
    };

    return FloorMenu;
})();

