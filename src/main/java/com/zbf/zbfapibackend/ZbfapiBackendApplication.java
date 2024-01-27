package com.zbf.zbfapibackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.zbf.zbfapibackend.mapper")
@ComponentScan("com.zbf.zbfapiclientsdk")
public class ZbfapiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZbfapiBackendApplication.class, args);
    }

}
