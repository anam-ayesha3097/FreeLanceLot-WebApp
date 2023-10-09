package models;

import models.BusinessLogic;
import models.Utilities;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Test Utilities Class for the Freelancelot Models Similar to Java.Util
 *
 * @author Raahul John
 * @version 1.0
 */
public class utilitiestest {
    Utilities ul = new Utilities();

    /**
     * Tests the date from miliseconds to the actual value
     * @return Date in Format ddMMyyyy hh:mm:ss
     * @author Raahul John
     */
    @Test
    public void dataconvertortest(){

        assertEquals("19 Mar 2022 13:57:38",ul.date_converter(1647712658));


    }
    /**
     * Tests the Word Frequency Count
     * @return LinkedHashMap of String and Integer
     * @author Raahul John
     */
    @Test
    public void wordFrequencyCountertest(){
        LinkedHashMap<String, Integer> linkmap = new LinkedHashMap<String, Integer>();
        linkmap.put("world",1);
        linkmap.put("hello",1);

        assertEquals(linkmap,ul.wordFrequencyCounter("hello world"));

    }

    /**
     * Tests if  Returns first ten projects from the 250 API response
     * @return 10 Projects from the 250 API Response
     * @author Raahul John
     */
    @Test
    public void first10projects(){
        LinkedHashMap<String, FreelaancelotList> projlistmap = new LinkedHashMap<>();
        assertEquals(ul.projlistmap_10Projs,ul.first10projects(projlistmap));
    }
}
