package com.jfeat.am.module.cg.api;

import com.jfeat.am.module.cg.config.WebMvcConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/cas/client/auth")
public class AuthorizedEndpoing {

    @Resource
    private WebMvcConfiguration webMvcConfiguration ;


    /**
     * 退出
     * @return
     */
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request,  HttpServletResponse response) {

        invalidateCookieAndSession(request,response);

        //使用cas退出成功后,跳转到CLIENT_URL
        try {
            response.sendRedirect(webMvcConfiguration.getServerUrl()+
                    "/cas/logout?service="+
                    webMvcConfiguration.getSuccessUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/test")
    public String test(){
        return "-----test-----";
    }

    /**
     * 退出成功页
     * @return
     */
    @RequestMapping("/logout/success")
    public String logoutsuccess(HttpServletRequest request,HttpServletResponse response) {
        invalidateCookieAndSession(request,response);
        return "info : logout/success--";
    }

    public void invalidateCookieAndSession(HttpServletRequest request,HttpServletResponse response){
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
