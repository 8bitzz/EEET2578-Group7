package main;

import org.junit.BeforeClass;
import org.junit.Test;
import support.Sensor;

import java.util.LinkedHashMap;

import static org.junit.Assert.*;

public class UnitTestingSensorSupport {
    Sensor data1 = new Sensor("Jack", "Location");
    Sensor data2 = new Sensor("David", "Temperature");
    Sensor data3 = new Sensor("David", "AQI");

    //    Used for testing getData and setData of data3 ("David")
    LinkedHashMap<String, Integer> result;
    public static final Integer testData1 = 15;
    public static final Integer testData2 = 11;


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

    @Test
    public void testSensorGetData() {
//        Get data from DavidAQI
        result = data3.getData();
        assertEquals(testData1, result.get("200"));
        assertEquals(testData2, result.get("90"));
    }

    @Test
    public void testSensorSetData() {
//        Creating a new data set to replace with the old one
        LinkedHashMap<String, Integer> newResult = new LinkedHashMap<String, Integer>();
        newResult.put("150", 10);
        newResult.put("120", 12);
        data3.setData(newResult);
        result = data3.getData();

        assertEquals(newResult.get("150"), result.get("150"));
        assertEquals(newResult.get("120"), result.get("120"));
    }

}