package com.buildit.question;

import java.util.List;

class Questions {

    private List<Question> questions;

    private List<QuestionType> questionTypes;

    Questions(List<Question> questions, List<QuestionType> questionTypes) {
        this.questions = questions;
        this.questionTypes = questionTypes;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<QuestionType> getQuestionTypes() {
        return questionTypes;
    }

    public void setQuestionTypes(List<QuestionType> questionTypes) {
        this.questionTypes = questionTypes;
    }
}
