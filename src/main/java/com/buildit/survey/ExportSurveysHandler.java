package com.buildit.survey;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.buildit.dynamoDB.TableMapperImpl;
import com.buildit.question.QuestionDao;
import com.buildit.response.ApiGatewayResponse;
import com.buildit.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExportSurveysHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Logger LOGGER = LogManager.getLogger(ExportSurveysHandler.class);

    private final ExportSurveyService exportSurveyService;

    public ExportSurveysHandler() {
        exportSurveyService = new ExportSurveyService(
                new QuestionDao(new TableMapperImpl()),
                new SurveyDao(new TableMapperImpl())
        );
    }

    public ExportSurveysHandler(ExportSurveyService exportSurveyService) {
        this.exportSurveyService = exportSurveyService;
    }

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            byte[] fileContent = exportSurveyService.exportToCSVFile();

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setHeaders(initHeaders())
                    .setBinaryBody(fileContent)
                    .build();
        } catch (IOException ex) {
            LOGGER.error("Error in exporting surveys: " + ex);
            Response responseBody = new Response("Error in exporting surveys: ", input);

            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .build();
        }
    }

    private Map<String, String> initHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Disposition", "attachment; filename=\"feedback_survey.csv\"");
        headers.put("Content-Type", "text/csv");
        headers.put("Accept", "text/csv");
        return headers;
    }
}
