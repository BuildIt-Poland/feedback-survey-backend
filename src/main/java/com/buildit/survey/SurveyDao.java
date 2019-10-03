package com.buildit.survey;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.buildit.dynamoDB.TableMapper;

import java.util.List;

class SurveyDao {

    private final TableMapper tableMapper;

    SurveyDao(TableMapper tableMapper) {
        this.tableMapper = tableMapper;
    }

    void save(Survey survey) {
        DynamoDBMapper mapper = tableMapper.getDynamoDBMapper(Survey.SURVEY_TABLE_NAME);
        mapper.save(survey);
    }

    List<Survey> getAll() {
        DynamoDBMapper mapper = tableMapper.getDynamoDBMapper(Survey.SURVEY_TABLE_NAME);
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
        return mapper.scan(Survey.class, scanExp);
    }

}
