package models;

import scala.Int;

import java.util.ArrayList;

/**
 * Freelancelot List  to get the Entire List of the Freelancelot Class
 *
 * @author Raahul John and Vikyath
 * @version 1.0
 */
public class FreelaancelotList {
    private ArrayList <Freelancelot> ProjectList = new ArrayList<Freelancelot>();

    /**
     * Sets the Freelancelot Project List
     * @param  projectList Arraylist of Freelancelot
     * @author Raahul John and Vikyath
     */
    public void setProjectList(ArrayList<Freelancelot> projectList) {
        ProjectList = projectList;
    }

    /**
     * Gets the Freelancelot Project List
     * @return the Arraylist of Freelancelot
     * @author Raahul John and Vikyath
     */
    public ArrayList<Freelancelot> getProjectList() {
        return ProjectList;
    }

    /**
     * Gets Average Readability of the Search Term
     * @param searchTerm String job searcehd
     * @author Anam Ayesh Shaikh
     */
    public Integer getAvgReadability(String searchTerm) {
        Integer avgReadability = 0;
        avgReadability = FleschReadabilityIndex.getAvgFleschIndex(searchTerm);
        return avgReadability;
    }

    /**
     * Gets Average Educational Level of the Search Term
     * @param searchTerm String job searcehd
     * @author Anam Ayesh Shaikh
     */
    public String getAvgEducationalLevel(String searchTerm) {
        String avgEducationalLevel = null;
        avgEducationalLevel = FleschReadabilityIndex.getAvgEducationLevel(searchTerm);
        return avgEducationalLevel;
    }
 }
