package main;

import org.junit.Test;
import support.LocationDetails;

import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.IceStorm.AlreadySubscribed;
import com.zeroc.IceStorm.BadQoS;
import com.zeroc.IceStorm.InvalidSubscriber;
import com.zeroc.IceStorm.TopicPrx;

import static org.junit.Assert.*;

public class LocationServerTest {
    public static List<LocationDetails> cityInfo = ContextManager.readCityInfo();
    public static LocationWorkerPrx locationWorker;
    public static final String INDOOR = "Indoor";
    public static final String OUTDOOR = "Outdoor";

    @Test
    public void testCheckIndoorOrOutdoor() {
        String[] expected_status = {INDOOR, INDOOR, OUTDOOR, OUTDOOR};

        assertEquals(4, cityInfo.size());
        for (int i = 0; i < cityList.size(); i++) {
            String locationStatus = locationWorker.locationMapping(cityInfo.get(i).getLocation());
            assertEquals(expected_status[i], locationStatus);
        }
    }
}