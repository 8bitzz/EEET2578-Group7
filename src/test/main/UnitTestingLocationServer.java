package main;

import helper.LocationWorkerPrx;
import org.junit.Test;
import support.LocationDetails;

import java.util.List;

import static org.junit.Assert.*;

public class UnitTestingLocationServer {
    public static List<LocationDetails> cityInfo = ContextManager.readCityInfo();
    public static LocationWorkerPrx locationWorker;
    public static final String INDOOR = "Indoor";
    public static final String OUTDOOR = "Outdoor";

    @Test
    public void testCheckIndoorOrOutdoor() {
        String[] expected_status = {INDOOR, INDOOR, OUTDOOR, OUTDOOR};

        assertEquals(4, cityInfo.size());
        for (int i = 0; i < cityInfo.size(); i++) {
            String locationStatus = locationWorker.locationMapping(cityInfo.get(i).getLocation());
            assertEquals(expected_status[i], locationStatus);
        }
    }
}