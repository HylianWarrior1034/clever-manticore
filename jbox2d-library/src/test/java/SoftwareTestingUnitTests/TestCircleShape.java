package SoftwareTestingUnitTests;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
}
