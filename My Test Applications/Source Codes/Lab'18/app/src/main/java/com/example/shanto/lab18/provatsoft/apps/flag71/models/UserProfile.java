package com.example.shanto.lab18.provatsoft.apps.flag71.models;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private String firstName;
    private String fistName;
    private String gender;
    private String lastName;

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
