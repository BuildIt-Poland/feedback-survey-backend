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
sls deploy --stage $stage --domain $customDomain --region $region


# Deploy Service 'feedback-survey-export'
echo "Deploy 'feedback-survey-export'"
cd export
sls deploy --stage $stage --domain $customDomain --region $region
cd ..
