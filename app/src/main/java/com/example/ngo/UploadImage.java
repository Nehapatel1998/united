package com.example.ngo;

public class UploadImage {
    private String Prof_name,prof_image;
    public UploadImage(){}

    public String getProf_name() {
        return Prof_name;
    }

    public void setProf_name(String prof_name) {
        this.Prof_name = prof_name;
    }

    public String getProf_image() {
        return prof_image;
    }

    public void setProf_image(String prof_image) {
        this.prof_image = prof_image;
    }

    public UploadImage(String Prof_name, String prof_image) {
        this.Prof_name = Prof_name;
        this.prof_image = prof_image;
    }
}
