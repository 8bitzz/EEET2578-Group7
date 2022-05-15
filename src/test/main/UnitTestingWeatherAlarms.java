package main;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UnitTestingWeatherAlarms {
    public static WeatherAlarms alarms = new WeatherAlarms();

    @Test
    public void testReadWeatherConditions() {
        System.out.println("\nTest Weather Alarms read weather_alarms file");
        List<Integer> expectedResult = Arrays.asList(0, 1, 2, 3);
        List<Integer> actualResult = alarms.readWeatherConditions();

        for (int i = 0; i < 4; i++) {
            assertEquals(expectedResult.get(i), actualResult.get(i));
        }
    }

}