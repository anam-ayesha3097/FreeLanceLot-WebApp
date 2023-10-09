package models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
/**
 * Gets/Sets the information about the employee when invoked
 *
 * @author Vikyath
 * @version 1.0
 */

public class OwnerInformation {

    String id;
    String username;
    String company;
    String country;
    String registrationDate;
    String limitedAccount;
    String role;
    String emailVerified;
    String primaryCurrency;



    LinkedHashMap<String, FreelaancelotList> OwnerProjects_active= new LinkedHashMap<String, FreelaancelotList>();

    /**
     * Gets Active Owner's project

     * @return HashMap of the Owner's Active Projects
     * @author Vikyath
     */

    public LinkedHashMap<String, FreelaancelotList> getOwnerProjects_active() {
        return OwnerProjects_active;
    }


    /**
     * Sets Owner ID
     * @param id Owner ID
     * @author Vikyath
     */
    public void setId(String id)
    {
        this.id=id;
    }

    /**
     * Sets Owner Name
     * @param username Owner Name
     * @author Vikyath
     */
    public void setUsername(String username)
    {
        this.username=username;
    }

    /**
     * Sets the Owner Company
     * @param company Owner Company
     * @author Vikyath
     */
    public void setCompany(String company)
    {
        this.company=company;
    }

    /**
     * Sets Owner Country
     * @param country Owner Company
     * @author Vikyath
     */
    public void setCountry(String country)
    {
        this.country=country;
    }
    /**
     * Gets Owner ID
     * @return id Owner ID
     * @author Vikyath
     */

    public String getId()
    {
        return this.id;
    }

    /**
     * Gets User Name
     * @return username Owner Name
     * @author Vikyath
     */
    public String getUsername()
    {
        return this.username;
    }

    /**
     * Gets Company Name
     * @return company Owner Company
     * @author Vikyath
     */

    public String getCompany()
    {
        return this.company;
    }
    /**
     * Gets Owner Country
     * @return Country String
     * @author Vikyath
     */

    public String getCountry()
    {
        return this.country;
    }


    /**
     * Gets Registration Date
     * @return registrationDate String
     * @author Vikyath
     */

    public String getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Sets Registartion Date
     * @param registrationDate String
     * @author Vikyath
     */
    public void setRegistrationDate(String registrationDate) {
        Date date = new Date(Long.parseLong(registrationDate+"000"));

        String pattern = "MMM-dd-yyyy";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        registrationDate = simpleDateFormat.format(date);
        this.registrationDate = registrationDate;
    }
    /**
     * Sets Limited Account
     * @param limitedAccount String
     * @author Vikyath
     */

    public void setLimitedAccount(String limitedAccount)
    {
        this.limitedAccount=limitedAccount;
    }
    /**
     * Gets Limited Account
     * @return limitedAccount String
     * @author Vikyath
     */

    public String getLimitedAccount()
    {
        return this.limitedAccount;
    }

    /**
     * Sets Role
     * @param role String
     * @author Vikyath
     */
    public void setRole(String role)
    {
        this.role=role;
    }
    /**
     * Gets Role
     * @return role String
     * @author Vikyath
     */
    public String getRole()
    {
        return this.role;
    }

    /**
     * Sets Email Verified
     * @param emailVerified String
     * @author Vikyath
     */

    public void setEmailVerified(String emailVerified)
    {
        this.emailVerified=emailVerified;
    }

    /**
     * Gets Email Verified
     * @return emailVerified String
     * @author Vikyath
     */
    public String getEmailVerified()
    {
        return this.emailVerified;
    }


    /**
     * Sets Primary Currency
     * @param primaryCurrency String
     * @author Vikyath
     */
    public void setPrimaryCurrency(String primaryCurrency)
    {
        this.primaryCurrency=primaryCurrency;
    }
    /**
     * Gets Primary Currency
     * @return primary Currency String
     * @author Vikyath
     */
    public String getPrimaryCurrency()
    {
        return this.primaryCurrency;
    }
}
