package com.buildit.question;

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
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class QuestionDaoTest {

    private static AmazonDynamoDBLocal amazonDynamoDBLocal;

    private static DynamoDBMapper questionDynamoDBMapper;

    private static DynamoDBMapper answerTypeDynamoDBMapper;

    @Mock
    private TableMapper tableMapper;

    @InjectMocks
    private QuestionDao questionDao;

    @BeforeAll
    public static void setUpClass() {
        DaoTestHelper.initSqlite();
        amazonDynamoDBLocal = DynamoDBEmbedded.create();
        questionDynamoDBMapper = DaoTestHelper.getDBMapper(amazonDynamoDBLocal, Question.QUESTION_TABLE_NAME);
        answerTypeDynamoDBMapper = DaoTestHelper.getDBMapper(amazonDynamoDBLocal, AnswerType.ANSWER_TYPE_TABLE_NAME);

        DaoTestHelper.createTable(amazonDynamoDBLocal, questionDynamoDBMapper, Question.class);
        DaoTestHelper.createTable(amazonDynamoDBLocal, answerTypeDynamoDBMapper, AnswerType.class);
    }

    @AfterAll
    public static void tearDownClass() {
        amazonDynamoDBLocal.shutdown();
    }

    @Test
    void getAllQuestions() {
        //WHEN
        questionDynamoDBMapper.save(prepareQuestion());
        answerTypeDynamoDBMapper.save(prepareAnswerType());
        when(tableMapper.getDynamoDBMapper(Question.QUESTION_TABLE_NAME)).thenReturn(questionDynamoDBMapper);
        when(tableMapper.getDynamoDBMapper(AnswerType.ANSWER_TYPE_TABLE_NAME)).thenReturn(answerTypeDynamoDBMapper);

        Questions questions = questionDao.getAllQuestions();

        //THEN
        verify(tableMapper, times(2)).getDynamoDBMapper(Question.QUESTION_TABLE_NAME);
        assertEquals(1, questions.getQuestions().size());
        assertEquals(1, questions.getAnswerTypes().size());
    }

    @Test
    void getQuestions() {
        //GIVEN
        Question question = prepareQuestion();

        //WHEN
        questionDynamoDBMapper.save(question);
        when(tableMapper.getDynamoDBMapper(any())).thenReturn(questionDynamoDBMapper);

        List<Question> questions = questionDao.getQuestions();

        //THEN
        verify(tableMapper).getDynamoDBMapper(Question.QUESTION_TABLE_NAME);
        assertEquals(1, questions.size());
        assertEquals(question.getId(), questions.get(0).getId());
    }

    @Test
    void saveAnswerTypes() {
        //GIVEN
        AnswerType answerType = prepareAnswerType();

        //WHEN
        when(tableMapper.getDynamoDBMapper(any())).thenReturn(answerTypeDynamoDBMapper);

        questionDao.saveAnswerTypes(Collections.singletonList(answerType));

        //THEN
        verify(tableMapper).getDynamoDBMapper(AnswerType.ANSWER_TYPE_TABLE_NAME);
        PaginatedList<AnswerType> answerTypes = answerTypeDynamoDBMapper.scan(AnswerType.class, new DynamoDBScanExpression());
        assertEquals(1, answerTypes.size());
        assertEquals("kindOfFeedback", answerTypes.get(0).getType());
    }

    @Test
    void saveQuestions() {
        //GIVEN
        Question question = prepareQuestion();

        //WHEN
        when(tableMapper.getDynamoDBMapper(any())).thenReturn(questionDynamoDBMapper);

        questionDao.saveQuestions(Collections.singletonList(question));

        //THEN
        verify(tableMapper).getDynamoDBMapper(Question.QUESTION_TABLE_NAME);
        PaginatedList<Question> questions = questionDynamoDBMapper.scan(Question.class, new DynamoDBScanExpression());
        assertEquals(1, questions.size());
        assertEquals("10", questions.get(0).getId());
    }

    private AnswerType prepareAnswerType() {
        AnswerType answerType = new AnswerType();
        answerType.setType("kindOfFeedback");
        answerType.setValues(Arrays.asList("monthly", "quarterly", "annual"));
        return answerType;
    }

    private Question prepareQuestion() {
        Question question = new Question();
        question.setId("10");
        question.setOrdinal(12L);
        return question;
    }
}