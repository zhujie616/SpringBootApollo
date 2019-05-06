package com.example;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableApolloConfig
public class SpringBootApolloApp {
	
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApolloApp.class, args);
    }
}



