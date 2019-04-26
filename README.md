# Pre-requisites

Serverless:
1. Node.js v6.5.0 or later.
2. Serverless CLI v1.9.0 or later. You can run ``` npm install -g serverless``` to install it.
3. An AWS account. 
4. Set-up your Provider Credentials -> [Watch the video on setting up credentials](https://www.youtube.com/watch?v=KngM5bfpttA)

Other:
1. Install jdk 1.8 and set JAVA_HOME path
    ```
    export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_201.jdk/Contents/Home
    ```
    
    Verify:
    ```
    java --version
    ```
    
2. Download maven 3.6.0 and set environment variables, [the instruction for mac](https://hathaway.cc/2008/06/how-to-edit-your-path-environment-variables-on-mac/)
    ```
    export M2_HOME="/Library/Maven/apache-maven-3.6.0"
    export PATH=${PATH}:${M2_HOME}/bin
    ```
    
    Verify:
    ```
    mvn --version
    ```
    
3. Plugin for binary files
    ```
    npm install --save-dev serverless-apigw-binary
    ```
    Source: https://github.com/maciejtreder/serverless-apigw-binary
    
4. Plugin for create domain
    ```
    npm install serverless-domain-manager --save-dev
    ```
    Source: https://github.com/amplify-education/serverless-domain-manager
    
# Build and deploy
If you in the project directory
1. Build project
    ```
    mvn clean install
    ```
2. Deploy the Service 'feedback-survey'
    ```
    sls deploy
    ```
3. Deploy Service 'feedback-survey-export'. The service is separate, because I defined other gateway API, which support binary files
    ```
    cd export
    sls deploy
    ```

# More information about Serverless:

https://serverless.com/framework/docs/providers/aws/guide/quick-start/

https://serverless.com/blog/how-to-create-a-rest-api-in-java-using-dynamodb-and-serverless/


# REST API

Save survey:
```
curl -d '{"surveyId": "12345", "answers":[{"questionId": "1", "answer": "answer123"}]}' -H "Content-Type: application/json" -X POST https://address.execute-api.us-east-1.amazonaws.com/dev/saveSurvey
```
