package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.toMap;
/**
 * Utilities Class for the Freelancelot Models Similar to Java.Util
 *
 * @author Sankeerth Koduri
 * @version 1.0
 */
public class Utilities {
    public static    LinkedHashMap<String, FreelaancelotList> projlistmap_10Projs = new LinkedHashMap<String, FreelaancelotList>();


    /**
     * Converts the date from miliseconds to the actual value
     * @param date Date in Integer i.e in Milisecond
     * @return Date in Format ddMMyyyy hh:mm:ss
     * @author Sankeerth Koduri
     */
    public static String date_converter(Integer date) {
        String milsec = Integer.toString(date) + "000";
        long miliSec = Long.valueOf(milsec);
        DateFormat obj = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        Date res = new Date(miliSec);
        return obj.format(res);
    }

    /**
     * Calcuates the Word Frequency Count
     * @param s String
     * @return LinkedHashMap of String and Integer
     * @author Sankeerth Koduri
     */
    public LinkedHashMap<String, Integer> wordFrequencyCounter(String s) {
        String[] words = s.replaceAll("^[.,\\s]+", "").split("[.,\\s]+");

        Map<String, Integer> frequency = new HashMap<String, Integer>();
        for (String word : words) {
            Integer count = frequency.get(word);
            count = (count == null) ? 1 : ++count;
            frequency.put(word, count);
        }
        LinkedHashMap<String, Integer> sortedfrequency = new LinkedHashMap<String, Integer>();
        sortedfrequency = frequency
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        return sortedfrequency;
    }

    /**
     * Returns first ten projects from the 250 API response
     * @param projlistmap LinkedHashMap Response of API
     * @return 10 Projects from the 250 API Response
     * @author Sankeerth Koduri
     */
    public LinkedHashMap<String, FreelaancelotList> first10projects(LinkedHashMap<String, FreelaancelotList> projlistmap) {
        for (String i : projlistmap.keySet()) {
            FreelaancelotList value = projlistmap.get(i);
            ArrayList<Freelancelot> entireprojectList = value.getProjectList();
            ArrayList<Freelancelot> tempList = new ArrayList<>();

            int counter = 1;
            for (Freelancelot proj : entireprojectList) {
                if (counter <= 10) {
                    tempList.add(proj);
                } else {
                    break;
                }
                counter++;
            }

            FreelaancelotList frlistobj = new FreelaancelotList();
            frlistobj.setProjectList(tempList);
            projlistmap_10Projs.put(i, frlistobj);
        }
        return projlistmap_10Projs;
    }
}
