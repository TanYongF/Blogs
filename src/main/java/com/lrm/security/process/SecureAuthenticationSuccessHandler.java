package com.lrm.security.process;

import com.lrm.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Describe:   自定义 Security 用户未登陆成功类
 * @Author:     tyf
 * @CreateTime: 2021/3/14
 **/

@Component
public class SecureAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        logger.info("用户" + user.getUsername() + "登录");
        httpServletResponse.sendRedirect("/admin");
    }
}