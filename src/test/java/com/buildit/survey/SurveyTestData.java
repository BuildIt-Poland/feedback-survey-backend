package com.buildit.survey;

import java.util.Arrays;

class SurveyTestData {

    Survey prepareSurvey() {
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
