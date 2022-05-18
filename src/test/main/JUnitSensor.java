package main;

import org.junit.BeforeClass;
import org.junit.Test;
import support.Sensor;

import static org.junit.Assert.*;

public class JUnitSensor {
    Sensor data = new Sensor("Jack", "Location");

    @BeforeClass
    public static void setUpClass() {
        SetupTest.setupService();
    }

//    @Test
//    public void testInitSensor() {
//
//    }

    @Test
    public void testSensorGetUsername() {
        assertEquals("Jack", data.getUsername());
    }

    @Test
    public void testSensorSetUsername() {
        data.setUsername("David");
        assertEquals("David", data.getUsername());
    }

    @Test
    public void testSensorGetType() {
        assertEquals("Location", data.getType());
    }

    @Test
    public void testSensorSetType() {
        data.setType("AQI");
        assertEquals("AQI", data.getType());
    }
}
