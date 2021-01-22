package com.jfeat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("datasource")
public class MysqlConfig {

/*  public  String url="jdbc:mysql://localhost:3307/uaas-saas?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC";
    public  String username="root";
    public  String password="root";
    public  String sql="SELECT * FROM t_sys_user WHERE account = ?";*/
    public  String url;
    public  String username;
    public  String password;
    public  String sql="SELECT * FROM t_sys_user WHERE account = ?";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
