package com.cwat.nades;

/**
 * Created by alexthornburg on 12/8/13.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.cwat.util.UniversalHTTP;
import com.cwat.util.UserDAO;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class AddData extends Activity {
    String username;
    String password;
    String phone;
    String server = "http://nades-game.elasticbeanstalk.com/add_user/";

    JSONObject results;
    String success;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        Intent intent = getIntent();
        username = intent.getStringExtra("sendUsername");
        password = intent.getStringExtra("sendPassword");
        phone = intent.getStringExtra("sendPhone");
        UserDAO sendResults;
        try {
            UniversalHTTP send = new UniversalHTTP();
            sendResults = send.execute(server+username+"/"+password+"/"+phone+"/").get();
            Intent joinIntent = new Intent(AddData.this,MainActivity.class);
            startActivity(joinIntent);
        } catch (InterruptedException e) {
            Toast error = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
            error.show();
        } catch (ExecutionException e) {
            Toast error1 = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
            error1.show();
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.join_game, menu);
        return true;
    }
    public void onResume(){
        super.onResume();
        Intent intent = new Intent(AddData.this,Loading.class);
        startActivity(intent);
    }

}
