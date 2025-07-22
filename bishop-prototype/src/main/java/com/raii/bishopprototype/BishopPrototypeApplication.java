package com.raii.bishopprototype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BishopPrototypeApplication {
    public static void main(String[] args) {
        SpringApplication.run(BishopPrototypeApplication.class, args);
    }
}
