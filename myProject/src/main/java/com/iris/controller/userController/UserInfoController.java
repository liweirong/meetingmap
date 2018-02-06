package com.iris.controller.userController;

import com.iris.service.IUserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lwrong on 2018/2/6.
 */

@Controller
@RequestMapping("/user")
public class UserInfoController {

    @Resource
    private IUserInfoService userInfoService;

    @RequestMapping("/showUser.do")
    public void selectUser(HttpServletRequest request, HttpServletResponse response) {

    }
}
