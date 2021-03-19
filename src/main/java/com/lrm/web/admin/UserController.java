package com.lrm.web.admin;

import com.lrm.po.User;
import com.lrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @Describe: 用户接口
 * @Author: tyf
 * @CreateTime: 2021/3/16
 **/

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * TODO:更新用户操作
     * @param user
     * @param authentication
     * @return
     */
    @PostMapping("/updateInfo")
    public String updateInfo(@Valid User user, Authentication authentication){
        userService.updateInfo(user);
        return "redirect:/";
    }

}
