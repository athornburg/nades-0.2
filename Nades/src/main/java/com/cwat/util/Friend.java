package com.cwat.util;

/**
 * @author Alex Thornburg and Cooper Wickum
 */
public class Friend {
    private String username;
    private String id;
    private String location;

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return username + " " + id;
    }
}
