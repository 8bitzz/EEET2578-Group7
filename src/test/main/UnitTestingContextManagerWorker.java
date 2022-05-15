package main;

import helper.ContextManagerWorker;
import org.junit.BeforeClass;
import org.junit.Test;

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
}