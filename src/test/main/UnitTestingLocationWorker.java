package main;

import org.junit.Before;
import org.junit.Test;

import static main.ContextManager.communicator;
import static org.junit.Assert.*;

public class UnitTestingLocationWorker {
    private final LocationServer.LocationWorkerI locationWorker = new LocationServer.LocationWorkerI();
    public static final String INDOOR = "Indoor";
    public static final String OUTDOOR = "Outdoor";

    @Before
    public void setUpLocationWorker() {
        communicator = com.zeroc.Ice.Util.initialize();
        ContextManager.iniLocationMapper();
        LocationServer.table = LocationServer.readConfig();
    }

    @Test
    public void testMappingKeyIndoorLocation() {
        System.out.println("\nTest LocationWorker mapping INDOOR location");
        String[] expectedLocations = {INDOOR, INDOOR};
        String[] locationList ={"A", "B"};

        for (int i = 0; i < locationList.length; i++) {
            assertEquals(expectedLocations[i], locationWorker.locationMapping(locationList[i], null));
        }
    }

    @Test
    public void testMappingKeyOutdoorLocation() {
        System.out.println("\nTest LocationWorker mapping OUTDOOR location");
        String[] expectedLocations = {OUTDOOR, OUTDOOR};
        String[] locationList ={"C", "D"};

        for (int i = 0; i < locationList.length; i++) {
            assertEquals(expectedLocations[i], locationWorker.locationMapping(locationList[i], null));
        }
    }

    @Test
    public void testMappingInvalidLocation() {
        System.out.println("\nTest LocationWorker mapping INVALID location");
        String[] expectedLocations = {null, null};
        String[] locationList ={"E", "F"};

        for (int i = 0; i < locationList.length; i++) {
            assertEquals(expectedLocations[i], locationWorker.locationMapping(locationList[i], null));
        }
    }
}