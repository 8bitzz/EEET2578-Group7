package main;

import helper.SensorData;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitTestingAllSensors {
    AllSensors sensors = new AllSensors("Jack");

    @Test
    public void testGetSensorDataForCurrentUser() {
        System.out.println("\nTest Get sensor data for current user");
        String expectedName = "Jack";
        String expectedLocation = "A";
        int expectedTemperature = 10;
        int expectedUvr = 200;
        SensorData data = sensors.getSensorData();
        assertEquals(expectedName, data.username);
        assertEquals(expectedTemperature, data.temperature);
        assertEquals(expectedUvr, data.aqi);
        assertEquals(expectedLocation, data.location);
    }

}