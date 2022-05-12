package main;

import org.junit.BeforeClass;
import org.junit.Test;
import support.LocationDetails;

import java.util.LinkedHashMap;
import java.util.List;
import static org.junit.Assert.*;

public class LocationServerTest {
    public static final String INDOOR = "Indoor";
    public static final String OUTDOOR = "Outdoor";
    public static LinkedHashMap<String, String> locationTable;

    @BeforeClass
    public static void setUpClass() {
        SetupTest.setupService();
    }

    @Test
    public void testCheckIndoorOrOutdoor() {
        List<LocationDetails> cityInfo = ContextManager.readCityInfo();
        String[] expected_status = {INDOOR, INDOOR, OUTDOOR, OUTDOOR};

        assertEquals(4, cityInfo.size());
        for (int i = 0; i < cityInfo.size(); i++) {
            String locationStatus = ContextManager.locationWorker.locationMapping(cityInfo.get(i).getLocation());
            assertEquals(expected_status[i], locationStatus);
        }
    }

    @Test
    public void testCheckLSReadInfo() {
        locationTable = LocationServer.readConfig();
        List<LocationDetails> cityInfo = ContextManager.readCityInfo();
        String[] expected_status = {INDOOR, INDOOR, OUTDOOR, OUTDOOR};

        assertEquals(4, cityInfo.size());
        for (int i = 0; i < 4; i++) {
            String locationStatus = locationTable.get(cityInfo.get(i).getLocation());
            assertEquals(expected_status[i], locationStatus);
        }
    }
}