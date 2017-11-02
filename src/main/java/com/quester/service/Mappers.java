package com.quester.service;

import com.quester.model.Quest;
import com.quester.model.UserProfile;
import org.springframework.jdbc.core.RowMapper;

/**
 * Created by sergeybutorin on 02/11/2017.
 */
public class Mappers {

    static final RowMapper<String> USER_TOKEN_ROW_MAPPER = (rs, rowNum) -> rs.getString("token");

    static final RowMapper<Integer> ID_MAPPER = (rs, rowNum) -> rs.getInt("id");

    static final RowMapper<Integer> NEXT_ID_MAPPER = (rs, rowNum) -> rs.getInt("nextval");

    static final RowMapper<UserProfile> USER_PROFILE_ROW_MAPPER = (rs, rowNum) -> {
        final int id = rs.getInt("id");
        final String email = rs.getString("email");
        final String password = rs.getString("password");
        final String fistName = rs.getString("firstname");
        final String lastName = rs.getString("lastname");
        return new UserProfile(id, email, password, fistName, lastName);
    };

    static final RowMapper<Quest> QUEST_ROW_MAPPER = (rs, rowNum) -> {
        final int id = rs.getInt("id");
        final String title = rs.getString("title");
        final int userId = rs.getInt("user_id");
        return new Quest(id, title, userId);
    };
}
