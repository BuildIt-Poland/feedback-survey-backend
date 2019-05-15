#! /bin/bash

ADDRESS=$1

echo "save questions"

curl -d '[       {
                  "id": "4",
                  "ordinal": 4,
                  "type": "select",
                  "content": "What kind of feedback are you providing?",
                  "required": true,
                  "name": "kind-of-feedback",
                  "answerType": "kindOfFeedback"
                },
                {
                  "id": "5",
                  "ordinal": 5,
                  "type": "radio",
                  "content": "Please state if you would like this feedback directly to be shared with the individual",
                  "required": true,
                  "name": "shared",
                  "answerType": "yesNo"
                },
                {
                  "id": "6",
                  "ordinal": 6,
                  "type": "radio",
                  "content": "Please state if you would like to receive copy of this feedback",
                  "required": true,
                  "name": "receive-copy",
                  "answerType": "yesNo"
                },
                {
                  "id": "8",
                  "ordinal": 8,
                  "type": "rating",
                  "content": "How would you rate overall performance of this individual in their current role?",
                  "required": true,
                  "name": "overall-performance",
                  "answerType": "rating"
                },
                {
                  "id": "9",
                  "ordinal": 9,
                  "type": "rating",
                  "content": "Based on the role this individual is performing in your organisation, how satisfied are you that they are performing to the best of their abilities?",
                  "required": true,
                  "name": "satisfied",
                  "answerType": "rating"
                },
                {
                  "id": "10",
                  "ordinal": 10,
                  "type": "rating",
                  "content": "How would you rate their contribution to your team, the company or to your customer?",
                  "required": true,
                  "name": "contribution",
                  "answerType": "rating"
                },
                {
                  "id": "11",
                  "ordinal": 11,
                  "type": "rating",
                  "content": "Given what you know of this person’s performance, how likely would you look to retain them longer term?",
                  "required": true,
                  "name": "performance",
                  "answerType": "rating"
                },
                {
                  "id": "12",
                  "ordinal": 12,
                  "type": "open-ended",
                  "content": "Are there areas of improvement that you would recommend for this individual that would help them to accomplish work more effectively?",
                  "required": false,
                  "name": "areas-of-improvement",
                  "answerType": null
                },
                {
                  "id": "13",
                  "ordinal": 13,
                  "type": "open-ended",
                  "content": "Please provide any information where an individual has gone above and beyond and you would like them to be recognised?",
                  "required": false,
                  "name": "test13",
                  "answerType": null
                },
                {
                  "id": "14",
                  "ordinal": 14,
                  "type": "open-ended",
                  "content": "Any additional information you would like to provide",
                  "required": false,
                  "name": "additional-information",
                  "answerType": null
                }]' -H "Content-Type: application/json" -X POST $ADDRESS/saveQuestions

