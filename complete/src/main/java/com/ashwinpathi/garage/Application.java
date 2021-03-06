package com.ashwinpathi.garage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ashwinpathi.garage"})
public class Application {
    public static void main(String[] args) {
        System.out.println("Application class main method");
        SpringApplication.run(Application.class, args);
    }
}
