package com.jfeat.am.module.cg.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import net.unicon.cas.client.configuration.CasClientConfigurerAdapter;
import net.unicon.cas.client.configuration.EnableCasClient;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCasClient
public class CasConfig extends CasClientConfigurerAdapter {

    @Value("${cas.server-url-prefix}")
    private String CAS_URL;

    @Value("${cas.client-host-url}")
    private String APP_URL;

    @Resource
    private WebMvcConfiguration webMvcConfiguration ;

    @Override
    public void configureAuthenticationFilter(FilterRegistrationBean authenticationFilter) {
        super.configureAuthenticationFilter(authenticationFilter);
        //authenticationFilter.getInitParameters().put("authenticationRedirectStrategyClass","com.patterncat.CustomAuthRedirectStrategy");
    }


    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean(){
        ServletListenerRegistrationBean  listenerRegistrationBean = new ServletListenerRegistrationBean();
        listenerRegistrationBean.setListener(new SingleSignOutHttpSessionListener());
        listenerRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return listenerRegistrationBean;
    }

    /**
     * 单点登录退出
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean singleSignOutFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new SingleSignOutFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter("casServerUrlPrefix", CAS_URL);
        registrationBean.setName("CAS Single Sign Out Filter");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    /**
     * 单点登录认证
     * @return
     */
    @Bean
    public FilterRegistrationBean AuthenticationFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new AuthenticationFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("CAS Filter");
        registrationBean.addInitParameter("casServerLoginUrl", CAS_URL);
        registrationBean.addInitParameter("serverName", APP_URL);
        registrationBean.setOrder(3);
        return registrationBean;
    }

    /**
     * 单点登录请求包装
     * @return
     */
    @Bean
    public FilterRegistrationBean httpServletRequestWrapperFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new HttpServletRequestWrapperFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("CAS HttpServletRequest Wrapper Filter");
        registrationBean.setOrder(5);
        return registrationBean;
    }

    //登出
/*    @RequestMapping("/logout")
    public void logout(HttpSession session,  HttpServletResponse response){
        session.invalidate();
        try {
            response.sendRedirect(webMvcConfiguration.getServerUrl()+
                    "/cas/logout?service="+
                    webMvcConfiguration.getSuccessUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/



}

