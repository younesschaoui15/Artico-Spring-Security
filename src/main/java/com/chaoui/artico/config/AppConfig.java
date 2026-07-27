package com.chaoui.artico.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            System.out.println("# App Started Successfully");
        };
    }
}
