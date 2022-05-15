package main;

import com.zeroc.IceStorm.AlreadySubscribed;
import com.zeroc.IceStorm.BadQoS;
import com.zeroc.IceStorm.InvalidSubscriber;
import helper.ContextManagerWorker;
import helper.*;

public class SetupTest {
    public static AllSensors allSensors;
    static Boolean isAlreadySetup = false;
    static ContextManagerWorker CM_Worker;

    static void setupService() {

        if (SetupTest.isAlreadySetup) {
            return;
        }

        ContextManager.communicator = com.zeroc.Ice.Util.initialize();
        ContextManager.cityInfo = ContextManager.readCityInfo();
        ContextManager.iniPreferenceWorker();
        ContextManager.iniLocationMapper();
        ContextManager.iniWeatherAlarmWorker();
        ContextManager.runWeatherAlarm();
        ContextManager.setupContextManagerWorker();
        CM_Worker = new ContextManager.ContextManagerWorkerI();

        allSensors = new AllSensors("Jack");
        allSensors.run();

        CM_Worker.addUser("Jack", null);
        CM_Worker.addUser("David", null);

        SetupTest.isAlreadySetup = true;
    }


}
