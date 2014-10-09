package org.erdc.cobie.graphics.entities;

import java.util.ArrayList;
import java.util.List;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcBuilding;
import org.erdc.cobie.graphics.Engine;

public final class Facility extends RenderableSpatialStructureBranch
{
    public Facility(IfcModelInterface model, Engine engine, IfcBuilding building)
    {
        super(model, engine, building);
    }
    
    /**
     * Gets a collection of a Facility's {@code Floor} branches.
     * 
     * Note that this is a convenience method. All of a {@code RenderableBranch}'s branches can be 
     * retrieved via {@code RenderableBranch.getBranches()}.
     * 
     * @return A collection of a Facility's {@code Floor} branches.
     */
    public List<Floor> getFloors()
    {
        List<RenderableBranch> branches = getBranches();
        List<Floor> floors = new ArrayList<Floor>();
        
        for (RenderableBranch branch : branches)
        {
            if (branch instanceof Floor)
            {
                floors.add((Floor)branch);
            }
        }
        
        return floors;
    } 
}
