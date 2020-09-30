package com.example.ngo;

import android.os.Message;

public class Messages {
    //Model Class
    String name,imageURL;
    //Creating default constructors

    public Messages(String name, String imageURL) {
        this.name = name;
        this.imageURL = imageURL;
    }
    //Empty Constructor
    public Messages(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
