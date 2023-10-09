package models;

import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static models.FleschReadabilityIndex.*;
import static org.junit.Assert.*;

/**
 * Tests Class to Calculate Flesch Readability Index of each Project with Education Level and its Average
 * @author Anam Ayesha Shaikh
 * @version 1.0
 */
public class FleschReadabilityIndexTest {
    FleschReadabilityIndex flt = new FleschReadabilityIndex();

    /**
     * Tets the Data from Preview Description using Completable Future as Asynchronous Non-Blocking Code
     * @author Raahul John
     */
    @Test
    public void getReadabilityAsyncTest() throws ExecutionException, InterruptedException {
        CompletableFuture<LinkedHashMap<String, FreelaancelotList>> readability = FleschReadabilityIndex.getReadabilityAsync(flt.projsWithReadability);

        assertEquals(readability.get(),FleschReadabilityIndex.getReadabilityAsync(flt.projsWithReadability).get());
    }

    /**
     * Tests the Data from Preview Description of each Projects
     * @author Raahul John
     */
    @Test
    public void getPreviewDescriptionTest() {
        assertEquals(flt.projsWithReadability,FleschReadabilityIndex.getPreviewDescription(flt.projsWithReadability));
    }

    /**
     *Tests Flesch Index of the words count, sentence count and syllable count of each Preview Description
     * @return long Flesch Index
     * @author Raahul John
     */
    @Test
    public void processPrevDescTest() {
        long wordCount = 0;
        long sentenceCount = 0;
        long syllableCount = 0;
        long fleschIndex = 0;
        wordCount = wordCounter("");
        sentenceCount = sentenceCounter("");
        syllableCount = syllableCounter("");


        fleschIndex = calculateFleschIndex(wordCount,sentenceCount,syllableCount);
        assertEquals(fleschIndex,FleschReadabilityIndex.processPrevDesc(""));
    }
    /**
     * Checks the ending of each string for the correct Syllable Count in order to remove 'es', 'ed', 'e'
     * @return Replaced String
     * @author Raahul John
     */
    @Test
    public void checkEndingsTest(){
        assertEquals("modul",FleschReadabilityIndex.checkEndings("modules"));
    }

    /**
     * Tests the Syllable for each Word
     * @return Integer count of the preview description
     * @author Raahul John
     */
    @Test
    public void checkSyllableTest(){
        assertSame(3,FleschReadabilityIndex.checkSyllable("expression"));
    }

    /**
     * Tests Syllable for each Character
     * @return Integer i.e. the Counted Syllable for Each Character
     * @author Raahul John
     */
    @Test
    public void countSyllableTest(){
        char[] ch = {'a','e','b','y'};
        assertSame(2,FleschReadabilityIndex.countSyllable(ch));

    }

    /**
     * Tests the Flesch Readability Index
     * @return Calculated Flesch Index
     * @author Raahul John
     */
    @Test
    public void calculateFleschIndexTest(){
        assertEquals(80L,FleschReadabilityIndex.calculateFleschIndex(17L,1L,22L));
    }


    /**
     * Tests the Educational Level for the Flesch Index
     * @return String of the Educational Level
     * @author Raahul John
     */
    @Test
   public void calculateEductionalLevelTest(){
        assertSame("7th Grade",FleschReadabilityIndex.calculateEductionalLevel(80L));
    }

    /**
     * Tests the Average Flesch Index for each Search Term
     * @author Anam Ayesha Shaikh
     */

    @Test
    public void averageFleschTest(){
        FleschReadabilityIndex.averageFlesch(80,"php");
    }
    @Test
    public void averageEducationalLevelTest(){
        FleschReadabilityIndex.averageEducationalLevel("7th Grade","php");
    }

    /**
     * Tests the Average Readability Index for Each Search Project
     * @return Integer i.e the Average Flesch Readability
     * @author Raahul John
     */
    @Test
    public void getAvgFleschIndexTest(){
        Integer averageFIndex = 0;
        for(Iterator<Map.Entry<String, Integer>> iterator = averageFleschIndex.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String, Integer> entries = iterator.next();
            if(entries.getKey().contains("php")){
                averageFIndex = entries.getValue();
            }
        }
        assertEquals(averageFIndex,FleschReadabilityIndex.getAvgFleschIndex("php"));
    }


    @Test
    public void getAvgEducationLevelTest(){
        String avgEducationalLevel = null;
        for(Iterator<Map.Entry<String, String>> iterator = averageEducationalLevel.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String,String> entries = iterator.next();
            if(entries.getKey().contains("php")){
                avgEducationalLevel = entries.getValue();
            }
        }
        assertEquals(avgEducationalLevel,FleschReadabilityIndex.getAvgEducationLevel("php"));
    }
}
