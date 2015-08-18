package org.bimserver.cobie.graphics3d;

import java.util.ArrayList;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.bimserver.cobie.graphics.entities.RenderableBranch;
import org.bimserver.cobie.graphics.entities.RenderableIfcProduct;
import org.bimserver.cobie.shared.utility.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.j3d.utils.geometry.GeometryInfo;

public class MeshAggregator
{
    protected static final Logger LOGGER = LoggerFactory.getLogger(MeshAggregator.class);
    private GeometryInfo aggregateMesh;
    private RenderableBranch branch;

    public MeshAggregator(RenderableBranch branch)
    {
        this(branch, true);
    }

    public MeshAggregator(RenderableBranch branch, boolean autoAggregate)
    {
        this.branch = branch;
        
        if (autoAggregate)
        {
            aggregateMesh = aggregate();
        }
    }

    // TODO This should be done in a more robust way.
    public GeometryInfo aggregate()
    {
        ArrayList<Point3f> aggregateVertexes = new ArrayList<Point3f>();
        ArrayList<Vector3f> aggregateNormals = new ArrayList<Vector3f>();

        for (RenderableIfcProduct renderableEntity : branch.getTree())
        {
            try
            {
                GeometryInfo mesh = renderableEntity.getMesh();

                Point3f[] vertexes = mesh.getCoordinates();

                CollectionUtils.appendList(aggregateVertexes, vertexes);

                Vector3f[] normals = mesh.getNormals();
                CollectionUtils.appendList(aggregateNormals, normals);
            } 
            catch (NullPointerException ex)
            {
                LOGGER.warn(renderableEntity.getName() + " cannot be aggregated.  This entity will be ignored");
            }

        }
        return Utils.makeMesh(aggregateVertexes, aggregateNormals);
    }

    public GeometryInfo getAggregateMesh()
    {
        if (aggregateMesh == null)
        {
            aggregateMesh = aggregate();
        }

        return aggregateMesh;
    }
}
