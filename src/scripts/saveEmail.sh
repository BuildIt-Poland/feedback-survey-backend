#! /bin/bash

ADDRESS=$1

echo "save email"

curl -d '{
               "id":"1",
               "sender":"mail@gmail.com",
               "recipients":"mail@gmail.com",
               "subject":"Feedback on {employee_name} from {client_id}",
               "bodyText":"Hi!\nThe client with ID: {client_id} just finished a survey about {employee_name}\nPlease find attached results.\nCheers,\nSurveyBot",
               "bodyHtml":"Hi!\nThe client with ID: {client_id} just finished a survey about {employee_name}\nPlease find attached results.\nCheers,\nSurveyBot"
          }' -H "Content-Type: application/json" -X POST $ADDRESS/saveEmail

