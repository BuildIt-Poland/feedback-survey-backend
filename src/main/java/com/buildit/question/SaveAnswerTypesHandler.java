package com.buildit.question;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.buildit.dynamoDB.TableMapperImpl;
import com.buildit.response.ApiGatewayResponse;
import com.buildit.response.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class SaveAnswerTypesHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final static Logger LOGGER = LogManager.getLogger(SaveAnswerTypesHandler.class);

    private final QuestionDao questionDao;

    public SaveAnswerTypesHandler() {
        questionDao = new QuestionDao(new TableMapperImpl());
    }

    public SaveAnswerTypesHandler(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String body = (String) input.get("body");
            List<AnswerType> answerTypes = objectMapper.readValue(body, new TypeReference<List<AnswerType>>(){});

            questionDao.saveAnswerTypes(answerTypes);

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(answerTypes)
                    .build();
        } catch (Exception ex) {
            LOGGER.error("Error in saving answer types: " + ex);
            Response responseBody = new Response("Error in saving answer types: ", input);

            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .build();
        }
    }

}
