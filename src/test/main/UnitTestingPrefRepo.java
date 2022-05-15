package main;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import support.Preference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)
public class UnitTestingPrefRepo {
    private final String username;
    private final String temperatureSuggestion;
    private final String apoSuggestion;
    private final String weatherSuggestion;
    private final int temperature;
    private final int weather;

    public UnitTestingPrefRepo(String username, int temperature, int weather , String tempSuggestion, String apoSuggestion, String weatherSuggestion) {
        this.username = username;
        this.temperatureSuggestion = tempSuggestion;
        this.apoSuggestion = apoSuggestion;
        this.weather = weather;
        this.weatherSuggestion = weatherSuggestion;
        this.temperature = temperature;
    }

    @Parameterized.Parameters
    public static Collection data () {

        return Arrays.asList(new Object[][] {
                {"Jack" , 30 , 0 , "pool", "bowling" , "cinema"},
//                {"Jack" , 30 , 1 , "pool", "bowling" , "cinema"},
//                {"Jack" , 30 , 2 , "pool", "bowling" , "cinema"},
//                {"Jack" , 30 , 3 , "pool", "bowling" , "cinema"},
//                {"Jack" , 30 , 4 , "pool", "bowling" , "cinema"},
        });
    }

    @Test
    public void testReadPreference() {
        System.out.println("\nTest the reading preference file in Preference Repository class");
        List<Preference> expectedResult = new ArrayList<>();
        List<String> jackPref = new ArrayList<>();
        jackPref.add("name: Jack");
        jackPref.add("Medical Condition Type: 2");
        jackPref.add("pref: when 20 suggest shops");
        jackPref.add("pref: when 30 suggest pool");
        jackPref.add("pref: when APO suggest bowling");
        jackPref.add("pref: when weather suggest cinema");

        List<String> davidPref = new ArrayList<>();
        davidPref.add("name: David");
        davidPref.add("Medical Condition Type: 3");
        davidPref.add("pref: when 16 suggest pool");
        davidPref.add("pref: when APO suggest cinema");
        davidPref.add("pref: when weather suggest shops");

        expectedResult.add(new Preference(jackPref));
        expectedResult.add(new Preference(davidPref));

        assertEquals(expectedResult.toString() , PreferenceRepository.readPreference().toString());
        System.out.println("Expected result: " + expectedResult);
        System.out.println("Actual result: " + PreferenceRepository.readPreference());
    }

    // testMissingPreferenceFile is executed as UAT testing since we need the Preference file for other unit testings

    @Test
    public void testGetTempSuggestionWhenNotReachedThreshold() {
        System.out.println("\nTest getting temperature suggestion when temperature value is LOWER THAN threshold value");
        String user = "Jack";
        int currentTemp =  0;
        String expectedSuggestion = null;
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        assertEquals(expectedSuggestion, PreferenceRepository.getSuggestionTemp(user , currentTemp));
        System.out.printf("Expected result: %s\n", expectedSuggestion);
        System.out.printf("Actual result: %s\n", PreferenceRepository.getSuggestionTemp(user , currentTemp));
    }

    @Test
    public void testGetTempSuggestionWhenThresholdReached() {
        System.out.println("\nTest getting temperature suggestion when temperature value is EQUALS TO threshold value");
        String user = "Jack";
        int temp1 =  20;
        int temp2 =  30;
        String expectedSuggestion1 = "shops";
        String expectedSuggestion2 = "pool";
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        assertEquals(expectedSuggestion1, PreferenceRepository.getSuggestionTemp(user , temp1));
        assertEquals(expectedSuggestion2, PreferenceRepository.getSuggestionTemp(user , temp2));
        System.out.printf("Expected result 1: %s\n", expectedSuggestion1);
        System.out.printf("Actual result 1: %s\n", PreferenceRepository.getSuggestionTemp(user , temp1));
        System.out.printf("Expected result 2: %s\n", expectedSuggestion2);
        System.out.printf("Actual result 2: %s\n", PreferenceRepository.getSuggestionTemp(user , temp2));
    }

    @Test
    public void testGetTempSuggestionWhenTempHigherThanBothThresholds() {
        System.out.println("\nTest getting temperature suggestion when temperature value is GREATER THAN BOTH threshold values");
        String user = "Jack";
        int currentTemp = 35;
        String expectedSuggestion = "pool";
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        assertEquals(expectedSuggestion, PreferenceRepository.getSuggestionTemp(user , currentTemp));
        System.out.printf("Expected result: %s\n", expectedSuggestion);
        System.out.printf("Actual result: %s\n", PreferenceRepository.getSuggestionTemp(user , currentTemp));
    }

    @Test
    public void testGetTempSuggestionForInvalidUser() {
        System.out.println("\nTest getting temperature suggestion for INVALID user");
        String user = "Fake user";
        int currentTemp = 20;
        String expectedSuggestion = null;
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        assertEquals(expectedSuggestion, PreferenceRepository.getSuggestionTemp(user , currentTemp));
        System.out.printf("Expected result: %s\n", expectedSuggestion);
        System.out.printf("Actual result: %s\n", PreferenceRepository.getSuggestionTemp(user , currentTemp));
    }

    @Test
    public void testGetTempSuggestionWhenTempBetweenThresholds() {
        System.out.println("\nTest getting temperature suggestion when temperature value is BETWEEN 2 threshold values");
        String user = "Jack";
        int currentTemp = 25;
        String expectedSuggestion = "shops";
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        assertEquals(expectedSuggestion, PreferenceRepository.getSuggestionTemp(user , currentTemp));
        System.out.printf("Expected result: %s\n", expectedSuggestion);
        System.out.printf("Actual result: %s\n", PreferenceRepository.getSuggestionTemp(user , currentTemp));
    }

    @Test
    public void testGetAPOSuggestionForValidUser () {
        System.out.println("\nTest getting APO suggestion for VALID user");
        String user = "Jack";
        String expectedSuggestion = "bowling";
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        assertEquals(expectedSuggestion , PreferenceRepository.getSuggestionAPO(user));
        System.out.printf("Expected result: %s\n", expectedSuggestion);
        System.out.printf("Actual result: %s\n", PreferenceRepository.getSuggestionAPO(user));
    }

    @Test
    public void testGetAPOSuggestionForInvalidUser () {
        System.out.println("\nTest getting APO suggestion for INVALID user");
        String user = "Invalid user";
        String expectedSuggestion = null;
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        assertEquals(expectedSuggestion , PreferenceRepository.getSuggestionAPO(user));
        System.out.printf("Expected result: %s\n", expectedSuggestion);
        System.out.printf("Actual result: %s\n", PreferenceRepository.getSuggestionAPO(user));
    }

    @Test
    public void testGetWeatherSuggestionNormalCondition() {
        System.out.println("\nTest getting weather suggestion in NORMAL weather");
        String user = "Jack";
        int weatherValue = 0;
        String expectedSuggestion = null;
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        assertEquals(expectedSuggestion , PreferenceRepository.getSuggestionWeather(user , weatherValue));
        System.out.printf("Expected result: %s\n", expectedSuggestion);
        System.out.printf("Actual result: %s\n", PreferenceRepository.getSuggestionWeather(user , weatherValue));
    }

    @Test
    public void testGetWeatherSuggestionExtremeCondition() {
        System.out.println("\nTest getting weather suggestion in HEAVY RAIN, HAIL STORM, STRONG WIND weather");
        String user = "Jack";
        int weatherValue1 = 1;
        int weatherValue2 = 2;
        int weatherValue3 = 3;
        String expectedSuggestion = "cinema";
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        assertEquals(expectedSuggestion , PreferenceRepository.getSuggestionWeather(user , weatherValue1));
        assertEquals(expectedSuggestion , PreferenceRepository.getSuggestionWeather(user , weatherValue2));
        assertEquals(expectedSuggestion , PreferenceRepository.getSuggestionWeather(user , weatherValue3));
        System.out.printf("Expected result: %s\n", expectedSuggestion);
        System.out.printf("Actual result 1: %s\n", PreferenceRepository.getSuggestionWeather(user , weatherValue1));
        System.out.printf("Actual result 2: %s\n", PreferenceRepository.getSuggestionWeather(user , weatherValue2));
        System.out.printf("Actual result 3: %s\n", PreferenceRepository.getSuggestionWeather(user , weatherValue3));
    }

    @Test
    public void testGetWeatherSuggestionInvalidValue() {
        System.out.println("\nTest getting weather suggestion for INVALID weather value");
        String user = "Jack";
        int weatherValue = 10;
        String expectedSuggestion = null;
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        assertEquals(expectedSuggestion , PreferenceRepository.getSuggestionWeather(user , weatherValue));
        System.out.printf("Expected result: %s\n", expectedSuggestion);
        System.out.printf("Actual result: %s\n", PreferenceRepository.getSuggestionWeather(user , weatherValue));
    }

    @Test
    public void testGetWeatherWeatherSuggestionInvalidUser() {
        System.out.println("\nTest getting weather suggestion for INVALID user");
        String user = "Invalid user";
        int weatherValue = 1;
        String expectedSuggestion = null;
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        assertEquals(expectedSuggestion , PreferenceRepository.getSuggestionWeather(user , weatherValue));
        System.out.printf("Expected result: %s\n", expectedSuggestion);
        System.out.printf("Actual result: %s\n", PreferenceRepository.getSuggestionWeather(user , weatherValue));
    }
}
