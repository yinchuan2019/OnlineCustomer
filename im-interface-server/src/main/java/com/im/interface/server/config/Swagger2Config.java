package com.app.interfaces.server.config;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

//import org.springframework.security.core.context.SecurityContext;
//import springfox.documentation.schema.Collections;

/**
 * Swagger2 UI配置
 *
 * <pre>
 * 通过访问http://your ip:8090/api/swagger-ui.html查看发布的REST接口;
 * </pre>
 */
@Configuration
//@PropertySources({ @PropertySource(value = "classpath:swagger2.properties", ignoreResourceNotFound = true, encoding = "UTF-8") })
@EnableSwagger2
public class Swagger2Config {

    @Value("${swagger.auth.server}")
    private String AUTH_SERVER;

    @Bean
    public Docket docket() {
        StringBuffer url = new StringBuffer();
        url.append("com.app.interfaces.server.module.order.web");
        url.append(",com.app.interfaces.server.module.policy.web");
        url.append(",com.app.interfaces.server.module.product.web");
		url.append(",com.app.interfaces.server.module.activity.web");
        url.append(",com.app.interfaces.server.module.user.web");
        url.append(",com.app.interfaces.server.module.base.web");

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)//是否激活开关
                .apiInfo(apiInfo())
                .select()
                .apis(Swagger2Config.basePackage(url.toString()))
                .paths(PathSelectors.any())
                .build()
//	                .pathMapping(SERVICE_NAME)
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()));
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


    /**
     * Predicate that matches RequestHandler with given base package name for the class of the handler method.
     * This predicate includes all request handlers matching the provided basePackage
     *
     * @param basePackage - base package of the classes
     * @return this
     */
    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return new Predicate<RequestHandler>() {

            @Override
            public boolean apply(RequestHandler input) {
                return declaringClass(input).transform(handlerPackage(basePackage)).or(true);
            }
        };
    }

    /**
     * 处理包路径配置规则,支持多路径扫描匹配以逗号隔开
     *
     * @param basePackage 扫描包路径
     * @return Function
     */
    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return new Function<Class<?>, Boolean>() {

            @Override
            public Boolean apply(Class<?> input) {
                for (String strPackage : basePackage.split(",")) {
                    boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                    if (isMatch) {
                        return true;
                    }
                }
                return false;
            }
        };
    }

    /**
     * @param input RequestHandler
     * @return Optional
     */
    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }

    /**
     * Swagger2创建该Api的基本信息
     *
     * @return ApiInfo
     */
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("移动端接口项目")
                .description("接口项目-接口说明文档")
                .termsOfServiceUrl("")
                .contact(new Contact("Jim.wang", "xxx@xxx.com", "xxx@xxx.com"))
                .version("1.0.0")
                .build();
    }

}