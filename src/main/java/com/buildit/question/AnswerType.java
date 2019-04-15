package com.buildit.question;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;

@DynamoDBTable(tableName = "PLACEHOLDER_ANSWER_TYPE_TABLE_NAME")
public class AnswerType {

    static final String ANSWER_TYPE_TABLE_NAME = System.getenv("ANSWER_TYPE_TABLE_NAME");

    private String type;

    private List<String> values;

    @DynamoDBHashKey(attributeName = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "AnswerType{" +
                "type='" + type + '\'' +
                ", values=" + values +
                '}';
    }
}
