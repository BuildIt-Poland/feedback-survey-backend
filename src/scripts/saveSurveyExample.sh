#! /bin/bash

ADDRESS=$1

echo "save survey example"

curl -d '{
           "id" : "1",
           "surveyId" : "1",
           "answers":[
                {"questionName": "kind-of-feedback", "answer": "monthly"},
                {"questionName": "shared", "answer": "Yes"},
                {"questionName": "receive-copy", "answer": "No"}
            ],
           "employeeName" : "Joanna"
         }' -H "Content-Type: application/json" -X POST $ADDRESS/saveSurvey

