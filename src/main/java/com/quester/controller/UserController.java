package com.quester.controller;

import com.quester.model.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(path = "/signup", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity signup(@RequestBody UserProfile body, HttpSession httpSession) {
        final String email = body.getEmail();
        final String password = body.getPassword();

        if (StringUtils.isEmpty(email)
                || StringUtils.isEmpty(password)) {
            LOGGER.info("Incorrect data", email);
        }

        LOGGER.info("User with email {} registered", email);
        return ResponseEntity.ok(null);
    }

}
