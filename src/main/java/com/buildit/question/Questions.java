package com.buildit.question;

import java.util.List;

class Questions {

    private List<Question> questions;

    private List<AnswerType> answerTypes;

    Questions(List<Question> questions, List<AnswerType> answerTypes) {
        this.questions = questions;
        this.answerTypes = answerTypes;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<AnswerType> getAnswerTypes() {
        return answerTypes;
    }

    public void setAnswerTypes(List<AnswerType> answerTypes) {
        this.answerTypes = answerTypes;
    }
}
