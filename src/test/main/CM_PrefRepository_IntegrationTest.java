package main;

import helper.*;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CM_PrefRepository_IntegrationTest {

    @BeforeClass
    public static void setUpClass() {
        SetupTest.setupService();
    }

    @Test
    public void testCMrequestsPRforSuggestion() {
        ContextManager cm = new ContextManager();
        PreferenceRequest request = new PreferenceRequest("Jack", 0, "25");
        String preference = cm.preferenceWorker.getPreference(request);
        assertEquals("shops", preference);
    }
}