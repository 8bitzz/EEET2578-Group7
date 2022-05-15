package main;

import helper.*;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntegrationTesting_CM_PrefRepo {

    @BeforeClass
    public static void setUpClass() {
        SetupTest.setupService();
    }

    @Test
    public void testServiceSuggestionForTempThresholdReached() {
        ContextManager cm = new ContextManager();
        PreferenceRequest request1 = new PreferenceRequest("Jack", 0, "20");
        PreferenceRequest request2 = new PreferenceRequest("Jack", 0, "30");
        PreferenceRequest request3 = new PreferenceRequest("David", 0, "16");

        String preference1 = cm.preferenceWorker.getPreference(request1);
        String preference2 = cm.preferenceWorker.getPreference(request2);
        String preference3 = cm.preferenceWorker.getPreference(request3);

        assertEquals("shops", preference1);
        assertEquals("pool", preference2);
        assertEquals("pool", preference3);
    }

    @Test
    public void testServiceSuggestionForApoReached() {
        ContextManager cm = new ContextManager();
        PreferenceRequest request1 = new PreferenceRequest("Jack", 0, "APO");
        PreferenceRequest request2 = new PreferenceRequest("David", 0, "APO");

        String preference1 = cm.preferenceWorker.getPreference(request1);
        String preference2 = cm.preferenceWorker.getPreference(request2);

        assertEquals("bowling", preference1);
        assertEquals("cinema", preference2);
    }

    @Test
    public void testServiceSuggestionForBadWeather() {
        ContextManager cm = new ContextManager();
        PreferenceRequest request1 = new PreferenceRequest("Jack", 1, null);
        PreferenceRequest request2 = new PreferenceRequest("Jack", 2, null);
        PreferenceRequest request3 = new PreferenceRequest("David", 3, null);

        String preference1 = cm.preferenceWorker.getPreference(request1);
        String preference2 = cm.preferenceWorker.getPreference(request2);
        String preference3 = cm.preferenceWorker.getPreference(request3);

        assertEquals("cinema", preference1);
        assertEquals("cinema", preference2);
        assertEquals("shops", preference3);
    }

    @Test
    public void testServiceSuggestionForNoThresholdReached() {
        ContextManager cm = new ContextManager();
        PreferenceRequest request4 = new PreferenceRequest("Jack", 0, "18");
        PreferenceRequest request5 = new PreferenceRequest("David", 0, "13");

        String preference4 = cm.preferenceWorker.getPreference(request4);
        String preference5 = cm.preferenceWorker.getPreference(request5);

        assertEquals("", preference4);
        assertEquals("", preference5);
    }

    @Test
    public void testServiceSuggestionForWeatherTempReached() {
        ContextManager cm = new ContextManager();
        PreferenceRequest request1 = new PreferenceRequest("Jack", 1, "30");
        PreferenceRequest request2 = new PreferenceRequest("David", 2, "20");

        String preference1 = cm.preferenceWorker.getPreference(request1);
        String preference2 = cm.preferenceWorker.getPreference(request2);

        assertEquals("pool", preference1);
        assertEquals("pool", preference2);
    }

    @Test
    public void testServiceSuggestionForWeatherAPOReached() {
        ContextManager cm = new ContextManager();
        PreferenceRequest request1 = new PreferenceRequest("Jack", 1, "APO");
        PreferenceRequest request2 = new PreferenceRequest("David", 2, "APO");

        String preference1 = cm.preferenceWorker.getPreference(request1);
        String preference2 = cm.preferenceWorker.getPreference(request2);

        assertEquals("bowling", preference1);
        assertEquals("cinema", preference2);
    }

    // How to test Weather + APO + Temp Reached at the same time ???
    @Test
    public void testServiceSuggestionForAPOTempReached() {
        ContextManager cm = new ContextManager();
        PreferenceRequest request1 = new PreferenceRequest("Jack", 0, "");
        PreferenceRequest request2 = new PreferenceRequest("David", 0, "");

        String preference1 = cm.preferenceWorker.getPreference(request1);
        String preference2 = cm.preferenceWorker.getPreference(request2);

        assertEquals("cinema", preference1);
        assertEquals("shops", preference2);
    }
}