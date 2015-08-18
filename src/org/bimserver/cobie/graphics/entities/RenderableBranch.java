package org.bimserver.cobie.graphics.entities;

import java.util.ArrayList;
import java.util.List;

import org.bimserver.emf.IfcModelInterface;
import org.bimserver.models.ifc2x3tc1.IfcBuildingStorey;
import org.bimserver.models.ifc2x3tc1.IfcCovering;
import org.bimserver.models.ifc2x3tc1.IfcDoor;
import org.bimserver.models.ifc2x3tc1.IfcEnergyConversionDevice;
import org.bimserver.models.ifc2x3tc1.IfcFlowController;
import org.bimserver.models.ifc2x3tc1.IfcFlowFitting;
import org.bimserver.models.ifc2x3tc1.IfcFlowMovingDevice;
import org.bimserver.models.ifc2x3tc1.IfcFlowSegment;
import org.bimserver.models.ifc2x3tc1.IfcFlowStorageDevice;
import org.bimserver.models.ifc2x3tc1.IfcFlowTerminal;
import org.bimserver.models.ifc2x3tc1.IfcFurnishingElement;
import org.bimserver.models.ifc2x3tc1.IfcObjectDefinition;
import org.bimserver.models.ifc2x3tc1.IfcProduct;
import org.bimserver.models.ifc2x3tc1.IfcRelDecomposes;
import org.bimserver.models.ifc2x3tc1.IfcSpace;
import org.bimserver.models.ifc2x3tc1.IfcStair;
import org.bimserver.models.ifc2x3tc1.IfcStairFlight;
import org.bimserver.models.ifc2x3tc1.IfcWall;
import org.bimserver.models.ifc2x3tc1.IfcWindow;
import org.bimserver.cobie.graphics.Engine;
import org.bimserver.cobie.graphics.InvalidMeshException;
import org.bimserver.cobie.graphics3d.MeshAggregator;
import org.bimserver.cobie.shared.utility.CollectionUtils;

import com.sun.j3d.utils.geometry.GeometryInfo;

public abstract class RenderableBranch extends RenderableIfcProduct
{
    private GeometryInfo aggregateMesh;
    private List<RenderableIfcProduct> leaves;
    private List<RenderableBranch> branches;

    protected RenderableBranch(IfcModelInterface model, Engine engine, IfcProduct product)
    {
        super(model, engine, product);
        leaves = getLeaves();
        branches = getBranches();
    }

    private static void parseTree(RenderableBranch renderableBranch, List<RenderableIfcProduct> children)
    {
        CollectionUtils.addAllIgnoreDuplicates(children, renderableBranch.getLeaves());
        
        for (RenderableBranch leafParent : renderableBranch.getBranches())
        {
            if (!children.contains(leafParent))
            {
                children.add(leafParent);
            }

            CollectionUtils.addAllIgnoreDuplicates(children, leafParent.getLeaves());

            RenderableBranch leafBranch = leafParent;
            parseTree(leafBranch, children);
        }
    }

    public final GeometryInfo getAggregateMesh()
    {
        if (aggregateMesh == null)
        {
            aggregateMesh = makeAggregateMesh();
        }

        return aggregateMesh;
    }

    public final List<RenderableBranch> getBranches()
    {
        if (branches == null)
        {
            branches = makeBranches();
        }
        
        return branches;
    }

    public final List<RenderableIfcProduct> getLeaves()
    {
        if (leaves == null)
        {
            leaves = makeLeaves();
        }
        
        return leaves;
    }

    protected List<IfcProduct> getPotentialBranchesAndLeaves()
    {
        List<IfcProduct> leafChildren = new ArrayList<IfcProduct>();
        
        for (IfcRelDecomposes decomposes : this.getProduct().getIsDecomposedBy())
        {
            for (IfcObjectDefinition object : decomposes.getRelatedObjects())
            {
                if (object instanceof IfcProduct)
                {
                    leafChildren.add((IfcProduct)object);
                }
            }
        }
        
        return leafChildren;
    }

    public final List<RenderableIfcProduct> getTree()
    {
        List<RenderableIfcProduct> tree = new ArrayList<RenderableIfcProduct>();
        parseTree(this, tree);
        
        return tree;
    }

    public final boolean hasBranches()
    {
        return !branches.isEmpty();
    }

    public boolean hasLeaves()
    {
        return !getLeaves().isEmpty();
    }

    protected final GeometryInfo makeAggregateMesh()
    {
        MeshAggregator meshAggregator = new MeshAggregator(this);
        return meshAggregator.getAggregateMesh();
    }

    protected List<RenderableBranch> makeBranches()
    {
        List<RenderableBranch> branches = new ArrayList<RenderableBranch>();
        List<Class<? extends IfcProduct>> supportedTypes = getSettings().getRenderSettings().getSupportedTypes();
        
        for (IfcProduct product : getPotentialBranchesAndLeaves())
        {
            if ((product instanceof IfcStair) && supportedTypes.contains(IfcStair.class))
            {
                branches.add(new Stair(getModel(), getEngine(), (IfcStair)product));
            }

            if ((product instanceof IfcSpace) && supportedTypes.contains(IfcSpace.class))
            {
                branches.add(new Space(getModel(), getEngine(), (IfcSpace)product));
            }

            if ((product instanceof IfcBuildingStorey) && supportedTypes.contains(IfcBuildingStorey.class))
            {
                branches.add(new Floor(getModel(), getEngine(), (IfcBuildingStorey)product));
            }
        }

        return branches;
    }

    private List<RenderableIfcProduct> makeLeaves()
    {
        List<RenderableIfcProduct> children = new ArrayList<RenderableIfcProduct>();
        List<Class<? extends IfcProduct>> supportedTypes = getSettings().getRenderSettings().getSupportedTypes();
        
        for (IfcProduct product : getPotentialBranchesAndLeaves())
        {            
            // This doesn't work.
            // if (supportedTypes.contains(product.getClass()))

            RenderableIfcProduct child = null;

            if ((product instanceof IfcDoor) && supportedTypes.contains(IfcDoor.class))
            {
                child = new Door(getModel(), getEngine(), (IfcDoor)product);
            }

            else if ((product instanceof IfcWall) && supportedTypes.contains(IfcWall.class))
            {
                child = new Wall(getModel(), getEngine(), (IfcWall)product);
            }

            else if ((product instanceof IfcCovering) && supportedTypes.contains(IfcCovering.class))
            {
                child = new Covering(getModel(), getEngine(), (IfcCovering)product);
            }

            else if ((product instanceof IfcStairFlight) && supportedTypes.contains(IfcStairFlight.class))
            {
                child = new StairFlight(getModel(), getEngine(), (IfcStairFlight)product);
            }

            else if ((product instanceof IfcWindow) && supportedTypes.contains(IfcWindow.class))
            {
                child = new Window(getModel(), getEngine(), (IfcWindow)product);
            }

            else if ((product instanceof IfcFurnishingElement) && supportedTypes.contains(IfcFurnishingElement.class))
            {
                child = new Furnishing(getModel(), getEngine(), (IfcFurnishingElement)product);
            }

            else if ((product instanceof IfcFlowSegment) && supportedTypes.contains(IfcFlowSegment.class))
            {
                child = new FlowSegment(getModel(), getEngine(), (IfcFlowSegment)product);
            }

            else if ((product instanceof IfcFlowFitting) && supportedTypes.contains(IfcFlowFitting.class))
            {
                child = new FlowFitting(getModel(), getEngine(), (IfcFlowFitting)product);
            }
            
            else if ((product instanceof IfcFlowTerminal) && supportedTypes.contains(IfcFlowTerminal.class))
            {
                child = new FlowTerminal(getModel(), getEngine(), (IfcFlowTerminal)product);
            }
            
            else if ((product instanceof IfcEnergyConversionDevice) && supportedTypes.contains(IfcEnergyConversionDevice.class))
            {
                child = new EnergyConversionDevice(getModel(), getEngine(), (IfcEnergyConversionDevice)product);
            }
            
            else if ((product instanceof IfcFlowController) && supportedTypes.contains(IfcFlowController.class))
            {
                child = new FlowController(getModel(), getEngine(), (IfcFlowController)product);
            }
            
            else if ((product instanceof IfcFlowMovingDevice) && supportedTypes.contains(IfcFlowMovingDevice.class))
            {
                child = new FlowMovingDevice(getModel(), getEngine(), (IfcFlowMovingDevice)product);
            }
            
            else if ((product instanceof IfcFlowStorageDevice) && supportedTypes.contains(IfcFlowStorageDevice.class))
            {
                child = new FlowStorageDevice(getModel(), getEngine(), (IfcFlowStorageDevice)product);
            }
            
            if (child != null)
            {
                children.add(child);
            }
        }
        
        return children;
    }

    @Override
    protected GeometryInfo makeMesh() throws InvalidMeshException
    {
        return getAggregateMesh();
    }
}
