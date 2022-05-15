package main;

import helper.LocationWorkerPrx;
import org.junit.Test;
import support.LocationDetails;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.*;

public class UnitTestingLocationServer {
    public static final String INDOOR = "Indoor";
    public static final String OUTDOOR = "Outdoor";

    @Test
    public void testReadLocationConfigFile() {
        System.out.println("\nTest LS read Location Config file");
        LinkedHashMap<String, String> actualResult = LocationServer.readConfig();
        assertEquals(INDOOR, actualResult.get("A"));
        assertEquals(INDOOR, actualResult.get("B"));
        assertEquals(OUTDOOR, actualResult.get("C"));
        assertEquals(OUTDOOR, actualResult.get("D"));
    }
}