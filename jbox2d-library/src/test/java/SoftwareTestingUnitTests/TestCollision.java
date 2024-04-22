package SoftwareTestingUnitTests;

import org.jbox2d.collision.Collision;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.pooling.normal.DefaultWorldPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
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
    private static Stream<Arguments> testGetPointStatesArgs() {
        return Stream.of(
                Arguments.of(1, 0, 0, new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)})
        );
    }
    @ParameterizedTest
    @MethodSource("testGetPointStatesArgs")
    public void testGetPointStates(float circle_rad, float circle_x, float circle_y, Vec2[] vertices) {
        Collision.PointState[] state1 = new Collision.PointState[Settings.maxManifoldPoints];
        Collision.PointState[] state2 = new Collision.PointState[Settings.maxManifoldPoints];
        CircleShape circle = new CircleShape();
        circle.m_radius = circle_rad;
        circle.m_p.x = circle_x;
        circle.m_p.y = circle_y;
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vertices, vertices.length);
        collision.collidePolygonAndCircle(manifold, polygonShape, identity, circle, identity);
        Collision.getPointStates(state1, state2, manifold, manifold);
        assertArrayEquals(state1, state2);
    }

    // Testing: collideCircles()
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
        System.out.println(manifold.localNormal);
        System.out.println(manifold.points[0].localPoint);
        System.out.println(manifold.localPoint);
        assertEquals(pointCount, manifold.pointCount);
        assertEquals(manifoldType, manifold.type);
    }

    // Testing: collidePolygonAndCircle()
    private static Stream<Arguments> testCollidePolygonAndCircleArgs() {
        return Stream.of(
                Arguments.of(1, 0, 0, new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)}, 1, Manifold.ManifoldType.FACE_A),
                Arguments.of(1, 10, 10, new Vec2[]{new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0)}, 0, null),
                Arguments.of(10, 0, 0, new Vec2[]{new Vec2(9, 0), new Vec2(9, 1), new Vec2(20, 1), new Vec2(20, 0)}, 1, Manifold.ManifoldType.FACE_A),
                Arguments.of(1, 0, 0, new Vec2[]{new Vec2(-10, -10), new Vec2(10, -10), new Vec2(-10, 10), new Vec2(10, 10)}, 1, Manifold.ManifoldType.FACE_A),
                // Adding tests for BC
                Arguments.of(100, 0, 0, new Vec2[]{new Vec2(1000, 1000), new Vec2(85, 75), new Vec2(80, 80)}, 0, null),
                Arguments.of(100, 0, 0, new Vec2[]{new Vec2(1000, 1000), new Vec2(100, 0), new Vec2(0, 99)}, 1, Manifold.ManifoldType.FACE_A),
                Arguments.of(100, 0, 0, new Vec2[]{new Vec2(1000, 1000), new Vec2(1000, 1001), new Vec2(100, 0)}, 1, Manifold.ManifoldType.FACE_A),
                Arguments.of(100, 0, 0, new Vec2[]{new Vec2(1000, 1000), new Vec2(1000, 1001), new Vec2(99.9f, 0)}, 1, Manifold.ManifoldType.FACE_A),
                Arguments.of(100, 0, 0, new Vec2[]{new Vec2(1000, 1000), new Vec2(1000, 1001), new Vec2(100.1f, 0)}, 0, null)
                //Arguments.of(100, 0, 0, new Vec2[]{new Vec2(-10, -10), new Vec2(10, -10), new Vec2(-10, 10)}, 1, Manifold.ManifoldType.FACE_A)
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
        System.out.println(manifold.localNormal);
        System.out.println(manifold.points[0].localPoint);
        System.out.println(manifold.localPoint);
        assertEquals(pointCount, manifold.pointCount);
        assertEquals(manifoldType, manifold.type);
    }

    // Testing: edgeSeparation()
    private static Stream<Arguments> testEdgeSeparationArgs() {
        return Stream.of(
                Arguments.of(
                        new Vec2[]{new Vec2(0,0), new Vec2(1,0), new Vec2(0,1)},
                        new Vec2[]{new Vec2(1,1), new Vec2(1,0), new Vec2(0,1)},
                        0, 0
                )
                //,Arguments.of(
                //        new Vec2[]{new Vec2(0,0), new Vec2(1,0), new Vec2(0,1)},
                //        new Vec2[]{new Vec2(1,1), new Vec2(1,0), new Vec2(0,1)},
                //        -1, 0
                //),
                //Arguments.of(
                //        new Vec2[]{new Vec2(0,0), new Vec2(1,0), new Vec2(0,1)},
                //        new Vec2[]{new Vec2(1,1), new Vec2(1,0), new Vec2(0,1)},
                //        10000, 0
                //)
        );
    }
    @ParameterizedTest
    @MethodSource("testEdgeSeparationArgs")
    public void testEdgeSeparation(Vec2[] vertices1, Vec2[] vertices2, int index, float sep) {
        PolygonShape polygonShape1 = new PolygonShape();
        polygonShape1.set(vertices1, vertices1.length);
        PolygonShape polygonShape2 = new PolygonShape();
        polygonShape2.set(vertices2, vertices2.length);
        assertEquals(sep, collision.edgeSeparation(polygonShape1, identity, index, polygonShape2, identity), 1e-5);
    }

    // Testing: collidePolygons()
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
                        0, null),
                // Adding more tests for BC
                Arguments.of(
                        new Vec2[]{new Vec2(1, 0), new Vec2(0, 1), new Vec2(0, 0)},
                        new Vec2[]{new Vec2(1, 0), new Vec2(0, 1), new Vec2(1, 1)},
                        2, Manifold.ManifoldType.FACE_A),
                Arguments.of(
                        new Vec2[]{new Vec2(1, 0), new Vec2(0, 1), new Vec2(0, 0)},
                        new Vec2[]{new Vec2(1.01f, 0), new Vec2(0, 1.01f), new Vec2(1, 1)},
                        2, Manifold.ManifoldType.FACE_A),
                Arguments.of(
                        new Vec2[]{new Vec2(1, 0), new Vec2(0, 1), new Vec2(0, 0)},
                        new Vec2[]{new Vec2(1.1f, 0), new Vec2(0, 1.1f), new Vec2(1, 1)},
                        0, null),
                //Arguments.of(
                //        new Vec2[]{new Vec2(1, 0), new Vec2(0, 1), new Vec2(0, 0)},
                //        new Vec2[]{new Vec2(1.01f, 0), new Vec2(0, 1.01f), new Vec2(1, 1)},
                //        0, null),
                Arguments.of(
                        new Vec2[]{new Vec2(1, 0), new Vec2(0, 1), new Vec2(0, 0)},
                        new Vec2[]{new Vec2(0.99f, 0.01f), new Vec2(0.01f, 0.99f), new Vec2(1, 1)},
                        2, Manifold.ManifoldType.FACE_A),
                Arguments.of(
                        new Vec2[]{new Vec2(100, 0), new Vec2(0, 100), new Vec2(0, 0)},
                        new Vec2[]{new Vec2(99f, 1f), new Vec2(1f, 99f), new Vec2(100, 100)},
                        2, Manifold.ManifoldType.FACE_A),
                Arguments.of(
                        new Vec2[]{new Vec2(100, 0), new Vec2(0, 100), new Vec2(0, 0)},
                        new Vec2[]{new Vec2(101f, 0), new Vec2(0, 101f), new Vec2(100, 100)},
                        0, null),
                Arguments.of(
                        new Vec2[]{new Vec2(100, 0), new Vec2(0, 100), new Vec2(0, 0)},
                        new Vec2[]{new Vec2(100f, 100f), new Vec2(1001, 101f), new Vec2(100, 101)},
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
        System.out.println(manifold.localNormal);
        System.out.println(manifold.points[0].localPoint);
        System.out.println(manifold.localPoint);
        assertEquals(pointCount, manifold.pointCount);
        assertEquals(manifoldType, manifold.type);
    }
    @ParameterizedTest
    @MethodSource("testCollidePolygonsArgs")
    public void testCollidePolygonsFlipped(Vec2[] vertices1, Vec2[] vertices2, int pointCount, Manifold.ManifoldType manifoldType) {
        PolygonShape polygonShape1 = new PolygonShape();
        polygonShape1.set(vertices1, vertices1.length);
        PolygonShape polygonShape2 = new PolygonShape();
        polygonShape2.set(vertices2, vertices2.length);
        collision.collidePolygons(manifold, polygonShape2, identity, polygonShape1, identity);
        System.out.println(manifold.localNormal);
        System.out.println(manifold.points[0].localPoint);
        System.out.println(manifold.localPoint);
        assertEquals(pointCount, manifold.pointCount);
        assertEquals(manifoldType, manifold.type);
    }

    private static Stream<Arguments> testCollidePolygonsConcaveArgs() {
        return Stream.of(
                Arguments.of(
                        new Vec2[]{new Vec2(0, 10), new Vec2(1, 1), new Vec2(10, 0), new Vec2(1, -1), new Vec2(0, -10), new Vec2(-1, -1), new Vec2(-1, 0), new Vec2(-1, 1)},
                        new Vec2[]{new Vec2(0, 10), new Vec2(1, 1), new Vec2(10, 0), new Vec2(1, -1), new Vec2(0, -10), new Vec2(-1, -1), new Vec2(-1, 0), new Vec2(-1, 1)},
                        1, Manifold.ManifoldType.FACE_A)
        );
    }
    @ParameterizedTest
    @MethodSource("testCollidePolygonsConcaveArgs")
    public void testCollidePolygonsConcave(Vec2[] vertices1, Vec2[] vertices2, int pointCount, Manifold.ManifoldType manifoldType) {
        PolygonShape polygonShape1 = new PolygonShape();
        PolygonShape polygonShape2 = new PolygonShape();

        polygonShape1.m_count = vertices1.length;
        polygonShape2.m_count = vertices2.length;

        System.arraycopy(vertices1, 0, polygonShape1.m_vertices, 0, vertices1.length);
        System.arraycopy(vertices2, 0, polygonShape2.m_vertices, 0, vertices2.length);
        Transform transform = new Transform(new Vec2(2,2), new Rot(1f));
        collision.collidePolygons(manifold, polygonShape2, transform, polygonShape1, identity);
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
                Arguments.of(1, 0, 0, new Vec2(1,2), new Vec2(0,0), 1, Manifold.ManifoldType.CIRCLES),
                // Adding additional tests for BC
                Arguments.of(100, 0, 0, new Vec2(80,80), new Vec2(81,81), 0, null)
                //Arguments.of(1, 0, 0, new Vec2(1,2), new Vec2(0,0), 1, Manifold.ManifoldType.CIRCLES),
                //Arguments.of(1, 0, 0, new Vec2(1,2), new Vec2(0,0), 1, Manifold.ManifoldType.CIRCLES)
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
        System.out.println(manifold.localNormal);
        System.out.println(manifold.points[0].localPoint);
        System.out.println(manifold.localPoint);
        assertEquals(pointCount, manifold.pointCount);
        assertEquals(manifoldType, manifold.type);
    }
}
