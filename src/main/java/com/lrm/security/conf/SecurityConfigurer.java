package com.lrm.security.conf;

import com.lrm.security.process.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Describe: Spring Security的配置类
 * @Author: tyf
 * @CreateTime: 2021/3/13
 **/

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    SecureAuthenticationSuccessHandler successHandler;

    @Autowired
    SecureAuthenticationFailureHandler failureHandler;

    @Autowired
    SecureLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    SecureAuthenticationEntryPoint entryPoint;
    
    @Autowired
    SecureSessionExpiredHandler secureSessionExpiredHandler;

    private static final String[] NO_AUTH_LIST = {
            "/",
            "/webjars/**",
            "/favicon.ico",
            "/images/**",
            "/lib/**",
            "/css/**",
            "/types/**",
            "/tags/**",
            "/search/**",
            "/blog/**",
            "/footer/**",
            "/archives/**",
            "/about/**",
            "/admin/error",
            "/comments/**"
    };

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable().cors()
               //处理登入
               .and()
                   .formLogin().loginPage("/admin").loginProcessingUrl("/login").permitAll()
                   .successHandler(successHandler)
                   .failureHandler(failureHandler)
               //处理登出
               .and()
                   .logout().logoutUrl("/admin/logout").permitAll()
                   .addLogoutHandler(logoutSuccessHandler)
                   .logoutSuccessUrl("/admin/logoutSuccess")
               .and().exceptionHandling()
                    .authenticationEntryPoint(entryPoint)
               .and()
                    .sessionManagement().maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
                    .expiredSessionStrategy(secureSessionExpiredHandler)
               .and()
               .and().authorizeRequests().antMatchers(NO_AUTH_LIST).permitAll().
               anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
