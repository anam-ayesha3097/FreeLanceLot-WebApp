package models;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;
/**
 * Gets the Data from FreeLancelot API using Completable Future
 *
 * @author Anam Ayesha Shaikh and Sankeerth Koduri
 * @version 1.0
 */
public class BusinessLogic {

    //BusinessLogic businessLogic = new BusinessLogic();
    public static LinkedHashMap<String, FreelaancelotList> projects_active= new LinkedHashMap<String, FreelaancelotList>();
    public static LinkedHashMap<String, FreelaancelotList> skills_active= new LinkedHashMap<String, FreelaancelotList>();
    public  static String API = "https://www.freelancer.com/api/projects/0.1/projects/active?job_details=true&limit=250&preview_description=true&query=";
    public  static Freelancelot proj_det = null;
    public  static String preview_description = "";
    static JSONObject result;
    /**
     * Gets the Data from FreeLancelot API using Completable Future as Asynchronous Non-Blocking Code
     * @param searchTerm Search Value String
     * @return Completable Future LinkedHashMap of the Freelancelot Structure
     * @author Anam Ayesha Shaikh and Sankeerth Koduri
     */
    public CompletableFuture<LinkedHashMap<String, FreelaancelotList>> getAPIDataAsync(String searchTerm )
    {
        return CompletableFuture.supplyAsync(() -> getData(searchTerm) );
    }

    /**
     * Gets the Data from FreeLancelot API
     * @param searchTerm Search Value String
     * @return LinkedHashMap of the Freelancelot Structure
     * @author Anam Ayesha Shaikh and Sankeerth Koduri
     */
    public static LinkedHashMap<String, FreelaancelotList> getData(String searchTerm ) {
        String [] s= searchTerm.split(" ");
        String string = "\"";
        for(int i =0;i<s.length;i++){
            string += s[i]+"%20";
        }
        string += "\"";
        FreelaancelotList projectList = new FreelaancelotList();
        try {
            URL url = new URL(API+ string);
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
                    System.out.println("Skills "+skills);
                    String seoUrl1 = skillarr[1];
                    preview_description += project_Description;
                    proj_det = new Freelancelot(owner_ID, converted_date,project_ID, project_title, project_Description, project_type, skills, "stats", 0L, "",seoUrl1,0,"");
                    projects.add(proj_det);
                }
                projectList.setProjectList(projects);
                projects_active.put(searchTerm,projectList);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projects_active;
    }
}
