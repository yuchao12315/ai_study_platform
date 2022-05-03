package com.csuft.uc;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.csuft.uc.mapper")
@ComponentScan("com.csuft.common.utils")
@ComponentScan("com.csuft.uc")
public class UCApp {
    public static void main(String[] args) {
        SpringApplication.run(UCApp.class, args);
    }
}
