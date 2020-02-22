package com.soldatu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by florin.soldatu on 09/02/2020.
 */

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.soldatu")
public class HelpMeCookApplication {
    public static void main(String... args) {
        SpringApplication.run(HelpMeCookApplication.class, args);
    }
}
