package com.buildit.email;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal;
import com.buildit.common.DaoTestHelper;
import com.buildit.common.TableMapperMock;
import com.buildit.response.ApiGatewayResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class SaveEmailHandlerTest {

    private static AmazonDynamoDBLocal amazonDynamoDBLocal;

    private static DynamoDBMapper dynamoDBMapper;

    private SaveEmailHandler testee = new SaveEmailHandler(new EmailDao(new TableMapperMock(amazonDynamoDBLocal)));

    @BeforeAll
    public static void setUpClass() {
        DaoTestHelper.initSqlite();
        amazonDynamoDBLocal = DynamoDBEmbedded.create();
        dynamoDBMapper = DaoTestHelper.getDBMapper(amazonDynamoDBLocal, Email.EMAIL_TABLE_NAME);

        DaoTestHelper.createTable(amazonDynamoDBLocal, dynamoDBMapper, Email.class);
    }

    @AfterAll
    public static void tearDownClass() {
        amazonDynamoDBLocal.shutdown();
    }

    @Test
    void handleRequest() throws JsonProcessingException {
        //GIVEN
        Email email = new EmailTestData().prepareEmail();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(email);
        Map<String, Object> input = new HashMap<>();
        input.put("body", body);


        ///WHEN
        ApiGatewayResponse response = testee.handleRequest(input, null);

        List<Email> emails = dynamoDBMapper.scan(Email.class, new DynamoDBScanExpression());

        //THEN
        assertEquals(200, response.getStatusCode());
        assertFalse(response.isIsBase64Encoded());
        assertEquals(1, emails.size());
    }

}