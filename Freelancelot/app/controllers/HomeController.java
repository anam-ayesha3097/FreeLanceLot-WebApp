package controllers;
import models.*;
import play.mvc.Controller;
import play.mvc.Result;
import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * This is the Main controller contains an action to handle HTTP requests
 * to the application's home page.
 * @author Sankeerth Koduri Anam Ayesha Shaikh Raahul John Vikyath
 * @version 1.0
 */


public class HomeController extends Controller {
    LinkedHashMap<String, FreelaancelotList> response = new LinkedHashMap<>();
    int flagCount = 0;
    private LinkedHashMap<String, FreelaancelotList> cache;
    OwnerDetails ownerDetails = new OwnerDetails();
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */

    public HomeController(){
        System.out.println("Default Home Controller");
    }
    /**
     * Constructor Initializes the Cache
     * @param cache Canche Object
     * @author Sankeerth Koduri
     */
    @Inject
    public HomeController( LinkedHashMap<String, FreelaancelotList> cache) {
        this.cache = cache;
    }


    /**
     * Calculates the Global Word Statistics
     * @param search Search Term Job Searched
     * @return Renders the Word Statistice Page
     * @author Sankeerth Koduri
     */
    public Result projectWordStats(String search)
    {
            Utilities ut = new Utilities();
            return ok(views.html.projectwordstats.render(ut.wordFrequencyCounter(search)));
    }

    /**
     * Calculates the Individual Word Statistics
     * @param search Search Term Job Searched
     * @return Renders the Word Statistice Page
     * @author Sankeerth Koduri
     */
    public Result wordStats(String search)
     {
         WordStats ws = new WordStats();
         return ok(views.html.wordstats.render(ws.GlobalStats(response.get(search))));
    }

    /**
     * Calls the Freelancelot API and displays the values in the views
     * @param searchTerm Job Searched
     * @return Renders Freelancelot Job Search Page with the Jobs output
     * @author Anam Ayesha Shaikh and Sankeerth Korduri
     */
    public Result freelancer(String searchTerm) throws ExecutionException, InterruptedException {
        LinkedHashMap<String, FreelaancelotList> projlistmap_10Projs = new LinkedHashMap<String, FreelaancelotList>();
        LinkedHashMap<String, FreelaancelotList> responseProcessed = new LinkedHashMap<>();

        int flag = 1;
        HomeController responseCache = null;
        BusinessLogic businessLogic = new BusinessLogic();
        Utilities ut = new Utilities();

        if(searchTerm.length() != 0 ) {
            flagCount += 1;
            if(flagCount <= 10 ){
                System.out.println("Flag Count "+flagCount);
                if (flag == 1) {
                    CompletableFuture<LinkedHashMap<String, FreelaancelotList>> futureAPIResult = businessLogic.getAPIDataAsync(searchTerm);
                    response = futureAPIResult.get();

                    responseCache = new HomeController(response);
                    flag += 1;

                }

                if (responseCache != null && responseCache.cache.containsKey(searchTerm)) {

                    projlistmap_10Projs = ut.first10projects(responseCache.cache);
                    CompletableFuture<LinkedHashMap<String, FreelaancelotList>> readability = FleschReadabilityIndex.getReadabilityAsync(projlistmap_10Projs);
                    LinkedHashMap<String, FreelaancelotList> responseReadability = readability.get();

                    responseProcessed = reverseOrder(responseReadability);
                }

                else {
                    CompletableFuture<LinkedHashMap<String, FreelaancelotList>> futureAPIResult = businessLogic.getAPIDataAsync(searchTerm);
                    response = futureAPIResult.get();

                    responseCache = new HomeController(response);

                    projlistmap_10Projs = ut.first10projects(responseCache.cache);

                    CompletableFuture<LinkedHashMap<String, FreelaancelotList>> readability = FleschReadabilityIndex.getReadabilityAsync(projlistmap_10Projs);
                    LinkedHashMap<String, FreelaancelotList> responseReadability = readability.get();

                    responseProcessed = reverseOrder(responseReadability);

                }

                return ok(views.html.freelancer.render(responseProcessed));
            }
            else
                return ok("Maximum Search Hit Reached").as("Text/html");
        }
        else {
            if(searchTerm.length() == 0)
            {
                response.clear();

                System.out.println("Bye!");
            }

            return ok(views.html.freelancerInit.render());
        }
        }

    /**
     * Displays the top 10 related skills of the job seacrhed
     * @param s String which takes the job searched term
     * @return Renders the skills HTML page
     * @author Raahul John
     */
    public Result skills(String s) {
        skills sl = new skills();
        LinkedHashMap<String, FreelaancelotList> data = sl.getDataSkills(s);
        return ok(views.html.skills.render(data));
    }

    /**
     * Generates the Response in Reverse Order
     * @param responseReadability Response of the API
     * @return Inverted Response of the API
     * @author Anam Ayesha Shaikh
     */
    public static LinkedHashMap<String, FreelaancelotList> reverseOrder(LinkedHashMap<String, FreelaancelotList> responseReadability) {
        LinkedHashMap<String, FreelaancelotList> response = new LinkedHashMap<>();
        List<String> allKeys = new ArrayList<String>(responseReadability.keySet());
        ArrayList<Freelancelot>  freelancelotArrayList= new ArrayList<Freelancelot>();
        Freelancelot freelancelotObj = null;
        Collections.reverse(allKeys);

        for(String keys: allKeys){
            System.out.println("Keys "+keys);
            freelancelotArrayList = responseReadability.get(keys).getProjectList();
            ArrayList<Freelancelot> listFreelancelot = new ArrayList<>();

            for(Freelancelot fl : freelancelotArrayList) {
                freelancelotObj = new Freelancelot(fl.getOwner_id(), fl.getDate(), fl.getProject_ID(), fl.getProject_title(), fl.getProject_description(), fl.getProject_type(), fl.getSkills(), "", fl.getReadability(), fl.getEducationalLevel(), fl.getSeoUrl(), 0, " ");
                listFreelancelot.add(freelancelotObj);

            }

            FreelaancelotList setListObjs = new FreelaancelotList();
            setListObjs.setProjectList(listFreelancelot);
            response.put(keys, setListObjs);
        }
        return response;
    }

    public Result ownerInformation(String ownerId)
    {
        return ok(views.html.ownerDetails.render(ownerDetails.getOwnerDetails(ownerId)));
    }

}

