ADDRESS=$1
region=$2
awsKey=$3

echo "save questions"

body="[       {
                  \"ordinal\": 4,
                  \"type\": \"radio\",
                  \"content\": \"What frequency of performance feedback are you providing?\",
                  \"required\": true,
                  \"name\": \"kind-of-feedback\",
                  \"answerType\": \"kindOfFeedback\"
                },
                {
                  \"ordinal\": 5,
                  \"type\": \"radio\",
                  \"content\": \"Would you like this feedback to be shared directly with the individual?\",
                  \"required\": true,
                  \"name\": \"shared\",
                  \"answerType\": \"yesNo\"
                },
                {
                  \"ordinal\": 6,
                  \"type\": \"radio\",
                  \"content\": \"Would you like to receive a copy of this feedback?\",
                  \"required\": true,
                  \"name\": \"receive-copy\",
                  \"answerType\": \"yesNo\"
                },
                {
                  \"ordinal\": 8,
                  \"type\": \"rating\",
                  \"content\": \"How would you rate overall performance of this individual in their current role?\",
                  \"required\": true,
                  \"name\": \"overall-performance\",
                  \"answerType\": \"rating\"
                },
                {
                  \"ordinal\": 9,
                  \"type\": \"rating\",
                  \"content\": \"Based on the role this individual is performing in your organisation, how satisfied are you that they are performing to the best of their abilities?\",
                  \"required\": true,
                  \"name\": \"satisfied\",
                  \"answerType\": \"rating\"
                },
                {
                  \"ordinal\": 10,
                  \"type\": \"rating\",
                  \"content\": \"How would you rate their contribution to your team, the company or to your customer?\",
                  \"required\": true,
                  \"name\": \"contribution\",
                  \"answerType\": \"rating\"
                },
                {
                  \"ordinal\": 11,
                  \"type\": \"rating\",
                  \"content\": \"Given what you know of this person’s performance, how likely would you look to retain them longer term?\",
                  \"required\": true,
                  \"name\": \"performance\",
                  \"answerType\": \"rating\"
                },
                {
                  \"ordinal\": 12,
                  \"type\": \"open-ended\",
                  \"content\": \"Are there areas of improvement that you would recommend for this individual that would help them to accomplish work more effectively?\",
                  \"required\": false,
                  \"name\": \"areas-of-improvement\",
                  \"answerType\": null
                },
                {
                  \"ordinal\": 13,
                  \"type\": \"open-ended\",
                  \"content\": \"Please provide any information where an individual has gone above and beyond and you would like them to be recognized?\",
                  \"required\": false,
                  \"name\": \"go-above-and-beyond\",
                  \"answerType\": null
                },
                {
                  \"ordinal\": 14,
                  \"type\": \"open-ended\",
                  \"content\": \"Any additional information you would like to provide\",
                  \"required\": false,
                  \"name\": \"additional-information\",
                  \"answerType\": null
                }]"


  bash invoke.sh \
      POST \
      $awsKey \
      $ADDRESS/saveQuestions \
      "${body}" \
      $region


