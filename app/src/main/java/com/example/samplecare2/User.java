package com.example.samplecare2;

public class User {
    private String uid;
    private String fullName;
    private String city;
    private String email;
    private String password;
    private String imageUri;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String uid, String fullName, String city, String email, String password, String imageUri) {
        this.uid = uid;
        this.fullName = fullName;
        this.city = city;
        this.email = email;
        this.password = password;
        this.imageUri = imageUri;
    }

    // Getters and setters

    public String getUId() {
        return uid;
    }

    public void setUId(String uid) {
        this.uid = uid;
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
