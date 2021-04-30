package com.example.asus.myproject.Model;

import com.google.gson.annotations.SerializedName;

public class CompanyInfo {
    @SerializedName("company_name")
    String company_name;
    String address;
    String category;
    String  web_link;

    public CompanyInfo(String company_name, String address, String category, String web_link) {
        this.company_name = company_name;
        this.address = address;
        this.category = category;
        this.web_link = web_link;
    }

    public String getcompany_name() {
        return company_name;
    }

    public void setcompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWeb_link() {
        return web_link;
    }

    public void setWeb_link(String web_link) {
        this.web_link = web_link;
    }
}
