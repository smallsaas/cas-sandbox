package com.jfeat.am.module.cg.filter;

import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Configuration
public class RemoteLoginFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("----login filter----");
        //todo 获取登录用户信息
        Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
        //获取登录用户名
        String loginName = assertion.getPrincipal().getName();
        Map<String, Object> attributes = assertion.getPrincipal().getAttributes();
        System.out.println("loginName"+loginName);
        System.out.println("attributes --");
        for (String key:attributes.keySet()){
            System.out.println("attributes:"+"key:"+key+"  value: "+attributes.get(key));
        }
        //todo 检查远程登录情况  远端登录情况方法 或者 自己设置session
        // （cas通过session判断，但是这里用不了 原因：成功session就会变成已登录的标识，而这里获取用户信息必须要已登录的情况下）

        //todo 远程登录 发送信息调用api登录


        //继续执行接下来的代码
        filterChain.doFilter(request,response);
    }
}
