package com.quester.response;

import com.quester.model.UserProfile;

/**
 * Created by sergeybutorin on 05/11/2017.
 */
public class TokenResponse {
    private final String token;

    public TokenResponse(String token) {

        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

