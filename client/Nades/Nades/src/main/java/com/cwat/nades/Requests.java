package com.cwat.nades;

/**
 * Created by alexthornburg on 12/8/13.
 */

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cwat.util.FriendsDataDistributor;
import com.cwat.util.UniversalHTTP;
import com.cwat.util.UserDAO;

import java.util.ArrayList;

public class Requests extends Activity {
    ListView list;
    String server = "http://nades-game.elasticbeanstalk.com/";
    String gcmID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        list = (ListView)findViewById(R.id.requests_lists);
        if(!getSharedPreferences("messages",1).getString("message","").equals("")){
            ArrayList<String> messageList = new ArrayList<String>();
            messageList.add(getSharedPreferences("messages",1).getString("message",""));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, messageList);
            list.setAdapter(adapter);
        }
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String message = getSharedPreferences("messages",1).getString("message","");
                String [] splitMessage = message.split(" ",2);
                String username = splitMessage[0];
                Toast debugtoast = Toast.makeText(getApplicationContext(), username, Toast.LENGTH_LONG);
                debugtoast.show();
                UniversalHTTP getID = new UniversalHTTP();
                try{
                    UserDAO array = getID.execute(server+"getID/"+username+"/").get();
                    gcmID = array.getGcmID();
                    getID.cancel(true);
                }catch(Exception e){
                    Toast toast = Toast.makeText(getApplicationContext(), "Error getting ID", Toast.LENGTH_LONG);
                    toast.show();
                }
                UniversalHTTP sendResponse = new UniversalHTTP();
                try{
                    UserDAO response = sendResponse.execute(server+"gcmMessage/"+gcmID+"/"+getSharedPreferences("Login",1).getString("username", "")+" has accepted your friend request."+"/").get();
                    Editor edit = getSharedPreferences("messages",1).edit();
                    edit.remove("message");
                    edit.commit();
                    Toast toast = Toast.makeText(getApplicationContext(),response.getGcmID(),Toast.LENGTH_LONG);
                    toast.show();
                    FriendsDataDistributor database = new FriendsDataDistributor(getApplicationContext());
                    database.open();
                    if(!database.containsFriend(username)){
                        database.createFriend(username,gcmID);
                    }else{
                        Toast toast2 = Toast.makeText(getApplicationContext(), "Already Friends", Toast.LENGTH_LONG);
                        toast2.show();
                    }
                }catch(Exception e){
                    Toast toast = Toast.makeText(getApplicationContext(), "Could not send friend response", Toast.LENGTH_LONG);
                    toast.show();
                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_requests, menu);
        return true;
    }

}
