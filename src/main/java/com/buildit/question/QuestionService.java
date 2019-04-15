package com.buildit.question;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.buildit.dynamoDB.TableMapper;

import java.util.List;

class QuestionService {

    /**
     * @return all questions and question types.
     */
    Questions getAllQuestions() {
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
        List<Question> questions = getQuestions(scanExp);
        List<AnswerType> answerTypes = getAnswerTypes(scanExp);

        return new Questions(questions, answerTypes);
    }

    private List<Question> getQuestions(DynamoDBScanExpression scanExp) {
        DynamoDBMapper mapper = new TableMapper(Question.QUESTION_TABLE_NAME).getDynamoDBMapper();
        return mapper.scan(Question.class, scanExp);
    }

    private List<AnswerType> getAnswerTypes(DynamoDBScanExpression scanExp) {
        DynamoDBMapper mapper = new TableMapper(AnswerType.ANSWER_TYPE_TABLE_NAME).getDynamoDBMapper();
        return mapper.scan(AnswerType.class, scanExp);
    }

    void saveAnswerTypes(List<AnswerType> answerTypes) {
        DynamoDBMapper mapper = new TableMapper(AnswerType.ANSWER_TYPE_TABLE_NAME).getDynamoDBMapper();
        answerTypes.forEach(mapper::save);
    }

    void saveQuestions(List<Question> questions) {
        DynamoDBMapper mapper = new TableMapper(Question.QUESTION_TABLE_NAME).getDynamoDBMapper();
        questions.forEach(mapper::save);
    }


}
