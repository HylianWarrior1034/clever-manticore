package SoftwareTestingUnitTests;

import org.jbox2d.collision.Collision;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.pooling.normal.DefaultWorldPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCollision {
    Collision collision;
    Manifold manifold;
    Transform identity;

    @BeforeEach
    public void setup() {
        collision = new DefaultWorldPool(100,100).getCollision();
        manifold = new Manifold();
        identity = new Transform();
        identity.setIdentity();
    }

    // Testing: testOverlap()
    // TODO: EP
    private static Stream<Arguments> testTestOverlapArgs() {
        // Arguments: circle rad, circle x, circle y, box hx, box hy, isOverlaping
        return Stream.of(
                Arguments.of(1,0,0,1,1, true),
                Arguments.of(1,10,10,1,1, false)
        );
    }
    @ParameterizedTest
    @MethodSource("testTestOverlapArgs")
    public void testTestOverlap(float circle_rad, float circle_x, float circle_y, float box_hx, float box_hy, boolean isOverlaping) {
        CircleShape circle = new CircleShape();
        circle.m_radius = circle_rad;
        circle.m_p.x = circle_x;
        circle.m_p.y = circle_y;
        PolygonShape square = new PolygonShape();
        square.setAsBox(box_hx,box_hy);
        assertEquals(isOverlaping, collision.testOverlap(circle, 0, square, 0, identity, identity));
    }

    // Testing: getPointStates()
    // TODO: complete this test
    private static Stream<Arguments> testGetPointStatesArgs() {
        return Stream.of(
        );
    }
    @ParameterizedTest
    @MethodSource("testGetPointStatesArgs")
    public void testGetPointStates() {
    }

    // Testing: clipSegmentToLine()
    // TODO: complete this test
    private static Stream<Arguments> testClipSegmentToLineArgs() {
        return Stream.of(
        );
    }
    @ParameterizedTest
    @MethodSource("testClipSegmentToLineArgs")
    public void testClipSegmentToLine() {
    }

    // Testing: collideCircles()
    // TODO: complete this test
    private static Stream<Arguments> testCollideCirclesArgs() {
        return Stream.of(
                Arguments.of(1, 0, 0, 1, 0.5f, 0.5f, 1, Manifold.ManifoldType.CIRCLES),
                Arguments.of(1, 0, 0, 1, 2, 2, 0, null)
        );
    }
    @ParameterizedTest
    @MethodSource("testCollideCirclesArgs")
    public void testCollideCircles(float circle1_rad, float circle1_x, float circle1_y, float circle2_rad, float circle2_x, float circle2_y, int pointCount, Manifold.ManifoldType manifoldType) {
        CircleShape circle1 = new CircleShape();
        circle1.m_radius = circle1_rad;
        circle1.m_p.x = circle1_x;
        circle1.m_p.y = circle1_y;
        CircleShape circle2 = new CircleShape();
        circle2.m_radius = circle2_rad;
        circle2.m_p.x = circle2_x;
        circle2.m_p.y = circle2_y;
        collision.collideCircles(manifold, circle1, identity, circle2, identity);
        // TODO: not sure what the expected output is suppose to be
        System.out.println(manifold.localNormal);
        System.out.println(manifold.points[0].localPoint);
        System.out.println(manifold.localPoint);
        assertEquals(pointCount, manifold.pointCount);
        assertEquals(manifoldType, manifold.type);
    }

    // Testing: collidePolygonAndCircle()
    // TODO: complete this test
    private static Stream<Arguments> testCollidePolygonAndCircleArgs() {
        return Stream.of(
                Arguments.of(1, 0, 0, new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)}, 1, Manifold.ManifoldType.FACE_A),
                Arguments.of(1, 10, 10, new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)}, 0, null),
                Arguments.of(10, 0, 0, new Vec2[]{new Vec2(9, 0), new Vec2(9, 1), new Vec2(20, 1), new Vec2(20, 0)}, 1, Manifold.ManifoldType.FACE_A),
                Arguments.of(1, 0, 0, new Vec2[]{new Vec2(-10, -10), new Vec2(10, -10), new Vec2(-10, 10), new Vec2(10, 10)}, 1, Manifold.ManifoldType.FACE_A)
         );
    }
    @ParameterizedTest
    @MethodSource("testCollidePolygonAndCircleArgs")
    public void testCollidePolygonAndCircle(float circle_rad, float circle_x, float circle_y, Vec2[] vertices, int pointCount, Manifold.ManifoldType manifoldType) {
        CircleShape circle = new CircleShape();
        circle.m_radius = circle_rad;
        circle.m_p.x = circle_x;
        circle.m_p.y = circle_y;
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices, vertices.length);
        collision.collidePolygonAndCircle(manifold, polygonShape, identity, circle, identity);
        // TODO: not sure what the expected output is suppose to be
        System.out.println(manifold.localNormal);
        System.out.println(manifold.points[0].localPoint);
        System.out.println(manifold.localPoint);
        assertEquals(pointCount, manifold.pointCount);
        assertEquals(manifoldType, manifold.type);
    }

    // Testing: edgeSeparation()
    // TODO: complete this test
    private static Stream<Arguments> testEdgeSeparationArgs() {
        return Stream.of(
        );
    }
    @ParameterizedTest
    @MethodSource("testEdgeSeparationArgs")
    public void testEdgeSeparation() {
    }

    // Testing: findMaxSeparation()
    // TODO: complete this test
    private static Stream<Arguments> testFindMaxSeparationArgs() {
        return Stream.of(
        );
    }
    @ParameterizedTest
    @MethodSource("testFindMaxSeparationArgs")
    public void testFindMaxSeparation() {
    }

    // Testing: findIncidentEdge()
    // TODO: complete this test
    private static Stream<Arguments> testFindIncidentEdgeArgs() {
        return Stream.of(
        );
    }
    @ParameterizedTest
    @MethodSource("testFindIncidentEdgeArgs")
    public void testFindIncidentEdge() {
    }

    // Testing: collidePolygons()
    // TODO: complete this test
    private static Stream<Arguments> testCollidePolygonsArgs() {
        return Stream.of(
                Arguments.of(
                        new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)},
                        new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)},
                        2, Manifold.ManifoldType.FACE_A),
                Arguments.of(
                        new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)},
                        new Vec2[]{new Vec2(1.5f, 0), new Vec2(1.5f, 1), new Vec2(0.5f, 1), new Vec2(0.5f, 0)},
                        2, Manifold.ManifoldType.FACE_A),
                Arguments.of(
                        new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)},
                        new Vec2[]{new Vec2(2, 0), new Vec2(2, 1), new Vec2(1, 1), new Vec2(1, 0)},
                        2, Manifold.ManifoldType.FACE_A),
                Arguments.of(
                        new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)},
                        new Vec2[]{new Vec2(3, 0), new Vec2(3, 1), new Vec2(2, 1), new Vec2(2, 0)},
                        0, null)
        );
    }
    @ParameterizedTest
    @MethodSource("testCollidePolygonsArgs")
    public void testCollidePolygons(Vec2[] vertices1, Vec2[] vertices2, int pointCount, Manifold.ManifoldType manifoldType) {
        PolygonShape polygonShape1 = new PolygonShape();
        polygonShape1.set(vertices1, vertices1.length);
        PolygonShape polygonShape2 = new PolygonShape();
        polygonShape2.set(vertices2, vertices2.length);
        collision.collidePolygons(manifold, polygonShape1, identity, polygonShape2, identity);
        // TODO: not sure what the expected output is suppose to be
        System.out.println(manifold.localNormal);
        System.out.println(manifold.points[0].localPoint);
        System.out.println(manifold.localPoint);
        assertEquals(pointCount, manifold.pointCount);
        assertEquals(manifoldType, manifold.type);
    }

    // Testing: collideEdgeAndCircle()
    private static Stream<Arguments> testCollideEdgeAndCircleArgs() {
        return Stream.of(
                Arguments.of(1, 0, 0, new Vec2(1,2), new Vec2(1,-2), 1, Manifold.ManifoldType.FACE_A),
                Arguments.of(1, 0, 0, new Vec2(1,2), new Vec2(-1,2), 0, null),
                Arguments.of(1, 0, 0, new Vec2(0,2), new Vec2(0,-2), 1, Manifold.ManifoldType.FACE_A),
                Arguments.of(1, 0, 0, new Vec2(0,0.5f), new Vec2(0,-0.5f), 1, Manifold.ManifoldType.FACE_A),
                Arguments.of(1, 0, 0, new Vec2(1,2), new Vec2(-1,-2), 1, Manifold.ManifoldType.FACE_A),
                Arguments.of(1, 0, 0, new Vec2(1,2), new Vec2(1,2), 0, null),
                Arguments.of(1, 0, 0, new Vec2(1,2), new Vec2(-3,-2), 1, Manifold.ManifoldType.FACE_A),
                Arguments.of(1, 0, 0, new Vec2(1,2), new Vec2(-2,-2), 1, Manifold.ManifoldType.FACE_A),
                Arguments.of(1, 0, 0, new Vec2(1,2), new Vec2(-1,-2), 1, Manifold.ManifoldType.FACE_A),
                Arguments.of(1, 0, 0, new Vec2(1,2), new Vec2(0,-2), 1, Manifold.ManifoldType.FACE_A),
                Arguments.of(1, 0, 0, new Vec2(1,2), new Vec2(2,-2), 0, null),
                Arguments.of(1, 0, 0, new Vec2(1,2), new Vec2(3,-2), 0, null),
                Arguments.of(1, 0, 0, new Vec2(1,2), new Vec2(4,-2), 0, null),
                Arguments.of(1, 0, 0, new Vec2(1,2), new Vec2(0,0), 1, Manifold.ManifoldType.CIRCLES)
        );
    }
    @ParameterizedTest
    @MethodSource("testCollideEdgeAndCircleArgs")
    public void testCollideEdgeAndCircle(float circle_rad, float circle_x, float circle_y, Vec2 edgeStart, Vec2 edgeEnd, int pointCount, Manifold.ManifoldType manifoldType) {
        CircleShape circle = new CircleShape();
        circle.m_radius = circle_rad;
        circle.m_p.x = circle_x;
        circle.m_p.y = circle_y;
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(edgeStart, edgeEnd);
        edgeShape.m_hasVertex0 = true;
        edgeShape.m_hasVertex3 = true;
        collision.collideEdgeAndCircle(manifold, edgeShape, identity, circle, identity);
        // TODO: not sure what the expected output is suppose to be
        System.out.println(manifold.localNormal);
        System.out.println(manifold.points[0].localPoint);
        System.out.println(manifold.localPoint);
        assertEquals(pointCount, manifold.pointCount);
        assertEquals(manifoldType, manifold.type);
    }

    // Testing: collideEdgeAndCircle()
    @ParameterizedTest
    @MethodSource("testCollideEdgeAndCircleArgs")
    public void testCollideEdgeAndCircle2(float circle_rad, float circle_x, float circle_y, Vec2 edgeStart, Vec2 edgeEnd, int pointCount, Manifold.ManifoldType manifoldType) {
        CircleShape circle = new CircleShape();
        circle.m_radius = circle_rad;
        circle.m_p.x = circle_x;
        circle.m_p.y = circle_y;
        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(edgeStart, edgeEnd);
        collision.collideEdgeAndCircle(manifold, edgeShape, identity, circle, identity);
        // TODO: not sure what the expected output is suppose to be
        System.out.println(manifold.localNormal);
        System.out.println(manifold.points[0].localPoint);
        System.out.println(manifold.localPoint);
        assertEquals(pointCount, manifold.pointCount);
        assertEquals(manifoldType, manifold.type);
    }

    // Testing: collideEdgeAndPolygon()
    // TODO: complete this test
    private static Stream<Arguments> testCollideEdgeAndPolygonArgs() {
        return Stream.of(
        );
    }
    @ParameterizedTest
    @MethodSource("testCollideEdgeAndPolygonArgs")
    public void testCollideEdgeAndPolygon() {
    }

    // Testing: set()
    // TODO: complete this test
    private static Stream<Arguments> testSetArgs() {
        return Stream.of(
        );
    }
    @ParameterizedTest
    @MethodSource("testSetArgs")
    public void testSet() {
    }

    // Testing: collide()
    // TODO: complete this test
    private static Stream<Arguments> testCollideArgs() {
        return Stream.of(
        );
    }
    @ParameterizedTest
    @MethodSource("testCollideArgs")
    public void testCollide() {
    }

    // Testing: computeEdgeSeparation()
    // TODO: complete this test
    private static Stream<Arguments> testComputeEdgeSeparationArgs() {
        return Stream.of(
        );
    }
    @ParameterizedTest
    @MethodSource("testComputeEdgeSeparationArgs")
    public void testComputeEdgeSeparation() {
    }

    // Testing: computePolygonSeparation()
    // TODO: complete this test
    private static Stream<Arguments> testComputePolygonSeparationArgs() {
        return Stream.of(
        );
    }
    @ParameterizedTest
    @MethodSource("testComputePolygonSeparationArgs")
    public void testComputePolygonSeparation() {
    }
}
