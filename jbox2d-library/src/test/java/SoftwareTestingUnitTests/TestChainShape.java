package SoftwareTestingUnitTests;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.RayCastInput;
import org.jbox2d.collision.RayCastOutput;
import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.collision.shapes.ShapeType;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Notes on Chain Shape:
 * There are two important inner variables: m_vertices and m_count. The latter
 * counts the number of vertices the shape has. The shape starts with a vertex,
 * its children have the next vertex if any.
 */
public class TestChainShape {
  // Testing: clone()
  private ChainShape chain;

  @BeforeEach
  public void setup() {
    chain = new ChainShape();
  }

  @Test
  public void testChainMyType() {
    assertEquals(ShapeType.CHAIN, chain.m_type);
  }

  @Test
  public void testChainGetType() {
    assertEquals(ShapeType.CHAIN, chain.getType());
  }

  @Test
  public void testChainConstructorNoVertices() {
    assertNull(chain.m_vertices);
  }

  @Test
  public void testNewChainNoChildren() {
    assertEquals(0, chain.getChildCount());
  }

  @Test
  public void testGetChildEdgeNoChildChain() {
    EdgeShape edge = new EdgeShape();
    chain.getChildEdge(edge, 0);
    assertNull(edge);
  }

  @Test
  public void testPointAlwaysFalse() {
    assertFalse(chain.testPoint(new Transform(),new Vec2()));
  }

  @Test
  public void testCloneProducesIdenticalChain() {
    ChainShape new_chain = (ChainShape) chain.clone();
    assertEquals(chain,(new_chain));
  }

  @ParameterizedTest
  @CsvSource({"1", "0", "-1"})
  public void testChainGetRadius(int a) {
    chain.m_radius = a;
    assertEquals(a, chain.getRadius());
  }

  @ParameterizedTest
  @CsvSource({"1", "0", "-1"})
  public void testChainSetRadius(int a) {
    chain.setRadius(a);
    assertEquals(a, chain.m_radius);
  }

  @Test
  public void testRaycast() {
    chain.m_count = 5;
    RayCastOutput outputray = new RayCastOutput();
    RayCastInput inputray = new RayCastInput();
    Transform xf = new Transform();
    assertTrue(chain.raycast(outputray, inputray, xf, 0));
  }

  @Test
  public void testComputeAABB() {
    AABB box = new AABB();
    Transform xf = new Transform();
    int childindex = 0;
    chain.computeAABB(box,xf,0);
    assertNotNull(box);
  }

  @Test
  public void testComputeMass() {
    MassData massData = new MassData();
    float density = 0.0F;
    chain.computeMass(massData, density);
    assertNotNull(massData);
  }



}
