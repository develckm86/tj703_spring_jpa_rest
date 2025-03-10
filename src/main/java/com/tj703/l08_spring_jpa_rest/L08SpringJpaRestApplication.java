package com.tj703.l08_spring_jpa_rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@SpringBootApplication
public class L08SpringJpaRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(L08SpringJpaRestApplication.class, args);
    }

}

@Configuration
class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .modules(new Hibernate6Module()
                                .configure(Hibernate6Module.Feature.FORCE_LAZY_LOADING,true)
                        ,new JavaTimeModule()) // Hibernate 프록시 지원 추가
                .build();
    }
}
