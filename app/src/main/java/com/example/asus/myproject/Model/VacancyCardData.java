package com.example.asus.myproject.Model;

public class VacancyCardData {
    int id;
    String str_position,str_location,str_skills,str_cata,str_lda,str_title,str_comp_name;
    int int_salary,int_vaca,int_exp;

    public VacancyCardData(int id, String str_position, String str_location, String str_skills, String str_cata, String str_lda, String str_title, String str_comp_name, int int_salary, int int_vaca, int int_exp) {
        this.id = id;
        this.str_position = str_position;
        this.str_location = str_location;
        this.str_skills = str_skills;
        this.str_cata = str_cata;
        this.str_lda = str_lda;
        this.str_title = str_title;
        this.str_comp_name = str_comp_name;
        this.int_salary = int_salary;
        this.int_vaca = int_vaca;
        this.int_exp = int_exp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStr_position() {
        return str_position;
    }

    public void setStr_position(String str_position) {
        this.str_position = str_position;
    }

    public String getStr_location() {
        return str_location;
    }

    public void setStr_location(String str_location) {
        this.str_location = str_location;
    }

    public String getStr_skills() {
        return str_skills;
    }

    public void setStr_skills(String str_skills) {
        this.str_skills = str_skills;
    }

    public String getStr_cata() {
        return str_cata;
    }

    public void setStr_cata(String str_cata) {
        this.str_cata = str_cata;
    }

    public String getStr_lda() {
        return str_lda;
    }

    public void setStr_lda(String str_lda) {
        this.str_lda = str_lda;
    }

    public String getStr_title() {
        return str_title;
    }

    public void setStr_title(String str_title) {
        this.str_title = str_title;
    }

    public String getStr_comp_name() {
        return str_comp_name;
    }

    public void setStr_comp_name(String str_comp_name) {
        this.str_comp_name = str_comp_name;
    }

    public int getInt_salary() {
        return int_salary;
    }

    public void setInt_salary(int int_salary) {
        this.int_salary = int_salary;
    }

    public int getInt_vaca() {
        return int_vaca;
    }

    public void setInt_vaca(int int_vaca) {
        this.int_vaca = int_vaca;
    }

    public int getInt_exp() {
        return int_exp;
    }

    public void setInt_exp(int int_exp) {
        this.int_exp = int_exp;
    }
}
