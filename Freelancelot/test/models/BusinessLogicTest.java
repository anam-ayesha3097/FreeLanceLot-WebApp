package models;

import models.BusinessLogic;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static models.BusinessLogic.getData;
import static org.junit.Assert.*;
/**
 * Tests Class for the Data Pull from FreeLancelot API using Completable Future
 *
 * @author Raahul John
 * @version 1.0
 */
public class BusinessLogicTest extends Mockito{

    BusinessLogic b1;

    @Before
    public void setup(){
        b1 = new BusinessLogic();
    }
    @Test
    public void testBusniesslogic()
    {
        BusinessLogic b2 = b1;
        b2.API = "https://www.freelancer-sandbox.com/api/projects/0.1/projects/active?job_details=true&limit=250&preview_description=true&query=";
        getData("PHP");
        assertNotNull(getData("java framework"));
        b2.API = "hppts://www.freelancer-sandbox.com/api/projects/0.1/projects/active?job_details=true&limit=250&preview_description=true&query=";
        getData("PHP");
    }
//    @Test
//    public void getdatatest(){
//     //   bl.getData("php");
//        assertSame(bl.projects_active,bl.getData("php"));
//
//
//    }
    /**
     * Tests the skill data set
     * @author Raahul John
     */
    @Test
    public void getskillsdatatest(){
        skills sl = new skills();
        assertSame(sl.skills_active,sl.getDataSkills("php"));
    }

    /**
     * Tests the Data from FreeLancelot API using Completable Future as Asynchronous Non-Blocking Code
     * @return Completable Future LinkedHashMap of the Freelancelot Structure
     * @author Raahul John
     */
    @Test
    public void getAPIDataAsyncTest() throws ExecutionException, InterruptedException {
        BusinessLogic businessLogic = new BusinessLogic();

        CompletableFuture<LinkedHashMap<String, FreelaancelotList>> futureAPIResult = businessLogic.getAPIDataAsync("php");
       // response = futureAPIResult.get();
        assertEquals(futureAPIResult.get(),businessLogic.getAPIDataAsync("php").get());
    }
}
