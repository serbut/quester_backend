package com.quester.response;

/**
 * Created by sergeybutorin on 21/09/2017.
 */
public class ErrorResponse {
    public static final String WRONG_PARAMETERS = "Wrong parameters";
    public static final String NOT_AUTHORIZED = "User not authorized";
    public static final String INCORRECT_DATA = "Incorrect login/password";
    public static final String EMAIL_ALREADY_EXISTS = "User with such email already exists";
    public static final String SESSION_BUSY = "User in this session already logged in";

    private final String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

    @SuppressWarnings("unused")
    public String getError() {
        return error;
    }
}
