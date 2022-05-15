package main;

import helper.PreferenceRequest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class IntegrationTesting_CM_PR_LS {
    @BeforeClass
    public static void setUpClass() {
        SetupTest.setupService();
    }

    @Test
    public void testLocationSuggestionForBadWeather() {
        System.out.println("\nCM request location suggestion in EXTREME weather condition");
        PreferenceRequest request1 = new PreferenceRequest("Jack", 1, null);
        String preference1 = ContextManager.preferenceWorker.getPreference(request1);
        List<String> actualLocationSuggestion = ContextManager.getLocationsByService(preference1);

        String[] expectedLocationSuggestion = {"Vivo City Shopping Centre", "Crescent Mall"};

        assertEquals(expectedLocationSuggestion.length, actualLocationSuggestion.size());
        for (int i = 0; i < expectedLocationSuggestion.length; i++) {
            assertEquals(expectedLocationSuggestion[i], actualLocationSuggestion.get(i));
        }
    }

    @Test
    public void testLocationSuggestionForApoReached() {
        System.out.println("\nCM request location suggestion when APO Threshold reached");
        PreferenceRequest request1 = new PreferenceRequest("Jack", 0, "APO");
        String preference1 = ContextManager.preferenceWorker.getPreference(request1);
        List<String> actualLocationSuggestion = ContextManager.getLocationsByService(preference1);

        String[] expectedLocationSuggestion= {"Vivo City Shopping Centre"};

        assertEquals(expectedLocationSuggestion.length, actualLocationSuggestion.size());
        for (int i = 0; i < expectedLocationSuggestion.length; i++) {
            assertEquals(expectedLocationSuggestion[i], actualLocationSuggestion.get(i));
        }
    }

    @Test
    public void testLocationSuggestionForTempThresholdReached() {
        System.out.println("\nCM request location suggestion when TEMPERATURE Threshold reached");
        PreferenceRequest request1 = new PreferenceRequest("Jack", 0, "20");
        String preference1 = ContextManager.preferenceWorker.getPreference(request1);
        List<String> actualLocationSuggestion = ContextManager.getLocationsByService(preference1);

        String[] expectedLocationSuggestion= {"Vivo City Shopping Centre", "Crescent Mall"};

        assertEquals(expectedLocationSuggestion.length, actualLocationSuggestion.size());
        for (int i = 0; i < expectedLocationSuggestion.length; i++) {
            assertEquals(expectedLocationSuggestion[i], actualLocationSuggestion.get(i));
        }
    }

    @Test
    public void testLocationSuggestionForNoThresholdReached() {
        System.out.println("\nCM request location suggestion when NO Threshold reached");
        PreferenceRequest request1 = new PreferenceRequest("Jack", 0, "18");
        String preference1 = ContextManager.preferenceWorker.getPreference(request1);
        List<String> actualLocationSuggestion = ContextManager.getLocationsByService(preference1);

        String[] expectedLocationSuggestion= {};

        assertEquals(expectedLocationSuggestion.length, actualLocationSuggestion.size());
        for (int i = 0; i < expectedLocationSuggestion.length; i++) {
            assertEquals(expectedLocationSuggestion[i], actualLocationSuggestion.get(i));
        }
    }

    @Test
    public void testLocationSuggestionForWeatherTempReached() {
        System.out.println("\nCM request service suggestion when Weather + Temp Threshold reached");
        PreferenceRequest request1 = new PreferenceRequest("Jack", 1, "30");
        String preference1 = ContextManager.preferenceWorker.getPreference(request1);
        List<String> actualLocationSuggestion = ContextManager.getLocationsByService(preference1);

        String[] expectedLocationSuggestion= {"Vivo City Shopping Centre"};

        assertEquals(expectedLocationSuggestion.length, actualLocationSuggestion.size());
        for (int i = 0; i < expectedLocationSuggestion.length; i++) {
            assertEquals(expectedLocationSuggestion[i], actualLocationSuggestion.get(i));
        }
    }

    @Test
    public void testLocationSuggestionForWeatherAPOReached() {
        System.out.println("\nCM request location suggestion when Weather + APO Threshold reached");
        PreferenceRequest request1 = new PreferenceRequest("Jack", 1, "APO");
        String preference1 = ContextManager.preferenceWorker.getPreference(request1);
        List<String> actualLocationSuggestion = ContextManager.getLocationsByService(preference1);

        String[] expectedLocationSuggestion= {"Vivo City Shopping Centre"};

        assertEquals(expectedLocationSuggestion.length, actualLocationSuggestion.size());
        for (int i = 0; i < expectedLocationSuggestion.length; i++) {
            assertEquals(expectedLocationSuggestion[i], actualLocationSuggestion.get(i));
        }
    }
}