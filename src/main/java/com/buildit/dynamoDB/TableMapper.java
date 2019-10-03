package com.buildit.dynamoDB;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public interface TableMapper {

    DynamoDBMapper getDynamoDBMapper(String tableName);
}
