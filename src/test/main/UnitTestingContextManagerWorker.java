package main;

import com.zeroc.Ice.Current;
import helper.ContextManagerWorker;
import org.junit.BeforeClass;
import org.junit.Test;
import support.LocationDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class UnitTestingContextManagerWorker {

    @BeforeClass
    public static void setUpClass() {
        SetupTest.setupService();
    }

    @Test
    public void testAddNonExistingUser(){
        System.out.println("\nTest CMWorker add a NON-EXISTING user");
        ContextManagerWorker CM_Worker;
        CM_Worker = new ContextManager.ContextManagerWorkerI();
        int beforeAddedSize = ContextManager.users.size();
        CM_Worker.addUser("Minh", null);
        assertEquals(ContextManager.users.size(),beforeAddedSize+1);
    }

    @Test
    public void testAddExistingUser(){
        System.out.println("\nTest CMWorker add an EXISTING user");
        ContextManagerWorker CM_Worker;
        CM_Worker = new ContextManager.ContextManagerWorkerI();
        int beforeAddedSize = ContextManager.users.size();
        CM_Worker.addUser("Jack", null);
        assertEquals(ContextManager.users.size(),beforeAddedSize);
    }

    @Test
    public void testDeleteExistingUser(){
        System.out.println("\nTest CMWorker delete an EXISTING user");
        ContextManagerWorker CM_Worker;
        CM_Worker = new ContextManager.ContextManagerWorkerI();
        int beforeDeleteSize = ContextManager.users.size();
        CM_Worker.deleteUser("Jack", null);
        assertEquals(ContextManager.users.size(),beforeDeleteSize-1);
    }

    @Test
    public void testDeleteNonExistingUser(){
        System.out.println("\nTest CMWorker delete a NON-EXISTING user");
        ContextManagerWorker CM_Worker;
        CM_Worker = new ContextManager.ContextManagerWorkerI();
        int beforeDeleteSize = ContextManager.users.size();
        CM_Worker.deleteUser("Hang", null);
        assertEquals(ContextManager.users.size(),beforeDeleteSize);
    }

    @Test
    public void testSearchInfoExistingItem() {
        System.out.println("\nTest CMWorker search info an EXISTING item");
        String[] itemsList = {"Vivo City Shopping Centre", "Crescent Mall", "Dam Sen Parklands", "Ho Chi Minh City, Downtown"};
        for (int i = 0; i < itemsList.length; i++) {
            assertEquals(ContextManager.cityInfo.get(i).getInfo(), SetupTest.CM_Worker.searchInfo(itemsList[i], new Current()));
        }
    }

    @Test
    public void testSearchInfoNonExistingItem() {
        System.out.println("\nTest CMWorker search info a NON-EXISTING item");
        assertEquals(null, SetupTest.CM_Worker.searchInfo("RMIT University", new Current()));
    }

    private CountDownLatch lock = new CountDownLatch(1);

    @Test
    public void testSearchItemValidUser() throws Exception {
        System.out.println("\nTest CMWorker search item for VALID user");
        String[] expectedResult = {"Dam Sen Parklands"};

        // Need to delay 1000 since the code run asynchronously
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                String[] actualResult = SetupTest.CM_Worker.searchItems("Jack", null);
                assertEquals(expectedResult[0], actualResult[0]);
                }
            },
            1000
        );

        lock.await(2000, TimeUnit.MILLISECONDS);
    }

    @Test
    public void testSearchItemInvalidUser() throws Exception {
        System.out.println("\nTest CMWorker search item for INVALID user");
        String[] expectedResult = {"Dam Sen Parklands"};

        // Need to delay 1000 since the code run asynchronously
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    String[] actualResult = SetupTest.CM_Worker.searchItems("Invalid", null);
                    assertEquals(expectedResult[0], actualResult[0]);
                }
            },
            1000
        );

        lock.await(2000, TimeUnit.MILLISECONDS);
    }

}