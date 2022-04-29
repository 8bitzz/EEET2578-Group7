package support;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Sensor {
	private String username;
	private String type;
	private LinkedHashMap<String, Integer> data;
	private Iterator<String> iterator;
	private String value;
	private Integer seconds;

	public Sensor(String username, String type) {
		this.username = username;
		this.type = type;
		this.data = readData();
		this.iterator = this.data.keySet().iterator();
		this.value = this.iterator.next();
		this.seconds = this.data.get(this.value);
	}

	public String getCurrentValue() {
		if (this.seconds == 0) {
			if (!this.iterator.hasNext()) {
				this.iterator = this.data.keySet().iterator();
			}
			this.value = this.iterator.next();
			this.seconds = this.data.get(this.value);
		}
		this.seconds -= 1;
		return this.value;
	}

	public LinkedHashMap<String, Integer> getData() {
		return data;
	}

	public void setData(LinkedHashMap<String, Integer> data) {
		this.data = data;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private LinkedHashMap<String, Integer> readData() {
		File file = new File(this.username + this.type);
		LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String[] words = sc.nextLine().split(",");
				String value = words[0];
				Integer number = Integer.parseInt(words[1]);
				result.put(value, number);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}
}
