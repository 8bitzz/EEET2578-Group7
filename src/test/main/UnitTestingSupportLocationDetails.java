package main;

import org.junit.Test;
import support.LocationDetails;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UnitTestingSupportLocationDetails {
    LocationDetails locationDetails;

    public void setUpLocationDetails() {
        locationDetails = new LocationDetails("Vivo City Shopping Centre",
                "A",
                "Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho Chi Minh City, Vietnam. It is the second largest shopping centre in the southern suburbs of Ho Chi Minh City, by gross area, and contains the only H&M store in that region.",
                Arrays.asList("cinema", "restaurants", "pool", "shops", "bowling"));
    }

    @Test
    public void testInitLocationDetails() {
        setUpLocationDetails();
        List<String> detailStrings = Arrays.asList("name:Vivo City Shopping Centre",
                "location:A",
                "information:Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho Chi Minh City, Vietnam. It is the second largest shopping centre in the southern suburbs of Ho Chi Minh City, by gross area, and contains the only H&M store in that region.",
                "services:cinema,restaurants,pool,shops,bowling");
        LocationDetails locationDetailsTest = new LocationDetails(detailStrings);

        assertEquals(locationDetails.getName(), locationDetailsTest.getName());
        assertEquals(locationDetails.getLocation(), locationDetailsTest.getLocation());
        assertEquals(locationDetails.getInfo(), locationDetailsTest.getInfo());
        assertEquals(locationDetails.getServices().size(), locationDetailsTest.getServices().size());
        for (int i = 0; i < locationDetailsTest.getServices().size(); i++) {
            assertTrue(locationDetails.getServices().contains(locationDetailsTest.getServices().get(i)));
        }
    }

    @Test
    public void testGetName() {
        setUpLocationDetails();
        String expectedName = "Vivo City Shopping Centre";
        String actualName = locationDetails.getName();
        assertEquals(expectedName, actualName);
    }

    @Test
    public void testSetName() {
        setUpLocationDetails();
        String newName = "RMIT University";
        locationDetails.setName(newName);

        assertEquals(newName, locationDetails.getName());
    }

    @Test
    public void testGetLocation() {
        setUpLocationDetails();
        String expectedLocation = "A";
        String actualLocation = locationDetails.getLocation();

        assertEquals(expectedLocation, actualLocation);
    }

    @Test
    public void testSetLocation() {
        setUpLocationDetails();
        String newLocation = "B";
        locationDetails.setLocation(newLocation);

        assertEquals(newLocation, locationDetails.getLocation());
    }

    @Test
    public void testGetInfo() {
        setUpLocationDetails();
        String expectedInfo ="Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho Chi Minh City, Vietnam. It is the second largest shopping centre in the southern suburbs of Ho Chi Minh City, by gross area, and contains the only H&M store in that region.";
        String actualInfo = locationDetails.getInfo();

        assertEquals(expectedInfo, actualInfo);
    }

    @Test
    public void testSetInfo() {
        setUpLocationDetails();
        String newInfo = "New info for location";
        locationDetails.setInfo(newInfo);

        assertEquals(newInfo, locationDetails.getInfo());
    }

    @Test
    public void testGetService() {
        setUpLocationDetails();
        List<String> expectedServices = Arrays.asList("cinema", "restaurants", "pool", "shops", "bowling");

        assertEquals(expectedServices.size(), locationDetails.getServices().size());
        for (int i = 0; i < locationDetails.getServices().size(); i++) {
            assertTrue(locationDetails.getServices().contains(expectedServices.get(i)));
        }
    }

    @Test
    public void testSetService() {
        setUpLocationDetails();
        List<String> newServices = Arrays.asList();
        locationDetails.setServices(newServices);

        assertEquals(newServices.size(), locationDetails.getServices().size());
        for (int i = 0; i < locationDetails.getServices().size(); i++) {
            assertTrue(locationDetails.getServices().contains(newServices.get(i)));
        }
    }
}