package com.karakoc.mezat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
public class JacksonConfiguration {
    //projenin saatleriyle alakali config dosyasi.
    //gmt yi gmt+3 yapiyor. turkiye saatine ceviriyor yani.

    @Bean
    public ObjectMapper objectMapper() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));

        ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().modules(javaTimeModule).build();
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+3")); // Saat dilimini burada ayarlayın

        return mapper;
    }
}
