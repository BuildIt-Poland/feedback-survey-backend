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
        List<QuestionType> questionTypes = getQuestionTypes(scanExp);

        return new Questions(questions, questionTypes);
    }

    private List<Question> getQuestions(DynamoDBScanExpression scanExp) {
        DynamoDBMapper mapper = new TableMapper(Question.QUESTION_TABLE_NAME).getDynamoDBMapper();
        return mapper.scan(Question.class, scanExp);
    }

    private List<QuestionType> getQuestionTypes(DynamoDBScanExpression scanExp) {
        DynamoDBMapper mapper = new TableMapper(QuestionType.QUESTION_TYPE_TABLE_NAME).getDynamoDBMapper();
        return mapper.scan(QuestionType.class, scanExp);
    }

    void saveQuestionTypes(List<QuestionType> questionTypes) {
        DynamoDBMapper mapper = new TableMapper(QuestionType.QUESTION_TYPE_TABLE_NAME).getDynamoDBMapper();
        questionTypes.forEach(mapper::save);
    }

    void saveQuestions(List<Question> questions) {
        DynamoDBMapper mapper = new TableMapper(Question.QUESTION_TABLE_NAME).getDynamoDBMapper();
        questions.forEach(mapper::save);
    }


}
