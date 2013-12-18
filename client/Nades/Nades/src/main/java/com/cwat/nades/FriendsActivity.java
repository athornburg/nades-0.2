package com.cwat.nades;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cwat.util.FriendsDataDistributor;

/**
 * Created by alexthornburg on 12/8/13.
 */
public class FriendsActivity extends Activity {

    Button sendRequest;
    EditText username;
    String server = "http://nades-game.elasticbeanstalk.com/";
    String gcmID;
    ListView list;
    FriendsDataDistributor database;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        list = (ListView) findViewById(R.id.friends_list);
        database = new FriendsDataDistributor(getApplicationContext());
        database.open();
        ArrayList<String> friendInfo = new ArrayList<String>();
        if(!database.getAllFriends().isEmpty()){
            for(Friend f : database.getAllFriends()){
                String[] username = f.getUsername().split(" ", 2);
                String friend= username[0];
                friendInfo.add(friend);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, friendInfo);
            list.setAdapter(adapter);
            database.close();
        }
        sendRequest = (Button)findViewById(R.id.add_friend);
        username = (EditText)findViewById(R.id.friend_username);
        sendRequest.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View arg0) {
                String friend = username.getText().toString();
                UniversalHTTP getID = new UniversalHTTP();
                try{
                    UserDAO response = getID.execute(server+"getID/"+friend+"/").get();
                    gcmID = response.getGcmID();
                    Toast toast = Toast.makeText(getApplicationContext(), gcmID, Toast.LENGTH_LONG);
                    toast.show();
                    getID.cancel(true);
                }catch(Exception e){
                    Log.i("Json Error", "Friend Request Error");
                }
                UniversalHTTP sendRequest = new UniversalHTTP();
                try{
                    UserDAO success = sendRequest.execute(server + "send_message/"+gcmID+"/"+getSharedPreferences("Login",1).getString("username", "")+" has sent you a friend request! Click here to respond."+"/").get();
                    Toast toast = Toast.makeText(getApplicationContext(),success.getGcmID(),Toast.LENGTH_LONG);
                    toast.show();
                }catch(Exception e){
                    Log.i("Request error","Request not sent");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //No menu here
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        database = new FriendsDataDistributor(getApplicationContext());
        database.open();
        ArrayList<String> friendInfo = new ArrayList<String>();
        if(!database.getAllFriends().isEmpty()){
            for(Friend f : database.getAllFriends()){
                friendInfo.add(f.getUsername());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, friendInfo);
            list.setAdapter(adapter);
        }
        database.close();
    }*/

}
