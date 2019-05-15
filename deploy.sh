#!/bin/bash

# Parameters
source configuration.txt

# Installation
npm install -g serverless
npm install --save-dev serverless-apigw-binary
npm install serverless-domain-manager --save-dev


# Create domain
serverless create_domain


# Build project
./mvnw clean install


# Deploy the Service 'feedback-survey'
echo "Deploy 'feedback-survey'"
sls deploy --stage $stage --domain $domain


# Deploy Service 'feedback-survey-export'
echo "Deploy 'feedback-survey-export'"
cd export
sls deploy --stage $stage --domain $domain
cd ..


# Initial data
cd src/scripts
ADDRESS="https://feedback-survey-$stage.$domain/api"
./saveAnswerTypes.sh $ADDRESS
echo ""
./saveQuestions.sh $ADDRESS
echo ""
./saveEmail.sh $ADDRESS $sender $recipients
