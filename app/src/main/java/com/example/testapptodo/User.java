package com.example.testapptodo;

/**
 * Created by Jacob on 2/27/2016.
 */
public class User {
    @com.google.gson.annotations.SerializedName("email")
    private String mEmail;

    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    @com.google.gson.annotations.SerializedName("gender")
    private String mGender;

    @com.google.gson.annotations.SerializedName("username")
    private String mUsername;

    @com.google.gson.annotations.SerializedName("weight")
    private int mWeight;

    @com.google.gson.annotations.SerializedName("height")
    private int mHeight;

    @com.google.gson.annotations.SerializedName("age")
    private int mAge;

    public User() {

    }

    public User(String id, String email, String username, String gender, int weight, int height, int age) {
        this.setmId(id);
        this.setmEmail(email);
        this.setmUsername(username);
        this.setmGender(gender);
        this.setmWeight(weight);
        this.setmHeight(height);
        this.setmAge(age);
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public int getmWeight() {
        return mWeight;
    }

    public void setmWeight(int mWeight) {
        this.mWeight = mWeight;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public int getmAge() {
        return mAge;
    }

    public void setmAge(int mAge) {
        this.mAge = mAge;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof User && ((User) o).mId == mId;
    }
}