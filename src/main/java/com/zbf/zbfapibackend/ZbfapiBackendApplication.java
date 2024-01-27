package com.zbf.zbfapibackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zbf.zbfapibackend.mapper")
public class ZbfapiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZbfapiBackendApplication.class, args);
    }

}
