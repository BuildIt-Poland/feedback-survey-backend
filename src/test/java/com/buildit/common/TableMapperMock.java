package com.buildit.common;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal;
import com.buildit.dynamoDB.TableMapper;

public class TableMapperMock implements TableMapper {

    private final AmazonDynamoDBLocal amazonDynamoDBLocal;

    public TableMapperMock(AmazonDynamoDBLocal amazonDynamoDBLocal) {
        this.amazonDynamoDBLocal = amazonDynamoDBLocal;
    }

    @Override
    public DynamoDBMapper getDynamoDBMapper(String tableName) {
        DynamoDBMapperConfig mapperConfig = DynamoDBMapperConfig.builder()
                .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(tableName))
                .build();
        return new DynamoDBMapper(amazonDynamoDBLocal.amazonDynamoDB(), mapperConfig);
    }
}
