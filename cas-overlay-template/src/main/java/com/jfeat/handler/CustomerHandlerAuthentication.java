package com.jfeat.handler;

import org.apereo.cas.authentication.*;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.FailedLoginException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Configuration("CustomerHandlerAuthentication")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class CustomerHandlerAuthentication extends AbstractPreAndPostProcessingAuthenticationHandler  {

    private static final Logger logger = LoggerFactory.getLogger(CustomerHandlerAuthentication.class);

    @Override
    public boolean supports(Credential credential) {
        //判断传递过来的Credential 是否是自己能处理的类型
        return credential instanceof UsernamePasswordCredential;
    }

    public CustomerHandlerAuthentication(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential) throws GeneralSecurityException, PreventedException {


        UsernamePasswordCredential usernamePasswordCredentia = (UsernamePasswordCredential) credential;

        String username = usernamePasswordCredentia.getUsername();
        String password = usernamePasswordCredentia.getPassword();
        if(username.equals("error")){
            throw new FailedLoginException("Sorry, you are a failure! username is error");
        }

        logger.info("username:{}",username);
        logger.info("password:{}",password);



        final List<MessageDescriptor> list = new ArrayList<>();
        return createHandlerResult(usernamePasswordCredentia,
                this.principalFactory.createPrincipal(username, Collections.emptyMap()), list);
    }



}
