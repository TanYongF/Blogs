package com.lrm.security.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/3/14
 **/
@Component
public class SecureLogoutSuccessHandler implements LogoutHandler {

    private boolean invalidateHttpSession = true;

    private boolean clearAuthentication = true;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //销毁Session
        if (invalidateHttpSession){
            HttpSession session = request.getSession(false);
            if (session != null){
                session.invalidate();
            }
        }
        //清空Authentication
        if (clearAuthentication){
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(null);
        }
        SecurityContextHolder.clearContext();
        logger.info("已经注销");
    }


    public void setInvalidateHttpSession(boolean invalidateHttpSession) {
        this.invalidateHttpSession = invalidateHttpSession;
    }

    public void setClearAuthentication(boolean clearAuthentication) {
        this.clearAuthentication = clearAuthentication;
    }
}