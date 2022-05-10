package main;

import helper.SensorData;
import helper.User;

public class UserFactory {

    static User buildUserType1GoodAQI() {
        SensorData data = new SensorData("Harry", "C", 25, 50);
        int[] tempThreshholds = {20, 30};
        int apoThreshhold = 30;
        return new User(1, tempThreshholds, apoThreshhold, 30, data, 0, false, true);
    }

    static User buildUserType1ModerateAQI() {
        SensorData data = new SensorData("Ron", "C", 15, 100);
        int[] tempThreshholds = {20, 30};
        int apoThreshhold = 15;
        return new User(1, tempThreshholds, apoThreshhold, 14, data, 0, false, true);
    }

    static User buildUserType1SensitiveAQI() {
        SensorData data = new SensorData("Hermione", "C", 20, 150);
        int[] tempThreshholds = {-20, 30};
        int apoThreshhold = 15;
        return new User(1, tempThreshholds, apoThreshhold, 1, data, 0, false, true);
    }

    static User buildUserType1UnhealthyAQI() {
        SensorData data = new SensorData("Ginny", "C", 30, 200);
        int[] tempThreshholds = {};
        int apoThreshhold = 15;
        return new User(1, tempThreshholds, apoThreshhold, 1, data, 0, false, true);
    }

    static User buildUserType2GoodAQI() {
        SensorData data = new SensorData("Luna", "C", 30, 1);
        int[] tempThreshholds = {20, 30};
        int apoThreshhold = 15;
        return new User(2, tempThreshholds, apoThreshhold, 1, data, 0, false, true);
    }

    static User buildUserType2ModerateAQI() {
        SensorData data = new SensorData("Dobby", "C", 30, 51);
        int[] tempThreshholds = {20, 30};
        int apoThreshhold = 15;
        return new User(2, tempThreshholds, apoThreshhold, 1, data, 0, false, true);
    }

    static User buildUserType2SensitiveAQI() {
        SensorData data = new SensorData("Hagrid", "C", 30, 101);
        int[] tempThreshholds = {20, 30};
        int apoThreshhold = 15;
        return new User(2, tempThreshholds, apoThreshhold, 1, data, 0, false, true);
    }

    static User buildUserType2UnhealthyAQI() {
        SensorData data = new SensorData("Sirius", "C", 30, 151);
        int[] tempThreshholds = {20, 30};
        int apoThreshhold = 15;
        return new User(2, tempThreshholds, apoThreshhold, 1, data, 0, false, true);
    }

    static User buildUserType3GoodAQI() {
        SensorData data = new SensorData("Malfoy", "C", 30, 25);
        int[] tempThreshholds = {20, 30};
        int apoThreshhold = 15;
        return new User(3, tempThreshholds, apoThreshhold, 1, data, 0, false, true);
    }

    static User buildUserType3ModerateAQI() {
        SensorData data = new SensorData("Voldemort", "C", 30, 75);
        int[] tempThreshholds = {20, 30};
        int apoThreshhold = 15;
        return new User(3, tempThreshholds, apoThreshhold, 1, data, 0, false, true);
    }

    static User buildUserType3SensitiveAQI() {
        SensorData data = new SensorData("Bellatrix", "C", 30, 125);
        int[] tempThreshholds = {20, 30};
        int apoThreshhold = 15;
        return new User(3, tempThreshholds, apoThreshhold, 1, data, 0, false, true);
    }

    static User buildUserType3UnhealthyAQI() {
        SensorData data = new SensorData("Dolores", "C", 30, 175);
        int[] tempThreshholds = {20, 30};
        int apoThreshhold = 15;
        return new User(3, tempThreshholds, apoThreshhold, 1, data, 0, false, true);
    }
}
