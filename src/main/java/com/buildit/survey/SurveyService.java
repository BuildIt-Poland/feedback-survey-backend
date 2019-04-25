package com.buildit.survey;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.buildit.dynamoDB.TableMapper;

import java.time.LocalDateTime;
import java.util.List;

class SurveyService {

    void save(Survey survey) {
        DynamoDBMapper mapper = new TableMapper(Survey.SURVEY_TABLE_NAME).getDynamoDBMapper();
        survey.setSavedDate(LocalDateTime.now());
        mapper.save(survey);
    }

    List<Survey> getAll() {
        DynamoDBMapper mapper = new TableMapper(Survey.SURVEY_TABLE_NAME).getDynamoDBMapper();
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
        return mapper.scan(Survey.class, scanExp);
    }

}
