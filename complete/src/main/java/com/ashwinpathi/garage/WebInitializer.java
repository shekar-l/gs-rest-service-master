package com.ashwinpathi.garage;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

public class WebInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        System.out.println("WebInitializer class, configure method ");
        return application.sources(
                 Application.class
                ,GarageDoorManager.class
//                ,SQLiteJDBCManager.class
);
    }
}