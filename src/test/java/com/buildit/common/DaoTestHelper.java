package com.buildit.common;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

public class DaoTestHelper {

    public static void initSqlite() {
        System.setProperty("sqlite4java.library.path", "native-libs");
    }

    public static DynamoDBMapper getDBMapper(AmazonDynamoDBLocal amazonDynamoDBLocal, String tableName) {
        return new TableMapperMock(amazonDynamoDBLocal).getDynamoDBMapper(tableName);
    }

    public static void createTable(AmazonDynamoDBLocal amazonDynamoDBLocal, DynamoDBMapper dynamoDBMapper, Class<?> clazz) {
        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(clazz);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        amazonDynamoDBLocal.amazonDynamoDB().createTable(tableRequest);
    }
}
