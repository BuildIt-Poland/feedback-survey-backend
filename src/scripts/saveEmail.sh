#! /bin/bash

ADDRESS=$1
sender=$2
recipients=$3

echo "save email"

curl -d '{
               "id":"1",
               "sender":"'$sender'",
               "recipients":"'$recipients'",
               "subject":"Feedback on {employee_name} from {client_id}",
               "bodyText":"Hi!\nThe client with ID: {client_id} just finished a survey about {employee_name}.\nPlease find attached results.\nCheers,\nSurveyBot"
          }' -H "Content-Type: application/json" -X POST $ADDRESS/saveEmail

