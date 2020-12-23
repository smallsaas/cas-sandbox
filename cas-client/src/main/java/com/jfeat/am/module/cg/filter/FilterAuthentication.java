package com.jfeat.am.module.cg.filter;

import com.jfeat.am.module.cg.config.WebMvcConfiguration;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FilterAuthentication {

    @Resource
    WebMvcConfiguration webMvcConfiguration;

    //<!--用来跳转登录-->
    @Bean
    public FilterRegistrationBean filterAuthenticationRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // AuthenticationFilter  该过滤器负责用户的认证工作
        registration.setFilter(new AuthenticationFilter());
        // 设定匹配的路径
        registration.addUrlPatterns("/*");
        Map initParameters = new HashMap();
        initParameters.put("casServerLoginUrl", webMvcConfiguration.getServerUrl()+"/cas/login");
        initParameters.put("serverName", webMvcConfiguration.getClientUrl());
        // 忽略 /logoutSuccess 的路径
        initParameters.put("ignorePattern", "/cas/client/auth/logout/success");
        registration.setInitParameters(initParameters);
        // 设定加载的顺序
        registration.setOrder(1);
        return registration;
    }

    // <!-- 单点登出过滤器-->
    @Bean
    public FilterRegistrationBean filterLogooutResistration(){
        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setFilter(new SingleSignOutFilter());
        registration.addUrlPatterns("/*");
        Map initParameters = new HashMap();
        initParameters.put("casServerUrlPrefix", webMvcConfiguration.getServerUrl()+"/cas/");
        registration.setInitParameters(initParameters);
        registration.setOrder(2);

        return registration;
    }






}
