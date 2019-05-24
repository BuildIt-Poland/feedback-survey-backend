package com.buildit.survey;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal;
import com.buildit.common.DaoTestHelper;
import com.buildit.common.TableMapperMock;
import com.buildit.question.Question;
import com.buildit.question.QuestionDao;
import com.buildit.question.QuestionTestData;
import com.buildit.response.ApiGatewayResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class ExportSurveysHandlerTest {

    private static AmazonDynamoDBLocal amazonDynamoDBLocal;

    private static DynamoDBMapper surveyDynamoDBMapper;

    private static DynamoDBMapper questionDynamoDBMapper;

    private QuestionDao questionDao = new QuestionDao(new TableMapperMock(amazonDynamoDBLocal));

    private SurveyDao surveyDao = new SurveyDao(new TableMapperMock(amazonDynamoDBLocal));

    private ExportSurveysHandler testee = new ExportSurveysHandler(new ExportSurveyService(questionDao, surveyDao));

    @BeforeAll
    public static void setUpClass() {
        DaoTestHelper.initSqlite();
        amazonDynamoDBLocal = DynamoDBEmbedded.create();
        surveyDynamoDBMapper = DaoTestHelper.getDBMapper(amazonDynamoDBLocal, Survey.SURVEY_TABLE_NAME);
        questionDynamoDBMapper = DaoTestHelper.getDBMapper(amazonDynamoDBLocal, Question.QUESTION_TABLE_NAME);

        DaoTestHelper.createTable(amazonDynamoDBLocal, surveyDynamoDBMapper, Survey.class);
        DaoTestHelper.createTable(amazonDynamoDBLocal, questionDynamoDBMapper, Question.class);
    }

    @AfterAll
    public static void tearDownClass() {
        amazonDynamoDBLocal.shutdown();
    }

    @Test
    void handleRequest() {
        //GIVEN
        Survey survey = new SurveyTestData().prepareSurvey();
        Map<String, Object> input = new HashMap<>();
        questionDynamoDBMapper.save(new QuestionTestData().prepareQuestion());
        surveyDynamoDBMapper.save(survey);

        //WHEN
        ApiGatewayResponse response = testee.handleRequest(input, null);

        //THEN
        assertEquals(200, response.getStatusCode());
        assertTrue(response.isIsBase64Encoded());
        String fileContent = new String(Base64.getDecoder().decode(response.getBody()));
        assertTrue(fileContent.contains("Id,Employee name,Date,Question 1"));
        assertTrue(fileContent.contains("surveyId,Joanna,2019-04-22 11:22:33,answer 1"));
    }
}