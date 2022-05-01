package main;

import org.junit.Test;
import support.LocationDetails;

import java.util.List;

import static org.junit.Assert.*;

public class ContextManagerTest {

    @Test
    public void testReadCityInfo() {
        List<LocationDetails> cityList = ContextManager.readCityInfo();
        String[] expected_names = {"Vivo City Shopping Centre", "Crescent Mall", "Dam Sen Parklands", "Ho Chi Minh City, Downtown"};
        String[] expected_codes = {"A", "B", "C", "D"};
        String[] expected_info = {"Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho Chi Minh City, Vietnam. It is the second largest shopping centre in the southern suburbs of Ho Chi Minh City, by gross area, and contains the only H&M store in that region.",
        "Crescent Mall Shopping Centre is located 10km South of the Ho Chi Minh City central business district(CBD) and includes Banana Republic, Baskin Robins, CGV Cinema, Bobapop and over 130 specialty stores.",
        "The Dam Sen Parklands area was created as part of the rejuvenation of the industrial upgrade undertaken for World Expo 1988. The Parklands area is spacious with plenty of green and spaces for all ages. A big lake promenade stretches the area of Dam Sen Parklands.",
        "The Ho Chi Minh City central business district (CBD), or 'the City' is located on a central point in district One. The point, known at its tip as Central Point, slopes upward to the north-west where 'the city' is bounded by parkland and the inner city suburb of District 3, District 4 and District 5."};
        int[] expected_services_num = {5, 3, 4, 4};

        assertEquals(4, cityList.size());
        for (int i = 0; i < cityList.size(); i++) {
            assertEquals(expected_names[i], cityList.get(i).getName());
            assertEquals(expected_codes[i], cityList.get(i).getLocation());
            assertEquals(expected_info[i], cityList.get(i).getInfo());
            assertEquals(expected_services_num[i], cityList.get(i).getServices().size());
        }
    }
}