package main;

import org.junit.BeforeClass;
import org.junit.Test;
import support.LocationDetails;

import java.util.List;

import static org.junit.Assert.*;

public class CM_LocationServer_IntegrationTest {

    @BeforeClass
    public static void setUpClass() {
        SetupTest.setupService();
    }

    @Test
    public void testLocationSuggestion1(){
        String service = "pool";
        List<LocationDetails> cityInfo = ContextManager.readCityInfo();
        List<String> locations = ContextManager.getLocationsByService(service);
        for (LocationDetails locationDetails : cityInfo){
            for (String location : locations){
                if (locationDetails.getName().equals(location))
                    assertTrue(locationDetails.getServices().contains(service));
            }
        }
    }
    @Test
    public void testLocationSuggestion2(){
        String service = "shops";
        List<LocationDetails> cityInfo = ContextManager.readCityInfo();
        List<String> locations = ContextManager.getLocationsByService(service);
        for (LocationDetails locationDetails : cityInfo){
            for (String location : locations){
                if (locationDetails.getName().equals(location))
                    assertTrue(locationDetails.getServices().contains(service));
            }
        }
    }
}