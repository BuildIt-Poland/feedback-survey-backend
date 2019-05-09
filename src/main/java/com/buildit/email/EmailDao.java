package com.buildit.email;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.buildit.dynamoDB.TableMapper;

import java.util.List;
import java.util.Optional;

public class EmailDao {

    private final TableMapper tableMapper;

    public EmailDao(TableMapper tableMapper) {
        this.tableMapper = tableMapper;
    }

    Optional<Email> getEmailConfiguration() {
        DynamoDBMapper mapper = tableMapper.getDynamoDBMapper(Email.EMAIL_TABLE_NAME);
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
        List<Email> emailConfigurations = mapper.scan(Email.class, scanExp);
        return emailConfigurations.size() == 1 ?
                Optional.of(emailConfigurations.get(0)) :
                Optional.empty();
    }

    void save(Email email) {
        DynamoDBMapper mapper = tableMapper.getDynamoDBMapper(Email.EMAIL_TABLE_NAME);
        mapper.save(email);
    }
}
