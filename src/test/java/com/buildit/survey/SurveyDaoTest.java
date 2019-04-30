package com.buildit.survey;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal;
import com.buildit.common.DaoTestHelper;
import com.buildit.dynamoDB.TableMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class SurveyDaoTest {

    private static AmazonDynamoDBLocal amazonDynamoDBLocal;

    private static DynamoDBMapper dynamoDBMapper;

    @Mock
    private TableMapper tableMapper;

    @InjectMocks
    private SurveyDao surveyDao;

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
    void getAll() {
        //WHEN
        dynamoDBMapper.save(prepareSurvey());
        when(tableMapper.getDynamoDBMapper(any())).thenReturn(dynamoDBMapper);

        List<Survey> surveys = surveyDao.getAll();

        //THEN
        verify(tableMapper).getDynamoDBMapper(Survey.SURVEY_TABLE_NAME);
        assertEquals(1, surveys.size());
    }

    @Test
    void save() {
        //GIVEN
        Survey survey = prepareSurvey();

        //WHEN
        when(tableMapper.getDynamoDBMapper(any())).thenReturn(dynamoDBMapper);

        surveyDao.save(survey);

        //THEN
        verify(tableMapper).getDynamoDBMapper(Survey.SURVEY_TABLE_NAME);
        PaginatedList<Survey> surveys = dynamoDBMapper.scan(Survey.class, new DynamoDBScanExpression());
        assertEquals(1, surveys.size());
        assertEquals("surveyId", survey.getSurveyId());
        assertNotNull(survey.getId());
        assertNotNull(survey.getSavedDate());
    }

    private Survey prepareSurvey() {
        Survey survey = new Survey();
        survey.setId("1");
        survey.setSurveyId("surveyId");
        survey.setAnswers(Arrays.asList(prepareAnswer(1L), prepareAnswer(2L)));
        return survey;
    }

    private Answer prepareAnswer(Long id) {
        Answer answer = new Answer();
        answer.setQuestionId("question " + id);
        answer.setAnswer("answer" + id);
        return answer;
    }

}