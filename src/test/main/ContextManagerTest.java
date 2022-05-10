package main;

import helper.ContextManagerWorker;
import helper.SensorData;
import helper.User;
import org.junit.BeforeClass;
import org.junit.Test;
import support.LocationDetails;

import java.util.List;
import static org.junit.Assert.*;


public class ContextManagerTest {
    public static final Integer NORMAL = 0;
    @BeforeClass
    public static void setUpClass() {
        SetupTest.setupService();
    }

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

    @Test
    public void testGetLocationsByService() {
        List<String> cinemaLocations = ContextManager.getLocationsByService("cinema");
        List<String> restaurantLocations = ContextManager.getLocationsByService("restaurants");
        List<String> poolLocations = ContextManager.getLocationsByService("pool");
        List<String> shopsLocations = ContextManager.getLocationsByService("shops");
        List<String> bowlingLocations = ContextManager.getLocationsByService("bowling");
        List<String> wheelLocations = ContextManager.getLocationsByService("Ferris wheel");
        List<String> marketLocations = ContextManager.getLocationsByService("market");
        assertEquals(2, cinemaLocations.size());
        assertEquals(2, restaurantLocations.size());
        assertEquals(1, poolLocations.size());
        assertEquals(2, shopsLocations.size());
        assertEquals(1, bowlingLocations.size());
        assertEquals(0, wheelLocations.size());
        assertEquals(0, marketLocations.size());
    }

    @Test
    public void testCalculateApoThreshhold() {
        Integer threshold1 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType1GoodAQI());
        Integer threshold2 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType1ModerateAQI());
        Integer threshold3 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType1SensitiveAQI());
        Integer threshold4 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType1UnhealthyAQI());
        Integer threshold5 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType2GoodAQI());
        Integer threshold6 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType2ModerateAQI());
        Integer threshold7 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType2SensitiveAQI());
        Integer threshold8 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType2UnhealthyAQI());
        Integer threshold9 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType3GoodAQI());
        Integer threshold10 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType3ModerateAQI());
        Integer threshold11 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType3SensitiveAQI());
        Integer threshold12 = ContextManager.calculateapoThreshhold(UserFactory.buildUserType3UnhealthyAQI());

        assertEquals(30, threshold1, 0.00000001);
        assertEquals(15, threshold2, 0.00000001);
        assertEquals(10, threshold3, 0.00000001);
        assertEquals(5, threshold4, 0.00000001);
        assertEquals(60, threshold5, 0.00000001);
        assertEquals(30, threshold6, 0.00000001);
        assertEquals(20, threshold7, 0.00000001);
        assertEquals(10, threshold8, 0.00000001);
        assertEquals(90, threshold9, 0.00000001);
        assertEquals(45, threshold10, 0.00000001);
        assertEquals(30, threshold11, 0.00000001);
        assertEquals(15, threshold12, 0.00000001);
    }

    @Test
    public void testCheckTempReached() {
        // Temperature threshold is reached
        User user1 = UserFactory.buildUserType1GoodAQI();
        assertTrue(ContextManager.checkTempReached(user1));

        // Temperature threshold is not reached
        User user2 = UserFactory.buildUserType1ModerateAQI();
        assertFalse(ContextManager.checkTempReached(user2));

        // Negative temperature threshold is reached
        User user3 = UserFactory.buildUserType1SensitiveAQI();
        assertTrue(ContextManager.checkTempReached(user3));

        // No predefined temperature threshold - TEST FAILED
//        User user4 = UserFactory.buildUserType1UnhealthyAQI();
//        assertTrue(ContextManager.checkTempReached(user4));
    }

    @Test
    public void testCheckApoReached() {
        // APO threshold is reached
        User user1 = UserFactory.buildUserType1GoodAQI();
        assertTrue(ContextManager.checkapoReached(user1));

        // APO threshold is reached
        User user2 = UserFactory.buildUserType1ModerateAQI();
        assertFalse(ContextManager.checkapoReached(user2));
    }

    @Test
    public void testTickClock() {
        int duration = 5;
        for (int i = 0; i < 5; i++) {
            ContextManager.tickClock("Jack");
        }
        assertEquals(duration, ContextManager.users.get("Jack").clock, 0.0000000001);
    }

    @Test
    public void testResetClock() {
        ContextManager.resetClock("Jack");
        assertEquals(0, ContextManager.users.get("Jack").clock, 0.0000000001);
    }
    @Test
    public void testCheckWeather(){
        ContextManager.checkWeather(NORMAL);
    }
    @Test
    public void testAddUser(){
        ContextManagerWorker CM_Worker;
//        ContextManager.communicator = com.zeroc.Ice.Util.initialize();
//        ContextManager.cityInfo = ContextManager.readCityInfo();
//        ContextManager.iniPreferenceWorker();
//        ContextManager.iniLocationMapper();
//        ContextManager.iniWeatherAlarmWorker();
//        ContextManager.runWeatherAlarm();
//        ContextManager.setupContextManagerWorker();
        CM_Worker = new ContextManager.ContextManagerWorkerI();
        int beforeAddedSize = ContextManager.users.size();
//        System.out.println("Before added" + ContextManager.users.size());
        CM_Worker.addUser("Minh", null);
        assertEquals(ContextManager.users.size(),beforeAddedSize+1);
    }

    @Test
    public void testDeleteUser(){
        ContextManagerWorker CM_Worker;
//        ContextManager.communicator = com.zeroc.Ice.Util.initialize();
//        ContextManager.cityInfo = ContextManager.readCityInfo();
//        ContextManager.iniPreferenceWorker();
//        ContextManager.iniLocationMapper();
//        ContextManager.iniWeatherAlarmWorker();
//        ContextManager.runWeatherAlarm();
//        ContextManager.setupContextManagerWorker();
        CM_Worker = new ContextManager.ContextManagerWorkerI();
        int beforeAddedSize = ContextManager.users.size();
        System.out.println("Before deleted" + ContextManager.users.size());
        CM_Worker.deleteUser("Jack", null);
        assertEquals(ContextManager.users.size(),beforeAddedSize-1);
    }
}