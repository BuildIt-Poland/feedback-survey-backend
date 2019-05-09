package com.buildit.survey;

import com.buildit.email.EmailSender;

import java.io.File;
import java.io.IOException;

class SurveyService {

    private final EmailSender emailSender;
    private final SurveyDao surveyDao;
    private final ExportSurveyService exportSurveyService;

    SurveyService(EmailSender emailSender, SurveyDao surveyDao, ExportSurveyService exportSurveyService) {
        this.emailSender = emailSender;
        this.surveyDao = surveyDao;
        this.exportSurveyService = exportSurveyService;
    }

    Survey save(Survey survey) throws IOException {
        survey = surveyDao.save(survey);
        File file = exportSurveyService.generateCSVFile(survey);
        emailSender.sendMail(file);
        return survey;
    }

}
