package com.yolo.tool.config;


import com.yolo.tool.properties.SpringDocProperties;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Component
@EnableSwagger2WebMvc
@RequiredArgsConstructor
// 对JSR303提供支持
@Import(BeanValidatorPluginsConfiguration.class)
public class Knife4jConfiguration {

    private final SpringDocProperties springDocProperties;

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        SpringDocProperties.InfoProperties info = springDocProperties.getInfo();
        SpringDocProperties.Contact contact = info.getContact();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title(info.getTitle())
                        .description(info.getDescription())
                        .termsOfServiceUrl(info.getTermsOfServiceUrl())
                        .contact(new Contact(contact.getName(), contact.getUrl(), contact.getEmail()))
                        .version("1.0")
                        .build())
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //这里指定Controller扫描包路径
//                .apis(RequestHandlerSelectors.basePackage("com.github.xiaoymin.knife4j.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}