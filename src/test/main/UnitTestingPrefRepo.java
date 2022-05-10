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
                {"Jack" , 30 , 0 , "pool" , "cinema" , null},
                {"Jack" , 30 , 1 , "pool", "cinema" , "cinema"},
                {"Jack" , 30 , 2 , "pool", "cinema" , "cinema"},
                {"Jack" , 30 , 3 , "pool", "cinema" , "cinema"},
                {"Jack" , 30 , 4 , "pool", "cinema" , "cinema"},
        });
    }

    @Test
    public void testGettingSuggestionTemp () {
        System.out.println("Test getting temperature suggestion from Preference Repository");
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        assertEquals(temperatureSuggestion, PreferenceRepository.getSuggestionTemp(username , temperature));
        System.out.println("Expected output: " + temperatureSuggestion);
        System.out.println("Actual output: " +PreferenceRepository.getSuggestionTemp(username , temperature) );
    }

    @Test
    public void testGettingSuggestionWeather () {
        System.out.println("Test getting weather suggestion from Preference Repository");
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        assertEquals(weatherSuggestion , PreferenceRepository.getSuggestionWeather(username , weather));
        System.out.println("Expected output: " + weatherSuggestion);
        System.out.println("Actual output: " +PreferenceRepository.getSuggestionWeather(username , weather) );
    }

    @Test
    public void testGettingSuggestionAPO () {
        System.out.println("Test getting temperature suggestion from Preference Repository");
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        assertEquals(apoSuggestion , PreferenceRepository.getSuggestionAPO(username ));
        System.out.println("Expected output:" + apoSuggestion);
        System.out.println("Actual output: " +PreferenceRepository.getSuggestionAPO(username) );
    }

    @Test
    public void testReadPreference() {
        System.out.println("Test the reading preference file in Preference Repository class");
        List<Preference> expectedResult = new ArrayList<>();
        List<String> jackPref = new ArrayList<>();
        jackPref.add("name: Jack");
        jackPref.add("Medical Condition Type: 1");
        jackPref.add("pref: when 30 suggest pool");
        jackPref.add("pref: when APO suggest cinema");
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
        System.out.println("Expected output: " + expectedResult);
        System.out.println("Actual output: " + PreferenceRepository.readPreference());
    }
}
