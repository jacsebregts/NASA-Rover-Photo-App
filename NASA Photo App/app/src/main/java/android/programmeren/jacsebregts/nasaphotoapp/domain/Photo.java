package android.programmeren.jacsebregts.nasaphotoapp.domain;

import android.util.Log;

import java.io.Serializable;

public class Photo implements Serializable {

    private static final String TAG = "Photo";

    private int ID;
    private String name;
    private String fullName;
    private String imageURL;
    private String earthDate;

    public Photo(int ID, String name, String fullName, String imageURL, String earthDate) {
        this.ID = ID;
        this.name = name;
        this.fullName = fullName;
        this.imageURL = imageURL;
        this.earthDate = earthDate;
    }

    public static String getTAG() {
        return TAG;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getEarthDate() {
        return earthDate;
    }

    public void setEarthDate(String earthDate) {
        this.earthDate = earthDate;
    }
}
