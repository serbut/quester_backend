package com.quester.controller;

import com.quester.model.Point;
import com.quester.model.Quest;
import com.quester.model.QuestBase;
import com.quester.response.ErrorResponse;
import com.quester.service.QuestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sergeybutorin on 02/11/2017.
 */

@RestController
@RequestMapping(value = "api/quest")
public class QuestController {
    private final QuestService questService;
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestController.class);

    @Autowired
    public QuestController(QuestService questService) {
        this.questService = questService;
    }

    @RequestMapping(path = "/new", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity addQuest(@RequestHeader("User-Token") String userToken,
                                 @RequestBody Quest body) {
        final String title = body.getTitle();
        final String description = body.getDescription();
        final List<Point> points = body.getPoints();

        if (StringUtils.isEmpty(title)
                || StringUtils.isEmpty(userToken)
                || StringUtils.isEmpty(points)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.WRONG_PARAMETERS);
        }

        final Quest newQuest = questService.addQuest(title, description, userToken, points);
        if (newQuest == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ErrorResponse.QUEST_CREATION_ERROR)); // TODO: too broad
        }
        LOGGER.info("Quest with id = {} and title = {} added", newQuest.getId(), newQuest.getTitle());
        return ResponseEntity.ok(newQuest);
    }

    @RequestMapping(path = "/get_all", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity getQuestsList() {
        final List<QuestBase> quests = questService.getQuests();
        return ResponseEntity.ok(quests);
    }

    @RequestMapping(path = "/{id}/details", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity getQuestDetails(@PathVariable(value="id") int questId) {
        final Quest quest = questService.getQuestById(questId);

        if (quest == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ErrorResponse.QUEST_NOT_FOUND));
        }
        return ResponseEntity.ok(quest);
    }
}
