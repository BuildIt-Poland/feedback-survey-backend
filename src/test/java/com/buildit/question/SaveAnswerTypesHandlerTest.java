package com.buildit.question;

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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class SaveAnswerTypesHandlerTest {

    private static AmazonDynamoDBLocal amazonDynamoDBLocal;

    private static DynamoDBMapper dynamoDBMapper;

    private SaveAnswerTypesHandler testee = new SaveAnswerTypesHandler(new QuestionDao(new TableMapperMock(amazonDynamoDBLocal)));

    @BeforeAll
    public static void setUpClass() {
        DaoTestHelper.initSqlite();
        amazonDynamoDBLocal = DynamoDBEmbedded.create();
        dynamoDBMapper = DaoTestHelper.getDBMapper(amazonDynamoDBLocal, AnswerType.ANSWER_TYPE_TABLE_NAME);

        DaoTestHelper.createTable(amazonDynamoDBLocal, dynamoDBMapper, AnswerType.class);
    }

    @AfterAll
    public static void tearDownClass() {
        amazonDynamoDBLocal.shutdown();
    }

    @Test
    void handleRequest() throws JsonProcessingException {
        //GIVEN
        AnswerType answerType = new QuestionTestData().prepareAnswerType();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(Collections.singletonList(answerType));

        Map<String, Object> input = new HashMap<>();
        input.put("body", body);

        //WHEN
        ApiGatewayResponse response = testee.handleRequest(input, null);

        List<AnswerType> answerTypes = dynamoDBMapper.scan(AnswerType.class, new DynamoDBScanExpression());

        //THEN
        assertEquals(200, response.getStatusCode());
        assertFalse(response.isIsBase64Encoded());
        assertEquals(body, response.getBody());
        assertEquals(1, answerTypes.size());
    }
}