package com.example.asus.myproject.Model;

public class VacancyData {
    int id;
    String companyName;
    int vacancyCount;

    public VacancyData(int id, String companyName, int vacancyCount) {
        this.id = id;
        this.companyName = companyName;
        this.vacancyCount = vacancyCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getVacancyCount() {
        return vacancyCount;
    }

    public void setVacancyCount(int vacancyCount) {
        this.vacancyCount = vacancyCount;
    }
}
