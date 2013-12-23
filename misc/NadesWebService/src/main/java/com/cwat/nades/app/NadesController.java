package com.cwat.nades.app;

import com.google.gson.Gson;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;




@Controller
public class NadesController {
    private static final String SENDER_ID = "AIzaSyD_5IrsNvP8PlCZhRyBCEnfiJuPhv2kWMU";
    private List<String> androidTargets = new ArrayList<String>();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "index";
    }
    //add user
    @RequestMapping(value = "/add_user/{username}/{password}/{gcmID}/", method = RequestMethod.GET,produces = "application/json")
    public @ResponseBody UserDAO username(ModelMap model,@PathVariable String username,@PathVariable String password,@PathVariable String gcmID) throws Exception {
        UserDAO user = new UserDAO();
        user.setId(username);
        user.setUsername(username);
        user.setPassword(password);
        user.setGcmID(gcmID);
        user.setPoints(0);
        user.setPoints(0);
        HttpClient authenticatedHttpClient = new StdHttpClient.Builder()
                .url("http://nades.cloudant.com:5984")
                .username("nades")
                .password("grenades")
                .build();
        CouchDbInstance dbInstance = new StdCouchDbInstance(authenticatedHttpClient);
        CouchDbConnector db = dbInstance.createConnector("users", true);
        db.create(user);
        return user;
    }
   //Add a mine
    @RequestMapping(value="/add_mine/{username}/{x}/{y}/",method = RequestMethod.GET,produces = "application/json")
    public @ResponseBody MineDAO mine(ModelMap model,@PathVariable String username,@PathVariable String x,@PathVariable String y) throws Exception {
        MineDAO mine = new MineDAO();
        mine.setId(username);
        mine.setUsername(username);
        mine.setX(Integer.parseInt(x));
        mine.setY(Integer.parseInt(y));
        HttpClient authenticatedHttpClient = new StdHttpClient.Builder()
                .url("http://nades.cloudant.com:5984")
                .username("nades")
                .password("grenades")
                .build();
        CouchDbInstance dbInstance = new StdCouchDbInstance(authenticatedHttpClient);
        CouchDbConnector db = dbInstance.createConnector("mines", true);
        db.create(mine);
        Gson gson = new Gson();
        String json = gson.toJson(mine);
        return mine;
    }

    @RequestMapping(value="/get_user/{username}",method = RequestMethod.GET,produces = "application/json")
    public @ResponseBody UserDAO checkUsername(ModelMap model,@PathVariable String username) throws Exception {
        HttpClient authenticatedHttpClient = new StdHttpClient.Builder()
                .url("http://nades.cloudant.com:5984")
                .username("nades")
                .password("grenades")
                .build();
        CouchDbInstance dbInstance = new StdCouchDbInstance(authenticatedHttpClient);
        CouchDbConnector db = dbInstance.createConnector("users", true);
        Gson gson = new Gson();
        if(!db.contains(username)){
            UserDAO user = new UserDAO();
            user.setUsername("safe");
            return user;
        }else{
            return db.get(UserDAO.class,username);
        }
    }



   /* @RequestMapping(value="gcmMessage/{message}/{gcmID}/",method = RequestMethod.GET,produces = "application/json")
    public @ResponseBody UserDAO message(ModelMap model,@PathVariable String message,@PathVariable String gcmID) throws Exception {
        Sender sender = new Sender(SENDER_ID);
        Message gcmMessage = new Message.Builder()
                .collapseKey("Testing")
                .timeToLive(30)
                .delayWhileIdle(true)
                .addData("message", message)
                .build();
        androidTargets.add(gcmID);
        try {
             MulticastResult result = sender.send(gcmMessage, androidTargets, 1);

            if (result.getResults() != null) {
                int canonicalRegId = result.getCanonicalIds();
                if (canonicalRegId != 0) {

                }
            } else {
                int error = result.getFailure();
                System.out.println("Broadcast failure: " + error);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        UserDAO user = new UserDAO();
        user.setId("success");
        Gson gson = new Gson();
        return user;
    }   */

    @RequestMapping(value="/check_credentials/{username}/{password}/",method = RequestMethod.GET,produces = "application/json")
    public @ResponseBody UserDAO checkCredentials(ModelMap model, @PathVariable String username) throws Exception {
        HttpClient authenticatedHttpClient = new StdHttpClient.Builder()
                .url("http://nades.cloudant.com:5984")
                .username("nades")
                .password("grenades")
                .build();
        CouchDbInstance dbInstance = new StdCouchDbInstance(authenticatedHttpClient);
        CouchDbConnector db = dbInstance.createConnector("users", true);
        Gson gson = new Gson();
        if(!db.contains(username)){
            UserDAO user = new UserDAO();
            user.setId("wrong");
            return user;
        }else{
            return db.get(UserDAO.class,username);
        }
    }

}