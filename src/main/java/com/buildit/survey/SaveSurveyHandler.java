package com.buildit.survey;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.buildit.dynamoDB.TableMapperImpl;
import com.buildit.email.EmailDao;
import com.buildit.email.EmailSender;
import com.buildit.question.QuestionDao;
import com.buildit.response.ApiGatewayResponse;
import com.buildit.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class SaveSurveyHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final static Logger LOGGER = LogManager.getLogger(SaveSurveyHandler.class);

    private final SurveyService surveyService;

    public SaveSurveyHandler() {
        SurveyDao surveyDao = new SurveyDao(new TableMapperImpl());
        QuestionDao questionDao = new QuestionDao(new TableMapperImpl());
        ExportSurveyService exportSurveyService = new ExportSurveyService(questionDao, surveyDao);
        EmailSender emailSender = new EmailSender(new EmailDao(new TableMapperImpl()));
        surveyService = new SurveyService(emailSender, surveyDao, exportSurveyService);
    }

    public SaveSurveyHandler(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String body = (String) input.get("body");
            Survey survey = objectMapper.readValue(body, Survey.class);

            survey = surveyService.save(survey);

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(survey)
                    .build();
        } catch (Exception ex) {
            LOGGER.error("Error in saving survey: " + ex);
            Response responseBody = new Response("Error in saving survey: ", input);

            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .build();
        }
    }

}
