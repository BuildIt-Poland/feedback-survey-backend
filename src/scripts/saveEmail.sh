#! /bin/bash

ADDRESS="https://------.execute-api.us-east-1.amazonaws.com/dev"

curl -d '{
               "id":"1",
               "sender":"mail@gmail.com",
               "recipients":"mail@gmail.com",
               "subject":"Feedback",
               "bodyText":"bodyText",
               "bodyHtml":"bodyHtml"
          }' -H "Content-Type: application/json" -X POST $ADDRESS/saveEmail

