package com.buildit.survey;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.buildit.dynamoDB.TableMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;

class SurveyService {

    private final static Logger LOGGER = LogManager.getLogger(SurveyService.class);

    void save(Survey survey) {
        DynamoDBMapper mapper = new TableMapper(Survey.SURVEY_TABLE_NAME).getDynamoDBMapper();
        survey.setSavedDate(LocalDateTime.now());
        mapper.save(survey);
    }

}
