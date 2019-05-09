package com.buildit.email;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.buildit.dynamoDB.TableMapperImpl;
import com.buildit.response.ApiGatewayResponse;
import com.buildit.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class SaveEmailHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final static Logger LOGGER = LogManager.getLogger(SaveEmailHandler.class);

    private final EmailDao emailDao;

    public SaveEmailHandler() {
        emailDao = new EmailDao(new TableMapperImpl());
    }

    SaveEmailHandler(EmailDao emailDao) {
        this.emailDao = emailDao;
    }

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String body = (String) input.get("body");
            Email email = objectMapper.readValue(body, Email.class);
            emailDao.save(email);

            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(email)
                    .build();
        } catch (Exception ex) {
            LOGGER.error("Error in saving email: " + ex);
            Response responseBody = new Response("Error in saving email: ", input);

            return ApiGatewayResponse.builder()
                    .setStatusCode(500)
                    .setObjectBody(responseBody)
                    .build();
        }
    }

}
