package com.zbf.zbfapibackend;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.zbf.zbfapibackend.bizmq.BiMqConstant;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.zbf.zbfapibackend.mapper")
@ComponentScan("com.zbf.*")
@ComponentScan("com.yupi.*")
@EnableDubbo
public class ZbfapiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZbfapiBackendApplication.class, args);
    }

}
