package SoftwareTestingUnitTests;
import org.jbox2d.collision.RayCastInput;
import org.jbox2d.collision.RayCastOutput;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestEdgeShape {
  EdgeShape edge;

  @BeforeEach
  public void createEdge() {
    edge = new EdgeShape();
  }

  /*
  Test getChildCount (stupid, I know)
   */
  @Test
  public void testgetChildCount() {
    assertEquals(1, edge.getChildCount());
  }

  /*
  testPoint returns false (also stupid...)
   */
  @Test
  public void testTestPoint() {
    Transform xf = new Transform();
    Vec2 p = new Vec2();

    assertFalse(edge.testPoint(xf, p));
  }

  /*
  Blackbox Partitioning
  set method sets m_vertex1 and m_vertex2 to each respective input
   */
  @ParameterizedTest
  @CsvSource({"-1, -1", "-1, 0", "-1, 1", "0, -1", "0, 0", "0, 1", "1, -1", "1, 0", "1, 1"})
  public void testSet(int x, int y) {
    Vec2 vec1 = new Vec2();
    Vec2 vec2 = new Vec2();
    vec1.x = x;
    vec1.y = y;
    vec2.x = x;
    vec2.y = y;
    edge.set(vec1, vec2);
    assertEquals(vec1, edge.m_vertex1);
    assertEquals(vec2, edge.m_vertex2);
  }

  /*
  Blackbox error guessing
  Property-Based Testing
  raycast should return false if two input vectors are parallel
   */
  @Test
  public void testRayCastParallelVectors() {
    Vec2 vec1 = new Vec2();
    Vec2 vec2 = new Vec2();
    Vec2 tr = new Vec2();
    Rot ro = new Rot();
    ro.s = 0;
    ro.c = 0;

    Transform trans = new Transform(tr, ro);

    vec1.x = 0.1f;
    vec2.x = 0.1f;
    vec1.y = -0.1f;
    vec2.y = -0.1f;
    RayCastOutput output = new RayCastOutput();
    RayCastInput input = new RayCastInput();
    input.p1.set(vec1);
    input.p2.set(vec2);

    assertFalse(edge.raycast(output, input, trans, 1));
  }

  /*
  Whitebox Testing, Branch Coverage
  raycast should return false if two input vectors are parallel
   */
  @Test
  public void testRayCastInvalidVectors1() {
    Vec2 vec1 = new Vec2();
    Vec2 vec2 = new Vec2();
    Vec2 tr = new Vec2();
    Rot ro = new Rot();
    ro.s = 0;
    ro.c = 1;

    Transform trans = new Transform(tr, ro);

    vec1.x = 0.2f;
    vec1.y = 0.2f;
    vec2.x = 0.1f;
    vec2.y = -0.1f;

    Vec2 vec3 = new Vec2();
    vec3.x = 0.1f;
    vec3.y = 0.1f;
    Vec2 vec4 = new Vec2();
    vec4.x = 0.0f;
    vec4.y = -0.1f;
    edge.set(vec3, vec4);
    RayCastOutput output = new RayCastOutput();
    RayCastInput input = new RayCastInput();
    input.p1.set(vec1);
    input.p2.set(vec2);

    assertFalse(edge.raycast(output, input, trans, 1));
  }

  /*
   Whitebox Testing, Branch Coverage
   raycast should return false if two input vectors are parallel
  */
  @Test
  public void testRayCastInvalidVectors2() {
    Vec2 vec1 = new Vec2();
    Vec2 vec2 = new Vec2();
    Vec2 tr = new Vec2();
    Rot ro = new Rot();
    ro.s = 0;
    ro.c = 1;

    Transform trans = new Transform(tr, ro);

    vec1.x = -0.2f;
    vec1.y = -0.2f;
    vec2.x = 0.1f;
    vec2.y = -0.1f;

    Vec2 vec3 = new Vec2();
    vec3.x = 0.1f;
    vec3.y = 0.1f;
    Vec2 vec4 = new Vec2();
    vec4.x = 0.0f;
    vec4.y = -0.1f;
    edge.set(vec3, vec4);
    RayCastOutput output = new RayCastOutput();
    RayCastInput input = new RayCastInput();
    input.p1.set(vec1);
    input.p2.set(vec2);

    assertFalse(edge.raycast(output, input, trans, 1));
  }

  /*
   Whitebox Testing, Branch Coverage
   raycast should return false if two input vectors are parallel
  */
  @Test
  public void testRayCastInvalidVectors3() {
    Vec2 vec1 = new Vec2();
    Vec2 vec2 = new Vec2();
    Vec2 tr = new Vec2();
    Rot ro = new Rot();
    ro.s = 0;
    ro.c = 1;

    Transform trans = new Transform(tr, ro);

    vec1.x = -0.2f;
    vec1.y = -0.2f;
    vec2.x = 0.1f;
    vec2.y = -0.1f;

    Vec2 vec3 = new Vec2();
    vec3.x = 0.1f;
    vec3.y = 0.1f;
    Vec2 vec4 = new Vec2();
    vec4.x = 0.1f;
    vec4.y = 0.1f;
    edge.set(vec3, vec4);
    RayCastOutput output = new RayCastOutput();
    RayCastInput input = new RayCastInput();
    input.p1.set(vec1);
    input.p2.set(vec2);

    assertFalse(edge.raycast(output, input, trans, 1));
  }

  /*
    Whitebox Testing, Branch Coverage
    raycast should return false if two input vectors are parallel
   */
  @Test
  public void testRayCastInvalidVectors4() {
    Vec2 vec1 = new Vec2();
    Vec2 vec2 = new Vec2();
    Vec2 tr = new Vec2();
    Rot ro = new Rot();
    ro.s = 0;
    ro.c = 1;

    Transform trans = new Transform(tr, ro);

    vec1.x = 0.2f;
    vec1.y = 0.2f;
    vec2.x = 0.1f;
    vec2.y = 0.1f;

    Vec2 vec3 = new Vec2();
    vec3.x = -1f;
    vec3.y = -1f;
    Vec2 vec4 = new Vec2();
    vec4.x = 0.0f;
    vec4.y = -0.1f;
    edge.set(vec3, vec4);
    RayCastOutput output = new RayCastOutput();
    RayCastInput input = new RayCastInput();
    input.p1.set(vec1);
    input.p2.set(vec2);

    assertFalse(edge.raycast(output, input, trans, 1));
  }
}
