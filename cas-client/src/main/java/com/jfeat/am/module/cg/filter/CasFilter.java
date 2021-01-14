package com.jfeat.am.module.cg.filter;

import net.unicon.cas.client.configuration.CasClientConfigurationProperties;
import net.unicon.cas.client.configuration.CasClientConfigurer;
import net.unicon.cas.client.configuration.EnableCasClient;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.authentication.Saml11AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.servlet.Filter;
import java.util.*;

@Configuration
@EnableConfigurationProperties({CasClientConfigurationProperties.class})
public class CasFilter{

    @Autowired
    CasClientConfigurationProperties configProps;

    private CasClientConfigurer casClientConfigurer;



    @Bean
    public FilterRegistrationBean casAuthenticationFilter() {
        FilterRegistrationBean authnFilter = new FilterRegistrationBean();
        Filter targetCasAuthnFilter = this.configProps.getValidationType() != EnableCasClient.ValidationType.CAS && this.configProps.getValidationType() != EnableCasClient.ValidationType.CAS3 ? new Saml11AuthenticationFilter() : new AuthenticationFilter();

        List<String> casUrlParamNames = new ArrayList<>();
        casUrlParamNames.add("casServerLoginUrl");
        casUrlParamNames.add("ignorePattern");
        List<String> values = new ArrayList<>();
        values.add(this.configProps.getServerLoginUrl());
        values.add("/client/*");
        this.initFilter(authnFilter, (Filter)targetCasAuthnFilter, 4, this.constructInitParams(
                casUrlParamNames
                ,values, this.configProps.getClientHostUrl())
                , this.configProps.getAuthenticationUrlPatterns());

        if (this.configProps.getGateway() != null) {
            authnFilter.getInitParameters().put("gateway", String.valueOf(this.configProps.getGateway()));
        }

        if (this.casClientConfigurer != null) {
            this.casClientConfigurer.configureAuthenticationFilter(authnFilter);
        }

        return authnFilter;
    }

    private Map<String, String> constructInitParams(String casUrlParamName, String casUrlParamVal, String clientHostUrlVal) {
        Map<String, String> initParams = new HashMap(2);
        initParams.put(casUrlParamName, casUrlParamVal);
        initParams.put("serverName", clientHostUrlVal);
        return initParams;
    }

    private Map<String, String> constructInitParams(List<String> casUrlParamNames, List<String> casUrlParamVals, String clientHostUrlVal) {
        Map<String, String> initParams = new HashMap(2);
        for (int i = 0;i<casUrlParamNames.size();i++){
            initParams.put(casUrlParamNames.get(i), casUrlParamVals.get(i));
        }

        initParams.put("serverName", clientHostUrlVal);
        return initParams;
    }

    private void initFilter(FilterRegistrationBean filterRegistrationBean, Filter targetFilter, int filterOrder, Map<String, String> initParams, List<String> urlPatterns) {
        filterRegistrationBean.setFilter(targetFilter);
        filterRegistrationBean.setOrder(filterOrder);
        filterRegistrationBean.setInitParameters(initParams);
        if (urlPatterns.size() > 0) {
            filterRegistrationBean.setUrlPatterns(urlPatterns);
        }

    }


    @Autowired(
            required = false
    )
    void setConfigurers(Collection<CasClientConfigurer> configurers) {
        if (!CollectionUtils.isEmpty(configurers)) {
            if (configurers.size() > 1) {
                throw new IllegalStateException(configurers.size() + " implementations of CasClientConfigurer were found when only 1 was expected. Refactor the configuration such that CasClientConfigurer is implemented only once or not at all.");
            } else {
                this.casClientConfigurer = (CasClientConfigurer)configurers.iterator().next();
            }
        }
    }

}
