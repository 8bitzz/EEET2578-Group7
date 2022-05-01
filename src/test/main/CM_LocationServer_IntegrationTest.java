package main;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CM_LocationServer_IntegrationTest {

    @BeforeClass
    public static void setUpClass() {
        SetupTest.setupService();
    }

    @Test
    public void testGetLocationsByService() {
        List<String> restaurantLocations = ContextManager.getLocationsByService("restaurants");
        assertEquals(2, restaurantLocations.size());
    }
}