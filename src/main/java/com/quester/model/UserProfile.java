package com.quester.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sergeybutorin on 21/09/2017.
 */
public class UserProfile {
    private long id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    private int rating;

    @SuppressWarnings("unused")
    private UserProfile() {
    }

    public UserProfile(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserProfile(int id, String email, String password, int rating) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.rating = rating;
    }

    @SuppressWarnings("unused")
    public long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getPassword() {
        return password;
    }

    @SuppressWarnings("unused")
    public String getEmail() {
        return email;
    }

    @SuppressWarnings("unused")
    public int getRating() {
        return rating;
    }

    @SuppressWarnings("unused")
    public void setEmail(String email) {
        this.email = email;
    }

    @SuppressWarnings("unused")
    public void setPassword(String password) {
        this.password = password;
    }
}
