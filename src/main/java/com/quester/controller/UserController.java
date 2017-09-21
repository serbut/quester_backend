package com.quester.controller;

import com.quester.model.UserProfile;
import com.quester.response.ErrorResponse;
import com.quester.response.UserResponse;
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

import javax.servlet.http.HttpSession;

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
    public ResponseEntity signup(@RequestBody UserProfile body, HttpSession httpSession) {
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

        if (httpSession.getAttribute("userId") != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(ErrorResponse.SESSION_BUSY));
        }

        final UserProfile newUser = userService.addUser(email, passwordEncoder.encode(password), firstName, lastName);
        if (newUser == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(ErrorResponse.EMAIL_ALREADY_EXISTS));
        }
        httpSession.setAttribute("userId", newUser.getId());
        LOGGER.info("User with email {} registered", email);
        return ResponseEntity.ok(new UserResponse(newUser));
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity login(@RequestBody UserProfile body, HttpSession httpSession) {
        final String email = body.getEmail();
        final String password = body.getPassword();

        if (StringUtils.isEmpty(email)
                || StringUtils.isEmpty(password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ErrorResponse.WRONG_PARAMETERS));
        }

        if (httpSession.getAttribute("userId") != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(ErrorResponse.SESSION_BUSY));
        }

        final UserProfile user = userService.getUserByEmail(email);

        if (user == null || !passwordEncoder.matches(password, user.getPassword()) || !user.getEmail().equals(email)) {
            LOGGER.info("User {} tried to login. Incorrect email/password", email);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(ErrorResponse.INCORRECT_DATA));
        }

        httpSession.setAttribute("userId", user.getId());
        LOGGER.info("User {} logged in", email);
        return ResponseEntity.ok(new UserResponse(user));
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public ResponseEntity logout(HttpSession httpSession) {
        if (httpSession.getAttribute("userId") != null) {
            LOGGER.info("User with id = {} logged out", httpSession.getAttribute("userId"));
            httpSession.invalidate();
            return ResponseEntity.ok("");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(ErrorResponse.NOT_AUTHORIZED));
    }
}
