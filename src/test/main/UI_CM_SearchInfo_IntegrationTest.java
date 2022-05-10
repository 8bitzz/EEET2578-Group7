package main;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class UI_CM_SearchInfo_IntegrationTest {
//    private String queryString;
//    @Given("the user is logged in")
//    public void the_user_is_logged_in() throws Exception {
//        Field communicatorField = (ContextManager.class).getDeclaredField("communicator");
//        ((Field) communicatorField).setAccessible(true);
//        communicatorField.set(null, com.zeroc.Ice.Util.initialize());
//
//        Method initMethod = (ContextManager.class).getDeclaredMethod("iniLocationMapper");
//        ((Method) initMethod).setAccessible(true);
//        initMethod.invoke(null);
//
//        Method readCityInfoMethod = (ContextManager.class).getDeclaredMethod("readCityInfo");
//        readCityInfoMethod.setAccessible(true);
//
//        Field field = (ContextManager.class).getDeclaredField("cityInfo");
//        field.setAccessible(true);
//        field.set(null, readCityInfoMethod.invoke(null));
//    }
//
//    @When("the user search for {string}")
//    public void the_user_search_for(String query) {
//        this.queryString = query;
//    }
//
//    private boolean isEqualButNotOrdered(List<String> expected, List<String> actual) {
//        return (expected.size() == actual.size()) && actual.containsAll(expected);
//    }
//
//    @Then("should return the following location {string}")
//    public void should_return_the_following_location(String locations) throws Exception {
//        Method method = (ContextManager.class).getDeclaredMethod("getLocationsByService", String.class);
//        method.setAccessible(true);
//        List<String> results = (List<String>) method.invoke(null, queryString);
//        List<String> expected;
//        if (locations.equals("")) {
//            expected = new ArrayList<>();
//        } else {
//            expected = new ArrayList<>((Arrays.asList(locations.split(";"))));
//        }
//        assertTrue(isEqualButNotOrdered(results, expected));
//        assertEquals(results.size(), expected.size());
//        TestCase.assert("Should be equal", , results);
//    }
}
