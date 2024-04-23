package SoftwareTestingUnitTests;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.RayCastInput;
import org.jbox2d.collision.RayCastOutput;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


/*
 * So given a red line in the pit report, you should make a test that reaches
 * that part of the code and fails for the given mutant.
 */
/**
 * Notes on Chain Shape:
 * There are two important inner variables: m_vertices and m_count. The latter
 * counts the number of vertices the shape has. The shape starts with a vertex,
 * its children have the next vertex if any.
 */
public class TestChainShape {
  private ChainShape chain;

  private final Vec2[] basicVertices = new Vec2[] {
    new Vec2(),
    new Vec2(1.0f,1.0f),
    new Vec2(2.0f,2.0f),
    new Vec2(3.0f,3.0f),
    new Vec2(4.0f, 4.0f)
  };

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
  public void testGetChildrenCountMut_SubtractionIsAddition() {
    chain.m_vertices = basicVertices;
    chain.m_count = basicVertices.length - 1;
    assertNotEquals(5, chain.getChildCount());
  }

  @Test
  public void testGetChildrenCountMut_ReturnZero() {
    chain.m_vertices = basicVertices;
    chain.m_count = basicVertices.length - 1;
    assertNotEquals(0,chain.getChildCount());
  }

//  @Test
//  public void testNewChainNoChildren() {
//    assertEquals(0, chain.getChildCount());
//  }

//  @Test
//  public void testGetChildEdgeNoChildChain() {
//    EdgeShape edge = new EdgeShape();
//    chain.getChildEdge(edge, 0);
//    assertNull(edge);
//  }

  @Test
  public void testGetChildEdgeChildChain() {
    chain.m_vertices = basicVertices;
    chain.m_count = basicVertices.length - 1; // 4
    EdgeShape edge;
    // Path 0: index: 0 pass assert, IF index = 0 , index < 2
    edge = new EdgeShape();
    chain.getChildEdge(edge, 0);
    assertEquals(chain.m_prevVertex,edge.m_vertex0);
    assertEquals(new Vec2(0f,0f), edge.m_vertex1);
    assertEquals(new Vec2(1f,1f), edge.m_vertex2);
    assertEquals(new Vec2(2f,2f), edge.m_vertex3);
    assertNotNull(edge);
  }

  @Test
  public void testGetChildEdgeChain2() {
    chain.m_vertices = basicVertices;
    chain.m_count = basicVertices.length - 1; // 4
    EdgeShape edge;
    // Path 1: index: 1 pass assert, IF index > 0 , index < 2
    edge = new EdgeShape();
    chain.getChildEdge(edge, 1);
    assertEquals(new Vec2(),edge.m_vertex0);
    assertEquals(new Vec2(1f,1f), edge.m_vertex1);
    assertEquals(new Vec2(2f,2f), edge.m_vertex2);
    assertEquals(new Vec2(3f,3f), edge.m_vertex3);
    assertNotNull(edge);
  }

  @Test
  public void testGetChildEdgeChain3() {
    chain.m_vertices = basicVertices;
    chain.m_count = basicVertices.length - 1; // 4
    EdgeShape edge;
    // Path 2: index 2 pass assert, IF index > 0 , index == 2
    edge = new EdgeShape();
    chain.getChildEdge(edge, 2);
    assertEquals(new Vec2(1f,1f),edge.m_vertex0);
    assertEquals(new Vec2(2f,2), edge.m_vertex1);
    assertEquals(new Vec2(3f, 3f), edge.m_vertex2);
    assertEquals(chain.m_nextVertex, edge.m_vertex3);
    assertNotNull(edge);
  }

  // @Test
  // public void testGetChildEdgeChildfullChain() {
  //   chain.m_vertices = basicVertices;
  //   chain.m_count = basicVertices.length - 1; // 4
  //   EdgeShape edge;
  //   //Path 3: index 3 fail assert: m_count - 1
  //   assertThrows(AssertionError.class, () -> {
  //     chain.getChildEdge(new EdgeShape(), 3);
  //   });
  //   // Path -1 : index fails assert:  index < 0
  //   assertThrows(AssertionError.class, () -> {
  //     chain.getChildEdge(new EdgeShape(), -1);
  //   });
  //
  // }

  @Test
  public void testPointAlwaysFalse() {
    assertFalse(chain.testPoint(new Transform(),new Vec2()));
  }

  @Test
  @Tag("modified test")
  public void testModifiedCloneWithBetterCount() {
    chain.m_vertices = basicVertices;
    chain.m_count = basicVertices.length;
    Shape copy = chain.clone();
    chain.m_count--; //covers the issue to reach deeper into the code
    assertEquals(ShapeType.CHAIN,copy.m_type);
  }

//  @Test
//  public void testCloneOfEmptyChain() {
//    ChainShape new_chain = (ChainShape) chain.clone();
//    assertEquals(chain,(new_chain));
//    assertNull(new_chain.m_vertices);
//    assertEquals(0, new_chain.m_count);
//    assertArrayEquals(chain.m_vertices, new_chain.m_vertices);
//    assertEquals(chain.m_prevVertex, new_chain.m_prevVertex);
//    assertEquals(chain.m_hasPrevVertex, new_chain.m_hasPrevVertex);
//    assertEquals(chain.m_hasNextVertex, new_chain.m_hasNextVertex);
//    assertEquals(chain.m_nextVertex, new_chain.m_nextVertex);
//  }

//  @Test
//  public void testCloneOfNonEmptyChain() {
//    chain.m_vertices = new Vec2[] {new Vec2(),new Vec2(0f,1f)};
//    chain.m_count = 1;
//    ChainShape new_chain = (ChainShape) chain.clone();
//    assertEquals(chain,(new_chain));
//    assertNull(new_chain.m_vertices);
//    assertEquals(1, new_chain.m_count);
//    assertArrayEquals(chain.m_vertices, new_chain.m_vertices);
//    assertEquals(chain.m_prevVertex, new_chain.m_prevVertex);
//    assertEquals(chain.m_hasPrevVertex, new_chain.m_hasPrevVertex);
//    assertEquals(chain.m_hasNextVertex, new_chain.m_hasNextVertex);
//    assertEquals(chain.m_nextVertex, new_chain.m_nextVertex);
//  }

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
  public void testRaycastNotChildIndex() {
    chain.m_count = 5;
    RayCastOutput outputray = new RayCastOutput();
    RayCastInput inputray = new RayCastInput();
    Transform xf = new Transform();
    assertThrows(NullPointerException.class, () -> {
      assertTrue(chain.raycast(outputray, inputray, xf, 0));
    });
    chain.m_vertices = new Vec2[] {new Vec2(), new Vec2()};
    chain.m_count = 1;
    assertFalse(chain.raycast(outputray, inputray, xf, 0));
    // assertThrows(NullPointerException.class, () -> {
    //   assertTrue(chain.raycast(outputray, inputray, xf, 0));
    // });

  }

  @Test
  public void testRaycastLastChild() {
    chain.m_count = 5;
    RayCastOutput outputray = new RayCastOutput();
    RayCastInput inputray = new RayCastInput();
    Transform xf = new Transform();
    chain.m_vertices = basicVertices;
    chain.m_count = basicVertices.length - 1;
    int childindex = chain.m_count -1;
    assertFalse(chain.raycast(outputray, inputray, xf, childindex));
    // assertThrows(NullPointerException.class, () -> {
    //   assertTrue(chain.raycast(outputray, inputray, xf, 0));
    // });

  }


  @Test
  public void testComputeAABBPoint() {
    AABB box3 = new AABB(new Vec2(),new Vec2());
    chain.m_vertices = new Vec2[]{new Vec2(),new Vec2(),new Vec2()};
    chain.m_count = basicVertices.length -1; //3
    chain.computeAABB(box3,new Transform(),1);
    assertEquals(new Vec2(), box3.lowerBound);
    assertEquals(new Vec2(), box3.upperBound);
  }

  @Test
  public void testComputeAABBPoint2() {
    AABB box3 = new AABB(new Vec2(),new Vec2());
    chain.m_vertices = new Vec2[]{new Vec2(1,1),new Vec2(),new Vec2()};
    chain.m_count = basicVertices.length -1; //3
    chain.computeAABB(box3,new Transform(),1);
    assertEquals(new Vec2(), box3.lowerBound);
    assertEquals(new Vec2(), box3.upperBound);
  }

  /**
   * NOTE: none of these exceptions should be thrown
   */
//  @Test
//  public void testComputeAABBPartitions() {
//    AABB box1 = null;
//    AABB box2 = new AABB();
//    AABB box3 = new AABB(new Vec2(Float.MIN_VALUE, Float.MAX_VALUE), new Vec2(Float.MAX_VALUE, Float.MIN_VALUE));
//    chain.m_vertices = basicVertices;
//    chain.m_count = basicVertices.length -1; //3
//    assertThrows(NullPointerException.class, () -> {
//      chain.computeAABB(null,new Transform(),0);
//    });
//    assertThrows(NullPointerException.class, () -> {
//      chain.computeAABB(box2,null,0);
//    });
//    assertThrows(NullPointerException.class, () -> {
//      chain.computeAABB(box3,new Transform(),-5);
//    });
//    assertThrows(NullPointerException.class, () -> {
//      chain.computeAABB(box3,new Transform(),5);
//    });
//
//  }
//  @Test
//  public void testComputeAABB() {
//    AABB box = new AABB();
//    Transform xf = new Transform();
//    chain.computeAABB(box,xf,0);
//    assertNotNull(box);
//  }

//  @Test
//  public void testComputeAABBValidIndices() {
//    Vec2[] vertices = new Vec2[] {
//      new Vec2(),
//      new Vec2(1.0f,1.0f),
//      new Vec2(2.0f,2.0f),
//      new Vec2(3.0f,3.0f),
//      new Vec2(4.0f, 4.0f)
//    };
//    chain.m_vertices = vertices;
//    chain.m_count = 3;
//    AABB aabb = new AABB();
//    chain.computeAABB(aabb, new Transform(),2);
//    assertFalse(new Vec2().equals(aabb.lowerBound));
//    assertFalse(new Vec2().equals(aabb.upperBound));
//  }

  @Test
  public void testComputeMass() {
    MassData massData = new MassData();
    float density = 0.0F;
    chain.computeMass(massData, density);
    assertNotNull(massData);
  }

  @Test
  public void testComputeMassCenter() {
    MassData massData = new MassData();
    float density = 0.0F;
    chain.computeMass(massData, density);
    assertEquals(new Vec2(0f,0f), massData.center);
  }

  @Test
  public void testSetPrevVertex() {
    Vec2 vertex = new Vec2(4.5f, 0.4f);
    chain.setPrevVertex(vertex);
    assertEquals(vertex, chain.m_prevVertex);
    assertTrue(chain.m_hasPrevVertex);
  }

  @Test
  public void testSetNextVertex() {
    Vec2 vertex = new Vec2(4.5f, 0.4f);
    chain.setNextVertex(vertex);
    assertEquals(vertex, chain.m_nextVertex);
    assertTrue(chain.m_hasNextVertex);
  }

  @Test
  public void testCreateLoopFromEmptyVertices() {
    Vec2[] loopVertices = {new Vec2()};
    assertThrows(ArrayIndexOutOfBoundsException.class,() -> {
      chain.createLoop(loopVertices,3);
    });
  }

  @Test
  public void testCreateLoop() {
    Vec2[] loopVertices = new Vec2[] {new Vec2(),new Vec2(0,1f),new Vec2(0.5f,1f),new Vec2()};
    chain.createLoop(loopVertices,3);
    assertArrayEquals(loopVertices, chain.m_vertices);
    assertTrue(chain.m_hasPrevVertex);
    assertTrue(chain.m_hasNextVertex);
    assertEquals(new Vec2(0,1f), chain.m_nextVertex);
    assertEquals(new Vec2(0.5f,1f), chain.m_prevVertex);
  }

  @Test
  public void testCreateLoopException() {
    Vec2[] loopVertices = new Vec2[] {new Vec2(),new Vec2(0.00004f,0.00004f),new Vec2(0.00004f,0.00004f)};
    assertThrows(RuntimeException.class, () -> {
      chain.createLoop(loopVertices,3);
    });
  }

//  @Test
//  public void testCreateLoopFails() {
//    Vec2[] loopVertices = new Vec2[] {new Vec2(),new Vec2(0.00004f,0.00004f),new Vec2(0.5f,-1.0f)};
//    assertThrows(AssertionError.class, () -> {
//      chain.createLoop(loopVertices,2);
//    });
//    chain.m_count = 3;
//    assertThrows(AssertionError.class, () -> {
//      chain.createLoop(loopVertices,3);
//    });
//    chain.m_count = 0;
//    chain.m_vertices = loopVertices;
//    assertThrows(AssertionError.class, () -> {
//      chain.createLoop(loopVertices,4);
//    });
//    chain.m_count = -1;
//    assertThrows(AssertionError.class, () -> {
//      chain.createLoop(loopVertices,4);
//    });
//  }

  @Test
  public void testCreateChainRegular() {
    chain.createChain(basicVertices, basicVertices.length - 1);
    //saves N-1 vertices
    assertEquals(basicVertices[0], chain.m_vertices[0]);
    // assertArrayEquals(basicVertices, chain.m_vertices);
    assertFalse(chain.m_hasNextVertex);
    assertFalse(chain.m_hasPrevVertex);
  }

  @Test
  public void testCreateChainRepeatingVecs() {
    Vec2[] v = new Vec2[] {new Vec2(), new Vec2(), new Vec2()};
    chain = new ChainShape();
    assertThrows(RuntimeException.class,() -> {
      chain.createChain(v, 2);
    });
    // assertArrayEquals(v, chain.m_vertices);
  }

  @Test
  public void testCreateChain2() {
    //Settings.linearSlop = 0.005f - > squared = 0.000025f
    Vec2[] v = new Vec2[] {new Vec2(1f,1f), new Vec2(0,0), new Vec2(0.0025f,0.06f)};
    // assertThrows(RuntimeException.class,() -> {
    //   chain.createChain(v, 1);
    // });
    chain.createChain(v, 3);
    assertArrayEquals(v, chain.m_vertices);
  }


  @Test
  public void testCreateChainEdgeCountEqualtoVecCount() {
    Vec2[] v = new Vec2[] {new Vec2(), new Vec2(1f,1f), new Vec2(2f,2f)};
    chain = new ChainShape();
    chain.createChain(v, 3);
    // assertThrows(RuntimeException.class,() -> {
    //   chain.createChain(v, 3);
    // });
    assertArrayEquals(v, chain.m_vertices);
  }

  /*
  null   | n
  empty  | p
  filled | z
   */
//  @Test
//  public void testCreateChainPartitions() {
//    Vec2[] vert1 = null;
//    Vec2[] vert2 = new Vec2[]{};
//    Vec2[] vert3 = basicVertices; // 5 vertices, 4 edges
//    int countN = -10;
//    int countP = 10;
//    int countZ = 0;
//    assertThrows(AssertionError.class,() -> {
//      chain.createChain(null, countN);
//    });
//    assertThrows(AssertionError.class,() -> {
//      chain.createChain(null, countP);
//    });
//    assertThrows(AssertionError.class,() -> {
//      chain.createChain(null, 0);
//    });
//    assertThrows(AssertionError.class,() -> {
//      chain.createChain(null, 1);
//    });
//  }

}
