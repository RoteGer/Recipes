package com.example.recipes.models;

public class Users {
    public String email;
    public String password;
    public String fullName;
    public Boolean isAdmin;

    public Users(String email, String password, String fullName, Boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.isAdmin = isAdmin;
    }

    public Users () {}

    public void setEmail(String email) { this.email = email; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }

    public String getFullName() { return fullName; }

    public String getPassword() { return password; }

}
