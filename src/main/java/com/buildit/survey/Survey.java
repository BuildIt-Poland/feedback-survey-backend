package com.buildit.survey;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.buildit.converter.LocalDateTimeConverter;

import java.time.LocalDateTime;
import java.util.List;

@DynamoDBTable(tableName = "PLACEHOLDER_SURVEY_TABLE_NAME")
public class Survey {

    static final String SURVEY_TABLE_NAME = System.getenv("SURVEY_TABLE_NAME");

    private String id;

    private String surveyId;

    private LocalDateTime savedDate;

    private List<Answer> answers;

    private String employeeName;

    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    public LocalDateTime getSavedDate() {
        return savedDate;
    }

    public void setSavedDate(LocalDateTime savedDate) {
        this.savedDate = savedDate;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "id='" + id + '\'' +
                ", surveyId='" + surveyId + '\'' +
                ", savedDate=" + savedDate +
                ", answers=" + answers +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }
}
