package com.cwat.util;

/**
 * Created by alexthornburg on 12/9/13.
 */
public class UserDAO {
    private String username;
    private String password;
    private String gcmID;
    private int points;

    public UserDAO(){
    }

    private String id;

    private String revision;

    public void setId(String s) {
        id = s;
    }

    public String getId() {
        return id;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getGcmID(){
        return this.gcmID;
    }

    public void setGcmID(String gcmID){
        this.gcmID = gcmID;
    }

    public int getPoints(){
        return this.points;
    }

    public void setPoints(int points){
        this.points = points;
    }


}

