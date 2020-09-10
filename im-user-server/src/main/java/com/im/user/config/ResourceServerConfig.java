package com.im.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 * 资源服务端
 * 
 * @author Jim.wang
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Override
    public void configure(HttpSecurity http) throws Exception {
        http
    	.authorizeRequests()
    	.antMatchers("/register/**", "/v2/api-docs", "/sms/**","/swagger-resources/**","/druid/**").permitAll()
    		.anyRequest().authenticated()
    	.and().logout().permitAll()
    	.and().formLogin().permitAll()
    	.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler())
//    	.and()
//    	.authorizeRequests()
//    	.anyRequest().authenticated()
    	.and()
    	.csrf().disable().httpBasic();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    	resources.resourceId("resource-user").stateless(true);
    }
}
