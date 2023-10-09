package models;

import scala.Int;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Calculates the Flesch Readability Index of each Project with Education Level and its Average
 * @author Anam Ayesha Shaikh
 * @version 1.0
 */

public class FleschReadabilityIndex {
   public  static LinkedHashMap<String,FreelaancelotList> projsWithReadability = new LinkedHashMap<>();

    static LinkedHashMap<String, Integer> averageFleschIndex = new LinkedHashMap<>();
    static LinkedHashMap<String, String> averageEducationalLevel = new LinkedHashMap<>();
    /**
     * Gets the Data from Preview Description using Completable Future as Asynchronous Non-Blocking Code
     * @param projsSearched LinkedHashMap of <Search Term ,Freelancelot>
     * @return Completable Future LinkedHashMap of the Freelancelot Structure
     * @author Anam Ayesha Shaikh
     */
    public static CompletableFuture<LinkedHashMap<String, FreelaancelotList>> getReadabilityAsync(LinkedHashMap<String,FreelaancelotList> projsSearched) {

        return CompletableFuture.supplyAsync(() -> getPreviewDescription(projsSearched));
    }
    /**
     * Gets the Data from Preview Description of each Projects
     * @param projsSearched LinkedHashMap of <Search Term ,Freelancelot>
     * @return LinkedHashMap of the Freelancelot Structure of each project's calculated Flesch Index and Educational Value (FKGL)
     * @author Anam Ayesha Shaikh
     */
    public static LinkedHashMap<String, FreelaancelotList> getPreviewDescription(LinkedHashMap<String,FreelaancelotList> projsSearched){
        LinkedHashMap<String,FreelaancelotList> prev = new LinkedHashMap<>();
        int index = 0;
        Freelancelot freelancelotObj = null;
        int averageFlesch = 0;
        String averageEducationalLevel = null;

        //System.out.println("Preview Description "+projsSearched);

        for(Iterator<Map.Entry<String, FreelaancelotList>> iterator = projsSearched.entrySet().iterator(); iterator.hasNext();){
            Map.Entry<String, FreelaancelotList> entries = iterator.next();
            System.out.println("Search Term : "+entries.getKey());
            ArrayList<Freelancelot> freelancelotArrayList =  entries.getValue().getProjectList();

            List<String> listPrevDesc = freelancelotArrayList.stream()
                    .map(fl -> fl.getProject_description().toLowerCase().replaceAll("[0-9]",""))
                    .collect(Collectors.toList());

            List<Long> fleschIndex = listPrevDesc.stream().map(s -> processPrevDesc(s)).collect(Collectors.toList());
            List<Integer> fleschIndexInt = fleschIndex.stream().mapToInt(Long::intValue).boxed().collect(Collectors.toList());
            averageFlesch = fleschIndexInt.stream().reduce(0, Integer::sum);
            averageFlesch = averageFlesch / 10;
            averageFlesch(averageFlesch, entries.getKey());
            System.out.println("Avaerage Flesch for "+entries.getKey() +" is" +averageFlesch);
            averageEducationalLevel = calculateEductionalLevel(averageFlesch);
            averageEducationalLevel(averageEducationalLevel,entries.getKey());
            System.out.println("Avaerage Educational Level  "+entries.getKey() +" is" +averageEducationalLevel);
            List<String> educationalLevel = fleschIndex.stream().map(fi -> calculateEductionalLevel(fi)).collect(Collectors.toList());

            index = 0;
            ArrayList<Freelancelot> listFreelancelot = new ArrayList<>();
            for(Freelancelot fl : freelancelotArrayList)
            {
                freelancelotObj = new Freelancelot( fl.getOwner_id(), fl.getDate(), fl.getProject_ID(), fl.getProject_title(), fl.getProject_description(), fl.getProject_type(), fl.getSkills(), "", fleschIndex.get(index), educationalLevel.get(index), fl.getSeoUrl(), fl.getAverageReadability(), fl.getEducationalLevel() );
                listFreelancelot.add(freelancelotObj);
                index += 1;
            }
            FreelaancelotList setListObjs = new FreelaancelotList();
            setListObjs.setProjectList(listFreelancelot);
            projsWithReadability.put(entries.getKey(), setListObjs);

            for(Iterator<Map.Entry<String, FreelaancelotList>> iterator1 = projsWithReadability.entrySet().iterator(); iterator1.hasNext();){
                Map.Entry<String, FreelaancelotList> entries1 = iterator1.next();
                System.out.println("Response Readability "+entries1.getKey());
                ArrayList<Freelancelot> freelancelotArrayList1 =  entries1.getValue().getProjectList();
                for(Freelancelot fl : freelancelotArrayList1)
                    fl.display();
            }
        }
        return projsWithReadability;
    }
    /**
     *Calculates Flesch Index of the words count, sentence count and syllable count of each Preview Description
     * @param prevDesc String Preview Description
     * @return long Flesch Index
     * @author Anam Ayesha Shaikh
     */
    public static long processPrevDesc(String prevDesc){
        long wordCount = 0;
        long sentenceCount = 0;
        long syllableCount = 0;
        long fleschIndex = 0;
        //System.out.println("String passed from Map" +prevDesc);

        wordCount = wordCounter(prevDesc);
        sentenceCount = sentenceCounter(prevDesc);
        syllableCount = syllableCounter(prevDesc);
        //System.out.println("Word Count of '" +prevDesc+ "' is " +wordCount);
        //System.out.println("Sentence Count of '" +prevDesc+ "' is " +sentenceCount);
        //System.out.println("Syllable Count of '" +prevDesc+ "' is " +syllableCount);

        fleschIndex = calculateFleschIndex(wordCount,sentenceCount,syllableCount);
        return fleschIndex;
    }

    /**
     * Calculates Word Count of Preview Descrition
     * @param prevDesc String Preview Description
     * @return long word counter
     * @author Anam Ayesha Shaikh
     */
    public static long wordCounter(String prevDesc){

        long wordCount = 0;

        List<String> stringSplit = Stream.of(prevDesc).map(s-> s.split("\\W+"))
                .flatMap( stringArray ->
                        Arrays.stream(stringArray).filter(s-> !s.equals(" ") && !s.equals("\t") && !s.equals("\n"))).collect(Collectors.toList());
        //System.out.println("String after Java 8 Split "+stringSplit);
        wordCount = stringSplit.size();
        //System.out.println("String Word Count "+wordCount);
        return wordCount;
    }
    /**
     * Gets Sentence Count of Preview Description
     * @param prevDesc String Preview Description
     * @return long senetence counter
     * @author Anam Ayesha Shaikh
     */
    public static long sentenceCounter(String prevDesc){
        long sentenceCount = 0;


        List<String> stringSplit = Stream.of(prevDesc).map(s-> s.split("[.!?:;]+"))
                .flatMap( stringArray ->
                        Arrays.stream(stringArray).filter(s-> !s.equals(" ") && !s.equals("\t") && !s.equals("\n") && !s.equals("."))).collect(Collectors.toList());

        sentenceCount = stringSplit.size();
        //System.out.println("Sentence Count Java 8 Streams"+sentenceCount);

        return sentenceCount;
    }

    /**
     * Gets Syllable Count of Preview Description
     * @param prevDesc String Prev Desc
     * @return long syllable counter
     * @author Anam Ayesha Shaikh
     */
    public static Long syllableCounter(String prevDesc) {

        int smallSyllableCount = 0;
        int bigSyllableCount = 0;
        long finalSyllableCount = 0;

        List<String> words = Stream.of(prevDesc).map(s -> s.split("\\W+"))
                .flatMap(stringArray -> Arrays.stream(stringArray).filter(s -> !s.equals(" ") && !s.equals("\t") && !s.equals("\n")))
                .collect(Collectors.toList());
        //System.out.println("String after Java 8 Split " + words);

        List<String> smallWords = words.stream().filter(s -> s.length() < 4).collect(Collectors.toList());
        //System.out.println("Single Syllable Words "+smallWords);
        List<String> bigWords = words.stream().filter(s -> s.length() >= 4).collect(Collectors.toList());
        //System.out.println("Before Big Words Replace " +bigWords);

        List<String> bigWordsUpdate = bigWords.stream().map(s -> checkEndings(s)).collect(Collectors.toList());
        //System.out.println("After Big Words Replace "+bigWordsUpdate);

        List<Integer> slSmallWords = smallWords.stream().map(s -> checkSyllable(s)).collect(Collectors.toList());
        //System.out.println("Small Words Syllable Count "+slSmallWords);
        List<Integer> slBigWords = bigWordsUpdate.stream().map(s-> checkSyllable(s)).collect(Collectors.toList());
        //System.out.println("Big Words Syllable Count "+slBigWords);

        smallSyllableCount = slSmallWords.stream().map(count -> count).reduce(0, Integer::sum);
        //System.out.println("Small Words Syllable Count" +smallSyllableCount);
        bigSyllableCount = slBigWords.stream().reduce(0, Integer::sum);
        //System.out.println("Big Words Syllable Count" +bigSyllableCount);
        finalSyllableCount = smallSyllableCount + bigSyllableCount;
        //System.out.println("Final Syllable Count "+finalSyllableCount);

        return finalSyllableCount;
    }

    /**
     * Checks the ending of each string for the correct Syllable Count in order to remove 'es', 'ed', 'e'
     * @param s String
     * @return Replaced String
     * @author Anam Ayesha Shaikh
     */
    public static String checkEndings(String s){
        List<String > bigWordsUpdate = new ArrayList<>();
        String replace = null;
        String secondLastValue = s.substring(s.length() - 2);
        String lastValue = s.substring(s.length() - 1);
        if (lastValue.equals("e")) {
            replace = s.replaceAll("e$", "");
        } else {
            if (secondLastValue.equals("es")) {
                replace = s.replaceAll("es$", "");
                String end = replace.substring(replace.length() - 1);
                if (end.equals("e")) {
                    replace = replace.replaceAll("e$", "");
                }
            }
            if (secondLastValue.equals("ed")) {
                replace = s.replaceAll("ed$", "");
                String end = replace.substring(replace.length() - 1);
                if (end.equals("e")) {
                    replace = replace.replaceAll("e$", "");
                }
            }
            if (secondLastValue.equals("ee")) {
                replace = s.replaceAll("ee$", "");
                String end = replace.substring(replace.length() - 1);
                if (end.equals("e")) {
                    replace = replace.replaceAll("e$", "");
                }
            }
        }
        if(!secondLastValue.equals("es") && !secondLastValue.equals("ed") && !secondLastValue.equals("ee") && !lastValue.equals("e"))
            replace = s;
        return replace;
    }

    /**
     * Checks the Syllable for each Word
     * @param s String Preview Description
     * @return Integer count of the preview description
     * @author Anam Ayesha Shaikh
     */
    public static Integer checkSyllable(String s){
        int syllableCounter = 0;
        //System.out.println(s);
        if(s.length() < 4 && (s.contains("a") || s.contains("e") || s.contains("i") || s.contains("o") || s.contains("u") || s.contains("y")))
            syllableCounter += 1;
        else if (s.length() >= 4) {
            // System.out.println("Big Words String Passed "+s);
            List<Integer> slCount = Stream.of(s).map(words -> words.toCharArray()).map(c -> countSyllable(c)).collect(Collectors.toList());
            //System.out.println("Syllable Counter After Count "+slCount);
            syllableCounter = slCount.get(0);
        }

        return syllableCounter;
    }
    /**
     * Counts Syllable for each Character
     * @param chars Character Array
     * @return Integer i.e. the Counted Syllable for Each Character
     * @author Anam Ayesha Shaikh
     */
    public static Integer countSyllable(char[] chars){
        int syllableCounter = 0;
        char temp = '@';
        //System.out.println("Initial Temp " +temp);
        for(char c: chars){
            // System.out.println("Char "+c);
            //System.out.println("Temp Inside For Loop "+temp);
            if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'y' )
            {
                if(temp == 'a' || temp == 'e' || temp == 'i' || temp== 'o' || temp== 'u' || temp=='y')
                    continue;
                else
                    syllableCounter += 1;
                //System.out.println("Syllable Cunter "+syllableCounter);
            }
            temp = c;
            //System.out.println("Temp after Previous Char Assignment "+temp);
        }
        //System.out.println("Syllable Counter inside CountSyllable Method "+syllableCounter);
        return syllableCounter;
    }
    /**
     * Calculates the Flesch Readability Index
     * @param wordCount sentenceCount syllableCount All the 3 Counters for the Preview Description
     * @return Calculated Flesch Index
     * @author Anam Ayesha Shaikh
     */
    public static long calculateFleschIndex(Long wordCount, Long sentenceCount, Long syllableCount){

        double fleschIndex = 0;
        long fleschIndexValue = 0;

        fleschIndex = 206.835 - 84.6 * syllableCount/wordCount  - 1.015 * wordCount/sentenceCount ;
        fleschIndexValue = Math.round(fleschIndex);
        //System.out.println("Flesch Index " +fleschIndex);
        System.out.println("Flesch Index " +fleschIndexValue);
        return fleschIndexValue;
    }


    /**
     * Calculates the Educational Level for the Flesch Index
     * @param fleschIndex LinkedHashMap of <Search Term ,Freelancelot>
     * @return String of the Educational Level
     * @author Anam Ayesha Shaikh
     */
    public static String calculateEductionalLevel(long fleschIndex){

        if(fleschIndex > 100 )
            return "Early";
        else if(fleschIndex > 91 )
            return "5th Grade";
        else if(fleschIndex > 81)
            return "6th Grade";
        else if(fleschIndex > 71)
            return "7th Grade";
        else if(fleschIndex > 66)
            return "8th Grade";
        else if(fleschIndex > 61)
            return "9th Grade";
        else if(fleschIndex > 51)
            return "High School";
        else if(fleschIndex > 31)
            return "Some College";
        else if(fleschIndex > 0)
            return "College Graduate";
        else if(fleschIndex <= 0)
            return "Law School Graduate";
        else
            return "Not Valid Flesch Index";
    }
    /**
     * Stores the Average Flesch Index for each Search Term
     * @param avgFleshIndex, seacrhTerm Stores the Average Flesch Index and Search Term
     * @author Anam Ayesha Shaikh
     */

    public static void averageFlesch(Integer avgFleshIndex, String searchTerm){
        System.out.println("Inside Put Avg FI");
        averageFleschIndex.put(searchTerm,avgFleshIndex);
    }

    /**
     * Stores the Average Educational Level for each Search Term
     * @param avgEducationalLevel, seacrhTerm Stores the Average Educational Level  and Search Term
     * @author Anam Ayesha Shaikh
     */
    public static void averageEducationalLevel(String avgEducationalLevel, String searchTerm){
        System.out.println("Inside Put Avg EducationalLevel");
        averageEducationalLevel.put(searchTerm,avgEducationalLevel);
    }

    /**
     * Gets the Average Readability Index for Each Search Project
     * @param searchTerm String job searched
     * @return Integer i.e the Average Flesch Readability
     * @author Anam Ayesha Shaikh
     */
    public static Integer getAvgFleschIndex(String searchTerm){
        System.out.println("Inside Get Avg Flesch Index ");
        Integer averageFIndex = 0;
        for(Iterator<Map.Entry<String, Integer>> iterator = averageFleschIndex.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String, Integer> entries = iterator.next();
            if(entries.getKey().contains(searchTerm)){
                averageFIndex = entries.getValue();
            }
        }
        return averageFIndex;
    }


    /**
     * Gets the Average Educational Level Index for Each Search Project
     * @param searchTerm String job searched
     * @return Integer i.e the Average Educational Readability
     * @author Anam Ayesha Shaikh
     */
    public static String getAvgEducationLevel(String searchTerm){
        System.out.println("Inside Get Avg Educational Level");
        String avgEducationalLevel = null;
        for(Iterator<Map.Entry<String, String>> iterator = averageEducationalLevel.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String,String> entries = iterator.next();
            if(entries.getKey().contains(searchTerm)){
                avgEducationalLevel = entries.getValue();
            }
        }
        return avgEducationalLevel;
    }
}




