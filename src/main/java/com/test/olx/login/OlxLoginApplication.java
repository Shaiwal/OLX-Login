package com.test.olx.login;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
public class OlxLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlxLoginApplication.class, args);
	}
	
	@Bean
	public Docket getCustomizedDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.any())//add path filters here by using ant instead of any.
				.apis(RequestHandlerSelectors.basePackage("com.test.olx.login"))
				.build()
				.apiInfo(getAPIInfo());
	}
	
	private ApiInfo getAPIInfo() {
		return new ApiInfo(
		"OLX Login Application Documentation",
		"OLX Login Application Documentation Description",
		"1.0.0", 
		"https://www.zensar.com/terms",
		new Contact("Shaiwal Sharma", "https://www.zensar.com", "shaiwal.sharma@zensar.com"),
		"GPL", 
		"https://www.zensar.com/LICENSE", new ArrayList<VendorExtension>());
	}

}
