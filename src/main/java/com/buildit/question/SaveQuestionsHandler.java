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

public class SaveQuestionsHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final static Logger LOGGER = LogManager.getLogger(SaveQuestionsHandler.class);

    private final QuestionDao questionDao;

    public SaveQuestionsHandler() {
        questionDao = new QuestionDao(new TableMapperImpl());
    }

    public SaveQuestionsHandler(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String body = (String) input.get("body");
            List<Question> questions = objectMapper.readValue(body, new TypeReference<List<Question>>() {});

            questionDao.saveQuestions(questions);

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(questions)
                    .build();
        } catch (Exception ex) {
            LOGGER.error("Error in saving questions: " + ex);
            Response responseBody = new Response("Error in saving questions: ", input);

            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .build();
        }
    }

}
