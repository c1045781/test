package com.gk.university;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gk.university.mapper")
public class UniversityApplication {

    public static void main(String[] args) {

        SpringApplication.run(UniversityApplication.class, args);
    }

}
