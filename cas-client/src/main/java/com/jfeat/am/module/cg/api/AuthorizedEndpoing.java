package com.jfeat.am.module.cg.api;

import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.module.cg.config.WebMvcConfiguration;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/client/auth")
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
    public String test(HttpServletRequest request){
        //获取cas给我们传递回来的对象，这个东西放到了session中
        //session的 key是 _const_cas_assertion_
        Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
        //获取登录用户名
        String loginName = assertion.getPrincipal().getName();
        System.out.printf("登录用户名:%s\r\n", loginName);
        return "登录用户名:%s\r\n"+loginName;
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

    /**
     * 从kweb-cas中获取登录信息
     * @param request
     * @return
     */
    @GetMapping(value = {"/loginUserInfo"})
    @ResponseBody
    public String loginUserInfo(HttpServletRequest request){
        Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
        if(assertion!= null){
            AttributePrincipal principal = assertion.getPrincipal();
            String s = JSONObject.toJSONString(assertion);
            //获取自定义返回值的数据
            Principal principal2  = (AttributePrincipal) request.getUserPrincipal();
            if (request.getUserPrincipal() != null) {

                if (principal2 instanceof AttributePrincipal) {
                    //cas传递过来的数据
                    Map<String,Object> result =( (AttributePrincipal)principal).getAttributes();
                    for(Map.Entry<String, Object> entry :result.entrySet()) {
                        String key = entry.getKey();
                        Object val = entry.getValue();
                        System.out.printf("%s:%s\r\n",key,val);
                    }
                }
            }
            return s;
        }else {
            return null;
        }
    }

}
