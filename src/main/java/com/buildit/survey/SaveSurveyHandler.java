package com.buildit.survey;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.buildit.utils.ApiGatewayResponse;
import com.buildit.utils.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class SaveSurveyHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final static Logger LOGGER = LogManager.getLogger(SaveSurveyHandler.class);

    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String body = (String) input.get("body");
            Survey survey = objectMapper.readValue(body, Survey.class);

            DynamoDBMapper mapper = new DynamoDBMapper(client);
            mapper.save(survey);

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
