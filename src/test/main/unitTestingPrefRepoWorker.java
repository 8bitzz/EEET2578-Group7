package main;
import com.zeroc.Ice.Current;
import helper.PreferenceRequest;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class unitTestingPrefRepoWorker {

    private final PreferenceRepository.PreferenceWorkerI preferenceWorkerI = new  PreferenceRepository.PreferenceWorkerI();

    @Test
    public void userInfoTesting(){
        System.out.println("User info comparison- Jack: ");
        assertEquals(1 , preferenceWorkerI.getUserInfo("Jack" , new Current()).medicalConditionType);
        System.out.println("Expected result: 1");
        System.out.println("Actual result: " + preferenceWorkerI.getUserInfo("Jack" , new Current()).medicalConditionType);
        System.out.println();
        System.out.println("User info comparison - David:");
        assertEquals( 3, preferenceWorkerI.getUserInfo("David" , new Current()).toString());
        System.out.println("Expected result: 3");
        System.out.println("Actual result: " + preferenceWorkerI.getUserInfo("David" , new Current()));
        System.out.println();
    }

    @Test
    public void userPreferenceTesting (){
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
}
