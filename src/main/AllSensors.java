package main;

import com.zeroc.Ice.Communicator;
import com.zeroc.IceStorm.TopicPrx;

import helper.*;
import support.Sensor;

public class AllSensors {
	private String username;
	private boolean signal;
	private Sensor locationSensor;
	private Sensor temperatureSensor;
	private Sensor aqiSensor;
	private Communicator communicator;
	private MonitorPrx monitor;
	

	public AllSensors(String username) {
		this.username = username;
		this.signal = true;
		this.locationSensor = new Sensor(username, "Location");
		this.temperatureSensor = new Sensor(username, "Temperature");
		this.aqiSensor = new Sensor(username, "AQI");
		this.communicator = com.zeroc.Ice.Util.initialize();
		setupMonitor();
	}

	public void run() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				while (signal == true) {
					SensorData data = getSensorData();
					monitor.report(data);
					try {
						Thread.currentThread();
						Thread.sleep(1000);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
	}

	public void stop() {
		signal = false;
		communicator.shutdown();
	}

	private void setupMonitor() {
		String topicName = username + "-sensors";
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
		monitor = MonitorPrx.uncheckedCast(pub);
	}

	private SensorData getSensorData() {
		System.out.println("----------Get sensor data--------");
		String location = this.locationSensor.getCurrentValue();
		int temperature = Integer.parseInt(this.temperatureSensor.getCurrentValue());
		int uvr = Integer.parseInt(this.aqiSensor.getCurrentValue());
		SensorData data = new SensorData(this.username, location, temperature, uvr);
		return data;
	}

}
