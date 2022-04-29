package support;

import java.util.Scanner;

import main.EnviroAPPUI;

public class HandleUserInput extends Thread {

	@Override
	public void run() {
		Scanner reader = new Scanner(System.in);
		if (reader.hasNextLine()) {
			String option = reader.nextLine();
			switch (option) {
			case "1":
				System.out.println("Please enter name of item of interest: ");
				String item = reader.nextLine();
				String info = EnviroAPPUI.contextManagerWorker.searchInfo(item);
				if (info.equals("")) {
					System.out.println("Not match found for item of interest");
				} else {
					System.out.println(info);
				}
				EnviroAPPUI.printMessage("");
				run();
				break;
			case "2":
				String[] items = EnviroAPPUI.contextManagerWorker.searchItems(EnviroAPPUI.username);
				if (items.length == 0) {
					System.out.println("There are no items of interest in your current location.");
				} else {
					System.out.println("The following items of interest are in your location: ");
					for (String it : items) {
						System.out.println(it);
					}
				}
				EnviroAPPUI.printMessage("");
				run();
				break;
			case "E":
				System.out.println("User wants to exit");
				EnviroAPPUI.allSensors.stop();
				EnviroAPPUI.contextManagerWorker.deleteUser(EnviroAPPUI.username);
				EnviroAPPUI.communicator.shutdown();
				System.out.println("UI has exited!");
				break;
			default:
				break;
			}
		}
	}

}
