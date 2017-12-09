package com.quester.service;

import com.quester.model.Point;
import com.quester.model.Quest;
import com.quester.model.QuestBase;
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

    public @Nullable Quest addQuest(@NotNull String title, @NotNull String description,
                                    @NotNull String userToken, @NotNull List<Point> points) {
        final String queryPoint = "INSERT INTO point (id, quest_id, x, \"y\") VALUES (?, ?, ?, ?)";
        final int questId;

        try (Connection conn = template.getDataSource().getConnection();
             PreparedStatement pst = conn.prepareStatement(queryPoint, Statement.NO_GENERATED_KEYS)) {
            final String queryQuest = "INSERT INTO quest (user_id, title, description) VALUES (" +
                    "(SELECT id FROM users WHERE token = ?), ?, ?) RETURNING id";
            questId = template.queryForObject(queryQuest, ID_MAPPER, userToken, title, description);
            for (Point p : points) {
                p.setId(template.queryForObject("SELECT nextval(pg_get_serial_sequence('point', 'id'))", NEXT_ID_MAPPER));

                pst.setInt(1, p.getId());
                pst.setInt(2, questId);
                pst.setDouble(3, p.getLatitude());
                pst.setDouble(4, p.getLongitude());
                pst.addBatch();
                LOGGER.info("Point with id {} in ({}; {}) created", p.getId(), p.getLatitude(), p.getLongitude());
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
            final Quest quest = template.queryForObject("SELECT * FROM quest WHERE id = ?", QUEST_ROW_MAPPER, id);
            quest.setPoints(getPoints(quest.getId()));
            return quest;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Quest with id = {} not found.", id);
            return null;
        } catch (DataAccessException e) {
            LOGGER.info(e.getLocalizedMessage());
            return null;
        }
    }

    public @Nullable List<QuestBase> getQuests() {
        try {
            return template.query("SELECT id, version FROM quest", QUESTS_LIST_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Quests not found.");
            return null;
        } catch (DataAccessException e) {
            LOGGER.info(e.getLocalizedMessage());
            return null;
        }
    }

    private @NotNull List<Point> getPoints(int questId) {
        return template.query("SELECT * FROM point WHERE quest_id = ?", POINT_ROW_MAPPER, questId);
    }
}
