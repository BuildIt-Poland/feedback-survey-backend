package com.buildit.email;

class EmailTestData {

    Email prepareEmail() {
        Email email = new Email();
        email.setId(100L);
        email.setSender("sender@test.com");
        email.setRecipients("recipient@test.com,recipient2@test.com");
        email.setSubject("subject");
        email.setBodyText("test");
        email.setBodyHtml("test");
        return email;
    }
}
