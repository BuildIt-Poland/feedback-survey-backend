package com.buildit.question;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.buildit.response.ApiGatewayResponse;
import com.buildit.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class GetQuestionsHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final static Logger LOGGER = LogManager.getLogger(GetQuestionsHandler.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            Questions questions = new QuestionService().getAllQuestions();

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(questions)
                    .build();
        } catch (Exception ex) {
            LOGGER.error("Error in getting questions: " + ex);
            Response responseBody = new Response("Error in getting questions: ", input);

            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .build();
        }
    }

}
