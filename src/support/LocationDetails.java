package support;

import java.util.Arrays;
import java.util.List;

public class LocationDetails {
	private String name;
	private String location;
	private String info;
	private List<String> services;

	public LocationDetails(String name, String location, String info, List<String> services) {
		this.name = name;
		this.location = location;
		this.info = info;
		this.services = services;
	}

	public LocationDetails(List<String> strings) {
		for (String string : strings) {
			String[] splits = string.split(":");
			String firstPart = splits[0].trim();
			String secondPart = splits[1].trim();
			switch (firstPart) {
			case "name":
				this.name = secondPart;
				break;
			case "location":
				this.location = secondPart;
				break;
			case "information":
				this.info = secondPart;
				break;
			case "services":
				this.services = Arrays.asList(secondPart.split(","));
				this.services.replaceAll(String::trim);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public String toString() {
		return "LocationDetails [name=" + name + ", location=" + location + ", info=" + info + ", services=" + services
				+ "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<String> getServices() {
		return services;
	}

	public void setServices(List<String> services) {
		this.services = services;
	}
}
