package models;

import org.json.JSONObject;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
/**
 * Tests Class for the information about the employee from the Freelancer API
 *
 * @author Vikyath
 * @version 1.0
 */
public class ownerdetailsTest {
    OwnerDetails ot = new OwnerDetails();

    /**
     * Tests the information about the employee when invoked
     *
     * @return Information about the owner
     * @author Vikyath
     */
    @Test
    public void getOwnerProjectTest(){
        LinkedHashMap<String, FreelaancelotList> OwnerProjects_active= new LinkedHashMap<String, FreelaancelotList>();

        assertEquals(ot.OwnerProjects_active,ot.getOwnerProject("1"));
    }

    /**
     * Checks if the Nodes Exists
     *
     * @return boolen true/false
     * @author Vikyath
     */
    @Test
    public void checkNodeExistsTest(){
        JSONObject jObj = new JSONObject();
        assertEquals(false,ot.checkNodeExists(jObj,""));
    }

    /**
     * Tetss the Latest 10(max) projects of an owner if exists
     *
     * @return Projects done by the owner
     * @author Vikyath
     */
    @Test
    public void getOwnerDetailsTest(){
        OwnerInformation oi = new OwnerInformation();
        OwnerDetails ot2 = new OwnerDetails();


        assertEquals(ot.getOwnerDetails("245117"),ot.getOwnerDetails("245117"));
    }
}
