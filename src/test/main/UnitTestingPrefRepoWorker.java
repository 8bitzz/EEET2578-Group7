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
        String expectedPreference = "cinema";
        assertEquals(expectedPreference, preferenceWorkerI.getPreference(request, new Current()));
        System.out.printf("Expected result: %s\n", expectedPreference);
        System.out.printf("Actual result: %s\n", preferenceWorkerI.getPreference(request, new Current()));
    }

//    @Test
//    public void userPreferenceTesting (){
//        System.out.println("\nTest get user info for INVALID user");
//        System.out.println("Preference validation 1:");
//        assertEquals("cinema",preferenceWorkerI.getPreference(new PreferenceRequest("Jack",0 , ""), new Current()));
//        System.out.println("Expected result:  cinema" );
//        System.out.println("Actual result: " + preferenceWorkerI.getPreference(new PreferenceRequest("Jack",0 , ""), new Current()));
//        System.out.println();
//        System.out.println("Preference validation 2:");
//        assertEquals("cinema",preferenceWorkerI.getPreference(new PreferenceRequest("Jack",1 , ""), new Current()));
//        System.out.println("Expected result:  cinema" );
//        System.out.println("Actual result: " + preferenceWorkerI.getPreference(new PreferenceRequest("Jack",1 , ""), new Current()));
//        System.out.println();
//    }
//
//    @Test
//    public void preferenceApoBased(){
//        System.out.println("Preference Test based on APO");
//        assertEquals("bowling",preferenceWorkerI.getPreference(new PreferenceRequest("Jack",0 , "APO"), new Current()));
//        System.out.println("Expected result:  cinema" );
//        System.out.println("Actual result: " + preferenceWorkerI.getPreference(new PreferenceRequest("Jack",0 , "APO"), new Current()));
//        System.out.println();
//    }
//
//    @Test
//    public void preferenceTempBased (){
//        System.out.println("Preference test based on Temp:");
//        assertEquals("pool",preferenceWorkerI.getPreference(new PreferenceRequest("Jack",0 , "30"), new Current()));
//        System.out.println("Expected result:  pool" );
//        System.out.println("Actual result: " + preferenceWorkerI.getPreference(new PreferenceRequest("Jack",0 , "30"), new Current()));
//        System.out.println();
//    }
//
//    @Test
//    public void preferenceNotTempBased (){
//        System.out.println("Test preference should be got");
//        assertEquals("pool",preferenceWorkerI.getPreference(new PreferenceRequest("Jack",0 , "31"), new Current()));
//        System.out.println("Expected result: " + "");
//        System.out.println("Actual result: " + preferenceWorkerI.getPreference(new PreferenceRequest("Jack",0 , "31"), new Current()));
//        System.out.println();
//    }
}
