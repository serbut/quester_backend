package com.quester.service;

import com.quester.model.Point;
import com.quester.model.Quest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.quester.service.Mappers.*;

/**
 * Created by sergeybutorin on 02/11/2017.
 */
@Service
public class QuestService {
    private final JdbcTemplate template;
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestService.class);

    QuestService(JdbcTemplate template) {
        this.template = template;
    }

    public @Nullable Quest addQuest(@NotNull String title, @NotNull String userToken, @NotNull List<Point> points) {
        final String queryPoint = "INSERT INTO point (id, quest_id, order_number, x, \"y\") VALUES (?, ?, ?, ?, ?)";
        final int questId;

        try (Connection conn = template.getDataSource().getConnection();
             PreparedStatement pst = conn.prepareStatement(queryPoint, Statement.NO_GENERATED_KEYS)) {
            final String queryQuest = "INSERT INTO quest (user_id, title) VALUES (" +
                    "(SELECT id FROM users WHERE token = ?), ?) RETURNING id";
            questId = template.queryForObject(queryQuest, ID_MAPPER, userToken, title);
            short orderNumber = 0;
            for (Point p : points) {
                p.setId(template.queryForObject("SELECT nextval(pg_get_serial_sequence('point', 'id'))", NEXT_ID_MAPPER));

                pst.setInt(1, p.getId());
                pst.setInt(2, questId);
                pst.setShort(3, ++orderNumber);
                pst.setDouble(4, p.getX());
                pst.setDouble(5, p.getY());
                pst.addBatch();
                LOGGER.info("Point with id {} in ({}; {}) created", p.getId(), p.getX(), p.getY());
            }

            pst.executeBatch();
        } catch (DataAccessException | SQLException e) {
            LOGGER.info(e.getLocalizedMessage());
            return null;
        }
        return getQuestById(questId);
    }

    public @Nullable Quest getQuestById(int id) {
        try {
            return template.queryForObject("SELECT * FROM quest WHERE id = ?", QUEST_ROW_MAPPER, id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Quest with id = {} not found.", id);
            return null;
        } catch (DataAccessException e) {
            LOGGER.info(e.getLocalizedMessage());
            return null;
        }
    }
}
