package com.lrm.po;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Describe: 用户注册信息pojo
 * @Author: tyf
 * @CreateTime: 2021/3/15
 **/

@Data
@Table(name = "t_user_register")
@ToString
@Entity
public class UserRegister {
    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户Username
     */
    @Id
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
