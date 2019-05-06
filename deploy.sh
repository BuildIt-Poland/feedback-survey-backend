#!/bin/bash

# Deploy the Service 'feedback-survey'
sls deploy

# Deploy Service 'feedback-survey-export'
cd export
sls deploy