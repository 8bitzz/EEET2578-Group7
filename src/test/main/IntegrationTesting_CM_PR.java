package main;

import helper.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class IntegrationTesting_CM_PR {

    @BeforeClass
    public static void setUpClass() {
        SetupTest.setupService();
    }

    @Test
    public void testServiceSuggestionForBadWeather() {
        System.out.println("\nCM request service suggestion in EXTREME weather condition");
        PreferenceRequest request1 = new PreferenceRequest("Jack", 1, null);
        PreferenceRequest request2 = new PreferenceRequest("Jack", 2, null);
        PreferenceRequest request3 = new PreferenceRequest("David", 3, null);

        String preference1 = ContextManager.preferenceWorker.getPreference(request1);
        String preference2 = ContextManager.preferenceWorker.getPreference(request2);
        String preference3 = ContextManager.preferenceWorker.getPreference(request3);

        assertEquals("cinema", preference1);
        assertEquals("cinema", preference2);
        assertEquals("shops", preference3);
    }

    @Test
    public void testServiceSuggestionForApoReached() {
        System.out.println("\nCM request service suggestion when APO Threshold reached");
        PreferenceRequest request1 = new PreferenceRequest("Jack", 0, "APO");
        PreferenceRequest request2 = new PreferenceRequest("David", 0, "APO");

        String preference1 = ContextManager.preferenceWorker.getPreference(request1);
        String preference2 = ContextManager.preferenceWorker.getPreference(request2);

        assertEquals("bowling", preference1);
        assertEquals("cinema", preference2);
    }

    @Test
    public void testServiceSuggestionForTempThresholdReached() {
        System.out.println("\nCM request service suggestion when TEMPERATURE Threshold reached");
        PreferenceRequest request1 = new PreferenceRequest("Jack", 0, "20");
        PreferenceRequest request2 = new PreferenceRequest("Jack", 0, "30");
        PreferenceRequest request3 = new PreferenceRequest("David", 0, "16");

        String preference1 = ContextManager.preferenceWorker.getPreference(request1);
        String preference2 = ContextManager.preferenceWorker.getPreference(request2);
        String preference3 = ContextManager.preferenceWorker.getPreference(request3);

        assertEquals("shops", preference1);
        assertEquals("pool", preference2);
        assertEquals("pool", preference3);
    }

    @Test
    public void testServiceSuggestionForNoThresholdReached() {
        System.out.println("\nCM request service suggestion when NO Threshold reached");
        PreferenceRequest request4 = new PreferenceRequest("Jack", 0, "18");
        PreferenceRequest request5 = new PreferenceRequest("David", 0, "13");

        String preference4 = ContextManager.preferenceWorker.getPreference(request4);
        String preference5 = ContextManager.preferenceWorker.getPreference(request5);

        assertEquals("", preference4);
        assertEquals("", preference5);
    }

    @Test
    public void testServiceSuggestionForWeatherTempReached() {
        System.out.println("\nCM request service suggestion when Weather + Temp Threshold reached");
        PreferenceRequest request1 = new PreferenceRequest("Jack", 1, "30");
        PreferenceRequest request2 = new PreferenceRequest("David", 2, "20");

        String preference1 = ContextManager.preferenceWorker.getPreference(request1);
        String preference2 = ContextManager.preferenceWorker.getPreference(request2);

        assertEquals("pool", preference1);
        assertEquals("pool", preference2);
    }

    @Test
    public void testServiceSuggestionForWeatherAPOReached() {
        System.out.println("\nCM request service suggestion when Weather + APO Threshold reached");
        PreferenceRequest request1 = new PreferenceRequest("Jack", 1, "APO");
        PreferenceRequest request2 = new PreferenceRequest("David", 2, "APO");

        String preference1 = ContextManager.preferenceWorker.getPreference(request1);
        String preference2 = ContextManager.preferenceWorker.getPreference(request2);

        assertEquals("bowling", preference1);
        assertEquals("cinema", preference2);
    }

}