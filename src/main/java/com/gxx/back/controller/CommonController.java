package com.gxx.back.controller;

import com.gxx.back.bean.Menu;
import com.gxx.back.bean.User;
import com.gxx.back.common.BaseResponse;
import com.gxx.back.common.StatusCode;
import com.gxx.back.service.impl.MenuServiceImpl;
import com.gxx.back.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommonController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private MenuServiceImpl menuService;

    /**
     * session超时跳转至登录页
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/timeout")
    public void sessionTimeout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase(
                "XMLHttpRequest")) { // ajax 超时处理
            response.getWriter().print("timeout");  //设置超时标识
            response.getWriter().close();
        } else {
            response.sendRedirect("/back/views/login.html");
        }
    }

    /**
     * 登录成功后获取如登录用户信息，角色，菜单等基础信息
     * @return
     */
    @RequestMapping("/infoList")
    public BaseResponse loginSuccessInfo(HttpServletRequest request){
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        HttpSession session = request.getSession();
        try {
            String loginUserName = SecurityContextHolder.getContext().getAuthentication().getName();
            //通过登录用户名获取用户账号信息
            User user = userService.getUserInfo(loginUserName);
            session.setAttribute("userid",user.getUserId());
            session.setAttribute("username",user.getUserName());
            session.setAttribute("userrealname",user.getUserRealName());
            //通过用户id获取菜单列表
            List<Menu> menuList = menuService.getMenuListByUserId(user.getUserId());
            Map map = new HashMap();
            map.put("userrealname",user.getUserRealName());
            map.put("menuList",menuList);
            baseResponse.setData(map);
        }catch (Exception e) {
            e.printStackTrace();
            baseResponse = new BaseResponse(StatusCode.Fail);
        }

        return baseResponse;
    }
}
