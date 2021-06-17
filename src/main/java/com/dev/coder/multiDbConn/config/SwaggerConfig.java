package com.dev.coder.multiDbConn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.builders.PathSelectors.ant;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerConfiguration(){
        return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .paths(ant("/v1/**"))
                    .apis(RequestHandlerSelectors.basePackage("com.dev.coder.multiDbConn"))
                    .build()
                    .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo(){
        return new ApiInfo(
                "Springboot Api Documentation",
                "Swagger Documentation for MultiDatabase Connection Example",
                "0.0.1",
                "",
                new Contact("tfizz","https://github.com/tfizz",""),
                "Apache Licence Version 2",
                "https://opensource.org/licenses/Apache-2.0",
                new ArrayList<>()
        );
    }
}
