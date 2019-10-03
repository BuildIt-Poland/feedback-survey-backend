#!/bin/bash

domain="feedback-survey-dev.buildit.digital"

# Deploy the Service 'feedback-survey'
#sls deploy --domain $domain

# Deploy Service 'feedback-survey-export'
cd export
#sls deploy --domain $domain