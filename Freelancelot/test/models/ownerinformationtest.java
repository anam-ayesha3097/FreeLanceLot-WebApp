package models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests Class Owner Information Gets/Sets the information about the employee when invoked
 *
 * @author Vikyath
 * @version 1.0
 */


public class ownerinformationtest {
    OwnerInformation ot = new OwnerInformation();
    @Test
    public void getOwnerProjects_activetest(){
        ot.getOwnerProjects_active();
    }
    @Test
    public void getPrimaryCurrencytest(){
        ot.getPrimaryCurrency();
    }
    @Test
    public void getidtest(){
        ot.getId();

    }
    @Test
    public void getUsernametest(){
        ot.getUsername();
    }
    @Test
    public void getCompanytest(){
        ot.getCompany();
    }
    @Test
    public void getCountrytest(){
        ot.getCountry();
    }
    @Test
    public void getRegistrationDatetest(){
        ot.getRegistrationDate();
    }
    @Test
    public void getRoletest(){
        ot.getRole();
    }

    @Test
    public void setIdtest(){
        ot.setId("");
        ot.setUsername("");
        ot.setCompany("");
        ot.setCountry("");

    }
}
