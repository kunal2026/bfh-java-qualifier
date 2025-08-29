package com.bfh.qualifier;

import com.bfh.qualifier.service.QualifierRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QualifierApplication {

    public static void main(String[] args) {
        SpringApplication.run(QualifierApplication.class, args);
    }

    @Bean
    public QualifierRunner qualifierRunner() {
        return new QualifierRunner();
    }
}
