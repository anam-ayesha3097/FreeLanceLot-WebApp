package models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

import models.Utilities.*;

import static models.Utilities.date_converter;
/**
 * Displays top 10 teleated Skills of the project
 * @author Raahul John
 * @version 1.0
 */
public class skills {
    public static LinkedHashMap<String, FreelaancelotList> projects_active= new LinkedHashMap<String, FreelaancelotList>();
    public static LinkedHashMap<String, FreelaancelotList> projects_active10= new LinkedHashMap<String, FreelaancelotList>();
    public static LinkedHashMap<String, FreelaancelotList> skills_active= new LinkedHashMap<String, FreelaancelotList>();

    public static Freelancelot proj_det = null;
    public static String preview_description = "";
    static JSONObject result;
    /**
     * Calculates the Flesch Readability Index of each Project with Education Level and its Average
     * @param searchTerm String Job Searched
     * @return Freelancelotlist Linked HashMap
     * @author Raahul John
     */
    public static LinkedHashMap<String, FreelaancelotList> getDataSkills(String searchTerm ){
        String [] s= searchTerm.split(" ");
        String string = "\"";
        for(int i =0;i<s.length;i++){
            string += s[i]+"%20";
        }
        string += "\"";
        FreelaancelotList projectList = new FreelaancelotList();
        System.out.println(searchTerm);
        try {
            URL url = new URL("https://www.freelancer.com/api/projects/0.1/projects/active?job_details=true&limit=10&preview_description=true&query="+ string);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() == 200) {
                Scanner sc = new Scanner(url.openStream());
                String temp = "";
                while (sc.hasNext()) {
                    temp = temp + sc.nextLine();
                }
                System.out.println("Temp Skills "+temp);
                JSONObject json = new JSONObject(temp);
                result = json.getJSONObject("result");
                JSONArray jsonArr = json.getJSONObject("result").getJSONArray("projects");
                ArrayList<Freelancelot> projects = new ArrayList<Freelancelot>();
                for(int i = 0; i < jsonArr.length(); i++){
                    Integer project_ID = jsonArr.getJSONObject(i).getInt("id");
                    String project_title = jsonArr.getJSONObject(i).getString("title");

                    String project_Description = jsonArr.getJSONObject(i).getString("preview_description");
                    String owner_ID = String.valueOf(jsonArr.getJSONObject(i).getInt("owner_id"));
                    Integer date = jsonArr.getJSONObject(i).getInt("time_submitted");
                    String converted_date = date_converter(date);

                    String project_type = jsonArr.getJSONObject(i).getString("type");
                    String skills = jsonArr.getJSONObject(i).getString("seo_url");
                    String skillarr[] = skills.split("/");
                    skills = skillarr[0];
                    proj_det = new Freelancelot(owner_ID, converted_date,project_ID, project_title, project_Description, project_type, skills, "", 1L,"","",0,"");
                    proj_det.display();
                    projects.add(proj_det);
                }
                projectList.setProjectList(projects);
                skills_active.put(searchTerm,projectList);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skills_active;
    }
}
