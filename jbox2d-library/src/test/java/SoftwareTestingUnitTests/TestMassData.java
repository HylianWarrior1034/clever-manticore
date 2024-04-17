package SoftwareTestingUnitTests;
import org.jbox2d.collision.shapes.MassData;
import org.jbox2d.common.Vec2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMassData {
  MassData mass = new MassData();

  @BeforeEach
  public void createMassData() {
    mass.I = 0.1f;
    mass.mass = 0.1f;
    mass.center.x = 0.2f;
    mass.center.y = 0.2f;
  }

  @Test
  public void testInit1() {
    MassData massComp = new MassData();
    Vec2 vecComp = new Vec2();
    assertEquals(0f, massComp.mass);
    assertEquals(0f, massComp.I);
    assertEquals(vecComp, massComp.center);
  }

  @Test
  public void testInit2() {
    MassData massComp = new MassData(mass);
    assertEquals(massComp.mass, mass.mass);
    assertEquals(massComp.I, mass.I);
    assertEquals(massComp.center, mass.center);
  }

  @Test
  public void testSet() {
    MassData massComp = new MassData(mass);
    massComp.set(mass);
    assertEquals(massComp.mass, mass.mass);
    assertEquals(massComp.I, mass.I);
    assertEquals(massComp.center, mass.center);
  }

  @Test
  public void testClone() {
    MassData massComp = mass.clone();
    assertEquals(massComp.mass, mass.mass);
    assertEquals(massComp.I, mass.I);
    assertEquals(massComp.center, mass.center);
  }
}
