package com.buildit.survey;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class SaveSurveyHandlerTest {

    private static AmazonDynamoDBLocal amazonDynamoDBLocal;

    private static DynamoDBMapper dynamoDBMapper;

    private SaveSurveyHandler testee = new SaveSurveyHandler(new SurveyDao(new TableMapperMock(amazonDynamoDBLocal)));

    @BeforeAll
    public static void setUpClass() {
        DaoTestHelper.initSqlite();
        amazonDynamoDBLocal = DynamoDBEmbedded.create();
        dynamoDBMapper = DaoTestHelper.getDBMapper(amazonDynamoDBLocal, Survey.SURVEY_TABLE_NAME);

        DaoTestHelper.createTable(amazonDynamoDBLocal, dynamoDBMapper, Survey.class);
    }

    @AfterAll
    public static void tearDownClass() {
        amazonDynamoDBLocal.shutdown();
    }

    @Test
    void handleRequest() throws JsonProcessingException {
        //GIVEN
        Survey survey = new Survey();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(survey);

        Map<String, Object> input = new HashMap<>();
        input.put("body", body);

        //WHEN
        ApiGatewayResponse response = testee.handleRequest(input, null);

        List<Survey> surveys = dynamoDBMapper.scan(Survey.class, new DynamoDBScanExpression());

        //THEN
        assertEquals(200, response.getStatusCode());
        assertFalse(response.isIsBase64Encoded());
        assertEquals(1, surveys.size());
    }
}