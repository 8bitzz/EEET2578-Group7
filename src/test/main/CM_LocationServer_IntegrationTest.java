package main;

import org.junit.BeforeClass;
import org.junit.Test;
import support.LocationDetails;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CM_LocationServer_IntegrationTest {
    @BeforeClass
    public static void setUpClass() {
        SetupTest.setupService();
    }

    @Test
    public void testLocationSuggestion1(){
        final String[] locationWithRestaurant= {"Vivo City Shopping Centre", "Crescent Mall"};
        String service = "restaurants";
        List<LocationDetails> cityInfo = ContextManager.readCityInfo();
        List<String> locations = ContextManager.getLocationsByService(service);
        System.out.println(locations.size());
        for (LocationDetails locationDetails : cityInfo){
            for (String location : locations){
                if (locationDetails.getName().equals(location)) {
                    System.out.println(location + " - " + locationDetails.getName());
                    assertTrue(Arrays.asList(locationWithRestaurant).contains(locationDetails.getName()));
                    assertTrue(locationDetails.getServices().contains(service));
                }
            }
        }
        assertEquals(locationWithRestaurant.length,locations.size());
    }
    @Test
    public void testLocationSuggestion2(){
        final String[] locationWithCinema= {"Vivo City Shopping Centre", "Crescent Mall"};
        String service = "cinema";
        List<LocationDetails> cityInfo = ContextManager.readCityInfo();
        List<String> locations = ContextManager.getLocationsByService(service);
        System.out.println(locations.size());
        for (LocationDetails locationDetails : cityInfo){
            for (String location : locations){
                if (locationDetails.getName().equals(location)) {
                    System.out.println(location + " - " + locationDetails.getName());
                    assertTrue(Arrays.asList(locationWithCinema).contains(locationDetails.getName()));
                    assertTrue(locationDetails.getServices().contains(service));
                }
            }
        }
        assertEquals(locationWithCinema.length,locations.size());
    }
    @Test
    public void testLocationSuggestion3(){
        final String[] locationWithPool= {"Vivo City Shopping Centre"};
        String service = "pool";
        List<LocationDetails> cityInfo = ContextManager.readCityInfo();
        List<String> locations = ContextManager.getLocationsByService(service);
        System.out.println(locations.size());
//
        for (LocationDetails locationDetails : cityInfo){
            for (String location : locations){
                if (locationDetails.getName().equals(location)) {
                    System.out.println(location + " - " + locationDetails.getName());
                    assertTrue(Arrays.asList(locationWithPool).contains(locationDetails.getName()));
                    assertTrue(locationDetails.getServices().contains(service));
                }
            }
        }
        assertEquals(locationWithPool.length,locations.size());
    }
    @Test
    public void testLocationSuggestion4(){
        final String[] locationWithShops= {"Vivo City Shopping Centre", "Crescent Mall"};
        String service = "shops";
        List<LocationDetails> cityInfo = ContextManager.readCityInfo();
        List<String> locations = ContextManager.getLocationsByService(service);
        System.out.println(locations.size());
//
        for (LocationDetails locationDetails : cityInfo){
            for (String location : locations){
                if (locationDetails.getName().equals(location)) {
                    System.out.println(location + " - " + locationDetails.getName());
                    assertTrue(Arrays.asList(locationWithShops).contains(locationDetails.getName()));
                    assertTrue(locationDetails.getServices().contains(service));
                }
            }
        }
        assertEquals(locationWithShops.length,locations.size());
    }

    @Test
    public void testLocationSuggestion5(){
        final String[] locationWithShops= {};
        String service = "Ferris wheel";
        List<LocationDetails> cityInfo = ContextManager.readCityInfo();
        List<String> locations = ContextManager.getLocationsByService(service);
        System.out.println(locations.size());
//
        for (LocationDetails locationDetails : cityInfo){
            for (String location : locations){
                if (locationDetails.getName().equals(location)) {
                    System.out.println(location + " - " + locationDetails.getName());
                    assertTrue(Arrays.asList(locationWithShops).contains(locationDetails.getName()));
                    assertTrue(locationDetails.getServices().contains(service));
                }
            }
        }
        assertEquals(locationWithShops.length,locations.size());
    }
    @Test
    public void testLocationSuggestion6(){
        final String[] locationWithShops= {};
        String service = "market";
        List<LocationDetails> cityInfo = ContextManager.readCityInfo();
        List<String> locations = ContextManager.getLocationsByService(service);
        System.out.println(locations.size());
//
        for (LocationDetails locationDetails : cityInfo){
            for (String location : locations){
                if (locationDetails.getName().equals(location)) {
                    System.out.println(location + " - " + locationDetails.getName());
                    assertTrue(Arrays.asList(locationWithShops).contains(locationDetails.getName()));
                    assertTrue(locationDetails.getServices().contains(service));
                }
            }
        }
        assertEquals(locationWithShops.length,locations.size());
    }
}