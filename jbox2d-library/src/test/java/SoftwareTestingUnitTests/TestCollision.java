package SoftwareTestingUnitTests;

import org.jbox2d.common.Vec2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TestCollision {
    // Testing: testOverlap()
    // TODO: complete this test
    private static Stream<Arguments> testTestOverlapArgs() {
        return Stream.of();
    }

    @ParameterizedTest
    @MethodSource("testTestOverlapArgs")
    public void testTestOverlap() {
    }

    // Testing: getPointStates()
    // TODO: complete this test
    private static Stream<Arguments> testGetPointStatesArgs() {
        return Stream.of();
    }

    @ParameterizedTest
    @MethodSource("testGetPointStatesArgs")
    public void testGetPointStates() {
    }

    // Testing: collideCircles()
    // TODO: complete this test
    private static Stream<Arguments> testCollideCirclesArgs() {
        return Stream.of();
    }

    @ParameterizedTest
    @MethodSource("testCollideCirclesArgs")
    public void testCollideCircles() {
    }

    // Testing: collidePolygonAndCircle()
    // TODO: complete this test
    private static Stream<Arguments> testCollidePolygonAndCircleArgs() {
        return Stream.of();
    }

    @ParameterizedTest
    @MethodSource("testCollidePolygonAndCircleArgs")
    public void testCollidePolygonAndCircle() {
    }

    // Testing: edgeSeparation()
    // TODO: complete this test
    private static Stream<Arguments> testEdgeSeparationArgs() {
        return Stream.of(
                Arguments.of(
                        new Vec2[] { new Vec2(0, 0), new Vec2(1, 0), new Vec2(0, 1) },
                        new Vec2[] { new Vec2(1, 1), new Vec2(1, 0), new Vec2(0, 1) },
                        0, 0)
        // ,Arguments.of(
        // new Vec2[]{new Vec2(0,0), new Vec2(1,0), new Vec2(0,1)},
        // new Vec2[]{new Vec2(1,1), new Vec2(1,0), new Vec2(0,1)},
        // -1, 0
        // ),
        // Arguments.of(
        // new Vec2[]{new Vec2(0,0), new Vec2(1,0), new Vec2(0,1)},
        // new Vec2[]{new Vec2(1,1), new Vec2(1,0), new Vec2(0,1)},
        // 10000, 0
        // )
        );
    }

    @ParameterizedTest
    @MethodSource("testEdgeSeparationArgs")
    public void testEdgeSeparation() {
    }

    // Testing: findMaxSeparation()
    // TODO: complete this test
    private static Stream<Arguments> testFindMaxSeparationArgs() {
        return Stream.of();
    }

    @ParameterizedTest
    @MethodSource("testFindMaxSeparationArgs")
    public void testFindMaxSeparation() {
    }

    // Testing: collidePolygons()
    // TODO: complete this test
    private static Stream<Arguments> testCollidePolygonsArgs() {
        return Stream.of(
                Arguments.of(
                        new Vec2[] { new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0) },
                        new Vec2[] { new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0) },
                        2, Manifold.ManifoldType.FACE_A),
                Arguments.of(
                        new Vec2[] { new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0) },
                        new Vec2[] { new Vec2(1.5f, 0), new Vec2(1.5f, 1), new Vec2(0.5f, 1), new Vec2(0.5f, 0) },
                        2, Manifold.ManifoldType.FACE_A),
                Arguments.of(
                        new Vec2[] { new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0) },
                        new Vec2[] { new Vec2(2, 0), new Vec2(2, 1), new Vec2(1, 1), new Vec2(1, 0) },
                        2, Manifold.ManifoldType.FACE_A),
                Arguments.of(
                        new Vec2[] { new Vec2(1, 0), new Vec2(1, 1), new Vec2(0, 1), new Vec2(0, 0) },
                        new Vec2[] { new Vec2(3, 0), new Vec2(3, 1), new Vec2(2, 1), new Vec2(2, 0) },
                        0, null),
                // Adding more tests for BC
                Arguments.of(
                        new Vec2[] { new Vec2(1, 0), new Vec2(0, 1), new Vec2(0, 0) },
                        new Vec2[] { new Vec2(1, 0), new Vec2(0, 1), new Vec2(1, 1) },
                        2, Manifold.ManifoldType.FACE_A),
                Arguments.of(
                        new Vec2[] { new Vec2(1, 0), new Vec2(0, 1), new Vec2(0, 0) },
                        new Vec2[] { new Vec2(1.01f, 0), new Vec2(0, 1.01f), new Vec2(1, 1) },
                        2, Manifold.ManifoldType.FACE_A),
                Arguments.of(
                        new Vec2[] { new Vec2(1, 0), new Vec2(0, 1), new Vec2(0, 0) },
                        new Vec2[] { new Vec2(1.1f, 0), new Vec2(0, 1.1f), new Vec2(1, 1) },
                        0, null),
                // Arguments.of(
                // new Vec2[]{new Vec2(1, 0), new Vec2(0, 1), new Vec2(0, 0)},
                // new Vec2[]{new Vec2(1.01f, 0), new Vec2(0, 1.01f), new Vec2(1, 1)},
                // 0, null),
                Arguments.of(
                        new Vec2[] { new Vec2(1, 0), new Vec2(0, 1), new Vec2(0, 0) },
                        new Vec2[] { new Vec2(0.99f, 0.01f), new Vec2(0.01f, 0.99f), new Vec2(1, 1) },
                        2, Manifold.ManifoldType.FACE_A),
                Arguments.of(
                        new Vec2[] { new Vec2(100, 0), new Vec2(0, 100), new Vec2(0, 0) },
                        new Vec2[] { new Vec2(99f, 1f), new Vec2(1f, 99f), new Vec2(100, 100) },
                        2, Manifold.ManifoldType.FACE_A),
                Arguments.of(
                        new Vec2[] { new Vec2(100, 0), new Vec2(0, 100), new Vec2(0, 0) },
                        new Vec2[] { new Vec2(101f, 0), new Vec2(0, 101f), new Vec2(100, 100) },
                        0, null),
                Arguments.of(
                        new Vec2[] { new Vec2(100, 0), new Vec2(0, 100), new Vec2(0, 0) },
                        new Vec2[] { new Vec2(100f, 100f), new Vec2(1001, 101f), new Vec2(100, 101) },
                        0, null));
    }

    @ParameterizedTest
    @MethodSource("testCollidePolygonsArgs")
    public void testCollidePolygons() {
    }

    // Testing: collideEdgeAndCircle()
    // TODO: complete this test
    private static Stream<Arguments> testCollideEdgeAndCircleArgs() {
        return Stream.of();
    }

    @ParameterizedTest
    @MethodSource("testCollideEdgeAndCircleArgs")
    public void testCollideEdgeAndCircle() {
    }

    // Testing: collideEdgeAndPolygon()
    // TODO: complete this test
    private static Stream<Arguments> testCollideEdgeAndPolygonArgs() {
        return Stream.of();
    }

    @ParameterizedTest
    @MethodSource("testCollideEdgeAndPolygonArgs")
    public void testCollideEdgeAndPolygon() {
    }

    // Testing: set()
    // TODO: complete this test
    private static Stream<Arguments> testSetArgs() {
        return Stream.of();
    }

    @ParameterizedTest
    @MethodSource("testSetArgs")
    public void testSet() {
    }

    // Testing: collide()
    // TODO: complete this test
    private static Stream<Arguments> testCollideArgs() {
        return Stream.of();
    }

    @ParameterizedTest
    @MethodSource("testCollideArgs")
    public void testCollide() {
    }

    // Testing: computeEdgeSeparation()
    // TODO: complete this test
    private static Stream<Arguments> testComputeEdgeSeparationArgs() {
        return Stream.of();
    }

    @ParameterizedTest
    @MethodSource("testComputeEdgeSeparationArgs")
    public void testComputeEdgeSeparation() {
    }

    // Testing: computePolygonSeparation()
    // TODO: complete this test
    private static Stream<Arguments> testComputePolygonSeparationArgs() {
        return Stream.of();
    }

    @ParameterizedTest
    @MethodSource("testComputePolygonSeparationArgs")
    public void testComputePolygonSeparation() {
    }
}
