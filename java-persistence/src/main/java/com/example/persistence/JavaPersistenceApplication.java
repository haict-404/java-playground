package com.example.persistence;

import com.example.persistence.repository.AccountRepository;
import com.example.persistence.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;

@SpringBootApplication
public class JavaPersistenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaPersistenceApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(AccountService accountService) {
        return args -> {
            // This is where you can add any startup logic if needed
            System.out.println("Java Persistence Application started successfully!");

            accountService.updateAccount();

        };
    }
}
