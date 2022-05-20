package main;
import com.zeroc.Ice.Current;
import helper.PreferenceRequest;
import org.junit.Before;
import org.junit.Test;

import static main.ContextManager.*;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTestingPrefRepoWorker {
    private final PreferenceRepository.PreferenceWorkerI preferenceWorkerI = new  PreferenceRepository.PreferenceWorkerI();

    @Before
    public void setUpPreferenceWorker() {
        communicator = com.zeroc.Ice.Util.initialize();
        ContextManager.iniPreferenceWorker();
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
    }

    @Test
    public void testGetUserInfoForValidUser(){
        System.out.println("\nTest get user info for VALID user");
        String user = "Jack";
        Integer expectedMedicalType = 2;
        assertEquals(expectedMedicalType , preferenceWorkerI.getUserInfo(user, new Current()).medicalConditionType);
        System.out.printf("Expected result: %d\n", expectedMedicalType);
        System.out.printf("Actual result: %d\n", preferenceWorkerI.getUserInfo(user , new Current()).medicalConditionType);
    }

    @Test
    public void testGetUserInfoForInvalidUser(){
        System.out.println("\nTest get user info for INVALID user");
        String user = "Invalid user";
        Integer expectedMedicalType = null;
        assertEquals(expectedMedicalType , preferenceWorkerI.getUserInfo(user, new Current()).medicalConditionType);
        System.out.printf("Expected result: %d\n", expectedMedicalType);
        System.out.printf("Actual result: %d\n", preferenceWorkerI.getUserInfo(user , new Current()).medicalConditionType);
    }

    @Test
    public void testGetPreferenceNormalWeather() {
        System.out.println("\nTest get preference NORMAL weather");
        PreferenceRequest request = new PreferenceRequest("Jack",0 , "");
        String expectedPreference = null;
        assertEquals(expectedPreference, preferenceWorkerI.getPreference(request, new Current()));
        System.out.printf("Expected result: %s\n", expectedPreference);
        System.out.printf("Actual result: %s\n", preferenceWorkerI.getPreference(request, new Current()));
    }

    @Test
    public void testGetPreferenceExtremeWeather() {
        System.out.println("\nTest get preference EXTREME weather");
        PreferenceRequest request = new PreferenceRequest("Jack",1 , "");
        String expectedPreference = "cinema";
        assertEquals(expectedPreference, preferenceWorkerI.getPreference(request, new Current()));
        System.out.printf("Expected result: %s\n", expectedPreference);
        System.out.printf("Actual result: %s\n", preferenceWorkerI.getPreference(request, new Current()));
    }

    @Test
    public void testGetPreferenceAPOThresholdReached() {
        System.out.println("\nTest get preference when APO threshold is reached");
        PreferenceRequest request = new PreferenceRequest("Jack",0 , "APO");
        String expectedPreference = "bowling";
        assertEquals(expectedPreference, preferenceWorkerI.getPreference(request, new Current()));
        System.out.printf("Expected result: %s\n", expectedPreference);
        System.out.printf("Actual result: %s\n", preferenceWorkerI.getPreference(request, new Current()));
    }

    @Test
    public void testGetPreferenceTempThresholdReached() {
        System.out.println("\nTest get preference when Temperature threshold is reached");
        PreferenceRequest request = new PreferenceRequest("Jack",0 , "30");
        String expectedPreference = "pool";
        assertEquals(expectedPreference, preferenceWorkerI.getPreference(request, new Current()));
        System.out.printf("Expected result: %s\n", expectedPreference);
        System.out.printf("Actual result: %s\n", preferenceWorkerI.getPreference(request, new Current()));
    }

    @Test
    public void testGetPreferenceTempThresholdNotReached() {
        System.out.println("\nTest get preference when Temperature threshold is NOT reached");
        PreferenceRequest request = new PreferenceRequest("Jack",0 , "10");
        String expectedPreference = null;
        assertEquals(expectedPreference, preferenceWorkerI.getPreference(request, new Current()));
        System.out.printf("Expected result: %s\n", expectedPreference);
        System.out.printf("Actual result: %s\n", preferenceWorkerI.getPreference(request, new Current()));
    }

    @Test
    public void testGetPreferenceWeatherTempThresholdReached() {
        System.out.println("\nTest get preference when Temperature threshold is NOT reached");
        PreferenceRequest request = new PreferenceRequest("Jack",1 , "35");
        String expectedPreference = "pool";
        assertEquals(expectedPreference, preferenceWorkerI.getPreference(request, new Current()));
        System.out.printf("Expected result: %s\n", expectedPreference);
        System.out.printf("Actual result: %s\n", preferenceWorkerI.getPreference(request, new Current()));
    }

}
