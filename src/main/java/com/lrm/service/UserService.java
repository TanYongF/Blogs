package com.lrm.service;

import com.lrm.po.User;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by limi on 2017/10/15.
 */
public interface UserService extends UserDetailsService{

    User checkUser(String username, String password);

    Boolean registerUsr(User user);

    User findUserByUsername(String username);
}
