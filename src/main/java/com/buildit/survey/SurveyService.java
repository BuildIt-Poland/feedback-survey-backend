package com.buildit.survey;

import com.buildit.email.EmailSender;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

class SurveyService {

    private final EmailSender emailSender;
    private final SurveyDao surveyDao;
    private final ExportSurveyService exportSurveyService;

    SurveyService(EmailSender emailSender, SurveyDao surveyDao, ExportSurveyService exportSurveyService) {
        this.emailSender = emailSender;
        this.surveyDao = surveyDao;
        this.exportSurveyService = exportSurveyService;
    }

    SurveyDTO save(SurveyDTO surveyDTO) throws IOException {
        surveyDTO.setSavedDate(LocalDateTime.now());
        Survey survey = new SurveyMapper().mapToEntity(surveyDTO);
        surveyDao.save(survey);

        sendEmail(surveyDTO);
        return surveyDTO;
    }

    private void sendEmail(SurveyDTO surveyDTO) throws IOException {
        File file = exportSurveyService.generateCSVFile(surveyDTO);
        Map<String, String> parameters = prepareParameters(surveyDTO);
        emailSender.sendMail(file, parameters);
    }

    private Map<String, String> prepareParameters(SurveyDTO surveyDTO) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("client_id", surveyDTO.getSurveyId());
        parameters.put("employee_name", surveyDTO.getEmployeeName());
        return parameters;
    }

}
