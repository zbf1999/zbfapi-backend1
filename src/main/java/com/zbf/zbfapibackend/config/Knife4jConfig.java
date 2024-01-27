package com.zbf.zbfapibackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;

@Configuration
@EnableSwagger2
@EnableKnife4j
@Profile("dev")
public class Knife4jConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 此处根据情况自行添加扫描的包路径
                .apis(RequestHandlerSelectors.basePackage("com.zbf.zbfapibackend.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("API 文档")
                // 描述
                .description("API 文档")
                // 版本号
                .version("0.0.1")
                .build();
    }
}
