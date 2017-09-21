package com.quester.response;

import com.quester.model.UserProfile;

/**
 * Created by sergeybutorin on 21/09/2017.
 */
public class UserResponse {
    private final long id;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final int rating;

    public UserResponse(UserProfile user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.rating = user.getRating();
    }

    @SuppressWarnings("unused")
    public long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getEmail() {
        return email;
    }

    @SuppressWarnings("unused")
    public int getRating() {
        return rating;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
