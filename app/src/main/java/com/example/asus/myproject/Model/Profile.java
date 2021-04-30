package com.example.asus.myproject.Model;

import com.google.gson.annotations.SerializedName;

public class Profile {
    @SerializedName("id")
    private int id;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("user_type")
    private Boolean user_type;
    @SerializedName("password")
    private String password;

    public Profile(int id, String email, String name, Boolean user_type, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.user_type = user_type;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getUser_type() {
        return user_type;
    }

    public void setUser_type(Boolean user_type) {
        this.user_type = user_type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
