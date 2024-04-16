package SoftwareTestingUnitTests;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
public class TestPolygonShape {
    // Testing: clone()
    @Test
    public void testCloneProducesIdenticalObject() {
        PolygonShape polygonShape = new PolygonShape();
        PolygonShape clonedPolygon = (PolygonShape) polygonShape.clone();
        assertEquals(polygonShape.m_count, clonedPolygon.m_count);
        assertEquals(polygonShape.m_radius, clonedPolygon.m_radius);
        assertEquals(polygonShape.m_centroid, clonedPolygon.m_centroid);
        assertEquals(polygonShape.m_normals, clonedPolygon.m_normals);
        assertEquals(polygonShape.m_vertices, clonedPolygon.m_vertices);
        assertEquals(polygonShape.m_type, clonedPolygon.m_type);
    }

    @Test
    public void testCloneProducesDistinctObject() {
        PolygonShape polygonShape = new PolygonShape();
        PolygonShape clonedPolygon = (PolygonShape) polygonShape.clone();
        float prevFloat = polygonShape.m_radius;
        clonedPolygon.m_radius = prevFloat+1;
        assertEquals(prevFloat, polygonShape.m_radius);
    }

    // Testing: getChildCount()
    @Test
    public void testGetChildCount() {
        PolygonShape polygonShape = new PolygonShape();
        assertEquals(1, polygonShape.getChildCount());
    }

    // Testing: set()
    @Test
    // TODO: do EP here
    public void testSet() {
        PolygonShape polygonShape = new PolygonShape();
        Vec2[] vertices = {new Vec2(1,0), new Vec2(1,1), new Vec2(0,1), new Vec2(0,0)};
        int count = 4;
        polygonShape.set(vertices, count);
        assertEquals(count, polygonShape.m_count);
        Vec2[] polygonVertices = Arrays.copyOf(polygonShape.m_vertices, count);
        List<Vec2> verticesList = Arrays.asList(vertices);
        List<Vec2> polygonVerticesList = Arrays.asList(polygonVertices);
        assertTrue(verticesList.containsAll(polygonVerticesList) && polygonVerticesList.containsAll(verticesList));
    }

    // Testing: setAsBox()

    // Testing: testPoint()

    // Testing: computeAABB()

    // Testing: getVertexCount()

    // Testing: raycast()

    // Testing: computeCentroidToOut()

    // Testing: computeMass()

    // Testing: validate()

    // Testing: getVertices()

    // Testing: getNormals()

    // Testing: centroid()

    // Testing: centroidToOut()




}
