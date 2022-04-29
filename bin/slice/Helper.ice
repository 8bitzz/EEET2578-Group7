module helper
{	
	sequence<int> listInt;
	sequence<string> listString;
	
	struct SensorData
    {
    	string username;
    	string location;
    	int temperature;
    	int aqi; 
    }
    
    struct WeatherData
    {
    	string weather;
    }
    
    struct User
    {	
    	int medicalConditionType;
    	listInt tempThreshholds;
    	int apoThreshhold;
    	int clock;
		SensorData sensorData;
		int weather;
		bool apoReached;
		bool tempReached;
    }
    
    struct PreferenceRequest
    {
    	string username;
    	int weather; 
    	string value;
    }
    
    struct Alert
    {
    	string type;
    	int value;
    	listString locations;
    }
    
    interface Alerter
    {
    	void alert(Alert alert);
    }
    
    interface Monitor
    {
        void report(SensorData data);
    }
    
    interface Alarm
    {
        void report(int weather);
    }
   
    
    interface LocationWorker
    {
    	string locationMapping(string location);
    	void terminate();
    }
    
    
    interface PreferenceWorker
    {
    	User getUserInfo(string name);
    	string getPreference(PreferenceRequest request);
    	void terminate();
    }
    
    interface ContextManagerWorker
    {
    	void addUser(string username);
    	void deleteUser(string username);
    	string searchInfo(string item);
    	listString searchItems(string username);
    }
    
    interface WeatherAlarmWorker
    {
    	int getWeather();
    	void terminate();
    }
}