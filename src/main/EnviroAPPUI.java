package main;

import java.util.LinkedHashMap;
import java.util.Scanner;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.IceStorm.AlreadySubscribed;
import com.zeroc.IceStorm.BadQoS;
import com.zeroc.IceStorm.InvalidSubscriber;
import com.zeroc.IceStorm.TopicPrx;

import helper.Alert;
import helper.Alerter;
import helper.AlerterPrx;
import helper.ContextManagerWorkerPrx;
import support.HandleUserInput;

public class EnviroAPPUI {
	public static String username;
	public static AllSensors allSensors;
	public static WeatherAlarms weatherAlarms;
	public static LinkedHashMap<Integer, String> weatherMapping = new LinkedHashMap(){{
		put(1, "heavy rain");
        put(2, "hail storm");
        put(3, "strong wind");
	}};
	public static ContextManagerWorkerPrx contextManagerWorker;
	public static Communicator communicator;
	public static TopicPrx subcriber;
	public static ObjectPrx proxy;
	public static String topicName;
	public static HandleUserInput handleUserInput;
	public static final String APO = "APO";
	public static final String TEMPERATURE = "Temperature";
	private static final String WEATHER = "Weather";
	private static final String NEWLINE = "\n";

	private static class AlerterI implements Alerter {

		@Override
		public void alert(Alert alert, Current current) {
			String message = "";
			switch (alert.type) {
			case WEATHER:
				message = "Warning, extreme weather is detected, the current weather event is  " + weatherMapping.get(alert.value) + NEWLINE;
				break;
			case APO:
				message = "Warning, significant air pollution level detected, the current AQI is " + alert.value + NEWLINE;
				break;
			case TEMPERATURE:
				message = "Warning,Temperature is now: " + alert.value + NEWLINE;
				break;
			default:
				break;
			}
			message += "Suggestion - please go to: " + NEWLINE;
			for (String location : alert.locations) {
				message += location + " , ";
			}
			printMessage(message);
		}
	}

	public static void main(String[] args) {
		communicator = com.zeroc.Ice.Util.initialize(args);
		System.out.println("Context-aware UV Smart Application");
		System.out.println("Please enter your user name:");

		Scanner reader = new Scanner(System.in);
		username = reader.nextLine();

		allSensors = new AllSensors(username);
		allSensors.run();

		topicName = username + "-alerts";
		setupSubcriber();
		
		iniContextManagerWorker(args);
		contextManagerWorker.addUser(username);

		printMessage("");
		handleUserInput = new HandleUserInput();
		handleUserInput.start();

		communicator.waitForShutdown();
		System.exit(0);
	}

	public static void printMessage(String message) {
		System.out.println("*******************************************************************");
		System.out.println("Context-aware UV Smart Application Main Menu");
		if (!message.equals("")) {
			System.out.println(message);
		}
		System.out.println("Please select an option");
		System.out.println("1. Search for information on a specific item of interest");
		System.out.println("2. Search for items of interest in current location");
		System.out.println("E. Exit");
	}

	private static void setupSubcriber() {
		com.zeroc.Ice.ObjectPrx obj = communicator.stringToProxy("IceStorm/TopicManager:tcp -p 10000");
		com.zeroc.IceStorm.TopicManagerPrx topicManager = com.zeroc.IceStorm.TopicManagerPrx.checkedCast(obj);

		com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints(topicName, "tcp");

		AlerterI alerter = new AlerterI();
		proxy = adapter.addWithUUID(alerter).ice_oneway();
		adapter.activate();

		try {
			subcriber = topicManager.retrieve(topicName);
			java.util.Map<String, String> qos = null;
			subcriber.subscribeAndGetPublisher(qos, proxy);
		} catch (com.zeroc.IceStorm.NoSuchTopic ex) {

		} catch (AlreadySubscribed e) {

			e.printStackTrace();
		} catch (BadQoS e) {

			e.printStackTrace();
		} catch (InvalidSubscriber e) {

			e.printStackTrace();
		}
	}

	private static void iniContextManagerWorker(String[] args) {
		com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("ContextManagerWorker:default -p 13333");
		contextManagerWorker = ContextManagerWorkerPrx.checkedCast(base);
	}

}
