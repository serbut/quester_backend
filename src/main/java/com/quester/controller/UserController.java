package com.quester.controller;

import com.quester.Constants;
import com.quester.model.UserProfile;
import com.quester.response.ErrorResponse;
import com.quester.response.TokenResponse;
import com.quester.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created by sergeybutorin on 21/09/2017.
 */
@RestController
@RequestMapping(value = "api/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity signup(@RequestBody UserProfile body) {
        final String email = body.getEmail();
        final String password = body.getPassword();
        final String firstName = body.getFirstName();
        final String lastName = body.getLastName();

        if (StringUtils.isEmpty(email)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(firstName)
                || StringUtils.isEmpty(lastName)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.WRONG_PARAMETERS);
        }

        final String token = getToken(Constants.TOKEN_LENGTH);

        final UserProfile newUser = userService.addUser(email, passwordEncoder.encode(password), firstName, lastName, token);
        if (newUser == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(ErrorResponse.EMAIL_ALREADY_EXISTS));
        }
        LOGGER.info("User with email {} registered", email);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity login(@RequestBody UserProfile body) {
        final String email = body.getEmail();
        final String password = body.getPassword();

        if (StringUtils.isEmpty(email)
                || StringUtils.isEmpty(password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ErrorResponse.WRONG_PARAMETERS));
        }

        final UserProfile user = userService.getUserByEmail(email);
        final String token = userService.getUserToken(email);

        if (user == null || !passwordEncoder.matches(password, user.getPassword()) || !user.getEmail().equals(email)) {
            LOGGER.info("User {} tried to login. Incorrect email/password", email);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(ErrorResponse.INCORRECT_DATA));
        }

        LOGGER.info("User {} logged in", email);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    static String getToken(int chars) {
        final String charSet = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890!@#$";
        final StringBuilder token = new StringBuilder();
        for (int a = 1; a <= chars; a++) {
            token.append(charSet.charAt(new Random().nextInt(charSet.length())));
        }
        return token.toString();
    }
}
