package controllers;

import models.*;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.mvc.Results.ok;
import static play.test.Helpers.*;

/**
 * This is the Test Class of Home controller which contains an action to handle HTTP requests
 * to the application's home page.
 * @author Sankeerth Koduri Anam Ayesha Shaikh Raahul John Vikyath
 * @version 1.0
 */


public class HomeControllerTest extends WithApplication {
    HomeController hm = new HomeController();
    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }


    @Test
    public void testIndex() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");

        Result result = route(app, request);
        assertEquals(200, result.status());
    }
    /**
     * Tests the Freelancelot API and displays the values in the views
     * @return Renders Freelancelot Job Search Page with the Jobs output
     * @author Anam Ayesha Shaikh and Sankeerth Korduri
     */
    @Test
    public void testFreelance() throws ExecutionException, InterruptedException {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/freelance?searchTerm=java");

        Result result = route(app, request);
        assertEquals(OK, result.status());
        Result r1,r2;
        r1 = ok(views.html.freelancer.render(null));
        r2 =hm.freelancer("");
        assertEquals(contentAsString(r1),contentAsString(r2));
    }

    /**
     * Tests the top 10 related skills of the job seacrhed
     * @return Renders the skills HTML page
     * @author Raahul John
     */
    @Test
    public void testskills(){
        Result r1,r2;
        r1 = ok(views.html.skills.render(skills.getDataSkills("php")));
        r2 = hm.skills("php");
        assertEquals(contentAsString(r1),contentAsString(r2));
    }

    /**
     * Tests the Global Word Statistics
     * @return Renders the Word Statistice Page
     * @author Sankeerth Koduri
     */
    @Test
    public void testprojectWordStats(){
        Result r1,r2;
        Utilities ut = new Utilities();
        r1 = ok(views.html.projectwordstats.render(ut.wordFrequencyCounter("php")));
        r2 = hm.projectWordStats("php");
        assertEquals(contentAsString(r1),contentAsString(r2));

    }

    /**
     * Calculates the Individual Word Statistics
     * @return Renders the Word Statistice Page
     * @author Sankeerth Koduri
     */
    @Test
    public void testWordStats(){
        Result r1,r2;
        Utilities ut = new Utilities();
        WordStats ws = new WordStats();
        Freelancelot f = new Freelancelot("1","",1,"","This is a test case to check the word stats","","","",1L,"","",0,"");
        FreelaancelotList fl = new FreelaancelotList();
        ArrayList<Freelancelot> fa = new ArrayList<Freelancelot>();
        fa.add(f);
        fl.setProjectList(fa);
        r1 = ok(views.html.wordstats.render(ws.GlobalStats(fl)));
        assertNotNull(r1);

    }
}