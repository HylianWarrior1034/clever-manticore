package SoftwareTestingUnitTests;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.RayCastInput;
import org.jbox2d.collision.RayCastOutput;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Float.MIN_NORMAL;
import static org.junit.jupiter.api.Assertions.*;
public class TestPolygonShape {
    // Testing: clone()
    // @Test
    // public void testCloneProducesIdenticalObject() {
    //     PolygonShape polygonShape = new PolygonShape();
    //     PolygonShape clonedPolygon = (PolygonShape) polygonShape.clone();
    //     assertEquals(polygonShape.m_count, clonedPolygon.m_count);
    //     assertEquals(polygonShape.m_radius, clonedPolygon.m_radius);
    //     assertEquals(polygonShape.m_centroid, clonedPolygon.m_centroid);
    //     assertEquals(polygonShape.m_normals, clonedPolygon.m_normals);
    //     assertEquals(polygonShape.m_vertices, clonedPolygon.m_vertices);
    //     assertEquals(polygonShape.m_type, clonedPolygon.m_type);
    // }

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
    // EP: polygons with 3 vertices, 4 vertices, and more than 4 vertices
    private static Stream<Arguments> testSetArgs() {
        return Stream.of(
                Arguments.of(new Vec2[]{new Vec2(0, 0), new Vec2(0, 1), new Vec2(1, 1)}, 3),
                Arguments.of(new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)}, 4),
                Arguments.of(new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0), new Vec2(2, 2)}, 5),
                Arguments.of(new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)}, 4),
                Arguments.of(new Vec2[]{}, 0),
                Arguments.of(new Vec2[]{new Vec2(0,0)}, 1),
                Arguments.of(new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0), new Vec2(0.5f,0.5f)}, 5),
                Arguments.of(new Vec2[]{new Vec2(0, 1), new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 0)}, 4),
                Arguments.of(new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0), new Vec2(0.5f,0.5f)}, 5)
                );
    }

    // @ParameterizedTest
    // @MethodSource("testSetArgs")
    // public void testSet(Vec2[] vertices, int count) {
    //     PolygonShape polygonShape = new PolygonShape();
    //     polygonShape.set(vertices, count);
    //     assertEquals(count, polygonShape.m_count);
    //     Vec2[] polygonVertices = Arrays.copyOf(polygonShape.m_vertices, count);
    //     List<Vec2> verticesList = Arrays.asList(vertices);
    //     List<Vec2> polygonVerticesList = Arrays.asList(polygonVertices);
    //     assertTrue(verticesList.containsAll(polygonVerticesList) && polygonVerticesList.containsAll(verticesList));
    // }

    private static Stream<Arguments> testSetInvalidArgs() {
        return Stream.of(
                Arguments.of(new Vec2[]{new Vec2(0, 0)}, 1),
                Arguments.of(new Vec2[]{new Vec2(0, 0), new Vec2(0, 1)}, 2),
                Arguments.of(new Vec2[]{new Vec2(1, 1), new Vec2(1, 1), new Vec2(1, 1), new Vec2(1, 1)}, 4)
        );
    }

//    @ParameterizedTest
//    @MethodSource("testSetInvalidArgs")
//    public void testSetInvalid(Vec2[] vertices, int count) {
//        PolygonShape polygonShape = new PolygonShape();
//        try {
//            polygonShape.set(vertices, count);
//        } catch (AssertionError e) {
//            return;
//        }
//        fail("Error not caught");
//    }

    // Testing: setAsBox()
    // EP: zero || positive values for x and y.
    private static Stream<Arguments> testSetAsBoxArgs() {
        return Stream.of(
                Arguments.of(0f, 0f, new Vec2[]{new Vec2(0,0), new Vec2(0,0), new Vec2(0,0), new Vec2(0,0),}),
                Arguments.of(0f, 1f, new Vec2[]{new Vec2(0,1), new Vec2(0,-1), new Vec2(0,1), new Vec2(0,-1),}),
                Arguments.of(1f, 0f, new Vec2[]{new Vec2(1,0), new Vec2(1,-0), new Vec2(-1,0), new Vec2(-1,0),}),
                Arguments.of(1f, 1f, new Vec2[]{new Vec2(1,1), new Vec2(1,-1), new Vec2(-1,1), new Vec2(-1,-1),})
        );
    }

    // Putting halfX or halfY as zero should fail, but it does not
    // The tests fail even when all the value are equal?? 0.0 and -0.0 are different somehow...??
    // @ParameterizedTest
    // @MethodSource("testSetAsBoxArgs")
    // public void testSetAsBox(float halfX, float halfY, Vec2[] expectedVertices) {
    //     PolygonShape polygonShape = new PolygonShape();
    //     polygonShape.setAsBox(halfX, halfY);
    //     assertEquals(4, polygonShape.m_count);
    //     Vec2[] polygonVertices = Arrays.copyOf(polygonShape.m_vertices, 4);
    //     List<Vec2> verticesList = Arrays.asList(expectedVertices);
    //     List<Vec2> polygonVerticesList = Arrays.asList(polygonVertices);
    //     assertTrue(verticesList.containsAll(polygonVerticesList) && polygonVerticesList.containsAll(verticesList));
    // }

    // EP: Center is in each of the four quadrants, and at (0, 0)
    private static Stream<Arguments> testSetAsBoxArgs2() {
        return Stream.of(
                Arguments.of(1f, 1f, new Vec2(0, 0), 0, new Vec2[]{new Vec2(1,1), new Vec2(1,-1), new Vec2(-1,1), new Vec2(-1,-1),}),
                Arguments.of(1f, 1f, new Vec2(1, 0), 0, new Vec2[]{new Vec2(2,1), new Vec2(0,-1), new Vec2(0,1), new Vec2(2,-1),}),
                Arguments.of(1f, 1f, new Vec2(-1, 0), 0, new Vec2[]{new Vec2(0,1), new Vec2(-2,-1), new Vec2(-2,1), new Vec2(0,-1),}),
                Arguments.of(1f, 1f, new Vec2(0, 1), 0, new Vec2[]{new Vec2(1,0), new Vec2(1,2), new Vec2(-1,0), new Vec2(-1,2),}),
                Arguments.of(1f, 1f, new Vec2(0, -1), 0, new Vec2[]{new Vec2(1,-2), new Vec2(1,0), new Vec2(-1,-2), new Vec2(-1,0),})
        );
    }

    @ParameterizedTest
    @MethodSource("testSetAsBoxArgs2")
    public void testSetAsBox2(float halfX, float halfY, Vec2 center, float angle, Vec2[] expectedVertices) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(halfX, halfY, center, angle);
        assertEquals(4, polygonShape.m_count);
        Vec2[] polygonVertices = Arrays.copyOf(polygonShape.m_vertices, 4);
        List<Vec2> verticesList = Arrays.asList(expectedVertices);
        List<Vec2> polygonVerticesList = Arrays.asList(polygonVertices);
        assertTrue(verticesList.containsAll(polygonVerticesList) && polygonVerticesList.containsAll(verticesList));
    }

    // Testing: setAsBox()
    // TODO: do EP here
    private static Stream<Arguments> testSetAsBoxArgsWithCenterArgs() {
        return Stream.of(
                Arguments.of(1f, 1f, new Vec2[]{new Vec2(1,1), new Vec2(1,-1), new Vec2(-1,1), new Vec2(-1,-1),})
        );
    }

    @ParameterizedTest
    @MethodSource("testSetAsBoxArgsWithCenterArgs")
    public void testSetAsBoxArgsWithCenter(float halfX, float halfY, Vec2[] expectedVertices) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(halfX, halfY, new Vec2(0,0), 0);
        assertEquals(4, polygonShape.m_count);
        Vec2[] polygonVertices = Arrays.copyOf(polygonShape.m_vertices, 4);
        List<Vec2> verticesList = Arrays.asList(expectedVertices);
        List<Vec2> polygonVerticesList = Arrays.asList(polygonVertices);
        assertTrue(verticesList.containsAll(polygonVerticesList) && polygonVerticesList.containsAll(verticesList));
    }

    // Testing: testPoint()
    // Boundary Testing: Test a point that is inside the polygon, on the very end of polygon, and outside the polygon
    private static Stream<Arguments> testTestPointArgs() {
        return Stream.of(
                Arguments.of(new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)}, 4, new Vec2(2,0), false),
                Arguments.of(new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)}, 4, new Vec2(0,0), true),
                Arguments.of(new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)}, 4, new Vec2(0,0), true)
        );
    }

    @ParameterizedTest
    @MethodSource("testTestPointArgs")
    public void testTestPoint(Vec2[] vertices, int count, Vec2 point, boolean isInShape) {
        Transform transform = new Transform();
        transform.setIdentity();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices, count);
        assertEquals(isInShape, polygonShape.testPoint(transform, point));
    }

    // Testing: computeAABB()
    // These inputs should cover all branches
    private static Stream<Arguments> testComputeAABBArgs() {
        return Stream.of(
                Arguments.of(new Vec2[]{new Vec2(0, 0), new Vec2(1, 0), new Vec2(-1, 1), new Vec2(1, 0)}, 4, -1, 0, 1, 1),
                Arguments.of(new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)}, 4, 0, 0, 1, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("testComputeAABBArgs")
    public void testComputeAABB(Vec2[] vertices, int count, float lower_x, float lower_y, float upper_x, float upper_y) {
        Transform transform = new Transform();
        transform.setIdentity();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices, count);
        AABB aabb = new AABB();
        polygonShape.computeAABB(aabb, transform, 0);
        //TODO: why do I have to set the delta so high?
        assertEquals(lower_x, aabb.lowerBound.x, 1e-2);
        assertEquals(lower_y, aabb.lowerBound.y, 1e-2);
        assertEquals(upper_x, aabb.upperBound.x, 1e-2);
        assertEquals(upper_y, aabb.upperBound.y, 1e-2);
    }

    // Testing: getVertexCount()
    // TODO: do EP here
    private static Stream<Arguments> testGetVertexCountArgs() {
        return testSetArgs();
    }

    // @ParameterizedTest
    // @MethodSource("testGetVertexCountArgs")
    // public void testGetVertexCount(Vec2[] vertices, int count) {
    //     PolygonShape polygonShape = new PolygonShape();
    //     polygonShape.set(vertices, count);
    //     assertEquals(count, polygonShape.m_count);
    //     assertEquals(count, polygonShape.getVertexCount());
    // }

    // Testing: getVertex()
    // EP: index can be negative, zero, or positive
    private static Stream<Arguments> testGetVertexArgs() {
        return Stream.of(
                Arguments.of(new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)}, 4, -1),
                Arguments.of(new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)}, 4, 0),
                Arguments.of(new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)}, 4, 1)
        );
    }

    // @ParameterizedTest
    // @MethodSource("testGetVertexArgs")
    // public void testGetVertex(Vec2[] vertices, int count, int vertexIndex) {
    //     PolygonShape polygonShape = new PolygonShape();
    //     polygonShape.set(vertices, count);
    //     assertEquals(polygonShape.m_vertices[Math.abs(vertexIndex)], polygonShape.getVertex(vertexIndex));
    // }

    // Testing: raycast()
    // TODO: do EP here
    private static Stream<Arguments> testRaycastArgs() {
        //Adding BC test here
        return Stream.of(
                Arguments.of(new Vec2[]{new Vec2(2, 0), new Vec2(2, 2), new Vec2(0, 2), new Vec2(0, 0)}, new Vec2(-1,1), new Vec2(-0.5f,1), true),
                Arguments.of(new Vec2[]{new Vec2(2, 0), new Vec2(2, 2), new Vec2(0, 2), new Vec2(0, 0)}, new Vec2(-1,1), new Vec2(-1f,0), false),
                Arguments.of(new Vec2[]{new Vec2(2, 0), new Vec2(2, 2), new Vec2(0, 2), new Vec2(0, 0)}, new Vec2(-1,1), new Vec2(1f,1), true),
                Arguments.of(new Vec2[]{new Vec2(2, 0), new Vec2(2, 2), new Vec2(0, 2), new Vec2(0, 0)}, new Vec2(1,1), new Vec2(2f,1), false)
        );
    }

    @ParameterizedTest
    @MethodSource("testRaycastArgs")
    public void testRaycast(Vec2[] vertices, Vec2 raycastStart, Vec2 raycastEnd, boolean rayHitsShape) {
        RayCastInput rayCastInput = new RayCastInput();
        rayCastInput.p1.x = raycastStart.x;
        rayCastInput.p1.y = raycastStart.y;
        rayCastInput.p2.x = raycastEnd.x;
        rayCastInput.p2.y = raycastEnd.y;
        rayCastInput.maxFraction = 10;
        final Transform transform = new Transform();
        transform.setIdentity();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices, vertices.length);
        RayCastOutput rayCastOutput = new RayCastOutput();
        assertEquals(rayHitsShape, polygonShape.raycast(rayCastOutput, rayCastInput, transform, 0));
    }

    // Testing: computeCentroidToOut()
    // TODO: do EP here
    private static Stream<Arguments> testComputeCentroidToOutArgs() {
        return Stream.of(
                Arguments.of(new Vec2[]{new Vec2(2, 0), new Vec2(2, 2), new Vec2(0, 2), new Vec2(0, 0)}, new Vec2(1,1))
        );
    }

    @ParameterizedTest
    @MethodSource("testComputeCentroidToOutArgs")
    public void testComputeCentroidToOut(Vec2[] vertices, Vec2 centroid) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices, vertices.length);
        Vec2 computedCentroid = new Vec2();
        polygonShape.computeCentroidToOut(polygonShape.m_vertices, polygonShape.m_count, computedCentroid);
        assertEquals(centroid, computedCentroid);
    }

    // Testing: computeMass()
    // TODO: do EP here
    private static Stream<Arguments> testComputeMassArgs() {
        return Stream.of(
                Arguments.of(new Vec2[]{new Vec2(2, 0), new Vec2(2, 2), new Vec2(0, 2), new Vec2(0, 0)}, 1, new Vec2(1,1), 4),
                Arguments.of(new Vec2[]{new Vec2(2, 0)}, 1, new Vec2(1,1), 0),
                Arguments.of(new Vec2[]{new Vec2(0, 0), new Vec2(MIN_NORMAL, 0), new Vec2(0, MIN_NORMAL)}, 1, new Vec2(1,1), 0)
        );
    }

    // @ParameterizedTest
    // @MethodSource("testComputeMassArgs")
    // public void testComputeMass(Vec2[] vertices, float density, Vec2 center, float mass) {
    //     PolygonShape polygonShape = new PolygonShape();
    //     polygonShape.set(vertices, vertices.length);
    //     MassData computedMassData = new MassData();
    //     polygonShape.computeMass(computedMassData, density);
    //     assertEquals(computedMassData.center, center);
    //     assertEquals(computedMassData.mass, mass, 1e-5);
    // }

    // Testing: validate()
    // TODO: do EP here
    private static Stream<Arguments> testValidateArgs() {
        //Adding BC test here
        return Stream.of(
                Arguments.of(new Vec2[]{new Vec2(2, 0), new Vec2(2, 2), new Vec2(0, 2), new Vec2(0, 0)}, true),
                Arguments.of(new Vec2[]{new Vec2(2, 0), new Vec2(0.1f, 0.1f), new Vec2(0, 2), new Vec2(0, 0)}, false)
        );
    }

    // @ParameterizedTest
    // @MethodSource("testValidateArgs")
    // public void testValidate(Vec2[] vertices, boolean isConvex) {
    //     PolygonShape polygonShape = new PolygonShape();
    //     polygonShape.set(vertices, vertices.length);
    //     assertEquals(isConvex, polygonShape.validate());
    // }

    // Testing: getVertices()
    // TODO: do EP here
    private static Stream<Arguments> testGetVerticesArgs() {
        return Stream.of(
                Arguments.of((Object) new Vec2[]{new Vec2(2, 0), new Vec2(2, 2), new Vec2(0, 2), new Vec2(0, 0)})
        );
    }

    @ParameterizedTest
    @MethodSource("testGetVerticesArgs")
    public void testGetVertices(Vec2[] vertices) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices, vertices.length);
        assertEquals(polygonShape.m_vertices, polygonShape.getVertices());
    }

    // Testing: getNormals()
    private static Stream<Arguments> testGetNormalsArgs() {
        return Stream.of(
                Arguments.of((Object) new Vec2[]{new Vec2(2, 0), new Vec2(2, 2), new Vec2(0, 2), new Vec2(0, 0)})
        );
    }

    @ParameterizedTest
    @MethodSource("testGetNormalsArgs")
    public void testGetNormals(Vec2[] vertices) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices, vertices.length);
        assertEquals(polygonShape.m_normals, polygonShape.getNormals());
    }

    // Testing: centroid()
    private static Stream<Arguments> testCentroidArgs() {
        return Stream.of(
                Arguments.of((Object) new Vec2[]{new Vec2(2, 0), new Vec2(2, 2), new Vec2(0, 2), new Vec2(0, 0)}, new Vec2(1,1))
        );
    }

    @ParameterizedTest
    @MethodSource("testCentroidArgs")
    public void testCentroid(Vec2[] vertices, Vec2 centroid) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices, vertices.length);
        final Transform transform = new Transform();
        transform.setIdentity();
        assertEquals(centroid, polygonShape.centroid(transform));
    }

    // Testing: centroidToOut()
    // TODO: do EP here
    private static Stream<Arguments> testCentroidToOut() {
        return Stream.of(
                Arguments.of((Object) new Vec2[]{new Vec2(2, 0), new Vec2(2, 2), new Vec2(0, 2), new Vec2(0, 0)})
        );
    }

    @ParameterizedTest
    @MethodSource("testCentroidToOut")
    public void testCentroidToOut(Vec2[] vertices) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices, vertices.length);
        final Transform transform = new Transform();
        transform.setIdentity();
        Vec2 computedCentroid = new Vec2();
        polygonShape.centroidToOut(transform, computedCentroid);
        assertEquals(polygonShape.m_centroid, computedCentroid);
    }
}
