package com.lrm.security.process;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @Describe: 类描述
 * @Author: tyf
 * @CreateTime: 2021/3/20
 **/

@Component
public class SecureSessionExpiredHandler implements SessionInformationExpiredStrategy {

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent eventØ) throws IOException, ServletException {
        eventØ.getResponse().sendError(499,"您的账号已在其他地方登录!");
    }
}
