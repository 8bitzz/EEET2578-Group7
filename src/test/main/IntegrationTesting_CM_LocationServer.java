package main;

import helper.SensorData;
import helper.User;
import org.junit.BeforeClass;
import org.junit.Test;
import support.LocationDetails;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class IntegrationTesting_CM_LocationServer {
    @BeforeClass
    public static void setUpClass() {
        SetupTest.setupService();
    }

    @Test
    public void testGetCurrentUserLocation() {
        System.out.println("\nCM request current location status of user");
        AllSensors sensors = new AllSensors("Jack");
        SensorData userSensorData = sensors.getSensorData();
        String currentLocationStatus = ContextManager.locationWorker.locationMapping(userSensorData.location);

        assertEquals(ContextManager.INDOOR, currentLocationStatus);
    }
}