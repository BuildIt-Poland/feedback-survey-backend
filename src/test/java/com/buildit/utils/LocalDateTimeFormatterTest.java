package com.buildit.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalDateTimeFormatterTest {

    @Test
    void parseDate() {
        //GIVEN
        LocalDateTime date = LocalDateTime.of(2019, 4, 26, 11, 22, 33, 44);

        //WHEN
        String result = LocalDateTimeFormatter.parseDate(date);

        //THEN
        assertEquals("2019-04-26 11:22:33", result);
    }
}