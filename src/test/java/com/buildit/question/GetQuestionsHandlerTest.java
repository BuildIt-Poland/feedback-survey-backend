package com.buildit.question;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal;
import com.buildit.common.DaoTestHelper;
import com.buildit.common.TableMapperMock;
import com.buildit.response.ApiGatewayResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class GetQuestionsHandlerTest {

    private static AmazonDynamoDBLocal amazonDynamoDBLocal;

    private static DynamoDBMapper dynamoDBMapper;

    private GetQuestionsHandler testee = new GetQuestionsHandler(new QuestionDao(new TableMapperMock(amazonDynamoDBLocal)));

    @BeforeAll
    public static void setUpClass() {
        DaoTestHelper.initSqlite();
        amazonDynamoDBLocal = DynamoDBEmbedded.create();
        dynamoDBMapper = DaoTestHelper.getDBMapper(amazonDynamoDBLocal, null);

        DaoTestHelper.createTable(amazonDynamoDBLocal, dynamoDBMapper, Question.class);
        DaoTestHelper.createTable(amazonDynamoDBLocal, dynamoDBMapper, AnswerType.class);
    }

    @AfterAll
    public static void tearDownClass() {
        amazonDynamoDBLocal.shutdown();
    }

    @Test
    void handleRequest_forGivenEmptyQuestionList() {
        //GIVEN
        Map<String, Object> input = new HashMap<>();

        //WHEN
        ApiGatewayResponse response = testee.handleRequest(input, null);

        //THEN
        assertEquals(200, response.getStatusCode());
        assertFalse(response.isIsBase64Encoded());
        assertEquals("{\"questions\":[],\"answerTypes\":[]}", response.getBody());
    }

    @Test
    void handleRequest_forGivenNotEmptyQuestionList() throws IOException {
        //GIVEN
        Map<String, Object> input = new HashMap<>();
        AnswerType answerType = new QuestionTestData().prepareAnswerType();
        Question question = new QuestionTestData().prepareQuestion();
        Questions expectedResult = new Questions(Collections.singletonList(question), Collections.singletonList(answerType));
        dynamoDBMapper.save(answerType);
        dynamoDBMapper.save(question);
        ObjectMapper objectMapper = new ObjectMapper();

        //WHEN
        ApiGatewayResponse response = testee.handleRequest(input, null);

        //THEN
        assertEquals(200, response.getStatusCode());
        assertFalse(response.isIsBase64Encoded());
        assertEquals(objectMapper.writeValueAsString(expectedResult), response.getBody());
    }
}