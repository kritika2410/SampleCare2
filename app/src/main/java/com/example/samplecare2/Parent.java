package com.example.samplecare2;

public class Parent {

    private String pid;
    private String fullName;
    private String city;
    private String email;
    private String password;
    private String imageUri;

    public Parent() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Parent(String pid, String fullName, String city, String email, String password, String imageUri) {
        this.pid = pid;
        this.fullName = fullName;
        this.city = city;
        this.email = email;
        this.password = password;
        this.imageUri = imageUri;
    }

    // Getters and setters

    public String getPId() {
        return pid;
    }

    public void setPId(String pid) {
        this.pid = pid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
