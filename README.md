#Pre-requisites

Serverless:
1. Node.js v6.5.0 or later.
2. Serverless CLI v1.9.0 or later. You can run ``` npm install -g serverless``` to install it.
3. An AWS account. 
4. Set-up your Provider Credentials -> [Watch the video on setting up credentials](https://www.youtube.com/watch?v=KngM5bfpttA)

Other:
1. Install jdk 8 and set JAVA_HOME path
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
    
#Build and deploy
If you in the project directory
1. Build project
    ```
    mvn clean install
    ```
2. Deploy the Service
    ```
    sls deploy
    ```

#More information about Serverless:
https://serverless.com/framework/docs/providers/aws/guide/quick-start/

https://serverless.com/blog/how-to-create-a-rest-api-in-java-using-dynamodb-and-serverless/
