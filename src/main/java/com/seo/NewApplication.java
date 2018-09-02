package com.seo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude =  {DataSourceAutoConfiguration.class})
//@SpringBootApplication
@EnableScheduling
@ServletComponentScan
@EnableCaching
public class NewApplication {

    public static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(NewApplication.class, args);
    }
}
