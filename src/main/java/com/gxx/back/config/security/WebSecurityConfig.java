package com.gxx.back.config.security;

import com.gxx.back.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * 配置类：
 * 重写它的方法来设置一些web安全的细节,如配置security的登录页面和传递的参数，公共路径权限属性等
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true) //控制到请求方法级别
@EnableWebSecurity //禁用boot默认的Security配置
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //自定义认证对象
    @Autowired
    private UserDetailInfo userDetailInfo;

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        http.headers().frameOptions().sameOrigin();//允许iframe

        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("USER_ADMIN") //仅管理员有该目录权限
                .antMatchers("/views/login.html","/timeout","/layuiadmin/**","/views/show/**","/layui/**","/article/**","/articleSort/**").permitAll() //全开放
                .anyRequest().authenticated();//其他需要登录后才可访问

        http.formLogin()
                .loginPage("/views/login.html") //登录页面
                .loginProcessingUrl("/login") //发送ajax请求的路径
                .usernameParameter("username")
                .passwordParameter("password")
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        String result = "{\"status\":\"error\",\"msg\":\"登录失败\"}";
                        if (exception.getMessage().equals("用户不存在")||exception.getMessage().equals("Bad credentials")){
                            result = "{\"status\":\"error\",\"msg\":\"用户名或密码错误\"}";
                            response.setContentType("application/json;charset=utf-8");
                            PrintWriter out = response.getWriter();
                            out.write(result);
                            out.flush();
                            out.close();
                        }
                    }
                })
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        out.write("{\"status\":\"success\",\"msg\":\"登录成功\"}");
                        out.flush();
                        out.close();
                    }
                })
                .permitAll();

        http.logout()
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.setContentType("application/json;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        out.write("{\"status\":\"success\",\"msg\":\"登出成功\"}");
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .csrf().disable()
                .exceptionHandling().accessDeniedPage("/403.html")
                .and().sessionManagement().invalidSessionUrl("/timeout");
    }
    /*
     * 身份验证管理生成器。一定要重载！！！不然自定义的登陆校验不生效
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailInfo)
                .passwordEncoder(new PasswordEncoder() {
                    //可以自己定义密码匹配规则
                    @Override
                    public String encode(CharSequence rawPassword) {
                        return MD5Util.generate((String) rawPassword);
                    }

                    @Override
                    public boolean matches(CharSequence rawPassword, String encodedPassword) {
                        return MD5Util.verify((String)rawPassword, encodedPassword);
                    }
                });
    }
}
