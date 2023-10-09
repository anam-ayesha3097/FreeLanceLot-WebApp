package models;

import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 * Test Class Calculates Global and Individual Word Stats
 *
 * @author Sankeerth Koduri
 * @version 1.0
 */

public class wordstatstest{
    WordStats ws = new WordStats();

    /**
     * Tests Calculates Global Word Stats and Individual
     * @return Global Statistics
     * @author Sankeerth Koduri
     */
    @Test
    public void StatsTest() throws IOException {
        Freelancelot f = new Freelancelot("1","",1,"","This is a test case to check the word stats","","","",1L,"","",0,"");
        FreelaancelotList fl = new FreelaancelotList();
        ArrayList<Freelancelot> fa = new ArrayList<Freelancelot>();
        fa.add(f);
        fl.setProjectList(fa);
        assertNotNull(ws.GlobalStats(fl));

        ArrayList<Freelancelot> fa2 = new ArrayList<Freelancelot>();
        Freelancelot freelancelotproj1 = new Freelancelot();
        freelancelotproj1.setProject_description("This is SOEN Project");
        Freelancelot freelancelotproj2 = new Freelancelot();
        freelancelotproj2.setProject_description("He is a boy");

        fa2.add(freelancelotproj1);
        fa2.add(freelancelotproj2);
        FreelaancelotList fl2 = new FreelaancelotList();
        fl2.setProjectList(fa2);


        HashMap<String, Integer> testcase = new HashMap<String, Integer>();

        testcase.put("is",2);
        testcase.put("a", 1);
        testcase.put("boy", 1);
        testcase.put("He",1);
        testcase.put("Project", 1);
        testcase.put("SOEN", 1);
        testcase.put("This", 1);



        assertEquals(
                ws.GlobalStats(fl2),
                testcase
        );

    }

}