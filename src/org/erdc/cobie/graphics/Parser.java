package org.erdc.cobie.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Transform3D;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.bimserver.geometry.Matrix;
import org.bimserver.models.geometry.GeometryData;
import org.bimserver.models.ifc2x3tc1.IfcProduct;
import org.erdc.cobie.graphics.string.Default;
import org.erdc.cobie.shared.utility.CollectionUtils;

/**
 * Parses an {@code IfcProduct} into a renderable COBie object.
 */
public class Parser
{
    private static final int BYTES_PER_VERTEX = 12;
    
	private final IfcProduct product;
    private List<Point3f> vertices;
    private List<Vector3f> normals;
    private List<Integer> indices;
    private Transform3D transform;
    
    /**
     * Constructs a COBieParser instance.
     * 
     * @param product The {@code IfcProduct} to parse.
     */
    public Parser(IfcProduct product)
    {
    	this.product = product;
    }
    
    private final static Vector3f getNormal(ByteBuffer normalsBuffer)
    {
        float xNormal = normalsBuffer.getFloat();
        float yNormal = normalsBuffer.getFloat();
        float zNormal = normalsBuffer.getFloat();
        return new Vector3f(xNormal, yNormal, zNormal);
    }

    private final static Point3f getVertex(ByteBuffer verticesBuffer)
    {
	    float x = verticesBuffer.getFloat();
	    float y = verticesBuffer.getFloat();
	    float z = verticesBuffer.getFloat(); 
	    return new Point3f(x, y, z);
    }

    /**
     * Gets the vertex normals parsed by this Transformer. 
     * 
     * @return The parsed vertex normals.
     */
    public final List<Vector3f> getNormals()
    {
        return normals;
    }

    /**
     * Gets the vertices parsed by this Transformer.
     * 
     * @return The parsed vertices.
     */
    public final List<Point3f> getVertices()
    {
        return vertices;
    }

    /**
     * Parses this Transformer's data.
     * 
     * @throws InvalidMeshException if the parsing fails.
     */
    public final void parse() throws InvalidMeshException
    {
        try
        {
        	GeometryData geometryInstance = product.getGeometry().getData();
        	
            ByteBuffer verticesBuffer = ByteBuffer.wrap(geometryInstance.getVertices());
            ByteBuffer normalsBuffer = ByteBuffer.wrap(geometryInstance.getNormals());
            ByteBuffer indexBuffer = ByteBuffer.wrap(geometryInstance.getIndices());
            ByteBuffer transformBuffer = ByteBuffer.wrap(product.getGeometry().getTransformation());
            
            verticesBuffer.order(ByteOrder.LITTLE_ENDIAN);
            normalsBuffer.order(ByteOrder.LITTLE_ENDIAN);
            indexBuffer.order(ByteOrder.LITTLE_ENDIAN);
            transformBuffer.order(ByteOrder.LITTLE_ENDIAN);
            
            vertices = new ArrayList<Point3f>();
            normals = new ArrayList<Vector3f>();
            indices = getIndices(indexBuffer);
            transform = getTransform(transformBuffer);
            
            for(int i= 0; i < verticesBuffer.capacity() / BYTES_PER_VERTEX ; i++)
            {
        		CollectionUtils.appendList(vertices, getVertex(verticesBuffer));
        		CollectionUtils.appendList(normals, getNormal(normalsBuffer));
            }
            
            sortVerticesAndNormals();
        }
        
        catch(NullPointerException ex)
        {
            throw new InvalidMeshException(Default.NO_GEOMETRY.toString());
        }
    }

	private Transform3D getTransform(ByteBuffer transformation)
	{
		float[] matrix = new float[16];
		FloatBuffer floatBuffer = transformation.asFloatBuffer();
		
		for (int i=0; i< matrix.length; i++) 
		{
			matrix[i] = floatBuffer.get();
		}
		
		matrix = Matrix.changeOrientation(matrix);
		return new Transform3D(matrix);
	}

	private void sortVerticesAndNormals()
	{
		List<Point3f> sortedVertices = new ArrayList<>(vertices.size());
		List<Vector3f> sortedNormals = new ArrayList<>(normals.size());
		
		for(int index : indices)
		{
			sortedVertices.add(vertices.get(index));
			sortedNormals.add(normals.get(index));
		}
		
		vertices = sortedVertices;
		normals = sortedNormals;
	}
	
	private List<Integer> getIndices(ByteBuffer indexBuffer)
	{
		List<Integer> indices = new ArrayList<>();
		
		for(int i=0; i < indexBuffer.capacity() / 4; i ++)
		{
			indices.add(indexBuffer.getInt());
		}
		
		return indices;
	}

	public Transform3D getTransform()
	{
		return transform;
	}
}
