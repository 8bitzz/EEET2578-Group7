package main;

import helper.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import support.LocationDetails;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import support.HandleUserInput;

public class ContextManagerTest {
    private static ContextManagerWorker CM_Worker;
    private static String username;

    @BeforeClass
    public static void setUpClass() {
        //executed only once, before the first test
        username = "Jack";
        ContextManager.communicator = com.zeroc.Ice.Util.initialize();
        ContextManager.cityInfo = ContextManager.readCityInfo();
        ContextManager.iniPreferenceWorker();
        ContextManager.iniLocationMapper();
        ContextManager.iniWeatherAlarmWorker();
        ContextManager.runWeatherAlarm();
        ContextManager.setupContextManagerWorker();
        CM_Worker = new ContextManager.ContextManagerWorkerI();
        CM_Worker.addUser(username, null);
    }

    @Test
    public void testReadCityInfo() {
        List<LocationDetails> cityList = ContextManager.readCityInfo();
        LocationDetails firstCity = cityList.get(0);
        assertEquals(4, cityList.size());
        assertEquals("Vivo City Shopping Centre", firstCity.getName());
        assertEquals("A", firstCity.getLocation());
        assertEquals("Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho Chi Minh City, Vietnam. " +
                "It is the second largest shopping centre in the southern suburbs of Ho Chi Minh City, by gross area, and contains the only H&M store in that region.", firstCity.getInfo());
        assertEquals(5, firstCity.getServices().size());
    }

    @Test
    public void testGetLocationsByService() {
        ContextManager.cityInfo = ContextManager.readCityInfo();
        List<String> restaurantLocations = ContextManager.getLocationsByService("restaurants");
        assertEquals(2, restaurantLocations.size());
    }
}