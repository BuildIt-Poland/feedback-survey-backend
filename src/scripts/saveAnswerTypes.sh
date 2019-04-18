#! /bin/bash

ADDRESS="https://------.execute-api.us-east-1.amazonaws.com/prod"

curl -d '[
            {
               "type":"kindOfFeedback",
               "values":[
                  "monthly",
                  "quarterly",
                  "annual"
               ]
            },
            {
               "type":"yesNo",
               "values":[
                  "Yes",
                  "No"
               ]
            },
            {
               "type":"rating",
               "values":[
                  "Bad",
                  "Average",
                  "Good",
                  "Excellent"
               ]
            }
         ]' -H "Content-Type: application/json" -X POST $ADDRESS/saveAnswerTypes

