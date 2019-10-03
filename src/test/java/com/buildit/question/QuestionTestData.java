package com.buildit.question;

import java.util.Arrays;

public class QuestionTestData {

    public AnswerType prepareAnswerType() {
        AnswerType answerType = new AnswerType();
        answerType.setType("kindOfFeedback");
        answerType.setValues(Arrays.asList("monthly", "quarterly", "annual"));
        return answerType;
    }

    public Question prepareQuestion() {
        return prepareQuestion(1L);
    }

    public Question prepareQuestion(Long id) {
        Question question = new Question();
        question.setName("Q" + id);
        question.setOrdinal(11L);
        question.setContent("Question " + id);
        question.setAnswerType("kindOfFeedback");
        return question;
    }
}
