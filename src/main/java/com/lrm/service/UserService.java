package com.lrm.service;

import com.lrm.po.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by limi on 2017/10/15.
 */
public interface UserService extends UserDetailsService{

    User checkUser(String username, String password);

    /**
     *
     * @param user           用户对象
     * @return              是否成功注册\
     * TODO:完善一下注册接口
     */
    Boolean registerUsr(User user);

    User findUserByUsername(String username);

    User updateInfo(User user);

}
