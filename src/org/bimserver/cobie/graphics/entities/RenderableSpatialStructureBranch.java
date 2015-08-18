package org.bimserver.cobie.graphics.entities;

import java.util.List;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcProduct;
import org.bimserver.models.ifc2x3tc1.IfcRelContainedInSpatialStructure;
import org.bimserver.models.ifc2x3tc1.IfcSpatialStructureElement;
import org.bimserver.cobie.graphics.Engine;

public abstract class RenderableSpatialStructureBranch extends RenderableBranch
{
    protected RenderableSpatialStructureBranch(IfcModelInterface model, Engine engine, IfcProduct product)
    {
        super(model, engine, product);
    }

    @Override
    protected List<IfcProduct> getPotentialBranchesAndLeaves()
    {
        List<IfcProduct> potentialLeaves = super.getPotentialBranchesAndLeaves();
        IfcSpatialStructureElement spacialStructureElement = (IfcSpatialStructureElement)getProduct();
        
        for (IfcRelContainedInSpatialStructure contains : spacialStructureElement.getContainsElements())
        {
            for (IfcProduct product : contains.getRelatedElements())
            {
                potentialLeaves.add(product);
            }
        }
        
        return potentialLeaves;
    }
}
