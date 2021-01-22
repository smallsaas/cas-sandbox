package com.jfeat.handler;


import com.jfeat.Md5Utils;
import com.jfeat.config.MysqlConfig;
import com.jfeat.model.SysUser;
import org.apereo.cas.authentication.*;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.configuration.CasConfigurationProperties;
import org.apereo.cas.services.ServicesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;
import javax.security.auth.login.FailedLoginException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.GeneralSecurityException;
import java.util.*;


@Configuration("CustomerHandlerAuthentication")
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class CustomerHandlerAuthentication extends AbstractPreAndPostProcessingAuthenticationHandler  {

    private static final Logger logger = LoggerFactory.getLogger(CustomerHandlerAuthentication.class);

    @Resource
    MysqlConfig mysqlConfig;


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

        //清除session和Cookie
        /*invalidateCookieAndSession();*/

        logger.info("username:{}",username);
        logger.info("password:{}",password);

        SysUser sysUser = getSysUser(username);
        if(sysUser==null){
            throw new FailedLoginException("用户不存在");
        }
        //
        if(!checkPassword(password,sysUser.getSalt(),sysUser.getPassword())){
            throw new FailedLoginException("密码错误");
        }

        final List<MessageDescriptor> list = new ArrayList<>();
        // 返回给客户端的属性信息
        HashMap<String, Object> returnInfo = new HashMap<>();
        //returnInfo.put("expired", info.getDisabled());
        returnInfo.put("email", sysUser.getEmail());
        returnInfo.put("username", sysUser.getName());
        returnInfo.put("password", sysUser.getPassword());
        returnInfo.put("phone", sysUser.getPhone());
        returnInfo.put("account", sysUser.getAccount());

        AuthenticationHandlerExecutionResult AHER = createHandlerResult(usernamePasswordCredentia,
                this.principalFactory.createPrincipal(username, returnInfo), list);
        return AHER;
    }

    private SysUser getSysUser(String username){
        // JDBC模板依赖于连接池来获得数据的连接，所以必须先要构造连接池
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        String url = mysqlConfig.getUrl();
        String userName = mysqlConfig.getUsername();
        System.out.println("=================DataBase INFO====================");
        System.out.println(url);
        System.out.println(userName);

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(mysqlConfig.getPassword());

        // 创建JDBC模板
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);

        String sql = mysqlConfig.getSql();
        System.out.println("sql:"+sql);
        System.out.println("username:"+username);
        SysUser info = (SysUser) jdbcTemplate.queryForObject(sql, new Object[]{username}, new BeanPropertyRowMapper(SysUser.class));

        return info;
    }

    Boolean checkPassword(String password,String salt,String sqlPassword){
        String passwordMd5 = Md5Utils.md5(password,salt);
        return sqlPassword.equals(passwordMd5);
    }

    public void invalidateCookieAndSession(){

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpServletResponse response =((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies){
            String name = cookie.getName();
            if(name!=null && name.equals("TGC")){
                cookie.setMaxAge(0);
                cookie.setPath("/cas/");
                response.addCookie(cookie);
            }
        }
        HttpSession requestSession = request.getSession();
        if(requestSession!=null){
            requestSession.invalidate();
        }

    }

}
