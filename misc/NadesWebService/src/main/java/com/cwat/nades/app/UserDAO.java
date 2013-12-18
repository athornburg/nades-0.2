package com.cwat.nades.app;

import org.ektorp.support.CouchDbDocument;
import org.codehaus.jackson.annotate.JsonWriteNullProperties;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
/**
 * User: alexthornburg
 * Date: 12/2/13
 * Time: 5:06 PM
 */


@JsonWriteNullProperties(false)
@JsonIgnoreProperties({"id", "revision"})
public class UserDAO extends CouchDbDocument{
    private String username;
    private String password;
    private String gcmID;
    private int points;

    public UserDAO(){
    }

    @JsonProperty("_id")
    private String id;

    @JsonProperty("_rev")
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
