#!/bin/bash

# Parameters
source configuration.txt


# Installation serverless
npm install --save-dev serverless-apigw-binary
npm install serverless-domain-manager --save-dev


# Create domain
serverless create_domain


# Build project
./mvnw clean install


# Deploy the Service 'feedback-survey'
echo "Deploy 'feedback-survey'"
sls deploy --stage $stage --domain $domain --region $region


# Deploy Service 'feedback-survey-export'
echo "Deploy 'feedback-survey-export'"
cd export
sls deploy --stage $stage --domain $domain --region $region
cd ..


# Initial data
cd src/scripts
ADDRESS="https://$domain/api"
./saveAnswerTypes.sh $ADDRESS $region $awsKey
echo ""
./saveQuestions.sh $ADDRESS $region $awsKey
echo ""
./saveEmail.sh $ADDRESS $region $awsKey $sender $recipients
