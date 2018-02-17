package com.iris.controller;

/**
 * Created by lwrong on 2018/2/17.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iris.model.User;
import com.iris.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/userLev")
public class UserLevController {

    @Resource
    private IUserService userService;

    @RequestMapping("/showUser.do")
    public void User(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        long userId = Long.parseLong(request.getParameter("id"));
        User user = this.userService.selectUser(userId);

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(user));
        response.getWriter().close();
    }
    @RequestMapping("/userLogin")
    public void userLogin(HttpServletRequest request, HttpServletResponse response)throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

    }
}