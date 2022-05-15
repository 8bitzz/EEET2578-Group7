package main;

import helper.SensorData;
import org.junit.BeforeClass;
import org.junit.Test;
import support.LocationDetails;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class IntegrationTesting_CM_LS {
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

    @Test
    public void testLocationSuggestionIndoor(){
        final String[] locationWithCinema= {"Vivo City Shopping Centre", "Crescent Mall"};
        String service = "cinema";
        List<LocationDetails> cityInfo = ContextManager.readCityInfo();
        List<String> locations = ContextManager.getLocationsByService(service);
        for (LocationDetails locationDetails : cityInfo){
            for (String location : locations){
                if (locationDetails.getName().equals(location)) {
                    assertTrue(Arrays.asList(locationWithCinema).contains(locationDetails.getName()));
                    assertTrue(locationDetails.getServices().contains(service));
                }
            }
        }
        assertEquals(locationWithCinema.length,locations.size());
    }

    @Test
    public void testLocationSuggestionIndoorOutdoor1(){
        final String[] locationWithRestaurant= {"Vivo City Shopping Centre", "Crescent Mall"};
        String service = "restaurants";
        List<LocationDetails> cityInfo = ContextManager.readCityInfo();
        List<String> locations = ContextManager.getLocationsByService(service);

        for (LocationDetails locationDetails : cityInfo){
            for (String location : locations){
                if (locationDetails.getName().equals(location)) {
                    assertTrue(Arrays.asList(locationWithRestaurant).contains(locationDetails.getName()));
                    assertTrue(locationDetails.getServices().contains(service));
                }
            }
        }
        assertEquals(locationWithRestaurant.length,locations.size());
    }

    @Test
    public void testLocationSuggestionIndoorOutdoor2(){
        final String[] locationWithPool= {"Vivo City Shopping Centre"};
        String service = "pool";
        List<LocationDetails> cityInfo = ContextManager.readCityInfo();
        List<String> locations = ContextManager.getLocationsByService(service);

        for (LocationDetails locationDetails : cityInfo){
            for (String location : locations){
                if (locationDetails.getName().equals(location)) {
                    assertTrue(Arrays.asList(locationWithPool).contains(locationDetails.getName()));
                    assertTrue(locationDetails.getServices().contains(service));
                }
            }
        }
        assertEquals(locationWithPool.length,locations.size());
    }

    @Test
    public void testLocationSuggestionIndoorOutdoor3(){
        final String[] locationWithShops= {"Vivo City Shopping Centre", "Crescent Mall"};
        String service = "shops";
        List<LocationDetails> cityInfo = ContextManager.readCityInfo();
        List<String> locations = ContextManager.getLocationsByService(service);

        for (LocationDetails locationDetails : cityInfo){
            for (String location : locations){
                if (locationDetails.getName().equals(location)) {
                    assertTrue(Arrays.asList(locationWithShops).contains(locationDetails.getName()));
                    assertTrue(locationDetails.getServices().contains(service));
                }
            }
        }
        assertEquals(locationWithShops.length,locations.size());
    }

    @Test
    public void testLocationSuggestionOutdoor1(){
        final String[] locationWithShops= {};
        String service = "Ferris wheel";
        List<LocationDetails> cityInfo = ContextManager.readCityInfo();
        List<String> locations = ContextManager.getLocationsByService(service);

        for (LocationDetails locationDetails : cityInfo){
            for (String location : locations){
                if (locationDetails.getName().equals(location)) {
                    assertTrue(Arrays.asList(locationWithShops).contains(locationDetails.getName()));
                    assertTrue(locationDetails.getServices().contains(service));
                }
            }
        }
        assertEquals(locationWithShops.length,locations.size());
    }

    @Test
    public void testLocationSuggestionOutdoor2(){
        final String[] locationWithShops= {};
        String service = "market";
        List<LocationDetails> cityInfo = ContextManager.readCityInfo();
        List<String> locations = ContextManager.getLocationsByService(service);

        for (LocationDetails locationDetails : cityInfo){
            for (String location : locations){
                if (locationDetails.getName().equals(location)) {
                    assertTrue(Arrays.asList(locationWithShops).contains(locationDetails.getName()));
                    assertTrue(locationDetails.getServices().contains(service));
                }
            }
        }
        assertEquals(locationWithShops.length,locations.size());
    }
}