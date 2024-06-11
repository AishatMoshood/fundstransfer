package com.dot.ai;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket createRestWebApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("funds")
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.dot.ai.http.api"))
        .paths(PathSelectors.any())
        .build();
  }

  @Bean
  public Docket createRestRpcApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("web")
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.dot.ai.http.controller"))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Dot Ai for funds transfer")
        .description("Dot Ai for funds transfer")
        .version("0.0.1-SNAPSHOT")
        .build();
  }
}
