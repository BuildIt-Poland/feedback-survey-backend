#! /bin/bash

ADDRESS=$1

echo "save answer types"

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

