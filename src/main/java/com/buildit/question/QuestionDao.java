package com.buildit.question;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.buildit.dynamoDB.TableMapper;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionDao {

    private final TableMapper tableMapper;

    public QuestionDao(TableMapper tableMapper) {
        this.tableMapper = tableMapper;
    }

    /**
     * @return all questions and question types.
     */
    Questions getAllQuestions() {
        List<Question> questions = getQuestions();
        List<AnswerType> answerTypes = getAnswerTypes();

        return new Questions(questions, answerTypes);
    }

    public List<Question> getQuestions() {
        DynamoDBMapper mapper = tableMapper.getDynamoDBMapper(Question.QUESTION_TABLE_NAME);
        PaginatedList<Question> questions = mapper.scan(Question.class, new DynamoDBScanExpression());
        return questions.stream()
                .sorted(Comparator.comparing(Question::getOrdinal))
                .collect(Collectors.toList());
    }

    private List<AnswerType> getAnswerTypes() {
        DynamoDBMapper mapper = tableMapper.getDynamoDBMapper(AnswerType.ANSWER_TYPE_TABLE_NAME);
        return mapper.scan(AnswerType.class, new DynamoDBScanExpression());
    }

    void saveAnswerTypes(List<AnswerType> answerTypes) {
        DynamoDBMapper mapper = tableMapper.getDynamoDBMapper(AnswerType.ANSWER_TYPE_TABLE_NAME);
        answerTypes.forEach(mapper::save);
    }

    void saveQuestions(List<Question> questions) {
        DynamoDBMapper mapper = tableMapper.getDynamoDBMapper(Question.QUESTION_TABLE_NAME);
        questions.forEach(mapper::save);
    }


}
