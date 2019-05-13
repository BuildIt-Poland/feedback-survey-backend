#!/bin/bash

domain="buildit.digital"

# Deploy the Service 'feedback-survey'
sls deploy --domain $domain

# Deploy Service 'feedback-survey-export'
cd export
sls deploy --domain $domain