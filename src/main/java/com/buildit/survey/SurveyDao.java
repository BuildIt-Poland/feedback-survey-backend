package com.buildit.survey;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.buildit.dynamoDB.TableMapper;

import java.time.LocalDateTime;
import java.util.List;

class SurveyDao {

    private final TableMapper tableMapper;

    SurveyDao(TableMapper tableMapper) {
        this.tableMapper = tableMapper;
    }

    Survey save(Survey survey) {
        DynamoDBMapper mapper = tableMapper.getDynamoDBMapper(Survey.SURVEY_TABLE_NAME);
        survey.setSavedDate(LocalDateTime.now());
        mapper.save(survey);
        return survey;
    }

    List<Survey> getAll() {
        DynamoDBMapper mapper = tableMapper.getDynamoDBMapper(Survey.SURVEY_TABLE_NAME);
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
        return mapper.scan(Survey.class, scanExp);
    }

}
