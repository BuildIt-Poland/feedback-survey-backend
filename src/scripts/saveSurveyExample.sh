#! /bin/bash

ADDRESS="https://-------.execute-api.us-east-1.amazonaws.com/dev"

curl -d '{
           "id" : "1",
           "surveyId" : "1",
           "answers":[
                {"questionId": "4", "answer": "answer4"},
                {"questionId": "5", "answer": "answer5"},
                {"questionId": "6", "answer": "answer6"}
            ]
         }' -H "Content-Type: application/json" -X POST $ADDRESS/saveSurvey

