package main;

import org.junit.BeforeClass;
import org.junit.Test;
import support.Sensor;

import static org.junit.Assert.*;

public class JUnitSensor {
    Sensor data1 = new Sensor("Jack", "Location");
    Sensor data2 = new Sensor("David", "Temperature");
    Sensor data3 = new Sensor("David", "AQI");

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
        assertEquals("Jack", data1.getUsername());
        assertEquals("David", data2.getUsername());
    }

    @Test
    public void testSensorSetUsername() {
        data1.setUsername("David");
        assertEquals("David", data1.getUsername());
        data2.setUsername("Jack");
        assertEquals("Jack", data2.getUsername());
    }

    @Test
    public void testSensorGetType() {
        assertEquals("Location", data1.getType());
        assertEquals("Temperature", data2.getType());
        assertEquals("AQI", data3.getType());
    }

    @Test
    public void testSensorSetType() {
        data1.setType("Temperature");
        assertEquals("Temperature", data1.getType());
        data1.setType("AQI");
        assertEquals("AQI", data1.getType());
        data1.setType("Location");
        assertEquals("Location", data1.getType());
    }
}
