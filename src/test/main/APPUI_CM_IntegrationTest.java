package main;

import org.junit.BeforeClass;
import org.junit.Test;
import support.LocationDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static main.ContextManager.cityInfo;
import static main.ContextManager.users;
import static main.EnviroAPPUI.username;
import static org.junit.Assert.assertEquals;

public class APPUI_CM_IntegrationTest {

    @BeforeClass
    public static void setUpClass() {
        SetupTest.setupService();
    }

    @Test
    public void searchInfo_Testing(){
        List<LocationDetails> cityList = ContextManager.readCityInfo();
        String[] expected_output = {"Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho Chi Minh City, Vietnam. It is the second largest shopping centre in the southern suburbs of Ho Chi Minh City, by gross area, and contains the only H&M store in that region.",
                "Crescent Mall Shopping Centre is located 10km South of the Ho Chi Minh City central business district(CBD) and includes Banana Republic, Baskin Robins, CGV Cinema, Bobapop and over 130 specialty stores.",
                "The Dam Sen Parklands area was created as part of the rejuvenation of the industrial upgrade undertaken for World Expo 1988. The Parklands area is spacious with plenty of green and spaces for all ages. A big lake promenade stretches the area of Dam Sen Parklands.",
                "The Ho Chi Minh City central business district (CBD), or 'the City' is located on a central point in district One. The point, known at its tip as Central Point, slopes upward to the north-west where 'the city' is bounded by parkland and the inner city suburb of District 3, District 4 and District 5."};
        for (int i = 0; i < cityList.size(); i++) {
            assertEquals(expected_output[i], cityList.get(i).getInfo());
        }
    }
    @Test
    public void searchItems_Testing(){
        List<LocationDetails> cityList = ContextManager.readCityInfo();
        String[] expected_output = {"Vivo City Shopping Centre", "Crescent Mall", "Dam Sen Parklands", "Ho Chi Minh City, Downtown"};
        String currentLocation = users.get(username).sensorData.location;
        List<String> result = new ArrayList<>();
        for (LocationDetails locationDetails : cityInfo) {
            if (locationDetails.getLocation().equals(currentLocation)) {
                result.add(locationDetails.getName());
            }
        }
        for (int i = 0; i < cityList.size(); i++) {
            if (Objects.equals(result.get(i), expected_output[i]))
            assertEquals(expected_output[i], cityList.get(i).getName());
        }
    }
//    @Test
//    public void searchItems_Testing(){
//        String[] item = ContextManager.searchItems("Dam Sen Parklands");
//        String[] expected_output = {"Vivo City Shopping Centre", "Crescent Mall", "Dam Sen Parklands", "Ho Chi Minh City, Downtown"};
//    }
}
