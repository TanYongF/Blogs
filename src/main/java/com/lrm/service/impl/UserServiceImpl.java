package com.lrm.service.impl;

import com.lrm.dao.UserRepository;
import com.lrm.po.User;
import com.lrm.service.UserService;
import com.lrm.util.MD5Utils;
import com.lrm.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by limi on 2017/10/15.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }

    /**
     * UserDetailService的实现类方法
     * @param s                             用户名
     * @return                              User实体类
     * @throws UsernameNotFoundException    未找到用户错误
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(s);
        if (user == null){
            throw new UsernameNotFoundException("用户"+ s + "不存在!");
        }
        //密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //TODO:处理权限

        //TODO:处理角色
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Transactional
    @Override
    public User updateInfo(User user) {
        User byUserName = userRepository.findByUserName(user.getUsername());
        BeanUtils.copyProperties(user,byUserName, MyBeanUtils.getNullPropertyNames(user));
        userRepository.save(byUserName);
        return byUserName;
    }


    @Override
    public Boolean registerUsr(User user){
        return false;
    }
}
