#!/bin/bash

# Parameters
source configuration.txt


# Build project
#./mvnw clean install


# Deploy the Service 'feedback-survey'
service="feedback-survey"
bucket=$service-$stage

surveyTableName=feedback_survey-$stage
questionTableName=feedback_question-$stage
answerTypeTableName=feedback_answer-type-$stage
emailTableName=feedback_email-$stage

aws s3 mb s3://$bucket --region $region

echo "Deploy 'feedback-survey'"
echo "CloudFormation packaging..."
aws cloudformation package \
    --region ${region} \
    --template-file template.yml \
    --output-template-file packaged-template.yml \
    --s3-bucket ${bucket} \

echo "CloudFormation deploying..."
aws cloudformation deploy  \
    --region ${region} \
    --template-file packaged-template.yml \
    --stack-name ${stage}-${service} \
    --capabilities CAPABILITY_NAMED_IAM \
    --parameter-override Stage=${stage} \
        surveyTableName=$surveyTableName \
        questionTableName=$questionTableName \
        answerTypeTableName=$answerTypeTableName \
        emailTableName=$emailTableName \
        domain=$domain \
        customDomain=$customDomain \
        certificateArn=$certificateArn \

