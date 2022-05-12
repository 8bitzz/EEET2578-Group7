package main;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class WeatherAlarmsTest {
    public static WeatherAlarms alarms = new WeatherAlarms();

    @Test
    public void testReadWeatherConditions() {
        List<Integer> expectedResult = Arrays.asList(0, 1, 2, 3);
        List<Integer> actualResult = alarms.readWeatherConditions();

        for (int i = 0; i < 4; i++) {
            assertEquals(expectedResult.get(i), actualResult.get(i));
        }
    }

}