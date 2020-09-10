package com.im.user.config;

import javax.sql.DataSource;

import com.im.user.exception.WebResponseExceptionTranslator;
import com.im.user.integration.IntegrationAuthenticationFilter;
import com.im.user.integration.IntegrationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

/**
 * 认证授权服务端
 * 
 * @author Jim.wang
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
    private DataSource dataSource;
	
	@Autowired
    private IntegrationUserDetailsService userDetailsService;
	
	@Autowired
    private WebResponseExceptionTranslator webResponseExceptionTranslator;
	
	@Autowired
    private IntegrationAuthenticationFilter integrationAuthenticationFilter;
	
	@Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
	
	@Bean 
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }
	
	@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails());
    }
	
	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore())
        	.userDetailsService(userDetailsService)
        	.authenticationManager(authenticationManager)
        	.exceptionTranslator(webResponseExceptionTranslator)
        	.tokenServices(defaultTokenServices());

    }
	
	 /**
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     * @return
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetails());
        tokenServices.setAccessTokenValiditySeconds(60*60*12); // token有效期自定义设置，默认12小时
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);//默认30天，这里修改
        return tokenServices;
    }
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    	security.allowFormAuthenticationForClients()
    		.tokenKeyAccess("permitAll()")
    		.checkTokenAccess("isAuthenticated()")
    		.addTokenEndpointAuthenticationFilter(integrationAuthenticationFilter);
    }
	
}
