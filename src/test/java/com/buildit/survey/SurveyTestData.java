package com.buildit.survey;

import java.time.LocalDateTime;
import java.util.Arrays;

class SurveyTestData {

    Survey prepareSurvey() {
        Survey survey = new Survey();
        survey.setId("1");
        survey.setSurveyId("surveyId");
        survey.setSavedDate(LocalDateTime.of(2019, 4, 22, 11, 22, 33, 44));
        survey.setAnswers(Arrays.asList(prepareAnswer(1L), prepareAnswer(2L), prepareAnswer(3L)));
        survey.setEmployeeName("Joanna");
        return survey;
    }

    SurveyDTO prepareSurveyDTO() {
        SurveyDTO survey = new SurveyDTO();
        survey.setId("1");
        survey.setEmployeeName("Joanna");
        survey.setSurveyId("surveyId");
        survey.setSavedDate(LocalDateTime.of(2019, 4, 22, 11, 22, 33, 44));
        survey.setAnswers(Arrays.asList(prepareAnswer(1L), prepareAnswer(2L), prepareAnswer(3L)));
        return survey;
    }

    private Answer prepareAnswer(Long id) {
        Answer answer = new Answer();
        answer.setQuestionName("Q" + id);
        answer.setAnswer("answer " + id);
        return answer;
    }
}
