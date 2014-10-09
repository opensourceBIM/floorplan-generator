package org.erdc.cobie.graphics3d;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.erdc.cobie.graphics.COBieColor;
import org.erdc.cobie.graphics.InvalidMeshException;
import org.erdc.cobie.graphics.settings.MaterialSettings;
import org.erdc.cobie.graphics.string.Default;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.geometry.Stripifier;
import com.sun.j3d.utils.geometry.Triangulator;

public class Utils
{
	private static NormalGenerator normalizer;
	private static Stripifier stripifier;
	private static Triangulator triangulator;

	public static void generateNormals(GeometryInfo mesh)
	{
		if (Utils.normalizer == null)
		{
			Utils.normalizer = new NormalGenerator();
		}

		Utils.normalizer.generateNormals(mesh);
	}

	public static Point3d getCenter(Shape3D shape)
	{
		Point3d center = new Point3d();

		if (shape.getBounds() instanceof BoundingBox)
		{
			BoundingBox boundingBox = (BoundingBox)shape.getBounds();
			Point3d lower = new Point3d();
			Point3d upper = new Point3d();

			boundingBox.getLower(lower);
			boundingBox.getUpper(upper);

			center = Math.getMidpoint(lower, upper);
		}

		else if (shape.getBounds() instanceof BoundingSphere)
		{
			BoundingSphere boundingSphere = (BoundingSphere)shape.getBounds();
			boundingSphere.getCenter(center);
		}

		return center;
	}

	public static Appearance makeAppearance(PolygonAttributes polygonAttributes, Color3f color)
	{
		Appearance appearance = new Appearance();
		appearance.setMaterial(makeMaterial(color));
		appearance.setPolygonAttributes(polygonAttributes);

		return appearance;
	}

	public static Appearance makeAppearance(PolygonAttributes polygonAttributes, Color3f ambientColor, Color3f emissiveColor, Color3f diffuseColor, Color3f specularColor, float shininess)
	{
		Appearance appearance = new Appearance();
		appearance.setMaterial(makeMaterial(ambientColor, emissiveColor, diffuseColor, specularColor, shininess));
		appearance.setPolygonAttributes(polygonAttributes);

		return appearance;
	}

	public static Material makeMaterial(Color3f color)
	{
		return makeMaterial(color, COBieColor.BLACK.toColor3f(), color, color, MaterialSettings.DEFAULT_SHINYNESS);
	}

	public static Material makeMaterial(Color3f ambientColor, Color3f emissiveColor, Color3f diffuseColor, Color3f specularColor, float shininess)
	{
		return new Material(Math.attenuateColor(ambientColor, MaterialSettings.DEFAULT_AMBIENT_ATTENUATION), emissiveColor, diffuseColor, specularColor, shininess);
	}

	public static GeometryInfo makeMesh(List<Point3f> vertices)
	{
		return Utils.makeMesh(vertices.toArray(new Point3f[vertices.size()]));
	}

	public static GeometryInfo makeMesh(List<Point3f> vertices, List<Vector3f> normals)
	{
		Point3f[] verticesArray = vertices.toArray(new Point3f[vertices.size()]);
		Vector3f[] normalsArray = normals.toArray(new Vector3f[normals.size()]);

		return Utils.makeMesh(verticesArray, normalsArray);
	}

	public static GeometryInfo makeMesh(Point3f[] vertices)
	{
		return Utils.makeMesh(vertices, null);
	}

	public static GeometryInfo makeMesh(Point3f[] vertices, Vector3f[] normals)
	{
		GeometryInfo mesh = new GeometryInfo(GeometryInfo.TRIANGLE_ARRAY);
		mesh.setCoordinates(vertices);

		if (normals != null)
		{
			mesh.setNormals(normals);
		}

		else
		{
			Utils.generateNormals(mesh);
		}

		// Utils.stripifyMesh(mesh);
		// mesh.compact();

		return mesh;
	}

	public static void stripifyMesh(GeometryInfo mesh)
	{
		if (Utils.stripifier == null)
		{
			Utils.stripifier = new Stripifier();
		}

		Utils.stripifier.stripify(mesh);
	}

	public static void triangulateMesh(GeometryInfo mesh)
	{
		if (Utils.triangulator == null)
		{
			Utils.triangulator = new Triangulator();
		}

		Utils.triangulator.triangulate(mesh);
	}

	public static void transformMesh(Transform3D transform, GeometryInfo mesh) throws InvalidMeshException
	{
		if (transform != null)
		{
			try
			{
				List<Point3f> transformedCoordinates = new ArrayList<>();
				for (Point3f point : mesh.getCoordinates())
				{
					Point3f transformedPoint = (Point3f)point.clone();
					transform.transform(transformedPoint);
					transformedCoordinates.add(transformedPoint);
				}

				List<Vector3f> transformedNormals = new ArrayList<>();
				for (Vector3f normal : mesh.getNormals())
				{
					Vector3f transformedNormal = (Vector3f)normal.clone();
					transform.transform(transformedNormal);
					transformedNormals.add(transformedNormal);
				}

				mesh.setCoordinates(transformedCoordinates.toArray(new Point3f[transformedCoordinates.size()]));
				mesh.setNormals(transformedNormals.toArray(new Vector3f[transformedNormals.size()]));
			}
			catch (NullPointerException e)
			{
				throw new InvalidMeshException(Default.INVALID_MESH.toString());
			}
		}
	}
}
