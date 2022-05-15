package main;
import com.zeroc.Ice.Current;
import helper.PreferenceRequest;
import org.junit.Test;

import static main.ContextManager.*;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTestingPrefRepoWorker {

    private final PreferenceRepository.PreferenceWorkerI preferenceWorkerI = new  PreferenceRepository.PreferenceWorkerI();

    @Test
    public void userInfoTesting(){
        communicator = com.zeroc.Ice.Util.initialize();
        ContextManager.iniPreferenceWorker();
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        System.out.println("User info comparison- Jack: ");
        assertEquals(2 , preferenceWorkerI.getUserInfo("Jack" , new Current()).medicalConditionType);
        System.out.println("Expected result: 2");
        System.out.println("Actual result: " + preferenceWorkerI.getUserInfo("Jack" , new Current()).medicalConditionType);
    }

    @Test
    public void userPreferenceTesting (){
        communicator = com.zeroc.Ice.Util.initialize();
        ContextManager.iniPreferenceWorker();
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        System.out.println("Preference validation 1:");
        assertEquals("cinema",preferenceWorkerI.getPreference(new PreferenceRequest("Jack",0 , ""), new Current()));
        System.out.println("Expected result:  cinema" );
        System.out.println("Actual result: " + preferenceWorkerI.getPreference(new PreferenceRequest("Jack",0 , ""), new Current()));
        System.out.println();
        System.out.println("Preference validation 2:");
        assertEquals("cinema",preferenceWorkerI.getPreference(new PreferenceRequest("Jack",1 , ""), new Current()));
        System.out.println("Expected result:  cinema" );
        System.out.println("Actual result: " + preferenceWorkerI.getPreference(new PreferenceRequest("Jack",1 , ""), new Current()));
        System.out.println();
    }

    @Test
    public void preferenceApoBased(){
        communicator = com.zeroc.Ice.Util.initialize();
        ContextManager.iniPreferenceWorker();
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        System.out.println("----------------------");
        System.out.println("Preference Test based on APO");
        assertEquals("bowling",preferenceWorkerI.getPreference(new PreferenceRequest("Jack",0 , "APO"), new Current()));
        System.out.println("Expected result:  cinema" );
        System.out.println("Actual result: " + preferenceWorkerI.getPreference(new PreferenceRequest("Jack",0 , "APO"), new Current()));
        System.out.println();
    }

    @Test
    public void preferenceTempBased (){
        communicator = com.zeroc.Ice.Util.initialize();
        ContextManager.iniPreferenceWorker();
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        System.out.println("----------------------");
        System.out.println("Preference test based on Temp:");
        assertEquals("pool",preferenceWorkerI.getPreference(new PreferenceRequest("Jack",0 , "30"), new Current()));
        System.out.println("Expected result:  pool" );
        System.out.println("Actual result: " + preferenceWorkerI.getPreference(new PreferenceRequest("Jack",0 , "30"), new Current()));
        System.out.println();
    }

    @Test
    public void preferenceNotTempBased (){
        communicator = com.zeroc.Ice.Util.initialize();
        ContextManager.iniPreferenceWorker();
        PreferenceRepository.preferences = PreferenceRepository.readPreference();
        System.out.println("----------------------");
        System.out.println("Test preference should be got");
        assertEquals("pool",preferenceWorkerI.getPreference(new PreferenceRequest("Jack",0 , "31"), new Current()));
        System.out.println("Expected result: " + "");
        System.out.println("Actual result: " + preferenceWorkerI.getPreference(new PreferenceRequest("Jack",0 , "31"), new Current()));
        System.out.println();
    }

}
