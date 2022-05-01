package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.IceStorm.AlreadySubscribed;
import com.zeroc.IceStorm.BadQoS;
import com.zeroc.IceStorm.InvalidSubscriber;
import com.zeroc.IceStorm.TopicPrx;

import helper.*;
import support.LocationDetails;

public class ContextManager {
	public static List<LocationDetails> cityInfo;
	public static Integer currentWeather;
	public static LocationWorkerPrx locationWorker;
	public static PreferenceWorkerPrx preferenceWorker;
	public static WeatherAlarmWorkerPrx weatherAlarmWorker;
	public static Communicator communicator;
	public static LinkedHashMap<String, TopicPrx> subcribers = new LinkedHashMap<>();
	public static LinkedHashMap<String, ObjectPrx> proxies = new LinkedHashMap<>();
	public static LinkedHashMap<String, User> users = new LinkedHashMap<>();
	public static LinkedHashMap<String, AlerterPrx> alerters = new LinkedHashMap<>();
	public static final String INDOOR = "Indoor";
	public static final String OUTDOOR = "Outdoor";
	public static final String APO = "APO";
	public static final String TEMPERATURE = "Temperature";
	public static final String WEATHER = "Weather";
	public static final Integer NORMAL = 0;
	
	public static class MonitorI implements Monitor {
		@Override
		public void report(SensorData sensorData, Current current) {
			String username = sensorData.username;
			User user = users.get(username);
			if (user.sensorData == null) {
				user.sensorData = sensorData;
				user.apoThreshhold = calculateapoThreshhold(user);
			}
			String previouslocationStatus = locationWorker.locationMapping(user.sensorData.location);
			String currentLocationStatus = locationWorker.locationMapping(sensorData.location);
			int previousaqi = user.sensorData.aqi;
			int previousTemperature = user.sensorData.temperature;
			user.sensorData = sensorData;

			if (previouslocationStatus.equals(OUTDOOR) && currentLocationStatus.equals(INDOOR)) {
				resetClock(username);
			}
			if (user.sensorData.aqi != previousaqi) {
				resetClock(username);
				user.apoThreshhold = calculateapoThreshhold(user);
			}
			if (user.sensorData.temperature != previousTemperature) {
				user.tempReached = false;
			}
			if (currentLocationStatus.equals(OUTDOOR)) {
				tickClock(username);
			}

			boolean apoReached = checkapoReached(user);
			boolean tempReached = checkTempReached(user);
			if (apoReached) {
				if (!user.apoReached) {
					PreferenceRequest request = new PreferenceRequest(username, currentWeather, APO);
					String preference = preferenceWorker.getPreference(request);
					List<String> locations = getLocationsByService(preference);
					Alert alert = new Alert(APO, user.sensorData.aqi, locations.stream().toArray(String[]::new));
					alerters.get(username).alert(alert);
				}
			} else {
				if (!user.tempReached && tempReached) {
					PreferenceRequest request = new PreferenceRequest(username, currentWeather,
							Integer.toString(user.sensorData.temperature));
					String preference = preferenceWorker.getPreference(request);
					List<String> locations = getLocationsByService(preference);
					Alert alert = new Alert(TEMPERATURE, user.sensorData.temperature,
							locations.stream().toArray(String[]::new));
					System.out.println(alerters.get(username).toString());
					alerters.get(username).alert(alert);
				}
			}
			user.apoReached = apoReached;
			user.tempReached = tempReached;
			System.out.println(username + " --Weather: " + currentWeather + "  --Location: " + user.sensorData.location + "  --Location Status:"
					+ currentLocationStatus + "  --aqi: " + user.sensorData.aqi + "  --apoThreshhold: "
					+ user.apoThreshhold + "  --Temperature: " + user.sensorData.temperature + "  --Clock: "
					+ user.clock);
		}
	}
	
	
	public static class ContextManagerWorkerI implements ContextManagerWorker {

		@Override
		public void addUser(String username, Current current) {
			User user = preferenceWorker.getUserInfo(username);
			users.put(username, user);
			setupAlerter(username);
			setupSubcriber(username);
			System.out.println(username + " added!");
			checkWeather(currentWeather);
		}

		@Override
		public String searchInfo(String item, Current current) {
			String result = null;
			for (LocationDetails locationDetails : cityInfo) {
				if (locationDetails.getName().equals(item)) {
					result = locationDetails.getInfo();
				}
			}
			return result;
		}

		@Override
		public String[] searchItems(String username, Current current) {
			List<String> result = new ArrayList<>();
			String currentLocation = users.get(username).sensorData.location;
			for (LocationDetails locationDetails : cityInfo) {
				if (locationDetails.getLocation().equals(currentLocation)) {
					result.add(locationDetails.getName());
				}
			}
			return result.stream().toArray(String[]::new);
		}

		@Override
		public void deleteUser(String username, Current current) {
			users.remove(username);
			alerters.remove(username);
			subcribers.get(username).unsubscribe(proxies.get(username));
			subcribers.remove(username);
			proxies.remove(username);
			if (users.size() == 0) {
				locationWorker.terminate();
				preferenceWorker.terminate();
				weatherAlarmWorker.terminate();
				communicator.shutdown();
				System.out.println("Context Manager has terminated!");
			}
		}

	}

	public static void main(String[] args) {
		communicator = com.zeroc.Ice.Util.initialize(args);
		cityInfo = readCityInfo();
		iniPreferenceWorker();
		iniLocationMapper();
		iniWeatherAlarmWorker();
		runWeatherAlarm();
		setupContextManagerWorker();
		System.out.println("CM is running!");
		communicator.waitForShutdown();
		System.exit(0);
	}

	public static void setupAlerter(String username) {
		String topicName = username + "-alerts";
		com.zeroc.Ice.ObjectPrx obj = communicator.stringToProxy("IceStorm/TopicManager:tcp -p 10000");
		com.zeroc.IceStorm.TopicManagerPrx topicManager = com.zeroc.IceStorm.TopicManagerPrx.checkedCast(obj);
		TopicPrx topic = null;
		while (topic == null) {
			try {
				topic = topicManager.retrieve(topicName);
			} catch (com.zeroc.IceStorm.NoSuchTopic ex) {
				try {
					topic = topicManager.create(topicName);
				} catch (com.zeroc.IceStorm.TopicExists ex1) {

				}
			}
		}
		com.zeroc.Ice.ObjectPrx pub = topic.getPublisher().ice_oneway();
		AlerterPrx alerter = AlerterPrx.uncheckedCast(pub);
		alerters.put(username, alerter);
	}

	private static void setupSubcriber(String username) {
		String topicName = username + "-sensors";
		com.zeroc.Ice.ObjectPrx obj = communicator.stringToProxy("IceStorm/TopicManager:tcp -p 10000");
		com.zeroc.IceStorm.TopicManagerPrx topicManager = com.zeroc.IceStorm.TopicManagerPrx.checkedCast(obj);

		com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints(topicName, "tcp");

		Monitor monitor = new MonitorI();
		ObjectPrx proxy = adapter.addWithUUID(monitor).ice_oneway();
		TopicPrx subcriber = null;
		adapter.activate();

		try {
			subcriber = topicManager.retrieve(topicName);
			java.util.Map<String, String> qos = null;
			subcriber.subscribeAndGetPublisher(qos, proxy);
			subcribers.put(username, subcriber);
			proxies.put(username, proxy);
		} catch (com.zeroc.IceStorm.NoSuchTopic ex) {

		} catch (AlreadySubscribed e) {

			e.printStackTrace();
		} catch (BadQoS e) {

			e.printStackTrace();
		} catch (InvalidSubscriber e) {

			e.printStackTrace();
		}
	}

	static void setupContextManagerWorker() {
		com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("ContextManagerWorker",
				"default -p 13333");
		adapter.add(new ContextManagerWorkerI(), com.zeroc.Ice.Util.stringToIdentity("ContextManagerWorker"));
		adapter.activate();
	}

	static void iniPreferenceWorker() {
		com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("PreferenceWorker:default -p 14444");
		preferenceWorker = PreferenceWorkerPrx.checkedCast(base);
	}

	static void iniLocationMapper() {
		com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("LocationWorker:default -p 11111");
		locationWorker = LocationWorkerPrx.checkedCast(base);
	}
	
	static void iniWeatherAlarmWorker() {
		com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("WeatherAlarmWorker:default -p 15555");
		weatherAlarmWorker = WeatherAlarmWorkerPrx.checkedCast(base);
	}
	
	static void runWeatherAlarm() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				while(true){
					currentWeather = weatherAlarmWorker.getWeather();
					checkWeather(currentWeather);
					try {
						Thread.currentThread();
						Thread.sleep(1000 * 60);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
	}

	private static void checkWeather(Integer currentWeather){
		if (currentWeather != NORMAL){
			alerters.forEach((username,alerter)->{
				PreferenceRequest request = new PreferenceRequest(username, currentWeather, null);
				String preference = preferenceWorker.getPreference(request);
				List<String> locations = getLocationsByService(preference);
				Alert alert = new Alert(WEATHER, currentWeather, locations.stream().toArray(String[]::new));
				alerter.alert(alert);
			});
		}
	}

	public static List<LocationDetails> readCityInfo() {
		List<LocationDetails> result = new ArrayList<>();
		File file = new File("CityInfo");
		int count = 0;
		try {
			Scanner sc = new Scanner(file);
			List<String> strings = new ArrayList<>();
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if (!line.equals("***")) {
					strings.add(line);
				} else {
					LocationDetails locationDetails = new LocationDetails(strings);
					result.add(locationDetails);
					strings = new ArrayList<>();
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static List<String> getLocationsByService(String service) {
		List<String> result = new ArrayList<>();
		for (LocationDetails locationDetails : cityInfo) {
			if (locationDetails.getServices().contains(service)) {
				String locationStatus = locationWorker.locationMapping(locationDetails.getLocation());
				if (locationStatus.equals(INDOOR)) {
					result.add(locationDetails.getName());
				}
			}
		}
		return result;
	}

	private static void resetClock(String username) {
		users.get(username).clock = 0;
	}

	private static void tickClock(String username) {
		users.get(username).clock += 1;
	}

	private static boolean checkapoReached(User user) {
		return user.clock == user.apoThreshhold;
	}

	private static boolean checkTempReached(User user) {
		int temperature = user.sensorData.temperature;
		List<Integer> tempThreshholds = Arrays.stream(user.tempThreshholds).boxed().collect(Collectors.toList());
		return temperature >= Collections.min(tempThreshholds);
	}

	private static Integer calculateapoThreshhold(User user) {
		Integer medicalConditionType = user.medicalConditionType;
		Integer aqi = user.sensorData.aqi;
		Integer result = null;
		if (aqi >= 1 && aqi <= 50) {
			result = medicalConditionType * 30;
		} else if (aqi >= 51 && aqi <= 100) {
			result = medicalConditionType * 15;
		} 
		else if (aqi >= 101 && aqi <= 150) {
			result = medicalConditionType * 10;
		}else {
			result = medicalConditionType * 5;
		}
		return result;
	}

}
