package models;

import scala.Int;

import java.util.HashMap;
/**
 * Freelanelot Structure for storing the JSON Structure
 * @author Raahul John Vikyath
 * @version 1.0
 */
public class Freelancelot {

    private String owner_id;
    private String date;
    private Integer project_ID;
    private String project_title;
    private String project_description;
    private String project_type;
    private String skills;
    private String stats;
    private Long readability;
    private String seoUrl;
    private String educationalLevel;
    private Integer averageReadability;
    private String averageEducationalLevel;

    /**
     * Default Constructor of Freelancelot
     * @author Raahul John Vikyath
     */
    public Freelancelot()
    {

    }
    /**
     * Constructor of Freelancelot to set the Freelancelot Data Structure
     * @author Raahul John Vikyath
     */
    public Freelancelot(String owner_id, String date, Integer project_ID, String project_title , String project_description, String project_type, String skills, String stats, Long readability, String educationalLevel , String seoUrl, Integer averageReadability, String averageEducationalLevel){
        this.owner_id = owner_id;
        this.date = date;
        this.project_title = project_title;
        this.project_ID = project_ID;
        this.project_description = project_description;
        this.project_type = project_type;
        this.skills = skills;
        this.stats = stats;
        this.readability = readability;
        this.educationalLevel = educationalLevel;
        this.seoUrl =seoUrl;
        this.averageEducationalLevel = averageEducationalLevel;
        this.averageReadability = averageReadability;
    }

    public java.lang.String getOwner_id() {
        return this.owner_id;
    }

    public java.lang.String getDate() {
        return this.date;
    }

    public java.lang.String getProject_description() {
        return this.project_description;
    }

    public java.lang.String getProject_type() {
        return this.project_type;
    }

    public java.lang.String getSkills() {
        return this.skills;
    }

    public java.lang.Long getReadability() {
        return this.readability;
    }

    public java.lang.String getProject_title() {
        return this.project_title;
    }

    public java.lang.Integer getProject_ID(){return this.project_ID;}

    public String getEducationalLevel() {
        return this.educationalLevel;
    }

    public String getSeoUrl() {
        return this.seoUrl;
    }

    public Integer getAverageReadability() {
        return this.averageReadability;
    }



    public void setDate(java.lang.String date) {
        this.date = date;
    }

    public void setSkills(java.lang.String skills) {
        this.skills = skills;
    }


    public void setStats(java.lang.String stats) {
        this.stats = stats;
    }

    public void setProject_description(String project_description) {
        this.project_description = project_description;
    }

    public String toString()
    {
        return "[Owner ID:" +getOwner_id()+
                    "\nDate: " +getDate()+
                    "\nProject Description : " +getProject_description()+
                    "\nProject Type: " +getProject_type() + "]\n";
    }

    public void display() {
        System.out.println("Owner ID: " +getOwner_id()+ " Date: " +getDate()+ " Project Title : " +getProject_title()+ " Project Type: " +getProject_type() +" Flesch Index: " +getReadability() +" Educational Level: "+getEducationalLevel());
    }
}