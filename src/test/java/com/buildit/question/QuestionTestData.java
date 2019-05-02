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
        Question question = new Question();
        question.setId("1");
        question.setOrdinal(11L);
        question.setContent("Question 1.");
        question.setAnswerType("kindOfFeedback");
        return question;
    }
}
