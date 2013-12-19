package com.cwat.util;

import android.content.SharedPreferences;

/**
 * Created by alexthornburg on 12/12/13.
 */
public class SharedPrefsManager {

    SharedPreferences sharedPrefs;

    public SharedPrefsManager(SharedPreferences sharedPrefs){
        this.sharedPrefs = sharedPrefs;

    }

    public void storeUsernameAndPassword(String username, String password){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }

    public String getUsername(){
        return sharedPrefs.getString("username","");
    }

    public String getPassword(){
        return sharedPrefs.getString("password","");
    }


    public String getPoints(){
        return sharedPrefs.getString("points","points");
    }

    public void storePoints(String s, String points) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("points",points);
    }
}
