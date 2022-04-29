package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import helper.PreferenceRequest;
import helper.SensorData;
import helper.User;
import support.Preference;

public class PreferenceRepository {
	private static List<Preference> preferences;
	private static final String APO = "APO";
	private static final String WEATHER = "weather";
	private static final Integer NORMAL = 0;
	private static Communicator communicator;

	public static class PreferenceWorkerI implements helper.PreferenceWorker {

		@Override
		public User getUserInfo(String name, Current current) {
			Integer medicalType = 0;
			Integer clock = 0;
			Integer apoThreshhold = 0;
			SensorData sensorData = null;
			int weather = 0;
			List<Integer> tempThreshholds = new ArrayList<>();
			boolean apoReached = false;
			boolean tempReached = false;
			for (Preference preference : preferences) {
				if (preference.getName().equals(name)) {
					medicalType = preference.getMedicalCondition();
					List<String> suggestions = preference.getSuggestions();
					for (String suggestion : suggestions) {
						try {
							Integer temp = Integer.parseInt(suggestion.split("\\s")[1]);
							tempThreshholds.add(temp);
						} catch (NumberFormatException e) {
						}
					}
				}
			}
			User result = new User(medicalType, tempThreshholds.stream().mapToInt(Integer::intValue).toArray(),
					apoThreshhold, clock, sensorData, weather, apoReached, tempReached);
			return result;
		}

		@Override
		public String getPreference(PreferenceRequest request, Current current) {
			String result = null;
			String username = request.username;
			Integer weather = request.weather;
			String value = request.value;
			if (value.isEmpty()){
				result = getSuggestionWeather(username, weather);
			}
			else{
				if (value.equals(APO)) {
					result = getSuggestionAPO(username);
				} else {
					Integer temp = Integer.parseInt(value);
					result = getSuggestionTemp(username, temp);
				}
			}
			
			return result;
		}

		@Override
		public void terminate(Current current) {
			communicator.shutdown();
			System.out.println("Preference Repository has terminated!");
		}
	}

	public static void main(String[] args) {
		preferences = readPreference();
		setupPreferenceWorker(args);
		System.out.println("PreferenceRepository is running!");
		communicator.waitForShutdown();
		System.exit(0);
	}

	private static void setupPreferenceWorker(String[] args) {
		communicator = com.zeroc.Ice.Util.initialize(args);
		com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("PreferenceWorker",
				"default -p 14444");
		adapter.add(new PreferenceWorkerI(), com.zeroc.Ice.Util.stringToIdentity("PreferenceWorker"));
		adapter.activate();
	}

	private static List<Preference> readPreference() {
		List<Preference> result = new ArrayList<>();
		File file = new File("Preference");
		try {
			Scanner sc = new Scanner(file);
			List<String> strings = new ArrayList<>();
			while (sc.hasNextLine()) {
				String line = sc.nextLine().trim();
				if (!line.equals("***")) {
					strings.add(line);
				} else {
					Preference preference = new Preference(strings);
					result.add(preference);
					strings = new ArrayList<>();
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String getSuggestionTemp(String name, Integer tempThreshhold) {
		String result = null;
		Integer distance = Integer.MAX_VALUE;
		for (Preference preference : preferences) {
			if (preference.getName().equals(name)) {
				List<String> suggestions = preference.getSuggestions();
				for (String suggestion : suggestions) {
					String[] splits = suggestion.split("\\s");
					try {
						Integer temp = Integer.parseInt(splits[1]);
						Integer newDistance = tempThreshhold - temp;
						if (newDistance < distance && temp <= tempThreshhold) {
							result = splits[3];
							distance = tempThreshhold - temp;
						}
					} catch (NumberFormatException e) {
					}
				}
			}
		}
		return result;
	}

	private static String getSuggestionAPO(String name) {
		String result = null;
		for (Preference preference : preferences) {
			if (preference.getName().equals(name)) {
				List<String> suggestions = preference.getSuggestions();
				for (String suggestion: suggestions){
					String[] splits = suggestion.split("\\s");
					String type = splits[1];
					if (type.equals(APO)){
						result = splits[3];
						break;
					}
				}
			}
		}
		return result;
	}
	
	private static String getSuggestionWeather(String name, Integer weather) {
		String result = null;
		for (Preference preference : preferences) {
			if (preference.getName().equals(name)) {
				List<String> suggestions = preference.getSuggestions();
				for (String suggestion: suggestions){
					String[] splits = suggestion.split("\\s");
					String type = splits[1];
					if (type.equals(WEATHER)){
						result = splits[3];
						break;
					}
				}
			}
		}
		return result;
	}
}
