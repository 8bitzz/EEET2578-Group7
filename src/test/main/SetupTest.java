package main;

import helper.ContextManagerWorker;
import helper.*;

public class SetupTest {

    static Boolean isAlreadySetup = false;
    private static ContextManagerWorker CM_Worker;
    private static String username;

    static void setupService() {

        if (SetupTest.isAlreadySetup) {
            return;
        }

        //executed only once, before the first test
        username = "Jack";
        ContextManager.communicator = com.zeroc.Ice.Util.initialize();
        ContextManager.cityInfo = ContextManager.readCityInfo();
        ContextManager.iniPreferenceWorker();
        ContextManager.iniLocationMapper();
        ContextManager.iniWeatherAlarmWorker();
        ContextManager.runWeatherAlarm();
        ContextManager.setupContextManagerWorker();
        CM_Worker = new ContextManager.ContextManagerWorkerI();
        CM_Worker.addUser(username, null);

        SetupTest.isAlreadySetup = true;
    }
}
