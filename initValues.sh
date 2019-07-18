#!/bin/bash

# Parameters - please enter correct values

stage="prod"
region="us-east-1"
domain="buildit.digital"
customDomain="feedback-survey-prod.buildit.digital"
#You can check your certificate for domain here: https://console.aws.amazon.com/acm/
certificateArn="arn:aws:acm:us-east-1:..."

#aws_access_key_id:aws_secret_access_key
awsKey="aws_access_key_id:aws_secret_access_key"

# Email
sender="sender@test.com"
# Use a comma as a delimiter for recipients
recipients="recipient1@test.com,recipient2@test.com"

#-----------------------------------------------------------------------------------------------------------------------

# Initial data
cd src/scripts
ADDRESS="https://$customDomain/api"
./saveAnswerTypes.sh $ADDRESS $region $awsKey
echo ""
./saveQuestions.sh $ADDRESS $region $awsKey
echo ""
./saveEmail.sh $ADDRESS $region $awsKey $sender $recipients
