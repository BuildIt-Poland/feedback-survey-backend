package com.buildit.dynamoDB;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

public class TableMapper {

    private static final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

    private final DynamoDBMapper dynamoDBMapper;

    public TableMapper(String tableName) {
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(tableName))
                .build();
        dynamoDBMapper = new DynamoDBMapper(client, mapperConfig);
    }

    public DynamoDBMapper getDynamoDBMapper() {
        return dynamoDBMapper;
    }
}
