package com.im.user.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.Collections;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
//import org.springframework.security.core.context.SecurityContext;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Swagger2Config {
	
	 @Value("${swagger.auth.server}")
	    private String AUTH_SERVER;
	 
	 @Bean
	    public Docket docket() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .enable(true)//是否激活开关
	                .apiInfo(apiInfo())
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.app.user.web"))
	                .paths(PathSelectors.any())
	                .build()
//	                .pathMapping(SERVICE_NAME)
	                .securitySchemes(Collections.singletonList(securityScheme()))
	                .securityContexts(Collections.singletonList(securityContext()));
	    }

	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                // 页面标题
	                .title("用户模块")
	                .contact(new Contact("Jim.wang", "xxx@xxx.com", "xxx@xxx.com"))
	                .description("接口说明文档")
	                .version("1.0.0")
	                .extensions(Collections.emptyList())
	                .build();
	    }

	    /**
	     * 这个类决定了你使用哪种认证方式，我这里使用密码模式
	     */
	    private SecurityScheme securityScheme() {
	        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(AUTH_SERVER);
	        return new OAuthBuilder()
	                .name("OAuth2")
	                .grantTypes(Collections.singletonList(grantType))
	                .scopes(Arrays.asList(scopes()))
	                .build();
	    }

	    /**
	     * 这里设置 swagger2 认证的安全上下文
	     */
	    private SecurityContext securityContext() {
	        return SecurityContext.builder()
	                .securityReferences(Collections.singletonList(new SecurityReference("OAuth2", scopes())))
	                .forPaths(PathSelectors.any())
	                .build();
	    }

	    /**
	     * 这里是写允许认证的scope
	     */
	    private AuthorizationScope[] scopes() {
	        return new AuthorizationScope[]{
	        };
	    }
}
