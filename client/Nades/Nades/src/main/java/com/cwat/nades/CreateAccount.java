package com.cwat.nades;

/**
 * Created by alexthornburg on 12/8/13.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cwat.util.UniversalHTTP;
import com.cwat.util.UserDAO;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.concurrent.ExecutionException;

public class CreateAccount extends Activity {
    UserDAO results;
    Button submit;
    EditText username;
    EditText password;
    Context numberContext;
    String server = "http://nades-game.elasticbeanstalk.com/";
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
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
        results = new UserDAO();
        final SharedPreferences prefs = getGCMPreferences(getApplicationContext());
        phoneNumber = prefs.getString(PROPERTY_REG_ID, "");
        if(phoneNumber.equals("")){
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
            phoneNumber = getRegistrationId(getApplicationContext());
        }
        Toast.makeText(getApplicationContext(),phoneNumber, Toast.LENGTH_LONG).show();
        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                usernameText = username.getText().toString();
                passwordText = password.getText().toString();


                UniversalHTTP check = new UniversalHTTP();
                try {
                    results = check.execute(server+"get_user/"+usernameText+"/").get();
                    System.out.println("RESULTS"+results);
                    usernameTaken=results.getUsername();
                    Toast.makeText(getApplicationContext(), usernameTaken, Toast.LENGTH_LONG).show();
                    check.cancel(true);

                } catch (InterruptedException e) {
                    Toast error = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                    error.show();
                } catch (ExecutionException e) {
                    Toast error1 = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                    error1.show();
                }

                if(!usernameTaken.equals("safe")){
                    toast = Toast.makeText(getApplicationContext(), "Username Taken! Try Another", Toast.LENGTH_LONG);
                    toast.show();
                }else {
                    if(passwordText.equals("")){
                        Toast enterPassword = Toast.makeText(getApplicationContext(), "EnterPassword", Toast.LENGTH_LONG);
                        enterPassword.show();
                    }else{
                        numberContext = getApplicationContext();
                        SharedPreferences.Editor editor = getSharedPreferences("Login",0).edit();
                        editor.putString("password", passwordText);
                        editor.putString("username", usernameText);
                        editor.commit();
                    }
                    Intent intent = new Intent(CreateAccount.this,AddData.class);
                    intent.putExtra("sendUsername", usernameText);
                    intent.putExtra("sendPassword", passwordText);
                    intent.putExtra("sendPhone", phoneNumber);
                    startActivity(intent);
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

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i("error", "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i("error", "App version changed.");
            return "";
        }
        return registrationId;
    }

    private SharedPreferences getGCMPreferences(Context context) {
        return getSharedPreferences(CreateAccount.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

}
