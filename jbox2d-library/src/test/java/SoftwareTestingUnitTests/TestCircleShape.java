package SoftwareTestingUnitTests;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.RayCastInput;
import org.jbox2d.collision.RayCastOutput;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.lang.Float.MIN_VALUE;
import static org.junit.jupiter.api.Assertions.*;

public class TestCircleShape {

    // Testing: clone()
    @Test
    public void testCloneProducesIdenticalCircle() {
        CircleShape circle = new CircleShape();
        circle.m_radius = 5;
        CircleShape new_circle = (CircleShape) circle.clone();
        assertEquals(circle.m_radius, new_circle.m_radius);
    }

    @Test
    public void testCloneProducesDistinctObjects() {
        int original_circle_radius = 5;
        int new_circle_radius = 5;

        CircleShape circle = new CircleShape();
        circle.m_radius = original_circle_radius;
        CircleShape new_circle = (CircleShape) circle.clone();
        new_circle.m_radius = new_circle_radius;

        assertEquals(circle.m_radius, original_circle_radius);
        assertEquals(new_circle.m_radius, new_circle_radius);
    }

    // Testing: getChildCount()
    @Test
    public void testGetChildCount() {
        CircleShape circle = new CircleShape();
        assertEquals(circle.getChildCount(), 1);
    }

    // Testing: getSupport()
    @Test
    public void testGetSupport() {
        CircleShape circle = new CircleShape();
        Vec2 direction = new Vec2(1,1);
        assertEquals(circle.getSupport(direction), 0);
    }

    // Testing: getSupportVertex()
    @Test
    public void testGetSupportVertex() {
        CircleShape circle = new CircleShape();
        Vec2 direction = new Vec2(1,1);
        assertEquals(circle.getSupportVertex(direction), circle.m_p);
    }

    // Testing: getVertexCount()
    @Test
    public void testGetVertexCount() {
        CircleShape circle = new CircleShape();
        assertEquals(circle.getVertexCount(), 1);
    }

    // Testing: getVertex()
    @ParameterizedTest
    @CsvSource({"1", "0", "-1"})
    public void testGetVertex(int index) {
        CircleShape circle = new CircleShape();
        Vec2 circle_center = circle.m_p;
        assertEquals(circle.getVertex(index), circle_center);
    }

    // Testing: testPoint()
    // EP: point can be inside the circle, on the edge, or outside of it
    // EP: circle rad can be negative, zero, or positive
    private static Stream<Arguments> testTestPointArgs() {
        // Format: circleRadius, pointPosition, isInCircle
        return Stream.of(
                Arguments.of(1, new Vec2(0,0), true),
                Arguments.of(1, new Vec2(1,1), true),
                Arguments.of(1, new Vec2(2,2), false),

                Arguments.of(0, new Vec2(0,0), true),
                Arguments.of(0, new Vec2(1,1), false),

                Arguments.of(-1, new Vec2(0,0), false),
                Arguments.of(-1, new Vec2(0.5f,0.5f), false),
                Arguments.of(-1, new Vec2(1,1), false),
                Arguments.of(-1, new Vec2(2,2), false)
        );
    }
    @ParameterizedTest
    @MethodSource("testTestPointArgs")
    public void testTestPoint(float circleRadius, Vec2 pointLocation, boolean isInCircle) {
        final Transform transform = new Transform();
        transform.setIdentity();
        CircleShape circle = new CircleShape();
        circle.m_radius = circleRadius;
        assertEquals(isInCircle, circle.testPoint(transform, pointLocation));
    }

    // Testing: raycast()
    // EP: a raycast end can be inside the circle, on it's edge, or not touching at all
    // EP: a raycast start can be inside the circle, on it's edge, or not touching at all
    private static Stream<Arguments> testRaycastArgs() {
        // Format: raycastStart, raycastEnd, circleRadius, rayHitsCircle
        return Stream.of(
                // raycast start outside of circle
                Arguments.of(new Vec2(3,3), new Vec2(2,2), 1, true),
                Arguments.of(new Vec2(2,2), new Vec2(1,1), 1, true),
                Arguments.of(new Vec2(2,2), new Vec2(3,3), 1, false),

                // raycast start on the edge of circle
                Arguments.of(new Vec2(1,1), new Vec2(0,0), 1, true),
                Arguments.of(new Vec2(1,1), new Vec2(-1,-1), 1, true),
                Arguments.of(new Vec2(1,1), new Vec2(-2,-2), 1, true),

                // raycast start inside of circle
                Arguments.of(new Vec2(0.5f,0.5f), new Vec2(0,0), 1, true),
                Arguments.of(new Vec2(0.5f,0.5f), new Vec2(-10,-10), 1, true),
                Arguments.of(new Vec2(0.5f,0.5f), new Vec2(100,0), 1, true),

                // Adding BC tests
                Arguments.of(new Vec2(1f-2*MIN_VALUE,1f-2*MIN_VALUE), new Vec2(1f-MIN_VALUE,1f-MIN_VALUE), 1f, true)
        );
    }

    @ParameterizedTest
    @MethodSource("testRaycastArgs")
    public void testRaycast(Vec2 raycastStart, Vec2 raycastEnd, float circleRadius, boolean rayHitsCircle) {
        RayCastInput rayCastInput = new RayCastInput();
        rayCastInput.p1.x = raycastStart.x;
        rayCastInput.p1.y = raycastStart.y;
        rayCastInput.p2.x = raycastEnd.x;
        rayCastInput.p2.y = raycastEnd.y;
        rayCastInput.maxFraction = 10;
        final Transform transform = new Transform();
        transform.setIdentity();
        CircleShape circle = new CircleShape();
        circle.m_radius = circleRadius;
        RayCastOutput rayCastOutput = new RayCastOutput();
        assertEquals(rayHitsCircle, circle.raycast(rayCastOutput, rayCastInput, transform, 0));
    }

    // Testing: computeAABB()
    // TODO: perform EP here
    private static Stream<Arguments> testComputeAABBArgs() {
        // Format: circleCenter, circleRadius, lower_x, lower_y, upper_x, upper_y
        return Stream.of(
                Arguments.of(new Vec2(0,0), 1, -1, -1, 1, 1)
        );
    }
    @ParameterizedTest
    @MethodSource("testComputeAABBArgs")
    public void testComputeAABB(Vec2 circleCenter, float circleRadius, float lower_x, float lower_y, float upper_x, float upper_y) {
        final Transform transform = new Transform();
        transform.setIdentity();
        CircleShape circle = new CircleShape();
        circle.m_radius = circleRadius;
        circle.m_p.x = circleCenter.x;
        circle.m_p.y = circleCenter.y;
        AABB aabb = new AABB();
        circle.computeAABB(aabb, transform, 0);
        assertEquals(lower_x, aabb.lowerBound.x, 1e-5);
        assertEquals(lower_y, aabb.lowerBound.y, 1e-5);
        assertEquals(upper_x, aabb.upperBound.x, 1e-5);
        assertEquals(upper_y, aabb.upperBound.y, 1e-5);
    }

    // Testing: computeMass()
    // TODO: perform EP here
    private static Stream<Arguments> testComputeMassArgs() {
        // Format: radius, density, mass, rotationalInertia, centerX, centerY
        return Stream.of(
                Arguments.of(1, 1, (float)Math.PI, (float)(Math.PI/2), 0,0)
        );
    }
    @ParameterizedTest
    @MethodSource("testComputeMassArgs")
    public void testComputeMass(float radius, float density, float mass, float rotationalInertia, float centerX, float centerY) {
        CircleShape circle = new CircleShape();
        circle.m_radius = radius;
        MassData massData = new MassData();
        circle.computeMass(massData, density);
        assertEquals(mass, massData.mass, 1e-5);
        assertEquals(rotationalInertia, massData.I, 1e-5);
        assertEquals(centerX, massData.center.x, 1e-5);
        assertEquals(centerY, massData.center.y, 1e-5);
    }

}
