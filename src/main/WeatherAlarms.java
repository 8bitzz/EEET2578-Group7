package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.IceStorm.TopicPrx;

import helper.Alarm;
import helper.AlarmPrx;
import helper.Monitor;
import helper.MonitorPrx;
import helper.SensorData;
import main.PreferenceRepository.PreferenceWorkerI;
import support.HandleUserInput;

public class WeatherAlarms {
	private List<Integer> weatherConditions;
	private Iterator<Integer> iterator;
	private Communicator communicator;
	
	public WeatherAlarms(){
		this.weatherConditions = readWeatherConditions();
		this.iterator = weatherConditions.iterator();
	}
	
	public class WeatherAlarmWorkerI implements helper.WeatherAlarmWorker {

		@Override
		public int getWeather(Current current) {
			if (!iterator.hasNext()) {
				iterator = weatherConditions.iterator();
			}
			return iterator.next();
		}

		@Override
		public void terminate(Current current) {
			communicator.shutdown();
			System.out.println("Weather Alarm has terminated!");
		}
	}
	
	public static void main(String[] args) {
		WeatherAlarms weatherAlarms = new WeatherAlarms();
		weatherAlarms.setupWeatherAlarmWorker(args);
		System.out.println("WeatherAlarms is running!");
		weatherAlarms.communicator.waitForShutdown();
		System.exit(0);
	}
	
	private void setupWeatherAlarmWorker(String[] args) {
		communicator = com.zeroc.Ice.Util.initialize(args);
		com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("WeatherAlarmWorker",
				"default -p 15555");
		adapter.add(new WeatherAlarmWorkerI(), com.zeroc.Ice.Util.stringToIdentity("WeatherAlarmWorker"));
		adapter.activate();
	}
	
	public List<Integer> readWeatherConditions(){
		File file = new File("weather_alarms.txt");
		List<Integer> result = new ArrayList();
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				result.add(Integer.parseInt(sc.nextLine()));
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

}
