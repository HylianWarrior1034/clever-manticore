package SoftwareTestingUnitTests;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.RayCastInput;
import org.jbox2d.collision.RayCastOutput;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.jbox2d.common.MathUtils.round;
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
  raycast should return false (fail) if two input vectors are parallel
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

  /*
  Whitebox testing
   */
  @Test
  public void testRayCastInvalidVectors5() {
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
    vec3.x = 0.4f;
    vec3.y = 0.4f;
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
Whitebox testing
 */
  @Test
  public void testRayCastInvalidVectors6() {
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
    vec3.x = 0.4f;
    vec3.y = 0.4f;
    Vec2 vec4 = new Vec2();
    vec4.x = 0.5f;
    vec4.y = 0.5f;
    edge.set(vec3, vec4);
    RayCastOutput output = new RayCastOutput();
    RayCastInput input = new RayCastInput();
    input.p1.set(vec1);
    input.p2.set(vec2);

    assertFalse(edge.raycast(output, input, trans, 1));
  }

  /*
    Whitebox Testing, Branch Coverage
 */
  @Test
  public void testRayCastValidVector1() {
    Vec2 vec1 = new Vec2();
    Vec2 vec2 = new Vec2();
    Vec2 tr = new Vec2(0.1f, 0.1f);
    Rot ro = new Rot();
    ro.s = 0;
    ro.c = 1;

    Transform trans = new Transform(tr, ro);

    vec1.x = 0.2f;
    vec1.y = 0.2f;
    vec2.x = 0.1f;
    vec2.y = 0.1f;

    Vec2 vec3 = new Vec2();
    vec3.x = 0.1f;
    vec3.y = 0.1f;
    Vec2 vec4 = new Vec2();
    vec4.x = 0.0f;
    vec4.y = 0.1f;
    edge.set(vec3, vec4);
    RayCastOutput output = new RayCastOutput();
    RayCastInput input = new RayCastInput();
    input.p1.set(vec1);
    input.p2.set(vec2);

    assertTrue(edge.raycast(output, input, trans, 1));
    assertEquals(-0.000000, output.fraction);
    assertEquals(0.000000, output.normal.x);
    assertEquals(1.000000, output.normal.y);
  }

  /*
  Whitebox Testing, Branch Coverage
*/
  @Test
  public void testRayCastValidVector2() {
    Vec2 vec1 = new Vec2();
    Vec2 vec2 = new Vec2();
    Vec2 tr = new Vec2();
    Rot ro = new Rot();
    ro.c = -0.707f;
    ro.s = 0.707f;

    Transform trans = new Transform(tr, ro);

    vec1.x = 0.3f;
    vec1.y = 0.3f;
    vec2.x = 0.1f;
    vec2.y = -0.1f;

    Vec2 vec3 = new Vec2();
    vec3.x = 0.3f;
    vec3.y = 0.2f;
    Vec2 vec4 = new Vec2();
    vec4.x = -0.4f;
    vec4.y = -0.4f;
    edge.set(vec3, vec4);
    RayCastOutput output = new RayCastOutput();
    RayCastInput input = new RayCastInput();
    input.p1.set(vec1);
    input.p2.set(vec2);

    assertTrue(edge.raycast(output, input, trans, 1));
    assertEquals(0.6730, output.fraction, 1e-4);
    assertEquals(0.6508, output.normal.x, 1e-4);
    assertEquals(-0.7593, output.normal.y, 1e-4);
  }


  /*
  There is literally no way to test computeAABB, the function is invisible,
  does nothing
   */

  @Test
  public void testComputeAABBValid1() {
    AABB aabb = new AABB();
    edge.set(new Vec2(0.1f, 0.2f), new Vec2(0.2f, 0.1f));
    Rot rot = new Rot();
    Transform trans = new Transform();
    trans.q.set(rot);
    trans.p.set(new Vec2());
    rot.c = 1;
    rot.s = 0;
    edge.computeAABB(aabb, trans, 1);
    assertEquals(0.0900, aabb.lowerBound.x, 1e-4);
    assertEquals(0.0900, aabb.lowerBound.y, 1e-4);
    assertEquals(0.2100, aabb.upperBound.x, 1e-4);
    assertEquals(0.2100, aabb.upperBound.y, 1e-4);
  }

  @Test
  public void testComputeAABBValid2() {
    AABB aabb = new AABB();
    edge.set(new Vec2(0.2f, 0.1f), new Vec2(0.1f, 0.2f));
    Rot rot = new Rot();
    Transform trans = new Transform();
    trans.q.set(rot);
    trans.p.set(new Vec2());
    rot.c = 0;
    rot.s = 1;
    edge.computeAABB(aabb, trans, 1);
    assertEquals(0.0900, aabb.lowerBound.x, 1e-4);
    assertEquals(0.0900, aabb.lowerBound.y, 1e-4);
    assertEquals(0.2100, aabb.upperBound.x, 1e-4);
    assertEquals(0.2100, aabb.upperBound.y, 1e-4);
  }

  /*
  Whitebox testing
   */
  @Test
  public void testcomputeMass() {
    MassData mass = new MassData();
    mass.mass = 0.1f;
    mass.I = 0.1f;

    Vec2 vec3 = new Vec2();
    vec3.x = 0.1f;
    vec3.y = 0.2f;
    Vec2 vec4 = new Vec2();
    vec4.x = 0.2f;
    vec4.y = 0.1f;
    edge.set(vec3, vec4);

    edge.computeMass(mass, 1);

    assertEquals(0f, mass.mass);
    assertEquals(0f, mass.I);
    assertEquals(0.15f, mass.center.x);
    assertEquals(0.15f, mass.center.y);
  }

  /*
  Blackbox testing, clone() should return an object with the
  same values as the thing that is being cloned
   */
  @Test
  public void testClone() {
    Vec2 vec3 = new Vec2();
    vec3.x = 0.1f;
    vec3.y = 0.2f;
    Vec2 vec4 = new Vec2();
    vec4.x = 0.2f;
    vec4.y = 0.1f;
    edge.set(vec3, vec4);
    edge.m_vertex3.set(vec3);
    edge.m_vertex0.set(vec3);
    EdgeShape edgeClone = (EdgeShape) edge.clone();
    assertEquals(edge.m_vertex0, edgeClone.m_vertex0);
    assertEquals(edge.m_vertex1, edgeClone.m_vertex1);
    assertEquals(edge.m_vertex2, edgeClone.m_vertex2);
    assertEquals(edge.m_vertex3, edgeClone.m_vertex3);
  }

  @Test
  public void testComputeAABBValid3() {
    AABB aabb = new AABB();
    edge.set(new Vec2(0.1f, 0.2f), new Vec2(0.2f, 0.1f));
    Rot rot = new Rot();
    Transform trans = new Transform();
    trans.q.set(rot);
    trans.p.set(new Vec2(0.1f, 0.1f));
    rot.c = 0;
    rot.s = 1;
    edge.computeAABB(aabb, trans, 1);
    assertEquals(0.190, aabb.lowerBound.x, 1e-4);
    assertEquals(0.190, aabb.lowerBound.y, 1e-4);
    assertEquals(0.310, aabb.upperBound.x, 1e-4);
    assertEquals(0.310, aabb.upperBound.y, 1e-4);
  }

  @Test
  public void testComputeAABBValid4() {
    AABB aabb = new AABB();
    edge.set(new Vec2(0.1f, 0.2f), new Vec2(0.2f, 0.1f));
    Rot rot = new Rot();
    rot.c = 0.707f;
    rot.s = 0.707f;
    Transform trans = new Transform();
    trans.q.set(rot);
    trans.p.set(new Vec2());
    edge.computeAABB(aabb, trans, 1);
    assertEquals(-0.0807, aabb.lowerBound.x, 1e-4);
    assertEquals(0.2021, aabb.lowerBound.y, 1e-4);
    assertEquals(0.0807, aabb.upperBound.x, 1e-4);
    assertEquals(0.2221, aabb.upperBound.y, 1e-4);
  }

  @Test
  public void testComputeAABBValid5() {
    AABB aabb = new AABB();
    edge.set(new Vec2(0.1f, 0.2f), new Vec2(0.2f, 0.1f));
    Rot rot = new Rot();
    rot.c = 0.707f;
    rot.s = 0.707f;
    Transform trans = new Transform();
    trans.q.set(rot);
    trans.p.set(new Vec2(0.1f, 0.1f));
    edge.computeAABB(aabb, trans, 1);
    assertEquals(0.0193, aabb.lowerBound.x, 1e-4);
    assertEquals(0.3021, aabb.lowerBound.y, 1e-4);
    assertEquals(0.1807, aabb.upperBound.x, 1e-4);
    assertEquals(0.3221, aabb.upperBound.y, 1e-4);
  }
}
