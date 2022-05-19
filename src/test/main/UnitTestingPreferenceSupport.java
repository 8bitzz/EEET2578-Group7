package main;
import org.junit.Test;

import support.Preference;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class UnitTestingPreferenceSupport {

    @Test
    public void testGetMedicalConditionPref() {
        System.out.println("\nTest get medical condition of user:");
        String user = "David";
        Integer expectedMedCondition = 3;
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        List<Preference> prefList = PreferenceRepository.readPreference();
        Preference pref = prefList.get(1);
        assertEquals(expectedMedCondition, pref.getMedicalCondition());
        System.out.println("Expected result: " + expectedMedCondition.toString());
        System.out.println("Actual result: " + pref.getMedicalCondition().toString());
    }

    @Test
    public void testSetMedicalConditionPref() {
        System.out.println("\nTest set medical condition of user:");
        String user = "David";
        Integer expectedMedCondition = 1;
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        List<Preference> prefList = PreferenceRepository.readPreference();
        Preference pref = prefList.get(1);
        pref.setMedicalCondition(1);
        assertEquals(expectedMedCondition, pref.getMedicalCondition());
        System.out.println("Expected result: " + expectedMedCondition.toString());
        System.out.println("Actual result: " + pref.getMedicalCondition().toString());
    }

    @Test
    public void testGetNamePref() {
        System.out.println("\nTest get name of user:");
        String user = "Jack";
        String expectedName = "Jack";
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        List<Preference> prefList = PreferenceRepository.readPreference();
        Preference jack = prefList.get(0);
        assertEquals(expectedName, jack.getName());
        System.out.println("Expected result: " + expectedName);
        System.out.println("Actual result: " + jack.getName());
    }

    @Test
    public void testSetNameConditionPref() {
        System.out.println("\nTest set name of user:");
        String user = "Jack";
        String expectedName = "Renamed";
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        List<Preference> prefList = PreferenceRepository.readPreference();
        Preference pref = prefList.get(0);
        pref.setName("Renamed");
        assertEquals(expectedName, pref.getName());
        System.out.println("Expected result: " + expectedName);
        System.out.println("Actual result: " + pref.getName());
    }

    @Test
    public void testGetSuggestionPref() {
        System.out.println("\nTest get suggestion of user:");
        String user = "Jack";
        List<String> jackSuggestion = new ArrayList<>();
        jackSuggestion.add("when 20 suggest shops");
        jackSuggestion.add("when 30 suggest pool");
        jackSuggestion.add("when APO suggest bowling");
        jackSuggestion.add("when weather suggest cinema");
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        List<Preference> prefList = PreferenceRepository.readPreference();
        Preference jack = prefList.get(0);
        assertEquals(jackSuggestion, jack.getSuggestions());
        System.out.println("Expected result: " + jackSuggestion.toString());
        System.out.println("Actual result: " + jack.getSuggestions());
    }

    @Test
    public void testSetSuggestionPref() {
        System.out.println("\nTest set suggestion of user:");
        String user = "Jack";
        List<String> expectedSuggestion = new ArrayList<>();
        expectedSuggestion.add("when 40 suggest airport");
        expectedSuggestion.add("when 50 suggest restaurant");
        expectedSuggestion.add("when APO suggest school");
        expectedSuggestion.add("when weather suggest hospital");
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        List<Preference> prefList = PreferenceRepository.readPreference();
        Preference jackPref = prefList.get(0);
        jackPref.setSuggestions(expectedSuggestion);
        assertEquals(expectedSuggestion, jackPref.getSuggestions());
        System.out.println("Expected result: " + expectedSuggestion.toString());
        System.out.println("Actual result: " + jackPref.getSuggestions());
    }

}