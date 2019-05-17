package com.buildit.survey;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class Answer {

    private String questionName;

    private String answer;

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "questionName='" + questionName + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
