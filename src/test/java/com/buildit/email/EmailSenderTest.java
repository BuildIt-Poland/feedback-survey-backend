package com.buildit.email;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class EmailSenderTest {

    @Mock
    private EmailDao emailDao;

    @InjectMocks
    private EmailSender emailSender;

    @Test
    void sendMailWhenNotGivenEmailConfiguration() throws IOException {
        //GIVEN
        File file = File.createTempFile("test", "csv");

        //WHEN
        boolean result = emailSender.sendMail(file, new HashMap<>());

        //THEN
        assertFalse(result);
    }

    @Test
    void sendMailWhenGivenEmailConfiguration() throws IOException {
        //GIVEN
        Email email = new EmailTestData().prepareEmail();
        File file = File.createTempFile("test", "csv");

        //WHEN
        when(emailDao.getEmailConfiguration()).thenReturn(Optional.of(email));

        boolean result = emailSender.sendMail(file, new HashMap<>());

        //THEN
        assertFalse(result);
    }
}