package com.app.interfaces.server.feign.config;

import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;

@Configuration
public class FeignConfig implements RequestInterceptor {

	@Bean
	public Retryer feignRetryer() {
		return new Retryer.Default(100, 120000, 1);
	}
	
	@Override
    public void apply(RequestTemplate requestTemplate) {
        Map<String,String> headers = getHeaders(getHttpServletRequest());
        for(String headerName : headers.keySet()){
        	if("Authorization".equals(headerName)){
        		requestTemplate.header(headerName, getHeaders(getHttpServletRequest()).get(headerName));
        	}
        }
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

}