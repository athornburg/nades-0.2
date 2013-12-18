package com.cwat.nades.app;

import org.ektorp.support.CouchDbDocument;

/**
 * User: alexthornburg
 * Date: 12/2/13
 * Time: 6:05 PM
 */
public class MineDAO extends CouchDbDocument {
    private int x;
    private int y;
    private String username;

    public MineDAO(){

    }

    public void setX(int x){
       this.x = x;
    }

    public int getX(){
        return this.x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getY(){
        return this.y;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }
}
