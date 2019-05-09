package com.buildit.email;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal;
import com.buildit.common.DaoTestHelper;
import com.buildit.dynamoDB.TableMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class EmailDaoTest {

    private static AmazonDynamoDBLocal amazonDynamoDBLocal;

    private static DynamoDBMapper dynamoDBMapper;

    @Mock
    private TableMapper tableMapper;

    @InjectMocks
    private EmailDao emailDao;

    @BeforeAll
    public static void setUpClass() {
        DaoTestHelper.initSqlite();
        amazonDynamoDBLocal = DynamoDBEmbedded.create();
        dynamoDBMapper = DaoTestHelper.getDBMapper(amazonDynamoDBLocal, Email.EMAIL_TABLE_NAME);

        DaoTestHelper.createTable(amazonDynamoDBLocal, dynamoDBMapper, Email.class);
    }

    @AfterAll
    public static void tearDownClass() {
        amazonDynamoDBLocal.shutdown();
    }

    @Test
    void getEmailConfiguration() {
        //GIVEN
        Email email = new EmailTestData().prepareEmail();
        dynamoDBMapper.save(email);

        //WHEN
        when(tableMapper.getDynamoDBMapper(any())).thenReturn(dynamoDBMapper);

        Optional<Email> emailOptional = emailDao.getEmailConfiguration();

        //THEN
        assertTrue(emailOptional.isPresent());
    }

    @Test
    void save() {
        //GIVEN
        Email email = new EmailTestData().prepareEmail();

        //WHEN
        when(tableMapper.getDynamoDBMapper(any())).thenReturn(dynamoDBMapper);

        emailDao.save(email);

        //THEN
        verify(tableMapper).getDynamoDBMapper(Email.EMAIL_TABLE_NAME);
        PaginatedList<Email> emails = dynamoDBMapper.scan(Email.class, new DynamoDBScanExpression());
        assertEquals(1, emails.size());
    }

}