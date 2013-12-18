package com.cwat.nades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cwat.util.UniversalHTTP;
import com.cwat.util.UserDAO;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class Loading extends Activity {

    ProgressBar spinner;
    String server= "http://nades-game.elasticbeanstalk.com/check_credentials/";
    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    String SENDER_ID = "717235139560";
    static final String TAG = "Nades";

    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;
    Context context;

    String regid;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        setContentView(R.layout.activity_loading_screen);
        context = getApplicationContext();
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            if (regid.isEmpty()) {
                registerInBackground();
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }

        connectAndQuery();

    }
    @Override
    public void onResume(){
        super.onResume();
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    public void connectAndQuery(){

        if(!getSharedPreferences("Login",1).contains("password")||
                !getSharedPreferences("Login",1).contains("username")){
            Intent intent = new Intent(Loading.this,JoinOrSignUp.class);
            startActivity(intent);
        }else{
            UserDAO sendResults = new UserDAO();

            String sent;
            try {
                UniversalHTTP send = new UniversalHTTP();
                sendResults = send.execute(server + getSharedPreferences("Login", 1).getString("username", "") + "/" +
                        getSharedPreferences("Login", 1).getString("password", "") + "/").get();
                if(sendResults.getPassword().equals(getSharedPreferences("Login", 1).getString("password", ""))){
                    Intent intent = new Intent(Loading.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Loading.this,JoinOrSignUp.class);
                    startActivity(intent);
                }
            } catch (InterruptedException e) {
                Toast error = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                error.show();
            } catch (ExecutionException e) {
                Toast error1 = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                error1.show();
            }
        }
    }

    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
            }
        }.execute(null, null, null);
    }

    private SharedPreferences getGCMPreferences(Context context) {
        return getSharedPreferences(Loading.class.getSimpleName(),
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

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
