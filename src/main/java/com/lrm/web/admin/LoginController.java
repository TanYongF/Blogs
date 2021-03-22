package com.lrm.web.admin;

import com.lrm.po.User;
import com.lrm.po.UserRegister;
import com.lrm.po.WebsiteInfo;
import com.lrm.service.UserService;
import com.lrm.service.WebsiteInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Describe: 登录接口
 * @Author: tyf
 * @CreateTime: 2021/3/15
 **/

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private WebsiteInfoService websiteInfoService;

    /**
     * 登录页面
     * @param session               用户Session
     * @param authentication        用户验证信息
     * @return                      返回视图
     */
    @GetMapping
    public String indexPage(Authentication authentication,
                            HttpSession session,
                            ModelMap modelMap,
                            @ModelAttribute(value = "message")String message) {
        if(authentication == null){
            if (message.isEmpty()){
                message = "欢迎您的登录";
            }
            modelMap.addAttribute("message",message);
            return "admin/login";
        }
        User principal = (User) authentication.getPrincipal();
        User user = (User) userService.loadUserByUsername(principal.getUsername());
        //设置密码为空
        user.setPassword("\n");
        Assert.notNull(user,"不为空!");
        session.setAttribute("user", user);
        session.setAttribute("aboutMeImageUrl", websiteInfoService.getAboutMeImageUrl());
        session.setAttribute("topTitle", websiteInfoService.getTopTitle());
        session.setAttribute("topTitle", websiteInfoService.getTopTitle());
        session.setAttribute("aboutMeContent", websiteInfoService.getAboutMeContent());
        return "admin/index";
    }


    /**
     * 登录失败页面
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/error")
    public String falseToLogin(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message","用户密码错误,请重新登录!");
        return "redirect:/admin";
    }

    /**
     * 用户登出成功接口
     * @param redirectAttributes        转发属性
     * @return
     */
    @GetMapping("/logoutSuccess")
    public String logoutSuccess(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message", "您已成功登出!");
        return "redirect:/admin";
    }

    /**
     * 用户注册
     * @param userT0register        封装的用户注册信息
     * @return                      返回结果
     * TODO:完善登录接口
     */
    public String register(@RequestParam UserRegister userT0register){
        return null;
    }

}
