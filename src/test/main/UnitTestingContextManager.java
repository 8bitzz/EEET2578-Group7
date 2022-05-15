package main;

import helper.ContextManagerWorker;
import helper.User;
import org.junit.BeforeClass;
import org.junit.Test;
import support.LocationDetails;

import java.util.List;
import static org.junit.Assert.*;


public class UnitTestingContextManager {
    public static final Integer NORMAL = 0;

    @BeforeClass
    public static void setUpClass() {
        SetupTest.setupService();
    }

//    @Test
//    public void testCheckWeather(){
//        ContextManager.checkWeather(NORMAL);
//    }

    @Test
    public void testReadCityInfo() {
        System.out.println("\nTest CM read city info file");
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

    @Test
    public void testGetLocationsByServiceIndoor() {
        System.out.println("\nTest CM get locations of services provided by INDOOR locations");
        List<String> cinemaLocations = ContextManager.getLocationsByService("cinema");
        String[] expectedLocations = {"Vivo City Shopping Centre", "Crescent Mall"};

        assertEquals(expectedLocations.length, cinemaLocations.size());
        for (int i = 0; i < cinemaLocations.size(); i++) {
            assertEquals(expectedLocations[i], cinemaLocations.get(i));
        }
    }

    @Test
    public void testGetLocationsByServiceOutdoor() {
        System.out.println("\nTest CM get locations of services provided by OUTDOOR locations");
        List<String> ferrisWheelsLocations = ContextManager.getLocationsByService("Ferris wheel");
        List<String> marketLocations = ContextManager.getLocationsByService("market");

        assertEquals(0, ferrisWheelsLocations.size());
        assertEquals(0, marketLocations.size());
    }

    @Test
    public void testGetLocationsByServiceIndoorOutdoor() {
        System.out.println("\nTest CM get locations of services provided by INDOOR + OUTDOOR locations");
        List<String> restaurantLocations = ContextManager.getLocationsByService("restaurants");
        List<String> poolLocations = ContextManager.getLocationsByService("pool");
        List<String> shopsLocations = ContextManager.getLocationsByService("shops");
        List<String> bowlingLocations = ContextManager.getLocationsByService("bowling");

        String[] expectedLocations = {"Vivo City Shopping Centre", "Crescent Mall"};

        assertEquals(2, restaurantLocations.size());
        assertEquals(2, shopsLocations.size());
        for (int i = 0; i < 2; i++) {
            assertEquals(expectedLocations[i], restaurantLocations.get(i));
            assertEquals(expectedLocations[i], shopsLocations.get(i));
        }

        assertEquals(1, poolLocations.size());
        assertEquals(1, bowlingLocations.size());
        assertEquals("Vivo City Shopping Centre", poolLocations.get(0));
        assertEquals("Vivo City Shopping Centre", bowlingLocations.get(0));
    }

    @Test
    public void testTickClockForValidUser() {
        System.out.println("\nTest tick clock for VALID user");
        int duration = 5;
        for (int i = 0; i < duration; i++) {
            ContextManager.tickClock("Jack");
        }
        assertEquals(duration, ContextManager.users.get("Jack").clock, 0.0000000001);
    }

    @Test
    public void testTickClockForInvalidUser() {
        System.out.println("\nTest tick clock for INVALID user");
        int duration = 5;
        for (int i = 0; i < duration; i++) {
            ContextManager.tickClock("Invalid user");
        }
        assertEquals(null, ContextManager.users.get("Invalid user").clock);
    }

    @Test
    public void testResetClockValidUser() {
        System.out.println("\nTest reset clock for VALID user");
        int duration = 5;
        for (int i = 0; i < duration; i++) {
            ContextManager.tickClock("David");
        }
        ContextManager.resetClock("David");
        assertEquals(0, ContextManager.users.get("David").clock, 0.0000000001);
    }

    @Test
    public void testResetClockInvalidUser() {
        System.out.println("\nTest reset clock for INVALID user");
        int duration = 5;
        for (int i = 0; i < duration; i++) {
            ContextManager.tickClock("Hang");
        }
        ContextManager.resetClock("Hang");
        assertEquals(null, ContextManager.users.get("Hang").clock);
    }

    @Test
    public void testCheckTempReached() {
        System.out.println("\nTest check temperature threshold when temp REACHED threshold");
        // Positive temperature threshold is reached
        User user1 = UserFactory.buildUserType1GoodAQI();
        assertTrue(ContextManager.checkTempReached(user1));

        // Negative temperature threshold is reached
        User user3 = UserFactory.buildUserType1SensitiveAQI();
        assertTrue(ContextManager.checkTempReached(user3));
    }

    @Test
    public void testCheckTempNotReached() {
        System.out.println("\nTest check temperature threshold when temp NOT REACHED threshold");
        User user2 = UserFactory.buildUserType1ModerateAQI();
        assertFalse(ContextManager.checkTempReached(user2));
    }

    @Test
    public void testCheckTempReachedWhenNoPredefinedThreshold() {
        System.out.println("\nTest check temperature threshold when user has NO PREDEFINED threshold");
        User user4 = UserFactory.buildUserType1UnhealthyAQI();
        assertTrue(ContextManager.checkTempReached(user4));
    }

    @Test
    public void testCheckApoReached() {
        System.out.println("\nTest check APO threshold when APO threshold is REACHED");
        User user1 = UserFactory.buildUserType1GoodAQI();
        assertTrue(ContextManager.checkapoReached(user1));
    }

    @Test
    public void testCheckApoNotReached() {
        System.out.println("\nTest check APO threshold when APO threshold is NOT REACHED");
        User user2 = UserFactory.buildUserType1ModerateAQI();
        assertFalse(ContextManager.checkapoReached(user2));
    }

    @Test
    public void testCalculateApoThreshholdGoodAirQuality() {
        System.out.println("\nTest calculate APO threshold in GOOD air quality");
        Integer threshold1 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType1GoodAQI());
        Integer threshold2 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType2GoodAQI());
        Integer threshold3 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType3GoodAQI());

        assertEquals(30, threshold1, 0.00000001);
        assertEquals(60, threshold2, 0.00000001);
        assertEquals(90, threshold3, 0.00000001);
    }

    @Test
    public void testCalculateApoThreshholdModerateAirQuality() {
        System.out.println("\nTest calculate APO threshold in MODERATE air quality");
        Integer threshold1 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType1ModerateAQI());
        Integer threshold2 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType2ModerateAQI());
        Integer threshold3 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType3ModerateAQI());

        assertEquals(15, threshold1, 0.00000001);
        assertEquals(30, threshold2, 0.00000001);
        assertEquals(45, threshold3, 0.00000001);
    }

    @Test
    public void testCalculateApoThreshholdSensitiveAirQuality() {
        System.out.println("\nTest calculate APO threshold in SENSITIVE air quality");
        Integer threshold3 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType1SensitiveAQI());
        Integer threshold7 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType2SensitiveAQI());
        Integer threshold11 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType3SensitiveAQI());

        assertEquals(10, threshold3, 0.00000001);
        assertEquals(20, threshold7, 0.00000001);
        assertEquals(30, threshold11, 0.00000001);
    }

    @Test
    public void testCalculateApoThreshholdUnhealthyAirQuality() {
        System.out.println("\nTest calculate APO threshold in UNHEALTHY air quality");
        Integer threshold4 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType1UnhealthyAQI());
        Integer threshold8 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType2UnhealthyAQI());
        Integer threshold12 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType3UnhealthyAQI());

        assertEquals(5, threshold4, 0.00000001);
        assertEquals(10, threshold8, 0.00000001);
        assertEquals(15, threshold12, 0.00000001);
    }

}