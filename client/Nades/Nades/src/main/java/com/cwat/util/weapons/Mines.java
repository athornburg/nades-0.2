package com.cwat.util.weapons;

import java.io.Serializable;

/**
 * @author Alex Thornburg and Cooper Wickum
 */
public class Mines implements Serializable {

    private static final long serialVersionUID = -6641292855569752036L;

    private String title;
    private double x;
    private double y;
    private String objectId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
