 ADDRESS=$1
 region=$2
 awsKey=$3

 echo "save answer types"


 body="[
                   {
                      \"type\":\"kindOfFeedback\",
                      \"values\":[
                         \"monthly\",
                         \"quarterly\",
                         \"annual\"
                      ]
                   },
                   {
                      \"type\":\"yesNo\",
                      \"values\":[
                         \"Yes\",
                         \"No\"
                      ]
                   },
                   {
                      \"type\":\"rating\",
                      \"values\":[
                         \"Below expectations\",
                         \"Average\",
                         \"Good\",
                         \"Excellent\"
                      ]
                   }
                ]"

  bash invoke.sh \
      POST \
      $awsKey \
      $ADDRESS/saveAnswerTypes \
      "${body}" \
      $region