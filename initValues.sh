#!/bin/bash

# Parameters
source configuration.txt


# Initial data
cd src/scripts
ADDRESS="https://$customDomain/api"
./saveAnswerTypes.sh $ADDRESS $region $awsKey
echo ""
./saveQuestions.sh $ADDRESS $region $awsKey
echo ""
./saveEmail.sh $ADDRESS $region $awsKey $sender $recipients
