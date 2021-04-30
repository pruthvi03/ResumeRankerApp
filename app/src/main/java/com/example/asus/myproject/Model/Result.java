package com.example.asus.myproject.Model;


import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("candidate")
    String name;
    @SerializedName("marks")
    int marks;

    public Result(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
