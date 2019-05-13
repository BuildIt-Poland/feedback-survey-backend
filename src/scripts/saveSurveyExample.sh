#! /bin/bash

ADDRESS=$1

echo "save survey example"

curl -d '{
           "id" : "1",
           "surveyId" : "1",
           "answers":[
                {"questionId": "4", "answer": "answer4"},
                {"questionId": "5", "answer": "answer5"},
                {"questionId": "6", "answer": "answer6"}
            ],
           "employeeName" : "Joanna"
         }' -H "Content-Type: application/json" -X POST $ADDRESS/saveSurvey

