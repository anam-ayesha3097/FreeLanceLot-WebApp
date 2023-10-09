package models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
/**
 * Gets the information about the employee from the Freelancer API
 *
 * @author Vikyath
 * @version 1.0
 */
public class OwnerDetails {

    LinkedHashMap<String, FreelaancelotList> OwnerProjects_active= new LinkedHashMap<String, FreelaancelotList>();
    OwnerInformation ownerInformation = new OwnerInformation();
    /**
     * Displays the information about the employee when invoked
     *
     * @param  ownerId : The identification number of the User/Owner
     * @return Information about the owner
     * @author Vikyath
     */

    public OwnerInformation getOwnerDetails(String ownerId)
    {

        try {
            URL url = new URL("https://www.freelancer.com/api/users/0.1/users/"+ownerId+"?avatar=true&display_info=true&country_details=true&jobs=true&portfolio_details=true&preferred_details=true&profile_description=true&qualification_details=true&recommendations=true&responsiveness=true&status=true&&operating_areas=true&compact=true");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if(conn.getResponseCode() == 200) {
                Scanner scan = new Scanner(url.openStream());
                String temp="";
                while(scan.hasNext()) {
                    temp = temp + scan.nextLine();
                }
                JSONObject json = new JSONObject(temp);
                JSONObject owner = json.getJSONObject("result");
                ownerInformation.setId(owner.get("id").toString());
                ownerInformation.setUsername(owner.get("username").toString());

                ownerInformation.setRegistrationDate(owner.get("registration_date").toString());

                ownerInformation.setLimitedAccount(owner.get("limited_account").toString());

                ownerInformation.setRole(owner.get("role").toString());

                if(checkNodeExists(owner,"status"))
                {
                    JSONObject status = owner.getJSONObject("status");
                    ownerInformation.setEmailVerified(status.get("email_verified").toString());
                }
                else
                    ownerInformation.setEmailVerified("NA");


                if(checkNodeExists(owner,"primary_currency"))
                {
                    JSONObject primaryCurrency = owner.getJSONObject("primary_currency");
                    ownerInformation.setPrimaryCurrency((primaryCurrency.get("name").toString()));

                }
                else
                    ownerInformation.setPrimaryCurrency("NA");


                if(checkNodeExists(owner,"public_name"))
                {
                    if(owner.get("public_name").toString().isEmpty())
                        ownerInformation.setCompany("NA");
                    else
                        ownerInformation.setCompany(owner.get("public_name").toString());
                }
                else
                    ownerInformation.setCompany("NA");

                JSONObject location = owner.getJSONObject("location");
                if(checkNodeExists(location,"country"))
                {
                    JSONObject country = location.getJSONObject("country");

                    if(country.get("name").toString().isEmpty())
                        ownerInformation.setCountry("NA");
                    else
                        ownerInformation.setCountry(country.get("name").toString());
                }
                else
                    ownerInformation.setCountry("NA");

            }
            ownerInformation.OwnerProjects_active = getOwnerProject(ownerId);
        }
        catch(Exception e) {}


        return ownerInformation;
    }

    /**
     * Checks if the Nodes Exists
     *
     * @param  key, JSONObject JSON Object and key
     * @return boolen true/false
     * @author Vikyath
     */

    public boolean checkNodeExists(JSONObject jObj, String key){
        if(jObj.has(key))
            return true;
        else
            return false;
    }

    /**
     * Displays the Latest 10(max) projects of an owner if exists
     *
     * @param  ownerId : The identification number of the User/Owner
     * @return Projects done by the owner
     * @author Vikyath
     */

    public LinkedHashMap<String, FreelaancelotList> getOwnerProject(String ownerId ) {

        Freelancelot proj_det = null;
        JSONObject result;
        String preview_description = "";
        FreelaancelotList projectList = new FreelaancelotList();
        try {
            URL url = new URL("https://www.freelancer.com/api/projects/0.1/projects/?owners[]="+ownerId+"&limit=10&job_details=true&compact=true&full_description=true");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() == 200) {
                Scanner sc = new Scanner(url.openStream());
                String temp = "";
                while (sc.hasNext()) {
                    temp = temp + sc.nextLine();
                }
                Utilities ut = new Utilities();
                JSONObject json = new JSONObject(temp);
                result = json.getJSONObject("result");
                JSONArray jsonArr = json.getJSONObject("result").getJSONArray("projects");

                ArrayList<Freelancelot> projects = new ArrayList<Freelancelot>();
                Map<String, Integer> frequencyMap = new HashMap<String, Integer>();
                for(int i = 0; i < jsonArr.length(); i++){
                    Integer project_ID = jsonArr.getJSONObject(i).getInt("id");
                    String project_title = jsonArr.getJSONObject(i).getString("title");
                    String project_Description = jsonArr.getJSONObject(i).getString("preview_description");
                    String owner_ID = String.valueOf(jsonArr.getJSONObject(i).getInt("owner_id"));
                    Integer date = jsonArr.getJSONObject(i).getInt("time_submitted");
                    String converted_date = ut.date_converter(date);
                    String project_type = jsonArr.getJSONObject(i).getString("type");
                    String seoUrl = jsonArr.getJSONObject(i).getString("seo_url");
                    String[] skillarr = seoUrl.split("/");
                    String skills = skillarr[0];
                    String seoUrl1 = skillarr[1];
                    preview_description += project_Description;
                    proj_det = new Freelancelot(owner_ID, converted_date,project_ID, project_title, project_Description, project_type, skills, "stats", 1L, "",seoUrl1,0," ");
                    projects.add(proj_det);
                }
                projectList.setProjectList(projects);
                OwnerProjects_active.put(ownerId,projectList);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OwnerProjects_active;
    }

}
