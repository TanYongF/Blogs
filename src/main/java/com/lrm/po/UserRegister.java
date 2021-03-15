package com.lrm.po;

import lombok.Data;

import javax.persistence.Entity;

/**
 * @Describe: 用户注册信息pojo
 * @Author: tyf
 * @CreateTime: 2021/3/15
 **/

@Data
public class UserRegister {
    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户Username
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户nickName
     */
    private String nickName;


}
