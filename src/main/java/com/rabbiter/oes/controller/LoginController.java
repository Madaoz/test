package com.rabbiter.oes.controller;

import com.rabbiter.oes.entity.*;
import com.rabbiter.oes.serviceimpl.LoginServiceImpl;
import com.rabbiter.oes.util.ApiResultHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @Autowired
    private LoginServiceImpl loginService;

    /**
     * 用户使用8位员工编号登录或用uass编号登录
     * @param login
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/login")
    public ApiResult login(@RequestBody Login login, HttpServletRequest request, HttpServletResponse response) {
        logger.info("============用户登录==============");
        String userId = login.getUsername();
        String passWord = login.getPassword();
        String useruass = login.getUsername();
        //用户使用8为员工编号登录
        User user1 = loginService.userLoginById(userId,passWord);
        //用户使用uass登录
        User user2 = loginService.userLoginByUass(useruass,passWord);
        if(user1 != null){
            Cookie token1 = new Cookie("rb_token",user1.getUserId());
            token1.setPath("/");
            Cookie role1 = new Cookie("rb_role",user1.getRole());
            role1.setPath("/");
            //将cookie对象加入response响应
            response.addCookie(token1);
            response.

                    addCookie(role1);
            logger.info("=================使用8位编号登录成功============");
            return ApiResultHandler.buildApiResult(200, "请求成功", user1);
        }
        else if(user2 != null){
            Cookie token2 = new Cookie("rb_token",user2.getUserId());
            token2.setPath("/");
            Cookie role2 = new Cookie("rb_role",user2.getRole());
            role2.setPath("/");

            //将cookie对象加入response响应
            response.addCookie(token2);
            response.addCookie(role2);
            logger.info("=============使用uass登录成功================");
            return ApiResultHandler.buildApiResult(200, "请求成功", user2);

        }
        return ApiResultHandler.buildApiResult(400, "请求失败", null);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        logger.info("==================用户退出登录==============");
        Cookie token = new Cookie("rb_token", null);
        token.setPath("/");
        token.setMaxAge(0);
        Cookie role = new Cookie("rb_role", null);
        role.setPath("/");
        role.setMaxAge(0);
        response.addCookie(token);
        response.addCookie(role);
    }
}
