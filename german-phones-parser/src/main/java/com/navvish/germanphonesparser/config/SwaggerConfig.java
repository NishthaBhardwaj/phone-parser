package com.navvish.germanphonesparser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final Contact DEFAULT_CONTACT = new Contact(
            "Nishtha Bhardwaj", "www.nishtha.com", "nishtha.bhardwaj10@gmail.com ");
    //    public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
//            "Awesome API Title" ," Awesome API Documentation" ,"1.0" , "urn:tos" ,
//            DEFAULT_CONTACT,"Apache 2.0" ,""
//
//    );
    private static final Set<String> DEFAULT_PRODUCERS_AND_CONSUMERS =
            Set.of("multipart/form-data");

    // Bean - Docket
    // swagger 2
    // All the path
    // All the apis

    @Bean
    public Docket swaggerConfiguration(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/german-phones-parser/*"))
                .apis(RequestHandlerSelectors.basePackage("com.navvish"))
                .build()
                .apiInfo(apiDetails());


    }

    private ApiInfo apiDetails(){
        return new ApiInfo(
                "German Phone Numbers Parser",
                "NavVis Code Challenge",
                "1.0",
                "Testing..",
                new Contact("Nishtha Bhardwaj" ," ","nishtha.bhardwaj10@gmail.com"),
                "API License",
                "https://www.navvis.com",
                Collections.emptyList());
    }
}
