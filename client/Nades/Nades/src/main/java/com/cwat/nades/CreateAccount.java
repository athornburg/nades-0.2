package com.cwat.nades;

/**
 * Created by alexthornburg on 12/8/13.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cwat.util.UniversalHTTP;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class CreateAccount extends Activity {
    JSONObject results;
    Button submit;
    EditText username;
    EditText password;
    private RadioGroup radioLoginGroup;
    Button login;
    Context numberContext;
    String server = "http://nades-game.elasticbeanstalk.com/";
    public static final String EXTRA_MESSAGE = "message";
    private static final String PROPERTY_APP_VERSION = "0.1";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    String usernameText;
    String passwordText;
    Toast toast;
    String usernameTaken;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        results = new JSONObject();
        radioLoginGroup = (RadioGroup)findViewById(R.id.radioLogin);
        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                usernameText = username.getText().toString();
                passwordText = password.getText().toString();
                int selectedId = radioLoginGroup.getCheckedRadioButtonId();
                String id = Integer.toString(selectedId);
                login = (RadioButton) findViewById(selectedId);
                if(login.getText().equals("Create Account")){

                UniversalHTTP check = new UniversalHTTP();
                try {
                    results = check.execute(server+"get_user/"+usernameText+"/").get();
                    usernameTaken=results.getString("_id");
                    Toast.makeText(getApplicationContext(),usernameTaken,Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    Toast error = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                    error.show();
                } catch (ExecutionException e) {
                    Toast error1 = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                    error1.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                    if(!usernameTaken.equals("safe")){
                    toast = Toast.makeText(getApplicationContext(), "Username Taken! Try Another", Toast.LENGTH_LONG);
                    toast.show();
                }else {
                    if(passwordText.equals("")){
                        Toast enterPassword = Toast.makeText(getApplicationContext(), "EnterPassword", Toast.LENGTH_LONG);
                        enterPassword.show();
                    }else{
                        UniversalHTTP add = new UniversalHTTP();
                        try {
                            results = add.execute(server+"add_user/"+usernameText+"/"+passwordText+"/"+"nothing/").get();
                        } catch (InterruptedException e) {
                            Toast error = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                            error.show();
                        } catch (ExecutionException e) {
                            Toast error1 = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                            error1.show();
                        }
                        numberContext = getApplicationContext();
                        SharedPreferences.Editor editor = getSharedPreferences("Login",0).edit();
                        editor.putString("password", passwordText);
                        editor.putString("username", usernameText);
                        editor.commit();
                        Intent i = new Intent(CreateAccount.this,MainActivity.class);
                        startActivity(i);
                    }
                }
            }
            }

        });
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_account, menu);
        return true;
    }


}
