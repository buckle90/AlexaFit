package com.example.testapptodo;

/**
 * Created by Jacob on 2/27/2016.
 */
public class Plan {

    @com.google.gson.annotations.SerializedName("id")
    private String mId;

    @com.google.gson.annotations.SerializedName("pushUpSets")
    private int mPushUpSets;

    @com.google.gson.annotations.SerializedName("pushUpReps")
    private int mPushUpReps;

    @com.google.gson.annotations.SerializedName("sitUpSets")
    private int mSitUpSets;

    @com.google.gson.annotations.SerializedName("sitUpReps")
    private int mSitUpReps;

    @com.google.gson.annotations.SerializedName("squatSets")
    private int mSquatSets;

    @com.google.gson.annotations.SerializedName("squatReps")
    private int mSquatReps;

    @com.google.gson.annotations.SerializedName("runningTime")
    private int mRunningTime;

    @com.google.gson.annotations.SerializedName("restTime")
    private int mRestTime;

    @com.google.gson.annotations.SerializedName("userId")
    private String mUserId;

    @com.google.gson.annotations.SerializedName("name")
    private String mName;




    public Plan(String mName, int mPushUpSets, int mPushUpReps, int mSitUpSets, int mSitUpReps, int mSquatSets, int mSquatReps, int mRunningTime, int mRestTime, String mUserId) {
        this.mPushUpSets = mPushUpSets;
        this.mPushUpReps = mPushUpReps;
        this.mSitUpSets = mSitUpSets;
        this.mSitUpReps = mSitUpReps;
        this.mSquatSets = mSquatSets;
        this.mSquatReps = mSquatReps;
        this.mRunningTime = mRunningTime;
        this.mRestTime = mRestTime;
        this.mUserId = mUserId;
        this.mName = mName;
    }

    public Plan() {

    }

    public String getmName() {return mName;}

    public void setmName(String mName) {this.mName = mName;}

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public int getmPushUpSets() {
        return mPushUpSets;
    }

    public void setmPushUpSets(int mPushUpSets) {
        this.mPushUpSets = mPushUpSets;
    }

    public int getmPushUpReps() {
        return mPushUpReps;
    }

    public void setmPushUpReps(int mPushUpReps) {
        this.mPushUpReps = mPushUpReps;
    }

    public int getmSitUpSets() {
        return mSitUpSets;
    }

    public void setmSitUpSets(int mSitUpSets) {
        this.mSitUpSets = mSitUpSets;
    }

    public int getmSitUpReps() {
        return mSitUpReps;
    }

    public void setmSitUpReps(int mSitUpReps) {
        this.mSitUpReps = mSitUpReps;
    }

    public int getmSquatSets() {
        return mSquatSets;
    }

    public void setmSquatSets(int mSquatSets) {
        this.mSquatSets = mSquatSets;
    }

    public int getmSquatReps() {
        return mSquatReps;
    }

    public void setmSquatReps(int mSquatReps) {
        this.mSquatReps = mSquatReps;
    }

    public int getmRunningTime() {
        return mRunningTime;
    }

    public void setmRunningTime(int mRunningTime) {
        this.mRunningTime = mRunningTime;
    }

    public int getmRestTime() {
        return mRestTime;
    }

    public void setmRestTime(int mRestTime) {
        this.mRestTime = mRestTime;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }
}
