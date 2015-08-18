package org.bimserver.cobie.graphics2d;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point2f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import org.bimserver.cobie.graphics.InvalidMeshException;
import org.bimserver.cobie.graphics.SettingsUser;
import org.bimserver.cobie.graphics.settings.GlobalSettings;
import org.bimserver.cobie.graphics.string.Default;
import org.bimserver.cobie.graphics3d.Math;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.j3d.utils.geometry.GeometryInfo;

public class PolygonBuilder implements SettingsUser
{
    private final GeometryInfo mesh;
    private final GlobalSettings settings;
    private Polygon polygon;

    public PolygonBuilder(GeometryInfo mesh, GlobalSettings settings)
    {
        this.mesh = mesh;
        this.settings = settings;
    }

    private static List<Triangle> buildTriangles(List<Point3f> vertices)
    {
        int step = Math.NUM_TRIANGLE_VERTEXES;
        int numTriangles = vertices.size() / step;
        int count = numTriangles * step;

        List<Triangle> triangles = new ArrayList<Triangle>(numTriangles);

        for (int i = 0; i < count; i += step)
        {
            Point3f a3D = vertices.get(i);
            Point3f b3D = vertices.get(i + 1);
            Point3f c3D = vertices.get(i + 2);

            Point2f a2D = new Point2f(a3D.x, a3D.y);
            Point2f b2D = new Point2f(b3D.x, b3D.y);
            Point2f c2D = new Point2f(c3D.x, c3D.y);

            triangles.add(new Triangle(a2D, b2D, c2D));
        }

        return triangles;
    }

    // TODO This can probably be optimized to only use the triangles from one of
    // the horizontal planes.
    // (using only the bottom plane broke some triangles from the clinic model).
    private Polygon convertToPolygon(GeometryInfo mesh) throws InvalidMeshException
    {
        Polygon polygon = null;

        try
        {
            // List<Point3f> vertices =
            // CollectionUtils.makeList(mesh.getCoordinates());
            List<Point3f> vertices = extractHorizontalPlanes(mesh.getCoordinates(), mesh.getNormals());
            List<Triangle> triangles = PolygonBuilder.buildTriangles(vertices);

            polygon = new Polygon(triangles);
        }

        catch (NullPointerException e)
        {
            throw new InvalidMeshException(Default.NULL_MESH.toString());
        }

        catch (Exception e)
        {
            throw new InvalidMeshException(Default.INVALID_MESH.toString());
        }

        return polygon;
    }

    private List<Point3f> extractHorizontalPlanes(Point3f[] vertices, Vector3f[] normals)
    {
        List<Point3f> horizontalPlaneVertices = new ArrayList<Point3f>();

        for (int i = 0; i < vertices.length; i++)
        {
            Point3f vertex = vertices[i];
            Vector3f normal = normals[i];

            double depthComponent = Math.getDepthComponent(new Point3d(normal), getSettings().getRenderSettings3D().getOrientation());

            if (!org.bimserver.cobie.graphics.Math.approxEqual(depthComponent, 0.0))
            {
                horizontalPlaneVertices.add(vertex);
            }
        }

        return horizontalPlaneVertices;
    }

    @Override
    public final Logger getLogger()
    {
        return LoggerFactory.getLogger(getClass());
    }

    public Polygon getPolygon() throws InvalidMeshException
    {
        if (polygon == null)
        {
            polygon = convertToPolygon(mesh);
        }

        return this.polygon;
    }

    @Override
    public final GlobalSettings getSettings()
    {
        return settings;
    }
}
