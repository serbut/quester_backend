package com.quester.service;

import com.quester.model.Point;
import com.quester.model.Quest;
import com.quester.model.QuestBase;
import com.quester.model.UserProfile;
import org.springframework.jdbc.core.RowMapper;

import java.util.UUID;

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
        final UUID uuid = (UUID) rs.getObject("uuid");
        final int version = rs.getInt("version");
        final String title = rs.getString("title");
        final String description = rs.getString("description");
        final int userId = rs.getInt("user_id");
        return new Quest(uuid, version, title, description, userId);
    };

    static final RowMapper<QuestBase> QUESTS_LIST_ROW_MAPPER = (rs, rowNum) -> {
        final UUID uuid = (UUID) rs.getObject("uuid");
        final int version = rs.getInt("version");
        return new QuestBase(uuid, version);
    };

    static final RowMapper<Point> POINT_ROW_MAPPER = (rs, rowNum) -> {
        final UUID uuid = (UUID) rs.getObject("uuid");
        final double x = rs.getDouble("x");
        final double y = rs.getDouble("y");
        return new Point(uuid, x, y);
    };
}
