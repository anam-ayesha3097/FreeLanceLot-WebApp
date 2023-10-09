package models;

import models.FreelaancelotList;
import models.Freelancelot;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Test Class of Freelancelot List  to get the Entire List of the Freelancelot Class
 *
 * @author Raahul John and Vikyath
 * @version 1.0
 */
public class FreelancelotListTest {

    FreelaancelotList fl = new FreelaancelotList();
    Freelancelot flt = new Freelancelot("1324","1234",13254,"","","","","",464545L,"","",0,"");
    ArrayList<Freelancelot> prolist = new ArrayList<Freelancelot>();


    /**
     * Gets the Freelancelot Project Lists
     * @author Raahul John
     */
    @Test
    public void getdatatest(){
        prolist.add(flt);
        fl.setProjectList(prolist);
       assertEquals(prolist,fl.getProjectList());

    }

    /**
     * Tets Average Readability of the Search Term
     * @author Raahul John
     */
    @Test
    public void getAvgReadabilitytest(){
        Integer avgReadability = 0;
        avgReadability = FleschReadabilityIndex.getAvgFleschIndex("php");
        assertSame(avgReadability,fl.getAvgReadability("php"));
    }

    /**
     * Tests Average Educational Level of the Search Term
     * @author Raahul John
     */
    @Test
    public void getAvgEducationalLeveltest(){
        String avgEducationalLevel = null;
        avgEducationalLevel = FleschReadabilityIndex.getAvgEducationLevel("php");
        assertEquals(avgEducationalLevel,fl.getAvgEducationalLevel("php"));
    }
}
