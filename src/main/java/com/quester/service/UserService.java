package com.quester.service;

import com.quester.model.UserProfile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

/**
 * Created by sergeybutorin on 21/09/2017.
 */

@Service
public class UserService {
    private final JdbcTemplate template;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    UserService(JdbcTemplate template) {
        this.template = template;
    }

    public @Nullable UserProfile addUser(@NotNull String email, @NotNull String password, @NotNull String firstName, @NotNull String lastName) {
        try {
            final String query = "INSERT INTO users (email, password, firstname, lastname) VALUES (?, ?, ?, ?)";
            template.update(query, email, password, firstName, lastName);
        } catch (DuplicateKeyException e) {
            LOGGER.info("User with email {} already exists.", email);
            return null;
        } catch (DataAccessException e) {
            LOGGER.info(e.getLocalizedMessage());
            return null;
        }
        return getUserByEmail(email);
    }

    public @Nullable UserProfile getUserByEmail(String email) {
        try {
            return template.queryForObject("SELECT * FROM users WHERE email = ?", USER_PROFILE_ROW_MAPPER, email);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("User with email {} not found.", email);
            return null;
        } catch (DataAccessException e) {
            LOGGER.info(e.getLocalizedMessage());
            return null;
        }
    }

    public @Nullable UserProfile getUserById(Long id) {
        try {
            return template.queryForObject("SELECT * FROM users WHERE id = ?", USER_PROFILE_ROW_MAPPER, id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("User with id = {} not found.", id);
            return null;
        } catch (DataAccessException e) {
            LOGGER.info(e.getLocalizedMessage());
            return null;
        }
    }

    private static final RowMapper<UserProfile> USER_PROFILE_ROW_MAPPER = (rs, rowNum) -> {
        final int id = rs.getInt("id");
        final String email = rs.getString("email");
        final String password = rs.getString("password");
        final String fistName = rs.getString("firstname");
        final String lastName = rs.getString("lastname");
        final int rating = rs.getInt("rating");
        return new UserProfile(id, email, password, fistName, lastName, rating);
    };
}
