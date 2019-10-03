#! /bin/bash

ADDRESS=$1
region=$2
awsKey=$3
sender=$4
recipients=$5

echo "save email"

  body="{\"id\":\"1\",
  \"sender\":\"$sender\",
  \"recipients\":\"$recipients\",
  \"subject\":\"Feedback on {employee_name} from {client_id}\",
  \"bodyHtml\":\"<p>Hi!<br/>The client with ID: {client_id} just finished a survey about {employee_name}.<br/>Please find the attached results.</p><p>Cheers,<br/>SurveyBot</p>\"
  }"


    bash invoke.sh \
        POST \
        $awsKey \
        $ADDRESS/saveEmail \
        "${body}" \
        $region

