package models;

import org.junit.Test;

/**
 * Freelanelot Test Class of Freelancelot Structure for storing the JSON Structure
 * @author Raahul John Vikyath
 * @version 1.0
 */
public class freelancelotTest {
    Freelancelot ft = new Freelancelot();
    @Test
    public void getSkillstest(){
        ft.getSkills();
        ft.toString();

    }
    @Test
    public void getProject_IDtest(){
        ft.getProject_ID();
    }
    @Test
    public void getSeoUrltest(){
        ft.getSeoUrl();
    }
    @Test
    public void getAverageReadabilitytest(){
        ft.getAverageReadability();
    }
    @Test
    public void setDatetest(){
        ft.setDate("");
    }
    @Test
    public void setSkillstest(){
        ft.setSkills("");
    }
    @Test
    public void setStatstest(){
        ft.setStats("");
    }
    @Test
    public void setProject_descriptiontest(){
        ft.setProject_description("");
    }
    @Test
    public void displaytest(){
        ft.display();
    }
}
