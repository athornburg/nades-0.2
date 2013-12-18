package com.cwat.nades;

/**
 * Created by alexthornburg on 12/8/13.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cwat.util.UniversalHTTP;
import com.cwat.util.UserDAO;

import java.util.concurrent.ExecutionException;

public class Login extends Activity {
    EditText username;
    EditText password;
    Button login;
    String server= "http://nades-game.elasticbeanstalk.com/check_credentials/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.submit);
        login.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();
                UserDAO sendResults = new UserDAO();
                try {
                    UniversalHTTP send = new UniversalHTTP();
                    sendResults = send.execute(server+usernameText+"/"+passwordText+"/").get();
                    send.cancel(true);
                    if(sendResults.getPassword().equals(passwordText)){
                        Editor editor = getSharedPreferences("Login",0).edit();
                        editor.putString("password", passwordText);
                        editor.putString("username", usernameText);
                        editor.commit();
                        Intent joinIntent = new Intent(Login.this,MainActivity.class);
                        startActivity(joinIntent);
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(), "Invalid. Try Again!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                } catch (InterruptedException e) {
                    Toast error = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                    error.show();
                } catch (ExecutionException e) {
                    Toast error1 = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                    error1.show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

}
