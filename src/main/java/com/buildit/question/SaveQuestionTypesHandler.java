package com.buildit.question;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.buildit.utils.ApiGatewayResponse;
import com.buildit.utils.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class SaveQuestionTypesHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final static Logger LOGGER = LogManager.getLogger(SaveQuestionTypesHandler.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String body = (String) input.get("body");
            List<QuestionType> questions = objectMapper.readValue(body, new TypeReference<List<QuestionType>>(){});

            new QuestionService().saveQuestionTypes(questions);

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(questions)
                    .build();
        } catch (Exception ex) {
            LOGGER.error("Error in saving question types: " + ex);
            Response responseBody = new Response("Error in saving question types: ", input);

            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .build();
        }
    }

}
