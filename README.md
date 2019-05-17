# About
The application automatically configures AWS and creates a database with initial values.
It's a serverless application using AWS Lambda functions written in Java, API Gateway, Simple Email Service and S3, frontend based on react/grommet.
The data will be stored in a DynamoDB, and the service will be deployed to AWS.


# Quick installation 
## Requirements:
1. Java 8 (jdk 8)
2. Node.js v6.5.0 or later
3. An AWS account

## Run:
1. In the file 'configuration.txt' enter parameters values
2. Run file 'deploy.sh':
```
./deploy.sh
```

## Verify mail on aws (sender and receiver)
1. Enter the Amazon SES Console
2. Click Verify an Email Address
3. You will receive a verification email

[More about email verification, steps 1-2](https://aws.amazon.com/getting-started/tutorials/send-an-email/)


------------------------------------------------------------------------------------------------------------------------

# Development 
## Pre-requisites

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
    
2. Plugin for binary files
    ```
    npm install --save-dev serverless-apigw-binary
    ```
    Source: https://github.com/maciejtreder/serverless-apigw-binary
    
3. Plugin for create domain
    ```
    npm install serverless-domain-manager --save-dev
    ```
    Source: https://github.com/amplify-education/serverless-domain-manager
    
## Build and deploy
If you in the project directory
1. Create custom domain (domain is defined in serverless.yml):
    ```
    serverless create_domain
    ```
2. Build project:
    ```
    ./mvnw clean install
    ```
3. Deploy the Service 'feedback-survey':
    ```
    sls deploy
    ```
4. Deploy Service 'feedback-survey-export'. The service is separate, because I defined other gateway API, which support binary files.
    ```
    cd export
    sls deploy
    ```

## AWS configuration 

[Verify a New Email Address, steps 1-2](https://aws.amazon.com/getting-started/tutorials/send-an-email/)

## More information about Serverless:

https://serverless.com/framework/docs/providers/aws/guide/quick-start/

https://serverless.com/blog/how-to-create-a-rest-api-in-java-using-dynamodb-and-serverless/
