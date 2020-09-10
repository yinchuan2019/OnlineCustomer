package com.app.interfaces.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 资源服务端
 * 
 * @author Jim.wang
 */
@Configuration
@EnableResourceServer
@EnableOAuth2Client
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Override
    public void configure(HttpSecurity http) throws Exception {
        http
        	.authorizeRequests()
        	.antMatchers("/actuator/**", "/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/validatorUrl", "/swagger-ui.html",
        			"/webjars/**", "/hystrix/**", "/hystrix", "*.stream", "/api/**").permitAll()
        	.and()
        	.authorizeRequests()
        	.anyRequest().authenticated();
    }
}
