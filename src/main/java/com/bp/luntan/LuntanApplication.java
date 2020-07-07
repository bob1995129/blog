package com.bp.luntan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@EnableScheduling
@SpringBootApplication
public class LuntanApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuntanApplication.class, args);
    }


}
