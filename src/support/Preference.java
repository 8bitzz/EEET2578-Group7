package support;

import java.util.ArrayList;
import java.util.List;

public class Preference {
	private String name;
	private Integer medicalCondition;
	private List<String> suggestions;

	public Preference(String name, Integer medicalCondition, List<String> suggestions) {
		super();
		this.name = name;
		this.medicalCondition = medicalCondition;
		this.suggestions = suggestions;
	}

	public Preference(List<String> strings) {
		List<String> suggestions = new ArrayList<>();
		for (String string : strings) {
			String[] splits = string.split(":");
			String firstPart = splits[0].trim();
			String secondPart = splits[1].trim();
			switch (firstPart) {
			case "name":
				this.name = secondPart;
				break;
			case "Medical Condition Type":
				this.medicalCondition = Integer.parseInt(secondPart);
				break;
			case "pref":
				suggestions.add(secondPart);
				break;
			default:
				break;
			}
		}
		this.suggestions = suggestions;
	}

	@Override
	public String toString() {
		return "Preference [name=" + name + ", medical condition=" + medicalCondition + ", suggestions=" + suggestions + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMedicalCondition() {
		return medicalCondition;
	}

	public void setMedicalCondition(Integer medicalCondition) {
		this.medicalCondition = medicalCondition;
	}

	public List<String> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(List<String> suggestions) {
		this.suggestions = suggestions;
	}

}
